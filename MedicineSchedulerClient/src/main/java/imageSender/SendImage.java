package imageSender;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendImage 
{
	public SendImage(String file) 
	{
		  try
		  {  
			  		String path="/home/space/Test/ClientReminder/src/images/";
					Socket sendingFile = new Socket("192.168.1.41", 54321);
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path+file));

					BufferedOutputStream bos = new BufferedOutputStream(sendingFile.getOutputStream());
					ObjectOutputStream oos = new ObjectOutputStream(sendingFile.getOutputStream());

					oos.writeObject(new File(path+file));

					int j;
					while ((j = bis.read()) != -1)
						bos.write(j);

					bos.flush();
					bis.close();
					bos.close();
			}  
	        catch (Exception ex)
		  {  
	            /* Catch any errors */  
	        	ex.printStackTrace();
		  }
	}
	/*public static void main(String[] args) 
	{
		new SendImage("test0.png");
	}*/
}
