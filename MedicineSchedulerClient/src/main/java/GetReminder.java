import java.sql.Date;
import java.sql.ResultSet;

import databaseManager.DBM;


class GetReminder implements Runnable
{	
	Thread threadReminder;
	int flag=0;
	DBM db=new DBM();

	int sid;
	Date fromDate,toDate;
	String description,prescribed_by;
	
	int rid;
	String dose_time;
	String mediname,qty,precautions;
	
	String phonenumber;
	
	public GetReminder()
	{
	    db.loadDriver("org.postgresql.Driver");
	    db.getConnection("jdbc:postgresql://192.168.1.41/MedicineScheduler");
		threadReminder=new Thread(this);
		threadReminder.start();
	}  
	
	public void timeCheck() throws Exception	
	{
		ResultSet rsTimeDate=db.fetchQuery("select * from schedule where CURRENT_DATE >= fromdate and CURRENT_DATE<=todate");
		while(rsTimeDate.next())
		{
			sid=rsTimeDate.getInt(1);
			fromDate=rsTimeDate.getDate(2);
			toDate=rsTimeDate.getDate(3);
			description=rsTimeDate.getString(4);
			prescribed_by=rsTimeDate.getString(5);
			System.out.println(sid+" "+fromDate+" "+toDate+" "+description+" "+prescribed_by);
			
			ResultSet reminderresult=db.fetchReminder(sid);
			while(reminderresult.next())
			{
				System.out.println("Reminder id is "+reminderresult.getString(1));
				rid=reminderresult.getInt(1);
				dose_time=reminderresult.getTime(2).toString();
				mediname=reminderresult.getString(3);
				qty=reminderresult.getString(4);
				precautions=reminderresult.getString(5);
				System.out.println(rid+" "+dose_time+" "+mediname+" "+qty+" "+precautions);
				flag=1;
			}
			
			ResultSet rsphonenum=db.fetchQuery("select phno from user1 where uid=(select uid from schedule where sid="+sid+")");
			while (rsphonenum.next()) 
			{
				phonenumber=rsphonenum.getString(1);
				System.out.println(phonenumber);
			}
		}
	}
	
	public static void main(String[] args) 
	{	
		new GetReminder();
	}

	public void run()
	{
		while(true)
		{
			if(flag==0)
			{
				try 
				{
					Thread.sleep(1000);
					timeCheck();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			else 
			if(flag==1)
			{
				try 
				{
					System.out.println("Audio");
					new SendSms(phonenumber,"Take Your Pills !!!");
					new AudioTest();
					new PopUpReminder(rid,dose_time,mediname,qty,precautions);
					new DetectMotionExample();
					flag=0;
					Thread.sleep(60000);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}	
	}
}
