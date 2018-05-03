

<%@page import="project.Commands"%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <% String firstname=(String)session.getAttribute(Commands.paramFirstLogin); %>
        <title></title>
<script src="javascript/menuscript.js" language="javascript" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/ProjectStyle.css" media="screen, print" >

    </head>
    <body bgcolor="white">
    

       
        <div  id="LeftDiVContainer">
           
            <fieldset class="UserFieldset" >
                <a href="Controller?<%=Commands.buttonUpload %>"> <img src="images/logo.jpg" alt="logo"  width="142" /></a>
            </fieldset>
        
<table border="0" cellpadding="0" cellspacing="0"><tr><td>
<a href="Controller?<%=Commands.buttonHome %>" onmouseover="setOverImg('1','');" onmouseout="setOutImg('1','');" target=""><img src="buttons/button1up.png"  height="30" border="0" id="button1"  vspace="10" hspace="0"></a><br>
   
<a href="Controller?<%=Commands.buttonForm %>" onmouseover="setOverImg('2','');" onmouseout="setOutImg('2','');" target=""><img src="buttons/button2up.png" height="30" border="0" id="button2" vspace="10" hspace="0"></a><br>
<a href="Controller?<%=Commands.buttonGallery%>" onmouseout="setOutImg('3','');" target=""><img src="buttons/button3up.png" border="0" id="button3" height="30" vspace="10" hspace="0"></a><br>
<a href="Controller?<%=Commands.buttonData %>" onmouseover="setOverImg('1','');" onmouseout="setOutImg('1','');" target=""><img src="buttons/button4up.png"  height="30" border="0" id="button4"  vspace="10" hspace="0"></a><br>
 <%if(firstname.equals("admin")){%>
<a href="Controller?<%=Commands.buttonAdminUpload%>" onmouseout="setOutImg('3','');" target=""><img src="buttons/button5up.png" border="0" id="button3" height="30" vspace="10" hspace="0"></a><br>
<a href="Controller?<%=Commands.buttonAdminGallery %>" onmouseover="setOverImg('1','');" onmouseout="setOutImg('1','');" target=""><img src="buttons/button7up.png"  height="30" border="0" id="button4"  vspace="10" hspace="0"></a><br>

<%}%>
        </td></tr></table>

       
            
        </div>
           
        
    </body>
</html>
