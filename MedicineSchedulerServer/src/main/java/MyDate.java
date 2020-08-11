import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**<h1>Medicine Scheduler MyDate</h1>
 * This class is designed to validate the date
 * entered by user while adding new schedule.
 * @author Sayali Mohite
 * @author Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */

public class MyDate {
	
	int dd,mm,yy,date,month,year;
	static String arr1[],arr2[];
	Date currentDate=new Date();
	Pattern datePattern;
	Matcher dateMatcher;

	/**
	 * This is the constructor of the class MyDate.
	 * In this constructor the this object is intialised 
	 * with the current date.
	 */
	@SuppressWarnings("deprecation")
	MyDate()
	{
		date=currentDate.getDate();
		month=currentDate.getMonth()+1;
		year=currentDate.getYear()+1900;
	}
	
	/**
	 * This method is used to check whether the particular
	 * date is valid or not.
	 * @param inputdate it is date which is to be verified as valid or invalid.
	 * @return boolean it returns true if the date is valid and false otherwise.
	 */
	boolean validDate(String inputdate)
	{
		boolean flag=true;
		
		datePattern=Pattern.compile("^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}$");
		dateMatcher=datePattern.matcher(inputdate);
		if(!dateMatcher.matches())
			return false;
		
		arr1=inputdate.split("/");
		yy=Integer.parseInt(arr1[0]);
		mm=Integer.parseInt(arr1[1]);
		dd=Integer.parseInt(arr1[2]);
		
		if(mm==1 || mm==3 || mm==5 || mm==7 || mm==8 || mm==10 || mm==12)//31
		{
			if(dd<=31);
			else
				flag=false;
		}
		else if(mm==4 || mm==6 || mm==9 || mm==11)//30
		{
			if(dd<=30);
			else
				flag=false;
		}
		else//mm==2
		{
			if(yy%4==0 && yy%100==0 && yy%400==0)
			{
				if(dd<=29);
				else
					flag=false;
			}
			else
			{
				if(dd<29);
				else
					flag=false;
			}
		}
		
		if(flag)//whether greater than or equals to current date
		{
			if(year==yy && month==mm)//same year and same month
			{
				if(dd > date || dd==date);
				else 
					return false;
			}
			else if(year==yy && mm>month);//same year next month
			else if(yy>year);//next year
			else 
				return false;
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used to compare two dates in such a 
	 * way that to check whether the date1 comes earlier than the date2 or not.
	 * @param date1 it is a date which should be earlier than date2.
	 * @param date2 it is a date which should occur after date1.
	 * @return boolean it returns true if the date1<date2 and false otherwise.
	 */
	boolean validDate(String date1,String date2)
	{
		arr1=date1.split("/");
		arr2=date2.split("/");
		
		int num=-1;
		if(Integer.parseInt(arr1[0])<=Integer.parseInt(arr2[0]))//year check
		{
			if(Integer.parseInt(arr1[1])<Integer.parseInt(arr2[1]))//month1 less than month 2
				num=0;
			else 
			{
				if((Integer.parseInt(arr1[1])==Integer.parseInt(arr2[1])))//date check when month is diff.
				{
					if((Integer.parseInt(arr1[2])<=Integer.parseInt(arr2[2])))
					{
						System.out.println("true ahe date");
						num=0;
					}
					else
					{
						System.out.println("false ahe date");
						num=-1;
					}
				}
			}
		}
		
		if(num==0)
		{
			System.out.println("2true");
			return true;
		}
		System.out.println("2false");
		return false;
	}
	
	public static void main(String[] args) {
		MyDate date=new MyDate();
		System.out.println(date.validDate("2016/2/22","2016/2/20"));
	}

}
