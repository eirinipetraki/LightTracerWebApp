/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HandleData;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author eirini
 */
public class LightData {
   
    private String login;
    private Integer sensor_id;
    private Integer value;
    private Timestamp date;
    

    public LightData(String username) {
        this.login = username;
    }

    public LightData(String username, Integer sensor_id) {
        this.login = username;
        this.sensor_id = sensor_id;
    }

    public void setLOGIN(String login){
        this.login=login;
    }
    public void setSensor_id(Integer sen_id){
        this.sensor_id=sen_id;
    }
    public void setDate(Timestamp date){
        this.date=date;
    }
    public void setValue(Integer value){
        this.value=value;
    }
     public String getLOGIN(){
        return this.login;
    }
    public Integer getSensor_id(){
        return this.sensor_id;
    }
    public Timestamp getDate(){
       return  this.date;
    }
    public Integer getValue( ){
       return this.value;
    }
   
}


