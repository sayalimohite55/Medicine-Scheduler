/**Description of ServerAccept class
 * 
 * @author Sayali Mohite 
 * @author Shabaj Momin
 *
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAccept
{
	int i;
	static ServerSocket ss;
	static Socket receivingSocket;
	
	/**
	 * @exception IOException constructor of ServerSocket class throws IOException
	 * @exception IOException accept() method of ServerSocket class throws IOException
	 * @exception IOException getInputStream() method of ServerSocket class throws IOException
	 * @exception IOException constructor of ObjectInputStream class throws IOException
	 * @exception ClassNotFoundException readObject() method of ObjectInputStream class throws ClassNotFoundException
	 * @exception FileNotFoundException constructor of FileOutputStream class throws FileNotFoundException
	 * @exception IOException  read() method of BufferedInputStream class throws IOException
	 * @exception IOException  write() method of BufferedOutputStream class throws IOException
	 * @exception IOException  flush() method of BufferedOutputStream class throws IOException
	 * @exception IOException  close() method of BufferedOutputStream class throws IOException
	 * @exception IOException  close() method of BufferedInputStream class throws IOException
	 */
	public ServerAccept() 
	{
		try
		{
			ss = new ServerSocket(54325);
			receivingSocket = ss.accept();
			System.out.println("In Server Accept");		
			BufferedInputStream receivingBIS = new BufferedInputStream(receivingSocket.getInputStream());
			ObjectInputStream receivingOIS = new ObjectInputStream(receivingSocket.getInputStream());
			
			File file = (File)receivingOIS.readObject();
			
			BufferedOutputStream  receivingBOS = new BufferedOutputStream(new FileOutputStream("/home/sayali/Taken Medicines/"+file.getName()));
				
			while( ( i=receivingBIS.read()) != -1)
			{
				receivingBOS.write(i);
			}
			
			System.out.println("------File recieved "+ file.getName());
		
			receivingBOS.flush();
			receivingBOS.close();
			//receivingBIS.close();
		}
		catch (Exception e) 
		{
			e.getMessage();
			e.printStackTrace();
		}
	}
	
/*	public static void main(String args[])
	{
		try 
		{
			ss = new ServerSocket(54321);
			receivingSocket = ss.accept();
			new ServerAccept(receivingSocket,ss);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			
	}*/
}
