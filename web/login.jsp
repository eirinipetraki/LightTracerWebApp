
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="project.Commands"/>

<html>
    <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/ProjectStyle.css">
        <title>Login into Server</title>
    </head>
    <body >
        <form id="LoginForm" action="Controller" method="POST" >
         <div  id="LoginDiV">
          
            <h1 align="center">Seasonal Affective Disorder Monitoring </h1>
                  
       <table align="center">
                <tr>
                    <td> UserName </td>
                    <td> <input type="text" name="<%=Commands.paramLogin%>" size ="20"> </td>
                </tr>
                <tr>
                    <td> PassWord </td>
                    <td> <input type="password" name="<%=Commands.paramPassword%>" size ="20"> </td>
                </tr>
                <tr>
                    
                    <td >
                        <button type="submit" id="Login" name="<%=Commands.buttonLogin%>">
                            Login
                        </button>
                    </td >
                    <td >
                        <button type="submit" id="Register" name="<%=Commands.buttonRegister%>">
                            Register
                        </button>
                    </td>
                </tr>
                <tr><p style="font-family:verdana;color:red;align:center;">        
                     <%String error = (String)request.getAttribute(Commands.attrError);%>
            <%if(error != null){%>
            <%=error%><br>
            <%}%>
                 </p></tr>
     </table>
                 
            </div>
        </form>
    </body>
</html>
