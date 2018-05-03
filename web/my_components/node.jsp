<%-- 
    Document   : node
    Created on : Jan 2, 2013, 10:12:20 PM
    Author     : eirini
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.page import="project.Commands"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Node</title>
    </head>
    <body>
        <div id="HomeDiVContainer">
       <fieldset class="GalleryDayStatisticFieldset" style="" >
            <legend> Leave Node</legend>
              <form  method="post" action="Controller">
                  Please enter your text:
            <BR>
            <TEXTAREA name="<%=Commands.attrUpdateNode%>" ROWS="15" cols="40"></TEXTAREA>
            <BR>
             <button type="submit" id="Register" name="<%=Commands.buttonUpdateNode%>">
                            Update
                        </button>
                
                             
            </form>
            </fieldset>
        </div>
                           
    </body>
</html>
