package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    protected static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
    protected static final String DATABASE_URL="jdbc:mysql://localhost/SensorDB";
    protected static final String USER="root";
    protected static final String PASSWORD="pE12_a";
    protected Connection connection = null;
    protected Statement statement = null;
    
    public void connect() {
        
        try{
            if(
                this.connection!=null &&
                !this.connection.isClosed() &&
                this.statement!=null &&
                !this.statement.isClosed()
            ) return;
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);
            this.statement = this.connection.createStatement();
        }
        catch(ClassNotFoundException e){
            System.err.println(e);
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }

    public Statement getStatement(){
        try{
            if(connection!=null && !connection.isClosed()){
                if(statement!=null && !statement.isClosed()){
                    return this.statement;
                }
                else{
                    this.statement = connection.createStatement();
                    return this.statement;
                }
            }
            else{
                this.connect();
                return this.statement;
            }
        }
        catch(SQLException e){
            System.err.println(e);
            return null;
        }
    }

    public void close(){
        try{
            if(!statement.isClosed())
                statement.close();
            if(!connection.isClosed())
                connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
