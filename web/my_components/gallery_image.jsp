<%@page import="project.Commands"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.*" %> 

<% String loginname=(String)request.getParameter("login"); %>
<%
      String JDBC_DRIVER ="com.mysql.jdbc.Driver";
      String DATABASE_URL="jdbc:mysql://localhost/SensorDB";
      String USER="root";
      String PASSWORD="pE12_a";
      Connection connection = null;
      Statement statement = null;
      System.out.println("galuser"+loginname);
    String sqlStmt = "SELECT Picture FROM  `SensorDB`.`UserData` WHERE login = '" + loginname + "'" ;
    try{
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);
        statement = connection.createStatement();
    }
    catch(ClassNotFoundException e){
        System.err.println(e);
    }
    catch(SQLException e){
        System.err.println(e);
    }
    ResultSet res = statement.executeQuery(sqlStmt);
    byte[] image = null;
    Blob blob = null;
    if(res.next()){
        blob = res.getBlob("Picture");
    }

    if(blob != null){
        try {
            image = blob.getBytes(1, (int)blob.length());
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
%>
<%
    if(image!=null){
      
        int size=image.length;
        response.reset();
        response.setContentType("image/png");
        response.setContentLength(size);
  
        ServletOutputStream o = response.getOutputStream();
        o.write(image);
        o.flush(); 
        o.close();
    }
    else{
          System.out.println("null");
        ServletOutputStream o = response.getOutputStream();
        o.println("Image NULL");
        o.flush();
        o.close();
    }
%>
<%
    try{
        if(!statement.isClosed())
            statement.close();
        if(!connection.isClosed())
            connection.close();
    }
    catch(SQLException e){
        System.err.println(e.getMessage());
    }
%>
   <!--   response.setHeader("Content-disposition","attachment; filename=" +  photoUUID);-->