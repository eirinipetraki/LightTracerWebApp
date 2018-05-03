<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="project.Commands"%>
<!DOCTYPE html>

<html>
    <head>
        <% String Error = (String)request.getAttribute(Commands.attrError); %>
        
        <title> Error page</title>
        <link rel="stylesheet" type="text/css" href="/css/ProjectStyle.css" media="screen, print" >
   
    </head>
<body>
  
    <div  id="HomeDiVContainer">
         <%=Error %>
    </div>
</body>
</html>
