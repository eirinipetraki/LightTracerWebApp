
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="project.Commands"%>
<!DOCTYPE html>

<html>
    <head>
         <%String error = null;%>
        <title>Insert Form</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" type="text/css" href="/css/ProjectStyle.css" media="screen, print" >
<script type="text/javascript">

// Popup window code
function newPopup(url) {
	popupWindow = window.open("my_components/information.html",'popUpWindow','height=350,width=450,left=700,top=370,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
}


</script>
    </head>
<body >
  
<div  id="FormDiVContainer">
    <fieldset class="FormFieldset">
        
            <%if(error != null){%>
            <%=error%><br>
            <%}%>
<form name="QuestionForm" method="get" action="Controller" onsubmit="return greeting()">
    <table align="center">
        <tr>
            <td align="center" > Sociability level:</td>
            <td><select name="<%=Commands.Socialization%>">
                <option value="1"> Lack</option>
                <option value="2"> Low </option>
                <option value="3"> Medium  </option>
                <option value="4"> High </option>
                </select>
             <a href="JavaScript:newPopup();"><img src="images/informatio.jpg"  width="15" height="15" /></a>
            </td>
                
       </tr>
    
   <tr>
            <td  align="center" > TEE (Total Energy Expenditure)</td>
            <td><select name="<%=Commands.EnergyLevel%>">
  <option value="1"> Exhausted </option>
  <option value="2"> Tied</option>
  <option value="3"> Fill Normal</option>
  <option value="4"> Energetic</option>
  <option value="5"> Very Energetic</option>
</select>
             <a href="JavaScript:newPopup();"><img src="images/informatio.jpg"  width="15" height="15" /></a>
            </td>
           
    </tr>
       <tr>
            <td align="center" > Emotional Frequency:</td>
            <td><select name="<%=Commands.DepressionLevel%>">
                <option value="1"> Depressed </option>
                <option value="2"> Moderate </option>
                <option value="3"> Normal </option>
                <option value="4"> Mild </option>
                <option value="5"> Elevated </option>
                </select> 
             <a href="JavaScript:newPopup();"><img src="images/informatio.jpg"  width="15" height="15" /></a>
            </td>
        
    </tr>
     <tr>
            <td  align="center" > PAL(Physical Activity level):</td>
            <td><select name="<%=Commands.ActivityLevel%>">
                <option value="1"> No exercise </option>
                <option value="2"> Little exercise</option>
                <option value="3"> Light exercise</option>
                <option value="4"> Moderate exercise</option>
                <option value="5"> Heavy exercise</option>
               </select>
            <a href="JavaScript:newPopup();"><img src="images/informatio.jpg"  width="15" height="15" /></a>
            </td>
   
    </tr>
    <tr>
        <td>Weight</td>
        <td><input  name="<%=Commands.Weight%>" type="text" size="5"> kg</td>
    </tr>
     
    <tr>
        <td align="center" width="400">Sleep date and time</td>
        <td> <input  name="<%=Commands.SleepDate%>" value="yyyy-mm-dd" type="date" > <input name="<%=Commands.SleepTime %>" value="hh:mm" type="time" ></td>
    </tr>
    <tr>
        <td align="center" width="400">Wakeup date and time: </td>
        <td><input name="<%=Commands.WakeupDate%>" value="yyyy-mm-dd" type="date"> <input name="<%=Commands.WakeupTime %>" value="hh:mm" type="time" ></td>
   
    </tr>
    <tr>
        <td></td>
        <td><button name="<%=Commands.Ctrl_Questionary%>">Submit</button></td>
    </tr>
     </table>

</form>
    </fieldset>
</div>
</body>
</html>
