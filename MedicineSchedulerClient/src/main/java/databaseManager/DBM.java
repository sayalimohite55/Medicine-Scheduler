package databaseManager;
import java.sql.*;

	public class DBM
	{
		public Connection con;
		Statement stmt;
		ResultSet rs;
		public void loadDriver(String driver)
		{
			try
			{
				Class.forName(driver);
			}	
			catch(Exception e)
			{
				System.out.println("Exception in DBM for loadDriver"+e.getMessage());
			}
		} 
		
		public void getConnection(String connection)
		{
			try
			{   
				con=DriverManager.getConnection(connection,"sayali","psql");
				stmt=con.createStatement();
			}
			catch(Exception e)
			{
				System.out.println("Exception in DBM for getConnection"+e.getMessage());		
			}
		}
		
		public ResultSet fetchQuery(String query) throws Exception
		{
			ResultSet rs1=null;
			stmt = con.createStatement();
			try
			{
				rs1=stmt.executeQuery(query);
				return rs1;
			}
			catch(Exception e)
			{	
				e.printStackTrace();
			}
			return null;
		}
		
		public ResultSet fetchReminder(int sid) throws Exception
		{
			ResultSet rs1=null;
			stmt = con.createStatement();
			try
			{
				String query="select * from reminder where EXTRACT(hour FROM dose_time)=EXTRACT(hour FROM CURRENT_TIME) and EXTRACT(minute FROM dose_time)=EXTRACT(minute FROM CURRENT_TIME) and sid="+sid;
				rs1=stmt.executeQuery(query);
				return rs1;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return rs1;
		}
	
		public void closeConnection()
		{
			try 
			{
				stmt.close();
				con.close();
			} 
			catch (Exception e) 
			{
			}
		}
}
