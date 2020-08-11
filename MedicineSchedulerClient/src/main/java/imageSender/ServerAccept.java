package imageSender;

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
	
	
	public ServerAccept(Socket receivingSocket,ServerSocket ss) 
	{
		try
		{
			System.out.println("In Server Accept");		
			BufferedInputStream receivingBIS = new BufferedInputStream(receivingSocket.getInputStream());
			ObjectInputStream receivingOIS = new ObjectInputStream(receivingSocket.getInputStream());
			
			File file = (File)receivingOIS.readObject();
			
			BufferedOutputStream  receivingBOS = new BufferedOutputStream(new FileOutputStream(file.getName()));
				
			while( ( i=receivingBIS.read()) != -1)
			{
				receivingBOS.write(i);
			}
		
			System.out.println("------File recieved "+ file.getName());
		
			receivingBOS.flush();
			receivingBOS.close();
			receivingBIS.close();
			
			/*if(file.getName().equals("PS.txt"))
			{
				obj.displayMonitor("/home/hp/NMS/src/PS.txt","Monitor Status");
			}*/
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
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
			
	}
}
