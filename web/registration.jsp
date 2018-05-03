<%-- 
    Document   : registration
    Created on : Jul 3, 2012, 6:40:36 PM
    Author     : eirini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="project.Commands"%>


<!DOCTYPE html>
<html>
    <head>
         <link rel="stylesheet" type="text/css" href="css/ProjectStyle.css">
        <title>Register new user</title>
    </head>
    <body >
        
         <form id="RegistrationMainForm" action="Controller" method="POST">
            <div  id="RegistrationMainDiv">
            <table align="center" >
                <tr>
                <h1 align="center"> Registration Form</h1>
                </tr>
                <tr>
                    <td> UserName : </td>
                    <td> <input type="text" name="<%=Commands.paramLogin%>" size ="30"> </td>
                </tr>

                <tr>
                    <td> PassWord : </td>
                    <td> <input type="password" id="pass" name="<%=Commands.paramPassword%>" size ="30"> </td>
                </tr>
                <tr>
                    <td> FirstName : </td>
                    <td> <input type="text"  name="<%=Commands.paramFirstName%>" size ="30"> </td>
                </tr>
                
		<tr>
                    <td> LastName : </td>
                    <td> <input type="text"  name="<%=Commands.paramLastName%>" size ="30"> </td>
                </tr>
                <tr>
                    <td> Sex</td>
                    <td><select name="<%=Commands.paramSex%>" >
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            </select> </td>
                    
                </tr>
                <tr>
                    <td>Birth Date</td>
                    <td><input type="date" min="1950/01/01"  name="<%=Commands.paramAge%>" size="10"> </td>
                    
                </tr>
                <tr>
                    <td> Profession</td>
                    <td><input type="text" name="<%=Commands.paramJob%>" size="30"> </td>
                    
                </tr>
                <tr>
                    <td> Mail</td>
                    <td><input type="email" placeholder="admin@uu.it.se" name="<%=Commands.paramMail%>" size="30"> </td>
                    
                </tr>
                 
                <tr>
                    <td></td>
                    <td>
                         <button type="submit" id="Redister" name="<%=Commands.Ctrl_Register%>">
                            Register
                        </button>
                        <td>
                          
                        <a href="index.jsp" ><img src="images/arrow.png" /></a>
                       </td>
                    </td>
                </tr>
            </table>
            </div>
    </body>
</html>
