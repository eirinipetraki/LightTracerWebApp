<%@page import="java.sql.Timestamp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="project.Commands"%>
<!DOCTYPE html>

<html>
  <head>
     <% String social[] = (String[])request.getAttribute(Commands.attrSosialTable); %>
     <% String energy[] = (String[])request.getAttribute(Commands.attrEnergyTable); %>
     <% String act[] = (String[])request.getAttribute(Commands.attrActivityTable); %>
     <% String phyco[] = (String[])request.getAttribute(Commands.attrPhycoTable); %>
     <% String weight[] = (String[])request.getAttribute(Commands.attrWeightTable); %>
     <% String SleepTime[] = (String[])request.getAttribute(Commands.attrSleepTable); %>
     <% String Accel[] = (String[])request.getAttribute(Commands.attrAccelTable); %>
     <% Timestamp [] EmotionTime=(Timestamp[]) request.getAttribute(Commands.attrEmotionTimeTable);%>
     <% Timestamp [] SleepDay=(Timestamp[]) request.getAttribute(Commands.attrSleepDayTable);%>
     <% Timestamp [] AccelDay=(Timestamp[]) request.getAttribute(Commands.attrAccelDayTable);%>
    <title>
      Statistic 
    </title>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load('visualization', '1.1', {packages: ['controls']});
    </script>
    <script type="text/javascript">
      function drawVisualization() {
               var data= new google.visualization.DataTable();
        data.addColumn('string', 'Category');
        data.addColumn('string', 'Value');
        data.addColumn('string', 'Date');
        data.addRows([
          
        <%if(social!=null){for(int i=0;i<social.length;i++){%>
          ['Social' , '<%=social[i]%>','<%=EmotionTime[i]%>'],
          <%}}%>
              <%if(energy!=null){for(int i=0;i<energy.length;i++){%>
          ['Energy' , '<%=energy[i]%>','<%=EmotionTime[i]%>'],
          <%}}%>
               <%if(act!=null){for(int i=0;i<act.length;i++){%>
          ['Activity' , '<%=act[i]%>','<%=EmotionTime[i]%>'],
          <%}}%>
              <%if(phyco!=null){for(int i=0;i<phyco.length;i++){%>
          ['PAL' , '<%=phyco[i]%>','<%=EmotionTime[i]%>'],
          <%}}%>
              <%if(weight!=null){for(int i=0;i<weight.length;i++){%>
          ['Weight' , '<%=weight[i]%>','<%=EmotionTime[i]%>'],
          <%}}%>
              <%if(SleepTime!=null){for(int i=0;i<SleepTime.length;i++){%>
          ['Hours of Rest' , '<%=SleepTime[i]%>','<%=SleepDay[i]%>'],
          <%}}%>
               <%if(Accel!=null){for(int i=0;i<Accel.length;i++){%>
          ['Acceleration' , '<%=Accel[i]%>','<%=AccelDay[i]%>'],
          <%}}%>
        
      ]);

       var dateFilter = new google.visualization.ControlWrapper({
          'controlType': 'StringFilter',
          'containerId': 'Filer2Container',
          'options': {
            'filterColumnLabel': 'Date'
            
          }
        });
        // Define a category picker for the 'Metric' column.
        var categoryPicker = new google.visualization.ControlWrapper({
          'controlType': 'CategoryFilter',
          'containerId': 'Filer1Container',
          'options': {
            'filterColumnLabel': 'Category',
            'ui': {
              'allowTyping': true,
              'allowMultiple': true,
              'selectedValuesLayout': 'aside'
            }
          }
          // Define an initial state, i.e. a set of metrics to be initially selected.
         // 'state': {'selectedValues': ['CPU']}
        });
      
        // Define a gauge chart.
        var gaugeChart = new google.visualization.ChartWrapper({
          'chartType': 'Table',
          'containerId': 'TableContainer',
          'options': {
            
          }
        });
      
        // Create the dashboard.
        var dashboard = new google.visualization.Dashboard(document.getElementById('HomeDiVContainer')).
          // Configure the category picker to affect the gauge chart
          bind([categoryPicker,dateFilter], gaugeChart).
          // Draw the dashboard
          draw(data);
      }
      

      google.setOnLoadCallback(drawVisualization);
    </script>
  </head>
  <body>
    <div id="HomeDiVContainer">
     
            <div id="Filer1Container"></div>
            <div id="Filer2Container"></div>
            <div style="float: left;" id="TableContainer"></div>
            
    </div>
  </body>
</html>