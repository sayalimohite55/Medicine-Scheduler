package notifications;
/**<h1>Medicine Scheduler Sending SMS</h1>
 * This class is designed to send sms 
 * to user where sms can be either to send
 * medicine name according to schedule or to 
 * recover the users password if he demanded. 
 */

import java.net.*;
import java.io.*;

public class SendSms 
{
	/**
	 * This is the constructor of SendSms class.
	 * @param phno phno is the contact number where the sms should be sent.
	 * @param msg msg is the message to be sent to user.
	 * @exception MalformedURLException this exception is thrown by the constructor of the URL class.
	 * @exception IOException this exception is thrown by the openConnection() method of the URL class.
	 * @exception IOException this exception is thrown by the getInputStream() method of the URLConnection class.
	 * @exception IOException this exception is thrown by the readLine() method of the BufferedReader class.
	 * @exception IOException this exception is thrown by the close() method of the BufferedReader class.
	 * 
	 */
	public SendSms(String phno,String msg) 
	{
		try 
		{
			System.out.println("in sms sending ");
			String url="http://smsalertbox.com/api/sms.php?uid=5370616365436f6d70&pin=547719fc726810a89e4d17976d384647&sender=your_sender_id&route=0&mobile="+phno+"&message="+msg+"&pushid=1";
			URL smsApiLink = new URL(url);
			URLConnection smsConnecion = smsApiLink.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(smsConnecion.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) 
				System.out.println(inputLine);
			in.close();
			System.out.println("sms sent");
		} 
		catch (MalformedURLException e) 
		{ 
			// new URL() failed
			e.printStackTrace();
		} 
		catch (IOException e) 
		{   
			// openConnection() failed
			e.printStackTrace();
		}
	}
}
