<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.page import="project.Commands"/>

<!DOCTYPE html>
<html>
    <head>
        <title>Graph Statistic</title>
        
    </head>
    <body>
      
        <div id="GalleryDivContener">
           <p style="font-family:verdana;color:red;align:center;">        
                     <%String error = (String)request.getAttribute(Commands.attrError);%>
            <%if(error != null){%>
            <%=error%><br>
            <%}%>
                 </p>
            <fieldset class="GalleryDayStatisticFieldset" style="" >
            <legend> Statistic per Day</legend>
              <form  method="post" action="Controller">
                  
                <table  >
                    <tr>
                        <td >
                           Plot: 
                        </td>
                        <td>
                            <select name="<%=Commands.attrPlotTypeDay%>">
                            <option value="LightData"> LightData </option>
                            <option value="Accelerometr"> Accelerometr </option>
                            <option value="Location"> Location</option>
                          
                            </select>
                        </td>
                        <td >
                            <input name="<%=Commands.attrRequestDate%>" value="yyyy-mm-dd" type="date">
                        </td>
                        <td >
                             <button type="submit" id="Register" name="<%=Commands.buttonPerDay%>">
                            Submit
                        </button>
                        </td>
                    </tr>
                </table>
                             
            </form>
            </fieldset>
                       
                           <fieldset class="GalleryPeriodStatisticFieldset" style="" >
            <legend>Request per Period</legend>
              <form  method="post"  action="Controller">
                <table  >
                    <tr>
                        <td >
                          Plot 
                        </td>
                          <td>
                            <select name="<%=Commands.attrPlotTypePeriod%>">
                                <option value="LightData"> LightData </option>      
                            <option value="Accelerometr"> Accelerometr </option>
                            <option value="Location"> Location</option>
                            <option value="Emotion"> Emotions</option>
                            </select>
                        </td>
                        <td >
                           <input size="6" name="<%=Commands.attrStartPeriod%>" value="yyyy-mm-dd" type="date">  </td><td> <input size="6" name="<%=Commands.attrFinishPeriod%>" value="yyyy-mm-dd" type="date">
                        </td>
                        <td >
                             <button type="submit" name="<%=Commands.buttonPerPeriod%>">Search</button>
                        </td>
                    </tr>
                </table>
                             
            </form>
            </fieldset>
        </div>
       
           
    </body>
</html>
