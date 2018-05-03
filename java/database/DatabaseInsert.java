/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author eirini
 */
import HandleData.Member;
import HandleData.Questionary;
import HandleData.GalleryPicture;
import HandleData.LightData;
import java.util.Date;
import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;





public class DatabaseInsert extends Database{
 /*************************** Login form *************************************************************/   
        public boolean checkPassword(String Login, String Password) {
        String sqlStmt ;
        boolean state = false;

        try {
           
            this.connect();
            sqlStmt = "SELECT login FROM `SensorDB`.`UserData` "
                    + "WHERE login = '" + Login
                    + "' AND password = '" + Password + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            state = (res.next()) ? true : false;
            if(!res.isClosed())
                res.close();
            this.close();
            return state;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return state;
        }
    }
         public void UpdateLastLogin(String Login) throws SQLException {
        String sqlStmt ;
        boolean state = false;

       
           
            this.connect();
            java.util.Date date= new java.util.Date(); 

            sqlStmt = "UPDATE `SensorDB`.`UserData` SET `lastlogin`='"+new Timestamp(date.getTime())+"' WHERE `login`='"+Login+"'";
            statement.executeUpdate(sqlStmt);
           
         
            
    }

    public boolean checkValid(String Login) {
        String sqlStmt;
        boolean state = true;

        try {
            this.connect();
            sqlStmt = "SELECT login FROM `SensorDB`.`UserData` "
                    + "WHERE login = '" + Login + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            state = (res.next()) ? false : true;
            if(!res.isClosed())
                res.close();
            this.close();
            return state;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return state;
        }
    }

