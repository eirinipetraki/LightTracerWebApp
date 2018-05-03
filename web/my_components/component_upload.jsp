
<%@page import="project.Commands"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Photos</title>
    </head>
    <body>
        <% String uNamePhotos = (String)session.getAttribute(Commands.attrUserName); %>
        <%
        String url = "http://localhost:8084/LightTracerWebApp/UploadServlet?";
       
        url += Commands.paramUploadGraph + "=" + uNamePhotos;
         System.err.print("url"+url);
        %>
        <div id="HomeDiVContainer">
            <fieldset class="UploadFieldset" style="" >
            <legend>Upload File</legend>
              <form  action=<%=url%> method="POST" enctype="multipart/form-data">
                <table  >
                    <tr>
                        <td >
                            <label for="filename">Graph: </label>
                        </td>
                        <td >
                            <input id="filename" type="file" name="<%=Commands.paramUploadGraph%>"/><br/>
                        </td>
                        <td >
                            <input type="submit" value="Upload File"/>
                        </td>
                    </tr>
                </table>
                        <p style="font-family:verdana;color:red;align:center;">        
                     <%String error = (String)request.getAttribute(Commands.attrError);%>
            <%if(error != null){%>
            <%=error%><br>
            <%}%>
                 </p>
            </form>
            </fieldset>
        </div>
       
           
    </body>
</html>
