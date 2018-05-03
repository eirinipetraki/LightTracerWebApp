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
public class Member {
   
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String mail;
    private String phone;
    private Date currDate;
    private String gender;
    private String profession;
    private Timestamp age;
    

    public Member(String username) {
        this.userName = username;
    }

    public Member(String username, String password) {
        this.userName = username;
        this.passWord = password;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setPassWord(String password) {
        this.passWord = password;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

   

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCurrentDate(Date currdate){
        this.currDate=currdate;
        
    }
    public void setAde(Timestamp age){
        this.age=age;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public void setProfession(String profession){
        this.profession=profession;
    }
    public Timestamp getAge() {
        return this.age;
    }
    public String getGender() {
        return this.gender;
    }
    public String getProfession() {
        return this.profession;
    }
    public Date getQuestionaryDate(){
        return this.currDate;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    
    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getMail() {
        return this.mail;
    }

   
}


