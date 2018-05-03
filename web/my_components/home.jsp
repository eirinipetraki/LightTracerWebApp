
<%@page import="java.sql.Timestamp"%>
<%@page import="project.Commands"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        
        <% String loginname=(String)session.getAttribute(Commands.attrUserName); %>
        <% String loginfname=(String)session.getAttribute(Commands.paramFirstLogin); %>
        <% Timestamp Lastlogin=(Timestamp)session.getAttribute(Commands.paramLastLogin); %>
        <%String Node=(String)session.getAttribute(Commands.paramNode);%>
     
        <title>Home Page</title>
        <link rel="stylesheet" type="text/css" href="/css/ProjectStyle.css" media="screen, print" >
   
    </head>
<body>
  
    <div  id="HomeDiVContainer">
        <p align="center">Welcome  "<%=loginname%>"
        <br> Last login was at <%=Lastlogin%><br><br></p>

        <table  >
            <tr>
                <th align="LEFT" >Last Day Statistic</th>
                <th> Doctors Advice</th>
            </tr>
            <tr>
                <td align="LEFT"  width=500 >
            <br> Your  Activity  Level  was    <STRONG>low </strong>                                 <br>
            <br> Your Sociability Level was   <strong> medium</strong>                                     <br>
            <BR> Your Emotional Frequency was <strong> moderate </strong>                                    <br>
            <br> You had     <strong> 6 hours  </strong> of  sleep
       
                </td>
                <td>
        <fieldset class="AdminNote"><legend> 2012-07-17</legend>
            
            <%if(Node!=null){%>
            <p><%=Node%></p>
        <%} else {%>
        <p>Note not available </p>
        <%}%>
        
        </fieldset>
                </td>
        </tr>
        </table>
    </div>
</body>
</html>