     public String get_Node(String username) {
          
        String Node = null;
        String sqlStmt;
        
        try {
            this.connect();
            sqlStmt = "SELECT AdminNode FROM `SensorDB`.`UserData` "
                    + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);

            if(res.next()){
               Node= res.getString("AdminNode");
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Node;
    }
     public Timestamp get_Lastlogin(String username) {
          
        Timestamp time = null;
        String sqlStmt ;
        
        try {
            this.connect();
            sqlStmt = "SELECT lastlogin FROM `SensorDB`.`UserData` "
                    + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);

            if(res.next()){
               time = res.getTimestamp("lastlogin");
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return time;
    }
 /************************* Memeber ******************************************************/
     public boolean insertUserIntoBase(Member user){
        String sqlStmt ;
        PreparedStatement pstmt ;

        try {
            this.connect();
            connection.setAutoCommit(false);
            sqlStmt = "INSERT INTO `SensorDB`.`UserData` (login,password,fName,lName,email,sex,BirthDate,profession) "+ 
                    "VALUES (?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sqlStmt);
            
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassWord());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setString(5, user.getMail());
            pstmt.setString(6, user.getGender());
            pstmt.setTimestamp(7, user.getAge());
            pstmt.setString(8, user.getProfession());
             
            
            System.err.println(pstmt.toString());

            pstmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            pstmt.close();
            this.close();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
 /************************** EmotionQuestionary **************************************************/
       public boolean insertQuestionaryIntoBase(Questionary test){
        String sqlStmt ;
        PreparedStatement pstmt;

        try {
            this.connect();
            connection.setAutoCommit(false);
            sqlStmt = "INSERT INTO `SensorDB`.`Questionary` (login,sleepDateTime,wakeDateTime,socalization,energy,depression,activity,weight) "+ 
                    "VALUES (?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sqlStmt);
            
            pstmt.setString(1, test.getUserName());
            pstmt.setTimestamp(2, test.getsleepdate());
            pstmt.setTimestamp(3, test.getwakedate());
            pstmt.setInt(4,test.getSocial());
            pstmt.setInt(5, test.getEnergy());
            pstmt.setInt(6, test.getDepres());
            pstmt.setInt(7, test.getActivity());
            pstmt.setFloat(8,test.getweight());
             
            
            
            pstmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            pstmt.close();
            this.close();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
/*************************** Image Gallery (probably don't need) *******************************/
       public boolean insertPictureIntoGallery(GalleryPicture pic){
        String sqlStmt = null;
        PreparedStatement pstmt = null;

        try {
            this.connect();
            connection.setAutoCommit(false);
            
            sqlStmt =  "UPDATE `SensorDB`.`UserData` SET Picture = ? WHERE login = '" + pic.getUserName() +"'";
            pstmt = connection.prepareStatement(sqlStmt);
            System.out.println("eimaisto insert"+pic.getUserName());
            
            if(pic.getPicture()!=null && pic.getPicture().length>0){
                pstmt.setBinaryStream(1, new ByteArrayInputStream(pic.getPicture()), pic.getPicture().length);
            }
            pstmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            pstmt.close();
            this.close();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
 public boolean updateAdminNode(String login,String node){
        String sqlStmt = null;
        PreparedStatement pstmt = null;

        try {
            this.connect();
            connection.setAutoCommit(false);
            
            sqlStmt =  "UPDATE `SensorDB`.`UserData` SET AdminNode = ? WHERE login = '" + login +"'";
            pstmt = connection.prepareStatement(sqlStmt);
            
            
           pstmt.setString(1,node);
            pstmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            pstmt.close();
            this.close();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

   public boolean insertAdminPictureIntoGallery(GalleryPicture pic){
        String sqlStmt = null;
        PreparedStatement pstmt = null;

        try {
            this.connect();
            connection.setAutoCommit(false);
            sqlStmt = "INSERT INTO `SensorDB`.`Gallery`(login,GraphUUID,Picture,Permission)" +
                    "VALUES (?,?,?,?)";
            pstmt = connection.prepareStatement(sqlStmt);
            
            pstmt.setString(1, pic.getUserName());
            pstmt.setString(2, pic.getPictureUUID().toString());

            if(pic.getPicture()!=null && pic.getPicture().length>0){
                pstmt.setBinaryStream(3, new ByteArrayInputStream(pic.getPicture()), pic.getPicture().length);
            }

            pstmt.setInt(4, pic.getPermissions());

            pstmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            pstmt.close();
            this.close();
            return true;
        }
        catch (SQLException ex) {
           Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String[] getPhotoGalleryUUIDs(String UserName){
        Vector<String> uuids = new Vector<String>();
        String sqlStmt = null;

        try {
            this.connect();
            sqlStmt = "SELECT GraphUUID FROM `SensorDB`.`Gallery`  "
                    + "WHERE login = '" + UserName + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            while(res.next()){
                String tmp = res.getString("GraphUUID");
                uuids.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(uuids.size()>0){
            String tmp[] = new String[uuids.size()];
            return uuids.toArray(tmp);
        } else {
            return null;
        }
    }
    public String[] getPhotoGalleryUUIDs_date(String date) throws ParseException{
        Vector<String> uuids = new Vector<String>();
        String sqlStmt = null;
       
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           
             
            Calendar cal = Calendar.getInstance();
            cal.setTime( dateFormat.parse( date) );
            cal.add( Calendar.DATE, 1 );
            java.util.Date parsedDate2=cal.getTime();
             java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());  
           
        try {
            this.connect();
            PreparedStatement p = connection.prepareStatement ("SELECT GraphUUID FROM `SensorDB`.`Gallery` WHERE Data > ? and  Data < ? ");
            p.setTimestamp(1, timestamp);
            p.setTimestamp(2, timestamp2);
            ResultSet res = p.executeQuery();
            while(res.next()){
                String tmp = res.getString("GraphUUID");
                uuids.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(uuids.size()>0){
            String tmp[] = new String[uuids.size()];
            return uuids.toArray(tmp);
        } else {
            return null;
        }
        
    }
    
    public String[] getPhotoGalleryUUIDs_period(String date1,String date2) throws ParseException{
        Vector<String> uuids = new Vector<String>();
        String sqlStmt = null;
        //TODO NA KANW CHECK ANA DWSANE MIKROT HMER PRWTH
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date1);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
            
             
           java.util.Date parsedDate2 = dateFormat.parse(date2);
            java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());  
           
        try {
            this.connect();
            PreparedStatement p = connection.prepareStatement ("SELECT GraphUUID FROM `SensorDB`.`Gallery` WHERE Data BETWEEN ? and  ? ");
            p.setTimestamp(1, timestamp);
            p.setTimestamp(2, timestamp2);
            ResultSet res = p.executeQuery();
            while(res.next()){
                String tmp = res.getString("GraphUUID");
                uuids.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(uuids.size()>0){
            String tmp[] = new String[uuids.size()];
            return uuids.toArray(tmp);
        } else {
            return null;
        }
        
    }
 
/*********************************    Latitude for Map              ***********************************************/
     public Float[] get_Latitude_Day(String date,String username) throws ParseException{
         
        Vector<Float> Latitude = new Vector<Float>();
        String sqlStmt ;
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
              
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);
           
        try {
           this.connect();
            sqlStmt = "SELECT Latitude FROM `SensorDB`.`MapData`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("Latitude");
                Latitude.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(Latitude.size()>0){
            return Latitude.toArray(new Float[Latitude.size()]);
        } else {
            return null;
        }
        
    }
/**********************************  Longtitude for Map           ********************************************************/
     public Float[] get_Longtitude_Day(String date,String username) throws ParseException{
         
        Vector<Float> Longtitude = new Vector<Float>();
        String sqlStmt ;
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
              
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);
           
        try {
           this.connect();
            sqlStmt = "SELECT Longtiude FROM `SensorDB`.`MapData`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("Longtiude");
                Longtitude.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(Longtitude.size()>0){
            return Longtitude.toArray(new Float[Longtitude.size()]);
        } else {
            return null;
        }
        
    }
/******************************* Map time ***************************************************/
 public Timestamp[] get_Map_Time_Day(String date,String username) throws ParseException{
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
     
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);
           
        try {
           this.connect();
            sqlStmt = "SELECT Date FROM `SensorDB`.`MapData`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("Date");
                time.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }

/******************************* Longtitude Period *******************************************/
      public Float[] get_Longtitude_Period(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
    
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

     
        try {
           this.connect();
            sqlStmt = "SELECT Longtiude FROM `SensorDB`.`MapData`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3+ "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("Longtiude");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
/******************************   Latitude Period ******************************************/
     public Float[] get_Latitude_Period(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

          
          
           
        try {
           this.connect();
            sqlStmt = "SELECT Latitude FROM `SensorDB`.`MapData`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("Latitude");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
 /*************************** Map Time Period **************************************************/
     public Timestamp[] get_Map_Time_Period(String startdate,String enddate,String username) throws ParseException{
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       
           long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT Date FROM `SensorDB`.`MapData`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("Date");
                time.add(tmp);
                
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
 /**************************** LightData for Day   ******************************************************/
     public Float[] get_LightData_Day(String date,String username) throws ParseException{
         
        Vector<Float> uuids = new Vector<Float>();
        String sqlStmt ;
       
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);
           
        try {
           this.connect();
            sqlStmt = "SELECT LightData FROM `SensorDB`.`LightStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("LightData");
                uuids.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(uuids.size()>0){
            return uuids.toArray(new Float[uuids.size()]);
        } else {
            return null;
        }
        
    }
/******************************* Time for LightData Day*****************************************************************/
     public Timestamp[] get_LightData_Time_Day(String date,String username) throws ParseException{
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);
           
        try {
           this.connect();
            sqlStmt = "SELECT Date FROM `SensorDB`.`LightStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("Date");
                time.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
/*************************   LightData Period       *********************************************/
public Float[] get_LightData_Period(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        
           
        try {
           this.connect();
             sqlStmt = "SELECT LightData FROM `SensorDB`.`LightStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("LightData");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
/********************************   Time for LightData Period              ****************************************/
     public Timestamp[] get_LightData_Time_Period(String startdate,String enddate,String username) throws ParseException{
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       
           long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT Date FROM `SensorDB`.`LightStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("Date");
                time.add(tmp);
                
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
/*********************************** Accelerometr Data Day **************************************************/
      public Float[] get_Acc_X_Day(String date,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT x FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("x");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
      public Float[] get_Acc_Y_Day(String date,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT y FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("y");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
      public Float[] get_Acc_Z_Day(String date,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT z FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("z");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
        public Timestamp[] get_AccData_Time_Day(String date,String username) throws ParseException{
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate = dateFormat.parse(date);
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);
           
        try {
           this.connect();
            sqlStmt = "SELECT Date FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("Date");
                time.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
/************************Form Statistic***********************************/
    public Integer[] get_EnergyLevel(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Integer> data = new Vector<Integer>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT energy FROM `SensorDB`.`Questionary`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("energy");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Integer[data.size()]);
        } else {
            return null;
        }
        
    }   
        public Timestamp[] get_Questionary_Time_Period(String startdate,String enddate,String username) throws ParseException{
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       
           long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT date FROM `SensorDB`.`Questionary` "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("date");
                time.add(tmp);
                
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
    public Integer[] get_ActivityLevel(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Integer> data = new Vector<Integer>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT activity FROM `SensorDB`.`Questionary`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("activity");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Integer[data.size()]);
        } else {
            return null;
        }
        
    }   
  
    public Integer[] get_PhycoLevel(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Integer> data = new Vector<Integer>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT depression FROM `SensorDB`.`Questionary`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("depression");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Integer[data.size()]);
        } else {
            return null;
        }
        
    }   
  
    public Integer[] get_SocialLevel(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Integer> data = new Vector<Integer>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT socalization FROM `SensorDB`.`Questionary`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("socalization");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Integer[data.size()]);
        } else {
            return null;
        }
        
    }   
  
/***************************** Acc Data Period **************************************************/
public Float[] get_Acc_X_Period(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT x FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("x");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
      public Float[] get_Acc_Y_Period(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
        long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT y FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("y");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
      public Float[] get_Acc_Z_Period(String startdate,String enddate,String username) throws ParseException{
         
        Vector<Float> data = new Vector<Float>();
        String sqlStmt ;
       
         long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);

        try {
           this.connect();
            sqlStmt = "SELECT z FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                Float tmp = res.getFloat("z");
                data.add(tmp);
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data.size()>0){
            return data.toArray(new Float[data.size()]);
        } else {
            return null;
        }
        
    }
        public Timestamp[] get_AccData_Time_Period(String startdate,String enddate,String username) throws ParseException{
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       
           long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(startdate);
         java.util.Date parsed_endDate = dateFormat.parse(enddate);
            
             
           
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);
           
        try {
           this.connect();
            sqlStmt = "SELECT Date FROM `SensorDB`.`AccStatistics`  "
                    + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("Date");
                time.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
        /*********************************Table data********************************************/
        public Timestamp[] getStatisticEmotionTime(String username) {
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       

        try {
           this.connect();
            sqlStmt = "SELECT date FROM `SensorDB`.`Questionary` "
                      + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("date");
                time.add(tmp);
                
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
        public String[] TableStatsticSocalization(String username) {
        
         Vector<String> data_value= new Vector<String>();
        String sqlStmt ;
       
         

        try {
           this.connect();
            sqlStmt = "SELECT socalization FROM `SensorDB`.`Questionary`  "
                       + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("socalization");
                if(tmp==1){
                    data_value.add("Luck of communication");
                }else if(tmp==2){
                    data_value.add("Midium level");
                }
                else if(tmp==3){
                    data_value.add("Normal Level");
                }
                else if(tmp==4){
                    data_value.add("Very Social person");
                }
              
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data_value.size()>0){
            System.out.println();
            return data_value.toArray(new String[data_value.size()]);
        } else {
            return null;
        }
        
    }   
         
        public String[] TableStatsticEnergy(String username) {
        
         Vector<String> data_value= new Vector<String>();
        String sqlStmt ;
       
         

        try {
           this.connect();
            sqlStmt = "SELECT energy FROM `SensorDB`.`Questionary`  "
                       + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("energy");
                if(tmp==1){
                    data_value.add("Exhausted");
                }else if(tmp==2){
                    data_value.add("Tied");
                }
                else if(tmp==3){
                    data_value.add("Normal Filling");
                }
                else if(tmp==4){
                    data_value.add("Energetic");
                }
                else if(tmp==5){
                    data_value.add("Hyperactive");
                }
                 
                
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data_value.size()>0){
            System.out.println();
            return data_value.toArray(new String[data_value.size()]);
        } else {
            return null;
        }
        
    }   
        
        
        
    /**************************** LightData for Day   ******************************************************/
     public LightData[] table_LightData_Statistic(String username) throws ParseException{
         
        Vector<LightData> uuids = new Vector<LightData>();
        String sqlStmt ;
       int lightDataAcc = 0;
       
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          
           
        try {
           this.connect();
            
            sqlStmt = "SELECT LightData,Date,SensorID FROM `SensorDB`.`LightStatistics`  "
                       + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                LightData tmp=new LightData(username);
                
                tmp.setSensor_id(res.getInt("SensorID"));
                tmp.setDate(res.getTimestamp("Date"));
                lightDataAcc+=res.getInt("LightData");
                tmp.setValue(res.getInt("LightData"));
                uuids.add(tmp);
            }
             System.out.print("aver1:"+(lightDataAcc/ uuids.size()));
            /*int lightDataAcc = 0;
            for(int i=0; i<uuids.size(); i++){
                lightDataAcc += uuids.elementAt(i).getValue();
            }
            lightDataAcc /= uuids.size();*/
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(uuids.size()>0){
            
            return uuids.toArray(new LightData[uuids.size()]);
        } else {
            return null;
        }
        
    }     
        public String[] TableStatsticActivity(String username) {
        
         Vector<String> data_value= new Vector<String>();
        String sqlStmt ;
       
         

        try {
           this.connect();
            sqlStmt = "SELECT activity FROM `SensorDB`.`Questionary`  "
                       + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("activity");
                if(tmp==1){
                    data_value.add("No Exersice");
                }else if(tmp==2){
                    data_value.add("Light Exersise");
                }
                else if(tmp==3){
                    data_value.add("Moderate Exersice");
                }
                else if(tmp==4){
                    data_value.add("Heavy Exersice");
                }
                 
                
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data_value.size()>0){
            System.out.println();
            return data_value.toArray(new String[data_value.size()]);
        } else {
            return null;
        }
        
    }   
         public String[] TableStatsticPhyco(String username) {
        
         Vector<String> data_value= new Vector<String>();
        String sqlStmt ;
       
         

        try {
           this.connect();
            sqlStmt = "SELECT depression FROM `SensorDB`.`Questionary`  "
                       + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("depression");
                if(tmp==1){
                    data_value.add("Depressed");
                }else if(tmp==2){
                    data_value.add("Not stable filling");
                }
                else if(tmp==3){
                    data_value.add("Normal filling");
                }
                else if(tmp==4){
                    data_value.add("Mild");
                }
                 else if(tmp==5){
                    data_value.add("Elevated");
                }
                 
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data_value.size()>0){
            System.out.println();
            return data_value.toArray(new String[data_value.size()]);
        } else {
            return null;
        }
        
    }   
         public String[] TableStatsticWeight(String username) {
        
         Vector<String> data_value= new Vector<String>();
        String sqlStmt ;
       
         

        try {
           this.connect();
            sqlStmt = "SELECT weight FROM `SensorDB`.`Questionary`  "
                       + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
                int tmp = res.getInt("weight");
                 String aString = Integer.toString(tmp);
                data_value.add(aString);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(data_value.size()>0){
            System.out.println();
            return data_value.toArray(new String[data_value.size()]);
        } else {
            return null;
        }
        
    }   
         public String[] getStatisticSleepHours(String username) {
          
        Vector<String> time = new Vector<String>();
        String sqlStmt ;
       
       

        try {
           this.connect();
            sqlStmt = "SELECT sleepDateTime,wakeDateTime FROM `SensorDB`.`Questionary` "
                      + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("sleepDateTime");
               Timestamp tmp2 = res.getTimestamp("wakeDateTime");
               String diff = "";
        long timeDiff = Math.abs(tmp2.getTime() - tmp.getTime());
       diff = String.format("%d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff),
       TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        
               time.add(diff);
                
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new String[time.size()]);
        } else {
            return null;
        }
        
    }
         public Timestamp[] getSleepDay(String username) {
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
       
       

        try {
           this.connect();
            sqlStmt = "SELECT sleepDateTime FROM `SensorDB`.`Questionary` "
                      + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("sleepDateTime");
                time.add(tmp);
                
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
         public String[] getStatisticAccelTable(String username) {
          
        Vector<String> activity = new Vector<String>();
        String sqlStmt ;
       
        try {
           this.connect();
            sqlStmt = "SELECT x,y,z  FROM `SensorDB`.`AccStatistics` "
                      + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
              Float x=res.getFloat("x");
              Float y=res.getFloat("y");
              Float z=res.getFloat("z");
              Double d=Math.sqrt((x*x)+(y*y)+(z*z));
               if(d==9.8){
                    activity.add("No Exersice");
                }else if(d<10 && d>9.8){
                    activity.add("Light Exersice");
                }
               else if(d>10 && d<12){
                    activity.add("Moderate Exersice");
                }
               else if(d>12){
                    activity.add("Heavy Exersice");
                }
              
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(activity.size()>0){
            return activity.toArray(new String[activity.size()]);
        } else {
            return null;
        }
        
    }
          public Timestamp[] get_AccData_Time_Table(String username) {
          
        Vector<Timestamp> time = new Vector<Timestamp>();
        String sqlStmt ;
     
        try {
           this.connect();
            sqlStmt = "SELECT Date FROM `SensorDB`.`AccStatistics`  "
                     + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
               Timestamp tmp = res.getTimestamp("Date");
                time.add(tmp);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(time.size()>0){
            return time.toArray(new Timestamp[time.size()]);
        } else {
            return null;
        }
        
    }
          public String[] getAllUsers() {
          
        Vector<String> login = new Vector<String>();
        String sqlStmt ;
       
        try {
           this.connect();
            sqlStmt = "SELECT login  FROM `SensorDB`.`UserData` ";
                      
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
              String l=res.getString("login");
             
               login.add(l);
                    
               
              
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(login.size()>0){
            return login.toArray(new String[login.size()]);
        } else {
            return null;
        }
        
    }
          public Member get_usefinfo(String username) {
          
        Member user=new Member(username);        
       
        String sqlStmt ;
        
        try {
            this.connect();
            sqlStmt = "SELECT fName,lName FROM `SensorDB`.`UserData` "
                    + "WHERE login = '" + username + "'";
            ResultSet res = statement.executeQuery(sqlStmt);

            if(res.next()){
               String time = res.getString("fName");
               String lname=res.getString("lName");
               user.setFirstName(time);
               user.setLastName(lname);
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException ex) {
            Logger.getLogger(DatabaseInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
           public LightData[] get_LightData(String Date,String username) {
          
        Vector<LightData> login = new Vector<LightData>();
        String sqlStmt ;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsedDate;
        try {
            parsedDate = dateFormat.parse(Date);
        
            
           long millisecInDay = 86400000;
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());  
           java.sql.Timestamp timestamp2 = new java.sql.Timestamp(timestamp.getTime()+millisecInDay);
       
        
           this.connect();
            sqlStmt = "SELECT Date,LightData,SensorID FROM `SensorDB`.`LightStatistics` "
                     + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp2 + "')";
                      
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
              LightData tmp=new LightData(username);
              Timestamp date=res.getTimestamp("Date");
              Integer data=res.getInt("LightData");
              Integer id=res.getInt("SensorID");
              
                 
              tmp.setDate(date);
              tmp.setValue(data);
              tmp.setSensor_id(id);
         
               login.add(tmp);
                   
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        catch (ParseException ex) {
            Logger.getLogger(DatabaseInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(login.size()>0){
           
            return login.toArray(new LightData[login.size()]);
        } else {
            return null;
        }
        
    }
            
  public LightData[] get_LightDataPeriod(String StartDate,String EndDate,String username) {
          
        Vector<LightData> login = new Vector<LightData>();
        String sqlStmt ;
        
     
        try {
           
        
            long millisecInDay = 86400000;
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date parsed_startDate = dateFormat.parse(StartDate);
         java.util.Date parsed_endDate = dateFormat.parse(EndDate);
    
           java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed_startDate.getTime());  
          java.sql.Timestamp timestamp2 = new java.sql.Timestamp(parsed_endDate.getTime());  
           java.sql.Timestamp timestamp3 = new java.sql.Timestamp(timestamp2.getTime()+millisecInDay);
        
           this.connect();
            sqlStmt = "SELECT Date,LightData,SensorID FROM `SensorDB`.`LightStatistics` "
                     + "WHERE login ='"+username+"' AND (Date BETWEEN '" +timestamp+ "' AND '" +  timestamp3 + "')";
                      
            ResultSet res = statement.executeQuery(sqlStmt);
            
            while(res.next()){
              LightData tmp=new LightData(username);
              Timestamp date=res.getTimestamp("Date");
              Integer data=res.getInt("LightData");
              Integer id=res.getInt("SensorID");
              
                 
              tmp.setDate(date);
              tmp.setValue(data);
              tmp.setSensor_id(id);
         
               login.add(tmp);
                   
            }
            if(!res.isClosed())
                res.close();
            this.close(); 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        catch (ParseException ex) {
            Logger.getLogger(DatabaseInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(login.size()>0){
           
            return login.toArray(new LightData[login.size()]);
        } else {
            return null;
        }
        
    }
}