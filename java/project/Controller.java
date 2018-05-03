package project;

import HandleData.LightData;
import HandleData.Member;
import HandleData.Questionary;
import database.DatabaseInsert;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller extends HttpServlet {
	public HashMap<String, Dispatcher> dispatchTable;
	public HttpSession session;

	public Controller(){
		dispatchTable = new HashMap<String, Dispatcher>();
		dispatchTable.put(Commands.buttonLogin, new Handler_Login());
		dispatchTable.put(Commands.param_CNTRL_Logout,new Handler_Logout());
		dispatchTable.put(Commands.buttonRegister, new Handler_Register());
		dispatchTable.put(Commands.Ctrl_Register, new DO_Register());
		dispatchTable.put(Commands.Ctrl_Questionary,new Do_Questionary());
		dispatchTable.put(Commands.buttonForm,new Go_to_Form());
		dispatchTable.put(Commands.buttonChangeProfil,new Go_to_ChangeProfil());
		dispatchTable.put(Commands.param_CNTRL_GoToNode,new Update_Node());
		dispatchTable.put(Commands.buttonUpdateNode,new  UpdateNodeBase());
		dispatchTable.put(Commands.buttonHome,new Go_to_Home());
		dispatchTable.put(Commands.buttonUpload,new Handler_GoTo_Upload());
                dispatchTable.put(Commands.buttonAdminUpload,new Handler_GoTo_AdminUpload());
		dispatchTable.put(Commands.buttonGallery,new Handler_GoTo_Request_Gallery());
                dispatchTable.put(Commands.buttonAdminGallery,new Handler_GoTo_AdminGallery());
		dispatchTable.put(Commands.buttonPerDay,new Handler_Statistic_PerDay());
		dispatchTable.put(Commands.buttonPerPeriod,new Handler_Statistic_PerPeriod());
		dispatchTable.put(Commands.buttonData,new Go_to_Statistic());
		dispatchTable.put(Commands.ERROR,new Go_to_Error());
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		session = request.getSession(true);
		boolean flag = false;

		Enumeration<String> allParamNames = request.getParameterNames();
		while(allParamNames.hasMoreElements()){
			String tmp = allParamNames.nextElement();
			if(dispatchTable.containsKey(tmp)){
				flag = true;
				dispatchTable.get(tmp).execute(request, response);
			}
		}
		if(!flag){

			dispatchTable.get(Commands.ERROR).execute(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		handle(request, response);
	} 

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		handle(request, response);
	}

	public void forward(String addr, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		RequestDispatcher rd = getServletContext().getRequestDispatcher(addr);
		try {
			rd.forward(request, response);
		} catch (ServletException ex) {
			System.err.println(ex);
		}
	}

	private interface Dispatcher{
		public void execute(HttpServletRequest request, HttpServletResponse response);
	}

	private class Handler_Login implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String userName = request.getParameter(Commands.paramLogin);
			String passWord = request.getParameter(Commands.paramPassword);
			Timestamp lastlogin =null;

			DatabaseInsert connector = new DatabaseInsert();
			Boolean loginState = connector.checkPassword(userName, passWord);


			lastlogin = connector.get_Lastlogin(userName);
			String Node=connector.get_Node(userName);


			if(loginState){
				session.setAttribute(Commands.attrUserName, userName); 
				session.setAttribute(Commands.paramFirstLogin,userName);
				session.setAttribute(Commands.paramLastLogin, lastlogin);
				session.setAttribute(Commands.paramNode,Node); 

				if(userName.equals("admin")){
					try {
						String users[]=connector.getAllUsers();

						request.setAttribute(Commands.allUsers,users);
						forward("/admin_main.jsp",request,response);
                                        } catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				else{
					try {
						forward("/main.jsp",request,response);
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			else{
				request.setAttribute(Commands.attrError, "You login or password are wrong!!");
				try {
					forward("/login.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	private class Handler_Register implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				forward("/registration.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private class Handler_Logout implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String userName = (String)session.getAttribute(Commands.attrUserName);
			DatabaseInsert connector = new DatabaseInsert();
			try {
				connector.UpdateLastLogin(userName);
			} catch (SQLException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
			session.removeAttribute(Commands.attrUserName);
			session.removeAttribute(Commands.paramFirstLogin);
			request.setAttribute(Commands.attrUserName, null);
			request.setAttribute(Commands.paramFirstLogin, null);
			try {
				forward("/login.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	private class DO_Register implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				String userName = request.getParameter(Commands.paramLogin);
				String passWord = request.getParameter(Commands.paramPassword);
				String firstName = request.getParameter(Commands.paramFirstName);
				String lastName = request.getParameter(Commands.paramLastName);
				String mail = request.getParameter(Commands.paramMail);
				String gender=request.getParameter(Commands.paramSex);
				String  age=request.getParameter(Commands.paramAge);
				String job=request.getParameter(Commands.paramJob);


				Member user = new Member(userName, passWord);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date parsedDate = dateFormat.parse(age);
				java.sql.Timestamp AgeDate = new java.sql.Timestamp(parsedDate.getTime()); 


				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setMail(mail);
				user.setAde(AgeDate);
				user.setGender(gender);
				user.setProfession(job);


				DatabaseInsert connector = new DatabaseInsert();
				Boolean loginState = connector.insertUserIntoBase(user);

				if(loginState){
					session.setAttribute(Commands.attrUserName, userName);
                                        forward("/main.jsp",request,response);
				}
				else{
					request.setAttribute(Commands.ERROR, "You WRONG!!");
					forward("/error.jsp",request,response);
				}
			} 
                         catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        catch (ParseException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
		} 
	}

	private class Go_to_Form implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				forward("/form.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	private class Go_to_ChangeProfil implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				String newlogin = request.getParameter(Commands.buttonChangeProfil);
				session.setAttribute(Commands.attrUserName, newlogin);
				forward("/main.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	private class Update_Node implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				String newlogin = request.getParameter(Commands.param_CNTRL_GoToNode);
				session.setAttribute(Commands.attrNodeUset,newlogin);
				forward("/Node.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private class Go_to_Error implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				forward("/error.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	private class UpdateNodeBase implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String name= (String)session.getAttribute(Commands.attrNodeUset);
			String node=request.getParameter(Commands.attrUpdateNode);
			DatabaseInsert connector = new DatabaseInsert();
			boolean state=connector.updateAdminNode(name, node);
			if(state){
				try {
                                        String users[]=connector.getAllUsers();
                                        request.setAttribute(Commands.allUsers,users);
					forward("/admin_main.jsp",request,response);
				}
                                catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else{
				try {
					request.setAttribute(Commands.ERROR, "You node is fail!!");
					forward("/error.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	private class Go_to_Home implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String userName = (String)session.getAttribute(Commands.attrUserName);
			String usefiName = (String)session.getAttribute(Commands.paramFirstLogin);

			DatabaseInsert connector = new DatabaseInsert();
			if(userName.equals("admin")||usefiName.equals("admin")){
				try {
					String users[]=connector.getAllUsers();

					session.setAttribute(Commands.attrUserName,usefiName);
					request.setAttribute(Commands.allUsers,users);
					forward("/admin_main.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else{
				try {
					forward("/main.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	private class Go_to_Upload implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				forward("/upload.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	} 
        

	private class Handler_GoTo_Upload implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String userName = (String)session.getAttribute(Commands.attrUserName);
			if(userName!=null){
				DatabaseInsert connector = new DatabaseInsert();
				String uuids[] = connector.getPhotoGalleryUUIDs(userName);
				request.setAttribute(Commands.attrPhotoUUIDs, uuids);
				try {
					forward("/upload.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else{
				request.setAttribute(Commands.attrError, "File upload faile");
				session.removeAttribute(Commands.attrUserName);
				try {
					forward("/main.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
        	private class Handler_GoTo_AdminUpload implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			try {
				forward("/upload_into_gallery.jsp",request,response);
			} catch (IOException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	private class Do_Questionary implements Dispatcher{
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String userName = (String)session.getAttribute(Commands.attrUserName);

			Integer energy = Integer.parseInt(request.getParameter(Commands.EnergyLevel));
			Integer Socialization = Integer.parseInt(request.getParameter(Commands.Socialization));
			Integer Phsihology = Integer.parseInt(request.getParameter(Commands.DepressionLevel));
			Integer activity = Integer.parseInt(request.getParameter(Commands.ActivityLevel));
			Float weight=Float.parseFloat(request.getParameter(Commands.Weight));
			String sleeptime = request.getParameter(Commands.SleepTime);
			String wakeup = request.getParameter(Commands.WakeupTime);
			String sleepdate = request.getParameter(Commands.SleepDate);
			String wakedate = request.getParameter(Commands.WakeupDate);
                        
                        
                        
			StringBuilder sb = new StringBuilder(100000); 
			StringBuilder sb1 = new StringBuilder(100000); 
			String sleep=sb.append(sleepdate).append(" ").append(sleeptime).toString();
			String wake=sb1.append(wakedate).append(" ").append(wakeup).toString();

			Questionary quest = new Questionary(userName);
			quest.setUserName(userName);
			quest.setSocial(Socialization);
			quest.setEnergy(energy);
			quest.setDepres(Phsihology);
			quest.setActivity(activity);
			quest.setweight(weight);


			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");


			try { 
				java.util.Date parsedDate = dateFormat.parse(sleep);
				java.util.Date parsedDate2 = dateFormat.parse(wake);
				java.sql.Timestamp timestampsleep = new java.sql.Timestamp(parsedDate.getTime()); 
				java.sql.Timestamp timestampwake = new java.sql.Timestamp(parsedDate2.getTime()); 

				quest.setsleepdate(timestampsleep);
				quest.setwakedate(timestampwake);



			} catch (ParseException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}

			DatabaseInsert connector = new DatabaseInsert();
			Boolean insertState = connector.insertQuestionaryIntoBase(quest);
			if(insertState == true){
				try {
					forward("/main.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

	}
	private class Handler_GoTo_Request_Gallery implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String userName = (String)session.getAttribute(Commands.attrUserName);
			if(userName!=null){
				try {
					forward("/gallery.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else{
				session.removeAttribute(Commands.attrUserName);
				try {
					forward("/main.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
        	private class Handler_GoTo_AdminGallery implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){
			String userName = (String)session.getAttribute(Commands.attrUserName);
			if(userName!=null){
				try {
					forward("/admin_gallery.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else{
				session.removeAttribute(Commands.attrUserName);
				try {
					forward("/main.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	private class Go_to_Statistic implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){

			try {
                    String userName = (String)session.getAttribute(Commands.attrUserName);
                    DatabaseInsert connector = new DatabaseInsert();
                    
                            Timestamp []emotionTime=connector.getStatisticEmotionTime(userName);
                            String[] soc=connector.TableStatsticSocalization(userName);
                            String[] ene=connector.TableStatsticEnergy(userName);
                            String[] act=connector.TableStatsticActivity(userName);
                            String[] dep=connector.TableStatsticPhyco(userName);
                            String[] weight=connector.TableStatsticWeight(userName);
                            String []SleepHours=connector.getStatisticSleepHours(userName);
                            String []AccTable=connector.getStatisticAccelTable(userName);
                            LightData[] tmp=connector.table_LightData_Statistic(userName);
                            Timestamp []sleepDay=connector.getSleepDay(userName);
                            Timestamp []AccelDay=connector.get_AccData_Time_Table(userName);

                            request.setAttribute(Commands. attrEmotionTimeTable,emotionTime);
                            request.setAttribute(Commands.attrSosialTable,soc);
                            request.setAttribute(Commands.attrEnergyTable,ene);
                            request.setAttribute(Commands.attrActivityTable,act);
                            request.setAttribute(Commands.attrPhycoTable,dep);
                            request.setAttribute(Commands.attrWeightTable,weight);
                            request.setAttribute(Commands.attrSleepTable,SleepHours);
                            request.setAttribute(Commands.attrSleepDayTable,sleepDay);
                            request.setAttribute(Commands.attrAccelTable,AccTable);
                            request.setAttribute(Commands.attrAccelDayTable,AccelDay);
                            request.setAttribute(Commands.attrLightStatistic,tmp);

                    try {
                            forward("/Data.jsp",request,response);
                    } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
            } catch (ParseException ex) {
				Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	} 
	private class Handler_Statistic_PerDay implements Dispatcher{ 
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){

			String PlottingType = (String)request.getParameter(Commands.attrPlotTypeDay);
			String RequestDate = request.getParameter(Commands.attrRequestDate);

			String userName = (String)session.getAttribute(Commands.attrUserName);
			if(PlottingType.equals("LightData")){
				
					DatabaseInsert connector = new DatabaseInsert();
					
					LightData[] test=connector.get_LightData(RequestDate ,userName);
                                       
                                        try {
                                            if(test != null){
                                                request.setAttribute(Commands.attrTestLight, test);
                                                forward("/PlotDayLight.jsp",request,response);
                                            }
                                            else{
                                                request.setAttribute(Commands.attrError, "Could not find Light Data for this Date");
                                                forward("/gallery.jsp",request,response);
                                            }
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				

			}
			else if(PlottingType.equals("Location")){
				try {

					DatabaseInsert connector = new DatabaseInsert();
					Float Latitude[]= connector.get_Latitude_Day(RequestDate,userName);
					Float Longtitude[]= connector.get_Longtitude_Day(RequestDate,userName);
					Timestamp [] Map_time=connector.get_Map_Time_Day(RequestDate,userName);

					
					try {
                                            if(Latitude!=null){
                                                request.setAttribute(Commands.attrLatitudeData,Latitude);
                                                request.setAttribute(Commands.attrLongtitudeData,Longtitude);
                                                request.setAttribute(Commands.attrDateTimeMap, Map_time);
                                                forward("/PlotLocation.jsp",request,response);
                                            }
                                            else{
                                               request.setAttribute(Commands.attrError, "Could not find GPS Data for this Date");
                                               forward("/gallery.jsp",request,response); 
                                            }
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				} catch (ParseException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if(PlottingType.equals("Accelerometr")){
				try {

					DatabaseInsert connector = new DatabaseInsert();
					Float x[]= connector.get_Acc_X_Day(RequestDate,userName);
					Float y[]= connector.get_Acc_Y_Day(RequestDate,userName);
					Float z[]= connector.get_Acc_Z_Day(RequestDate,userName);
					Timestamp [] Time=connector.get_AccData_Time_Day(RequestDate,userName);

					
					try {
                                             if(x != null){
                                                request.setAttribute(Commands.attrAccX,x);
                                                request.setAttribute(Commands.attrAccY,y);
                                                request.setAttribute(Commands.attrAccZ,z);
                                                request.setAttribute(Commands.attrAccTime,Time);
                                                request.setAttribute(Commands.attrRequestDate, RequestDate);
                                                forward("/PlotDayAcc.jsp",request,response);
                                            }
                                             else{
                                                request.setAttribute(Commands.attrError, "Could not find Accelerometr Data for this Date");
                                                forward("/gallery.jsp",request,response);
                                             }
						
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				} catch (ParseException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else{
				try {
					// session.removeAttribute(Commands.attrUserName);
					forward("/main.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}

			}


		}
	}
	private class Handler_Statistic_PerPeriod implements Dispatcher {
		@Override
		public void execute(HttpServletRequest request, HttpServletResponse response){

			String PlottingType = (String)request.getParameter(Commands.attrPlotTypePeriod);
			String userName = (String)session.getAttribute(Commands.attrUserName);
			String StartPeriod=(String)request.getParameter(Commands.attrStartPeriod);
			String EndPeriod=(String)request.getParameter(Commands.attrFinishPeriod);

			if(PlottingType.equals("LightData") ){
                                        
                                        DatabaseInsert connector = new DatabaseInsert();
					
					LightData[] test=connector.get_LightData(StartPeriod ,userName);
                                    
					try {
                                             if(test != null){
                                                request.setAttribute(Commands.attrTestLight, test);
                                                forward("/PlotPeriodLight.jsp",request,response);
                                            }
                                            else{
                                                request.setAttribute(Commands.attrError, "Could not find Light Data for this Dates");
                                                forward("/gallery.jsp",request,response);
                                            }
						
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				

			}
			else if(PlottingType.equals("Location")){
				try {

					DatabaseInsert connector = new DatabaseInsert();

					Float Longtitude[]= connector.get_Longtitude_Period(StartPeriod,EndPeriod,userName);
					Float Latitude[]= connector.get_Latitude_Period(StartPeriod,EndPeriod,userName);
					Timestamp[] Map_Time=connector.get_Map_Time_Period(StartPeriod,EndPeriod,userName);

					try {
                                            if(Longtitude!=null){
                                                request.setAttribute(Commands.attrStartPeriod,StartPeriod);
                                                request.setAttribute(Commands.attrFinishPeriod,EndPeriod);
                                                request.setAttribute(Commands.attrLongtitudeData, Longtitude);
                                                request.setAttribute(Commands.attrLatitudeData, Latitude);
                                                request.setAttribute(Commands.attrDateTimeMap,Map_Time);
						forward("/PlotLocation.jsp",request,response);
                                            }
                                            else{
                                                request.setAttribute(Commands.attrError, "Could not find GPS Data for this Dates");
                                                forward("/gallery.jsp",request,response);
                                            }
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				} catch (ParseException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if(PlottingType.equals("Accelerometr")){
				try {

					DatabaseInsert connector = new DatabaseInsert();

					Float x[]= connector.get_Acc_X_Period(StartPeriod,EndPeriod,userName);
					Float y[]= connector.get_Acc_Y_Period(StartPeriod,EndPeriod,userName);
					Float z[]= connector.get_Acc_Z_Period(StartPeriod,EndPeriod,userName);
					Timestamp[] Time=connector.get_AccData_Time_Period(StartPeriod,EndPeriod,userName);

					try {
						
                                             if(x != null){
                                                request.setAttribute(Commands.attrStartPeriod,StartPeriod);
                                                request.setAttribute(Commands.attrFinishPeriod,EndPeriod);
                                                request.setAttribute(Commands.attrAccX, x);
                                                request.setAttribute(Commands.attrAccY, y);
                                                request.setAttribute(Commands.attrAccZ, z);
                                                request.setAttribute(Commands.attrAccTime, Time);
                                                 forward("/PlotPeriodAcc.jsp",request,response);
                                            }
                                             else{
                                                request.setAttribute(Commands.attrError, "Could not find Accelerometr Data for this Dates");
                                                forward("/gallery.jsp",request,response);
                                             }
                                            
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				} catch (ParseException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else if(PlottingType.equals("Emotion")){
				try {

					DatabaseInsert connector = new DatabaseInsert();

					Integer energy[]= connector.get_EnergyLevel(StartPeriod,EndPeriod,userName);
					Integer activity[]= connector.get_ActivityLevel(StartPeriod,EndPeriod,userName);
					Integer phyco[]= connector.get_PhycoLevel(StartPeriod,EndPeriod,userName);
					Integer shocial[]= connector.get_SocialLevel(StartPeriod,EndPeriod,userName);
					Timestamp[] Time=connector.get_Questionary_Time_Period(StartPeriod,EndPeriod,userName);

					try {
                                            if(energy!=null ||activity!=null ||phyco!=null ||shocial!=null ){
                                                request.setAttribute(Commands.attrStartPeriod,StartPeriod);
                                                request.setAttribute(Commands.attrFinishPeriod,EndPeriod);
                                                request.setAttribute(Commands.attrEnergyLevel, energy);
                                                request.setAttribute(Commands.attrAcivityLevel, activity);
                                                request.setAttribute(Commands.attrPhycoLevel, phyco);
                                                request.setAttribute(Commands.attrSocialLevel, shocial);
                                                request.setAttribute(Commands.attrQuestionaryTime, Time);
                                                forward("/PlotForm.jsp",request,response);

                                            }
                                            else{
                                                request.setAttribute(Commands.attrError, "Could not find Personal Data for this Dates");
                                                forward("/gallery.jsp",request,response);
                                            }
						
					} catch (IOException ex) {
						Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
					}
				} catch (ParseException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else{
				try {
					 request.setAttribute(Commands.attrError, "Could not find  Data for this Period");
                                         forward("/gallery.jsp",request,response);
				} catch (IOException ex) {
					Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
