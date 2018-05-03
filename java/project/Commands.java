/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;


public class Commands {
    public static final String param_CNTRL_Login = "CMD_LOGIN";
    public static final String param_CNTRL_Logout = "CMD_LOGOUT";
     public static final String Ctrl_Register="Submit_Register";
    public static final String Ctrl_Questionary="Submit_Questionary";
    public static final String param_CNTRL_GoToupload="goupload";
    public static final String param_CNTRL_GoToNode="gotoNode";
    
   /************** Registration **********************************/
    public static final String paramLogin = "Login";
     public static final String paramFirstLogin = "FirstLogin";
    public static final String paramPassword = "Password";
    public static final String paramFirstName = "FirstName";
    public static final String paramLastName = "LastName";
    public static final String paramMail = "Mail";
    public static final String paramSex = "Gender";
    public static final String paramAge = "Age";
    public static final String paramJob = "Job";
    public static final String paramLastLogin="LastLogin";
    public static final String paramNode="DoctorNode";
    /********************** Questionary ********************************/
    public static final String Socialization="Social";
    public static final String EnergyLevel="Energy";
    public static final String DepressionLevel="Depression";
    public static final String ActivityLevel="Activity";
    public static final String WakeupTime="WakeupTime"; //TODO na grapsw function pou upologizei posoi wra upnou
    public static final String SleepTime="SleepTime";
    public static final String SleepDate="SleepDate";
    public static final String WakeupDate="WakeupDate";
    public static final String Weight="Weight";
    
   /****************************************************************************/
    public static final String Admin_upload="Admin_upload_name";
    public static final String Admin_upload_username="Admin_upload_username";
    
    
    public static final String UploadGraph="UploadGraph";
     public static final String paramUploadGraph="paramUploadPhoto";
    public static final String paramUploadGalleryPicName="paramUploadGalleryPicName";
     public static final String attrPhotoUUIDs="PhotoUUIDs";
    
    //buttoms
    public static final String buttonLogin="CTRL_Login";
    public static final String buttonRegister="CTRL_Go_to_Register";
    public static final String buttonForm="CTRL_Go_To_Form";
    public static final String buttonHome="CTRL_Go_To_Home";
    public static final String buttonUpload="CTRL_Go_To_Upload";
     public static final String buttonAdminUpload="CTRL_Go_To_AdminUpload";
    public static final String buttonGallery="CTRL_Go_To_Gallery";
     public static final String buttonAdminGallery="CTRL_Go_To_AdminGallery";
    public static final String buttonData="CTRL_Go_To_Statistic";
     public static final String buttonChangeProfil="CTRL_ChangeProfil";
      public static final String buttonUpdateNode="CTRL_UpdateNode";
     public static final String ERROR="CTRL_Go_To_ERROR";
     
    public static final String buttonPerDay="CTRL_Request_Per_Day";
    public static final String buttonPerPeriod="CTRL_Request_Per_Period";
    public static final String buttonPerDayStatistic="CTRL_Request_Per_Day_statistic";
    public static final String buttonPerPeriodStatistic="CTRL_Request_Per_Period_statistic";
   
    
    //attributes
     public static final String attrUserName = "LoggedUserName";
     public static final String attrError="Error";
     public static final String allUsers="users";
      /* This field is used by photo gallery to return String[] of picture UUIDs */
   
//gallery request attributes
    public static final String attrRequestDate="PerDate";
    public static final String attrStartPeriod="StartPeriod";
    public static final String attrFinishPeriod="FinishPeriod";
    public static final String attrPlotTypeDay="PlotTypeDay";
    public static final String attrPlotTypePeriod="PlotTypePeriod";
//attributes statistic
    public static final String attrTestLight="TestLight";
    public static final String attrTestLightper="TestLightper";
    public static final String attrLightData="LightperDay";
      public static final String attrLightDataTime="LightperDayTime";
       public static final String attrLatitudeData="LatitudeDay";
     public static final String attrLongtitudeData="LongtitudeDay";
      public static final String attrDateTimeMap="TimeMap";
      public static final String attrAccX="x-range";
      public static final String attrAccY="y-range";
      public static final String attrAccZ="z-range";
      public static final String attrAccTime="AccTime";
 //form statistic
      public static final String attrEnergyLevel="energyLevel";
      public static final String attrAcivityLevel="ActivityLevel";
      public static final String attrPhycoLevel="PhycoLevel";
      public static final String attrSocialLevel="SocialLevel";
      public static final String attrQuestionaryTime="QuestionaryTime";
  //table attr
      public static final String attrAddressTable="";
      public static final String attrEmotionTimeTable="EmotionTimeTable";
      public static final String attrSosialTable="SocializationForTable";
      public static final String attrEnergyTable="EnergyForTable";
      public static final String attrActivityTable="ActivityForTable";
      public static final String attrPhycoTable="PhycoForTable";
      public static final String attrWeightTable="WeightForTable";
      public static final String attrSleepTable="SleeptForTable";
      public static final String attrSleepDayTable="SleepDayForTable";
      public static final String attrAccelTable="AccelTable";
      public static final String attrAccelDayTable="AccelDayTable";
       public static final String attrUpdateNode="Node";
        public static final String attrNodeUset="Nodeuser";
         public static final String attrLightStatistic="LightStatistic";
}
