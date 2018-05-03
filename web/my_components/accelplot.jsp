<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Time"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="project.Commands"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
  <% String RequestDate = (String)request.getAttribute(Commands.attrRequestDate); %>
  <% Float x[] = (Float[])request.getAttribute(Commands.attrAccX); %>
  <% Float y[] = (Float[])request.getAttribute(Commands.attrAccY); %>
  <% Float z[] = (Float[])request.getAttribute(Commands.attrAccZ); %>
  <% Timestamp []Time=(Timestamp[]) request.getAttribute(Commands.attrAccTime);%>
    
 <script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">

google.load('visualization', '1.1', {packages: ['corechart']});

google.setOnLoadCallback(drawExample2);

function drawExample2() {
  // Some raw data (not necessarily accurate)
 
  var rowData2 = [['Date', 'A_x', 'A_y', 'A_z'],
                    <%for(int i=0;i<x.length;i++){%>
                  ['<%=Time[i].getHours()%>:<%=Time[i].getMinutes()%>',<%=x[i]%>,<%=y[i]%>,<%=z[i]%>],
                  <%}%>
                 ];
  var rowData1 = [['Date', 'Absolute Acceleration'],
                   <%for(int j=0;j<x.length;j++){
                       Double d=Math.sqrt((x[j]*x[j])+(y[j]*y[j])+(z[j]*z[j]))-9.8;%>
                        ['<%=Time[j].getHours()%>:<%=Time[j].getMinutes()%>',<%=d%>],
                  <%}%>];
 

  // Create and populate the data tables.
  var data = [];
  data[0] = google.visualization.arrayToDataTable(rowData1);
  data[1] = google.visualization.arrayToDataTable(rowData2);
  

  var options = {
    vAxis: {title: "Acceleration"},
    hAxis: {title: "Date"},
    seriesType: "lvs",
    title:'Activity monitoring',
    animation:{
      duration: 100,
      easing: 'out'
    }
  };
  var current = 0;
  // Create and draw the visualization.
  var chart = new google.visualization.ComboChart(document.getElementById('plottContainer'));
  var button1 = document.getElementById('example2-b1');
   var button2 = document.getElementById('example2-b2');
   
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

  button1.onclick = function() {
      if(current!=1){
      current++;
      drawChart();
      }
      else {
          current=0;
          drawChart();
      }
  }
   button2.onclick = function() {
       if(current==0){
      current=1;
      drawChart();
       }
       else{
           current--;
           drawChart();
       }
  }
}

</script>

</head>
<body>
   
<div id="HomeDiVContainer" >
  <form><input id="example2-b2" type="button" value="Previous"><input id="example2-b1" type="button" value="Next"></form>
  <div id="plottContainer"></div>
</div>
  
</body>
</html>
