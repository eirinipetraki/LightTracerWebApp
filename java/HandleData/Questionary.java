/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HandleData;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
/**
 *
 * @author eirini
 */
public class Questionary {
    private String login;
    private Date currdate;//Insert current date from db
    private Integer Social ;
    private Integer energy;
    private Integer depression;
    private Integer activity;
    private Float weight;
    private Timestamp sleep;
     private Timestamp wake;
    
    
       public Questionary(String username) {
        this.login= username;
    }

    public  Questionary(String username, Date time) {
        this.login=username;
        this.currdate=time;
    }

public void setsleepdate(Timestamp sleep){
    this.sleep=sleep;
}
public Timestamp getsleepdate(){
    return this.sleep;
}
public void setwakedate(Timestamp wake){
    this.wake=wake;
    
}
public Timestamp getwakedate(){
    return this.wake;
}

public void setweight(Float weight){
   this.weight=weight;
}
public Float getweight(){
    return this.weight;
}
    public void setUserName(String username) {
        this.login= username;
    }

    public void setcurrDate(Date time) {
        this.currdate=time;
    }

    public void setSocial(int social) {
        this.Social=social;
    }

    public void setEnergy(int energy) {
        this.energy=energy;
    }
     public void setDepres(int depresion) {
        this.depression=depresion;
    }
      public void setActivity(int act) {
        this.activity=act;
    }
   
        
public String getUserName() {
       return this.login;
    }

    public Date getcurrDate() {
       return this.currdate;
    }

    public Integer getSocial() {
        return this.Social;
    }

    public Integer getEnergy() {
        return this.energy;
    }
     public Integer getDepres() {
        return this.depression;
    }
      public Integer getActivity() {
        return this.activity;
    }
    



}
