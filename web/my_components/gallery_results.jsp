
<%@page import="project.Commands"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Photos</title>
        
  <style>
   .zoom:hover { zoom: 3; }
  </style>
    </head>
    <body>
        <% String uNamePhotos = (String)request.getAttribute(Commands.attrUserName); %>
        <% String PhotoUUIDs[] = (String[])request.getAttribute(Commands.attrPhotoUUIDs); %>
        <form id="PhotoViewComponent" method="post" action="Controller">
        <div  id="HomeDiVContainer">
           
          <!--  <fieldset style="height:70%;min-height:500px;width:720px;position:absolute;left:50%;margin-left:-376px;overflow:auto" >
            <legend>Album Pictures</legend>-->
            <table>
           
                <tr>
                <td>
                <img src="images/images.jpg" width="300" height="300" class="zoom">
                </td>
               <td>
                <img src="images/images1.jpg" width="300" height="300" class="zoom">
                </td>
                </tr>
                <tr>
                <td>
                <img src="images/images2.jpg" width="300" height="300" class="zoom">
                </td>
               <td>
                <img src="images/images3.jpg" width="300" height="300" class="zoom">
                </td>
                </tr>
               
            </table>
           <!-- </fieldset>-->
        </div>
        </form>
        
    </body>
</html>

