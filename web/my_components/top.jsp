
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:directive.page import="project.Commands"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" type="text/css" href="css/ProjectStyle.css">
        <title></title>
    </head>
    <body >
         
        <form id="TopViewComponent" action="Controller" method="POST">
      
        <div  id="TopViewDivComponent">
          
          
        <table class="TopTable" align="center"  >
            <tr>
                <td  width="25"></td>
                <td>
                     <h1>Seasonal Affective Disorder Monitoring</h1>
                </td>
                <td >
                       <button  type="submit" class="CTRL_Logout" name="<%=Commands.param_CNTRL_Logout%>">LogOut</button>        
                </td>
            </tr>
        </table>
            
        </div>
    </form>
    </body>
</html>
