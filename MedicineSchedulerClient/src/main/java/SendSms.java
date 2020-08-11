import java.net.*;
import java.io.*;

public class SendSms 
{
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
