#include <iostream>
#include <vector>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <time.h>
#include <math.h>

#define MYSQLPP_MYSQL_HEADERS_BURIED
#include <mysql++.h>
#include <datetime.h>

#define MAX_STR_BUFFER_LEN	256

using namespace std;
using namespace mysqlpp;

typedef struct AccData *pAccData;

struct AccData {
	double x, y,z;
	int year;
	int mon, day;
	int hour, min, sec;
	time_t tstamp;
};

inline time_t StringToTimestamp ( const char* timeStr ) ;
inline void StringToAccData ( char line[], pAccData data ) ;
int readline ( FILE *in, char line[], int maxlen ) ;
bool TAS_Record ( pAccData data );
pAccData getMathMean ( void );
void connect ( void );
void commit ( pAccData data );
double radians (double d);

const char *server =	"localhost";
const char *db =		"SensorDB";
const char *userName =	"root";
const char *password =	"pE12_a";

mysqlpp::Connection *conn = NULL;
std::vector<pAccData> gpsBuffer;	// Used to accumulate all values for the same second and create AVG

inline void clearGpsBuffer ( void )
{
	for(uint32_t i=0; i<gpsBuffer.size(); i++) {
		delete gpsBuffer[i];
	}
	gpsBuffer.clear();
}

inline time_t StringToTimestamp (const char* timeStr)
{
	time_t sse;
	struct tm ctm;

	strptime(timeStr, "%Y-%m-%d %H:%M:%S", &ctm);
	sse = mktime(&ctm);
	return (sse);
}

bool updateBuffer ( pAccData data )
{
	if(!gpsBuffer.size()){
		gpsBuffer.push_back(data);
		return true;
	}
	double x=data->x;
	double y=data->y;
	double z=data->z;


	if( x<-0.5 && x>0.5 && y<-0.5 && y>0.5 && z<9.5 && z>10.5 ){

	cout<< "x\t" << x << endl;
	cout<< "y\t" << x << endl;
	cout<< "z\t" << x << endl;
		gpsBuffer.push_back(data);
		return true;
	}

	return false;
}

pAccData StringToAccData ( char line[] )
{
	struct tm ctm;
	pAccData data = new struct AccData;

	sscanf(line, "%lf\t%lf\t%lf\t%d-%d-%d %d:%d:%d",
			&data->x, &data->y,&data->z,
			&data->year, &data->mon, &data->day,
			&data->hour, &data->min, &data->sec
	);

	ctm.tm_sec = data->sec;         /* seconds */
	ctm.tm_min = data->min;         /* minutes */
	ctm.tm_hour = data->hour;       /* hours */
	ctm.tm_mday = data->day;        /* day of the month */
	ctm.tm_mon = data->mon-1;       /* month */
	ctm.tm_year = data->year-1900;  /* year */

	data->tstamp = mktime(&ctm);

	return data;
};

int readline ( FILE *in, char line[], int maxlen )
{
	char *p;
	int fix=0;

	p = fgets(line, maxlen, in);

	fix = strlen(line)-1;
	line[fix] = '\0';
	if (p == NULL)
		return (0);
	else
		return (fix-1);
}

void connect ( void )
{
	try {
		conn = new mysqlpp::Connection(true);
		conn->connect(db, server,userName, password);
	}
	catch (const mysqlpp::Exception& er) {
		cerr << "Unable to connect to DataBase. Check connection configuration." << endl;
		cerr << "Error: " << er.what() << endl;
		exit(1);
	}
}

void commit ( pAccData data )
{
	Query q = conn->query();
	mysqlpp::DateTime	dt(data->year,data->mon,data->day,data->hour,data->min,data->sec);

    q << "INSERT INTO AccStatistics VALUES ('arisha','" << dt << "'," << data->x << "," << data->y << "," << data->z << ")";

    q.execute();
}

void commit ( void )
{
	vector<pAccData>::iterator it;
	for ( it=gpsBuffer.begin() ; it < gpsBuffer.end(); it++ ){
		commit(*it);
	}
	clearGpsBuffer();
}

void parse_and_store ( FILE *in )
{
	char buffer[MAX_STR_BUFFER_LEN];
	pAccData data = NULL;

	while(1){
		if(!readline(in, buffer, MAX_STR_BUFFER_LEN))
			break;

		pAccData data = StringToAccData(buffer);
		updateBuffer(data);
	}

	commit();
}

int main (int argc, char *argv[])
{
	FILE *in = NULL;

	if(argc != 2) {
		std::cerr << "Wrong input. Please provide file to read." << endl;
		exit(-1);
	}

	in = fopen(argv[1], "r");
	if(in==NULL) {
		std::cerr << "File not exists or insufficient user privilleges." << endl;
		exit(-1);
	}

	connect();
	parse_and_store(in);
	conn->shutdown();
	return 0;
}

