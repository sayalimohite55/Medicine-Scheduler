package databaseManager;
/**<h1>Medicine Scheduler DBManager</h1>
 * This class is designed for the database 
 * connectivity with the java programs to 
 * perform certain operations.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author Sayali Mohite
 * @author Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */

public class DBManager {
	public Connection con;//Connection object
	public Statement st;//Statement object
	
	/**
	 * This method is used to load the
	 * Driver of the respective database.
	 * Here it is postgresql Driver.
	 * @exception ClassNotFoundException this exception is thrown by the forName() method of the class Class.  
	 */
	public void loader()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch(Exception e)
		{
			System.out.println("Exception in LoadDriver"+e.getMessage());
		}
	}
	
	/**
	 * This method is written to get the 
	 * connectivity between the java program and the 
	 * database on the machine.
	 * @exception SQLException this exception is thrown by the getConnection method of the DriverManager class.
	 * @exception SQLException this exception is thrown by the createStatement method of the Connection class.
	 */
	public void getConnection()
	{ 
		try
		{
			con=DriverManager.getConnection("jdbc:postgresql:MedicineScheduler","sayali","psql");
			st=con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Exception in getConnection"+e.getMessage());
		}
	}
	
	/**
	 * This method is used to get the recently added records' primary key or the max no from the primary key.
	 * @param query It is a query which is to be executed on database
	 * @return integer the max id is returned
	 * @exception SQLException this exception is thrown by the executeQuery() method of the Statement class.
	 * @exception SQLException this exception is thrown by the next() method of the ResultSet class.
	 * @exception SQLException this exception is thrown by the getInt() method of the ResultSet class.
	 */
	public int getMaxId(String query)
	{
		int id=1;
		try
		{
			ResultSet rs = st.executeQuery(query);
			rs.next();
			id=rs.getInt(1);
			id=id+1;
			System.out.println("id in dbmanager "+id);
			return id;
		}
		catch(Exception e)
		{
			System.out.println("Exception in getMaxId "+e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * This method is used to fetch the results from the database.
	 * @param query it is the query which is to be executed onto the database to fetch results.
	 * @return ResultSet it returns ResultSet object containing the fetched results.
	 * @exception SQLException this exception is thrown by the executeQuery() method of the Statement class.
	 */
	public ResultSet fetchQuery(String query)
	{
		ResultSet rs=null;
		try
		{
			rs = st.executeQuery(query);
		}catch(Exception e)
		{
			System.out.println("Exception in fetchQuery"+e.getMessage());
		}		
		return rs;
	}
	
/*	public void insertRecord(String query)
	{
		try
		{
				int rs = st.executeUpdate(query);
				if(rs>0)
					System.out.println("Inserted Successfully!!!!!!!!!!!");
		}catch(Exception e)
		{
			System.out.println("Exception in insertRecord"+e.getMessage());
		}	
	}
	*/
	/**
	 * This method is used to delete the records from the database.
	 * @param query it is the query which is to be executed onto the database.
	 * @return boolean it returns true if the record is deleted and false otherwise.
	 * @exception SQLException this exception is thrown by the executeUpdate() method of the Statement class.
	 */
	public boolean deleteRecord(String query)
	{
		try
		{
			int rs = st.executeUpdate(query);
			if(rs>0){
				System.out.println("Deleted Successfully!!!!!!!!!!!");
				return true;
			}
			else 
				return false;
		}catch(Exception e)
		{
			System.out.println("Exception in deleteRecord"+e.getMessage());
		}	
		return false;
	}
	
/*	public String getSecQuestion(String username)
	{
		ResultSet result = null;
		try
		{
			String query="select ques from user1 where email_id='"+username+"';";
			result=st.executeQuery(query);
			result.next();
			if(result.getObject(1)!=null)
			{
				//result.close();
				return result.getObject(1).toString();
			}
				
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return "";
	}
	public boolean emailIdValidation(String emailId)
	{
		ResultSet result = null;
		try
		{
			String query="select * from user1  where email_id='"+emailId+"';";
			result=st.executeQuery(query);
			result.next();
			if(result.getObject(1)!=null)
			{
				result.close();
				return false;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return true;
	}
	public boolean userValidation(String question,String answer,String emailId)
	{
		//System.out.println(uname+"\t"+ question+"\t"+answer);
		ResultSet result = null;
		try
		{
			String query="SELECT * from user1 where ques='"+question+"' and ans='"+answer+"' and email_id='"+emailId+"';";
			//System.out.println(query);
			result=st.executeQuery(query);
			result.next();
			if(result.getObject(1)!= null)
			{
				result.close();
				return true;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	*/
	/**
	 * This method is used to fetch the data from the reminder table and do concurrent updations on it.
	 * @param sid indicated the schedule id of the reminder 
	 * @return ResultSet it returns ResultSet object containing data from reminder table for the respective schedule indicated by the sid.
	 * @exception SQLException this exception is thrown by the executeQuery() method of the Statement class.
	 */
	public ResultSet getDataForReminderTable(int sid)
	{
			ResultSet result = null;
			try
			{
				result=st.executeQuery("select * from reminder where sid='"+sid+"';");
				return result;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e.getMessage();
			}
			return result;
	}
	
	/**
	 * This method is used to fetch the data from the schedule table and do concurrent updations on it.
	 * @param uid indicated the user id of the schedule 
	 * @return ResultSet it returns ResultSet object containing data from schedule table for the respective user indicated by the uid.
	 * @exception SQLException this exception is thrown by the executeQuery() method of the Statement class.
	 */
	public ResultSet getDataForScheduleTable(int uid)
	{
			
			try
			{
				System.out.println(uid);
				ResultSet result=st.executeQuery("select * from schedule where uid="+uid);
				return result;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				e.getMessage();
			}
			return null;
	}
	
	/**
	 * This method is used to close the database connectivity.
	 * @exception SQLException this exception is thrown by the close() method of the Connection class.
	 * @exception SQLException this exception is thrown by the close() method of the Statement class.
	 */
	 
	public void closeConnection()
	{
		try
		{
			con.close();
			st.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception in closeConnection"+e.getMessage());
		}
	}
}