
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
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
        <% String StartPeriod=(String)request.getParameter(Commands.attrStartPeriod);%>
        <%String EndPeriod=(String)request.getParameter(Commands.attrFinishPeriod); %>
        <% Integer energy[] = (Integer[])request.getAttribute(Commands.attrEnergyLevel); %>
        <% Integer activity[] = (Integer[])request.getAttribute(Commands.attrAcivityLevel); %>
        <% Integer phyco[] = (Integer[])request.getAttribute(Commands.attrPhycoLevel); %>
        <% Integer social[] = (Integer[])request.getAttribute(Commands.attrSocialLevel); %>
        <% Timestamp [] Time=(Timestamp[]) request.getAttribute(Commands.attrQuestionaryTime);%>
    

<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">

google.load('visualization', '1.1', {packages: ['corechart']});

google.setOnLoadCallback(drawExample2);

function drawExample2() {
  // Some raw data (not necessarily accurate)
  var rowData1 = [['Date', 'Energy', 'Activity', 'Phycology', 'Social'],
                    <%for(int i=0;i<energy.length;i++){%>
                  ['<%=new java.sql.Date(Time[i].getTime())%>',<%=energy[i]%>,<%=activity[i]%>,<%=phyco[i]%>,<%=social[i]%>],
                  <%}%>
                 ];
  var rowData2 = [['Date', 'Energy'],
                   <%for(int i=0;i<energy.length;i++){%>
                  ['<%=new java.sql.Date(Time[i].getTime())%>',<%=energy[i]%>],
                  <%}%>];
  var rowData3 = [['Date', 'Activity'],
                   <%for(int i=0;i<activity.length;i++){%>
                  ['<%=new java.sql.Date(Time[i].getTime())%>',<%=activity[i]%>],
                  <%}%>];
  var rowData4= [['Date', 'Social'],
                   <%for(int i=0;i<social.length;i++){%>
                  ['<%=new java.sql.Date(Time[i].getTime())%>',<%=social[i]%>],
                  <%}%>];
  var rowData5 = [['Date', 'Phyco'],
                   <%for(int i=0;i<phyco.length;i++){%>
                  ['<%=new java.sql.Date(Time[i].getTime())%>',<%=phyco[i]%>],
                  <%}%>];

  // Create and populate the data tables.
  var data = [];
  data[0] = google.visualization.arrayToDataTable(rowData1);
  data[1] = google.visualization.arrayToDataTable(rowData2);
  data[2] = google.visualization.arrayToDataTable(rowData3);
  data[3] = google.visualization.arrayToDataTable(rowData4);
  data[4] = google.visualization.arrayToDataTable(rowData5);

  var options = {
    vAxis: {title: "Level"},
    hAxis: {title: "Date"},
    seriesType: "bars",
    title:'Questionary Statistic',
    animation:{
      duration: 1000,
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
        if(current!=0){
            options. seriesType='lvs';
        }
        else{options.seriesType='bars';}
    chart.draw(data[current], options);
  }
  drawChart();

  button1.onclick = function() {
      if(current<4){
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
      current=4;
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
