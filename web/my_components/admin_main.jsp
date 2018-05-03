
<%@page import="java.util.Vector"%>
<%@page import="HandleData.Member"%>
<%@page import="database.DatabaseInsert"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="project.Commands"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        
        <% String loginname=(String)session.getAttribute(Commands.attrUserName); %>
        <% String FirstName=(String)session.getAttribute(Commands.paramFirstLogin); %>
        <% Timestamp Lastlogin=(Timestamp)session.getAttribute(Commands.paramLastLogin); %>
        <%String Node=(String)session.getAttribute(Commands.paramNode);%>
        <%String users[]=(String[])request.getAttribute(Commands.allUsers);%>
        <% DatabaseInsert connector = new DatabaseInsert();
          Vector<String> table = new Vector<String>();%>
        <title>Home Page</title>
        <link rel="stylesheet" type="text/css" href="/css/ProjectStyle.css" media="screen, print" >
 
    </head>
<body>
 <% for(int i=0;i<users.length;i++){
             
             if(!users[i].equals("admin")){
                 table.add(users[i]);
             }                   
                            }               
            %>
    <div  id="HomeDiVContainer">
        <p align="center">Welcome  "<%=loginname%>"
        
            <br> Last login at <%=Lastlogin%> </p>
          <table>
            <% for(int j=0;j<table.size();j++){
             
                
             Member user= connector.get_usefinfo(table.elementAt(j)); 
                               
            %>
                <% if(j==0){ %>
                <tr>
                <% } else if(j%2==0){ %>
                </tr>
                <tr>
                <% } %>
                <td>
                <fieldset class="UserInfo" ><legend>User info</legend>
                       <form  method="post" action="Controller">
            
            <table>
                <tr>
                    <td>
            <img src="my_components/gallery_image.jsp?login=<%=table.elementAt(j)%>" width="50" height="50">
                    </td>
                    <td>
            <%=user.getFirstName()%> <%=user.getLastName()%>
            
                    </td>
            </tr>
            <tr>
                <td>
                    
             <a href="Controller?<%=Commands.buttonChangeProfil%>=<%=table.elementAt(j)%>"><img src="buttons/statistic.png"   id="button4" ></a>
                </td>
                <td> <a href="Controller?<%=Commands.param_CNTRL_GoToNode%>=<%=table.elementAt(j)%>"><img src="buttons/postNote.png"   id="button4" ></a></td>
            </tr>
            </table>
                       </form>
        </fieldset>
                <td>
                <% if(j==users.length-1){ %>
                </tr>
                <% } %>
            <% }%>
            </table>
        
    </div>
</body>
</html>
