<%@page import="java.util.Vector"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Time"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="project.Commands"%>
<%@page import="HandleData.LightData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
    <title>LightSensor Statistic</title>
    <%LightData tmp[]=(LightData[])request.getAttribute(Commands.attrTestLight);%>
    <%Vector<LightData> table1 = new Vector<LightData>();%>
     <%Vector<LightData> table2 = new Vector<LightData>();%>
      <%Vector<LightData> table3 = new Vector<LightData>();%>
       <%Vector<LightData> table4 = new Vector<LightData>();%>
       <% Integer aver1=0,aver2=0;%>
       <% Integer aver3=0,aver4=0;%>
    <%
    for(int i=0; i<tmp.length; i++){
        if(tmp[i].getSensor_id()==1){
           aver1+=tmp[i].getValue();
           table1.add(tmp[i]);
        }
               else if(tmp[i].getSensor_id()==2){
                    aver2+=tmp[i].getValue();
           table2.add(tmp[i]);
        }
        else if(tmp[i].getSensor_id()==3){
             aver3+=tmp[i].getValue();
           table3.add(tmp[i]);
        }
        else if(tmp[i].getSensor_id()==4){
             aver4+=tmp[i].getValue();
           table4.add(tmp[i]);
        }
        
    }
       aver1=aver1/table1.size();
       aver2=aver2/table2.size();
       aver3=aver3/table3.size();
       aver4=aver4/table4.size();
    %>
  
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">

google.load('visualization', '1.1', {packages: ['corechart']});

google.setOnLoadCallback(drawExample2);

function drawExample2() {
  // Some raw data (not necessarily accurate)

  var rowData1 = [['Date', 'Sensor 1','Sensor2','Sensor3','Sensor4'],
                    <%for(int i=0;i<table1.size();i++){%>
                  ['<%=table1.elementAt(i).getDate().getHours()%>:<%=table1.elementAt(i).getDate().getMinutes()%>',<%=aver1%>,<%=aver2%>,<%=aver3%>,<%=aver4%>],
                 <%}%>
                 ];

  
  // Create and populate the data tables.
  var data = [];
  data[0] = google.visualization.arrayToDataTable(rowData1);
 
  var options = {
    vAxis: {title: "Value"},
    hAxis: {title: "Date"},
   // seriesType: "line",
    title:'LightSensor Statistic',
    animation:{
      duration: 100,
      easing: 'out'
    }
    
  };
  var current = 0;
  // Create and draw the visualization.
  var chart = new google.visualization.AreaChart(document.getElementById('plottContainer'));

   
  function drawChart() {
     // Disabling the button while the chart is drawing.
    button1.disabled = true;
    button2.disabled = true;
    google.visualization.events.addListener(chart, 'ready',
        function() {
          button1.disabled = false;
          button2.disabled = false;
         
        });
        
    chart.draw(data[current], options);
  }
  drawChart();


  
}


</script>

</head>
<body>
   
<div id="HomeDiVContainer" >
  
  <div id="plottContainer"></div>
</div>
</body>
</html>