/*#include <iostream>
#include <vector>
#include <stdint.h>
#include <stdio.h>
#include <time.h>
#include <assert.h>

#define MYSQLPP_MYSQL_HEADERS_BURIED
#include <mysql++.h>
#include <datetime.h>

#include <stdlib.h>
#include <assert.h>

#define MAX_STR_BUFFER_LEN	256

using namespace std;
using namespace mysqlpp;

typedef struct AccData *pAccData;

struct AccData {
	double x, y, z;
	int year;
	int mon, day;
	int hour, min, sec;
	time_t tstamp;
};


inline time_t StringToTimestamp ( const char* timeStr ) ;
inline void StringToAccData ( char line[], pAccData data ) ;
int readline ( FILE *in, char line[], int maxlen ) ;
bool TAS_Record ( pAccData data );
pAccData getMathMean ( void );
void connect ( void );
void commit ( pAccData data );


const char *server =	"localhost";
const char *db =		"SensorDB";
const char *userName =	"root";
const char *password =	"pE12_a";

mysqlpp::Connection *conn = NULL;
std::vector<pAccData> tmpData;	// Used to accumulate all values for the same second and create AVG

// Return true is data is set succesfully and false if timestamp clustering not applicable.
bool TAS_Record ( pAccData data )
{
	if(!tmpData.size()){
		tmpData.push_back(data);
		return true;
	}

	if(tmpData[0]->tstamp != data->tstamp)
		return false;

	tmpData.push_back(data);
	return true;
}

pAccData getMathMean ( void )
{
	pAccData data = new struct AccData;

	assert(tmpData.size());

	data = (struct AccData *)memcpy(data, tmpData[0], sizeof(struct AccData));
	assert(data != NULL);

	for(uint16_t i=1; i<tmpData.size(); i++) {
		data->x += tmpData[i]->x;
		data->y += tmpData[i]->y;
		data->z += tmpData[i]->z;
	}

	data->x /= (double)tmpData.size();
	data->y /= (double)tmpData.size();
	data->z /= (double)tmpData.size();

	return data;
}

inline void clearTmpData ( void )
{
	for(uint16_t i=0; i<tmpData.size(); i++) {
		delete tmpData[i];
	}
	tmpData.clear();
}

inline time_t StringToTimestamp (const char* timeStr)
{
	time_t sse;
	struct tm ctm;

	strptime(timeStr, "%Y-%m-%d %H:%M:%S", &ctm);
	sse = mktime(&ctm);
	return (sse);
}

inline void StringToAccData ( char line[], pAccData data )
{
	struct tm ctm;

	sscanf(line, "%lf\t%lf\t%lf\t%d-%d-%d %d:%d:%d",
			&data->x, &data->y, &data->z,
			&data->year, &data->mon, &data->day,
			&data->hour, &data->min, &data->sec
	);

	ctm.tm_sec = data->sec;
	ctm.tm_min = data->min;
	ctm.tm_hour = data->hour;
	ctm.tm_mday = data->day;
	ctm.tm_mon = data->mon-1;
	ctm.tm_year = data->year-1900;

	data->tstamp = mktime(&ctm);
};

int readline ( FILE *in, char line[], int maxlen )
{
	char *p;
	int fix=0;

	p = fgets(line, maxlen, in);

	fix = strlen(line)-1;
	line[fix] = '\0';
	if (p == NULL)
		return (0);
	else
		return (fix-1);
}

void connect ( void )
{
	try {
		conn = new mysqlpp::Connection(true);
		conn->connect(db, server,userName, password);
	}
	catch (const mysqlpp::Exception& er) {
		cerr << "Unable to connect to DataBase. Check connection configuration." << endl;
		cerr << "Error: " << er.what() << endl;
		exit(1);
	}
}

void commit ( pAccData data )
{
	Query q = conn->query();
	mysqlpp::DateTime	dt(data->year,data->mon,data->day,data->hour,data->min,data->sec);

    q << "INSERT INTO AccStatistics VALUES ('arisha','" << dt << "'," << data->x << "," << data->y << "," << data->z << ")";

    q.execute();
}

void parse_and_store ( FILE *in )
{
	char buffer[MAX_STR_BUFFER_LEN];

	while(1){
		if(!readline(in, buffer, MAX_STR_BUFFER_LEN))
			break;

		pAccData data = new struct AccData;

		StringToAccData(buffer, data);
		if(!TAS_Record(data)){
			pAccData tmp = getMathMean();
			clearTmpData();
			TAS_Record(data);
			commit(tmp);
			delete tmp;
		}
	}

	if(tmpData.size()){
		pAccData tmp = getMathMean();
		clearTmpData();
		commit(tmp);
		delete tmp;
	}
}

int main (int argc, char *argv[])
{
	FILE *in = NULL;

	if(argc != 2) {
		std::cerr << "Wrong input. Please provide file to read." << endl;
		exit(-1);
	}

	in = fopen(argv[1], "r");
	if(in==NULL) {
		std::cerr << "File not exists or insufficient user privilleges." << endl;
		exit(-1);
	}

	connect();
	parse_and_store(in);
	conn->shutdown();
	return 0;
}
*/
