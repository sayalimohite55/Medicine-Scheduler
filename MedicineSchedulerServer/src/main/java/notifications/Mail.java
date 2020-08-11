package notifications;
/**
 * <h1>Medicine Scheduler Sending Mail</h1>
 * This class is designed to send mail to the
 * user on his/her email id to recover his/her 
 * password when demanded.
 * 
 * @author  Sayali Mohite
 * @author  Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */

import java.util.Properties;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail
{
	String d_email = "sayalimohite55@gmail.com";//sender email id
	String d_password = "9922941082";//account password
	String d_host = "smtp.gmail.com";
	String d_port = "587";//587
	String message_text;
	String mail_send_to;
	
	/**
	 * This method is usedv to send mail to user to recover his password.
	 * @param mail_send_to It specifies that whom the mail should be delivered
	 * @param message_text It specifies the content of the mail that should be delivered to the user.
	 */
	public void sendMail(String mail_send_to,String message_text)//call this function
	{
		System.out.println(""+mail_send_to+"\t"+message_text);
		String m_subject="OTP Password";
		this.message_text=message_text;
		this.mail_send_to=mail_send_to;
		
		Properties props = new Properties();
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		try 
		{
			SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			MimeMessage msg = new MimeMessage(session);
			msg.setText(message_text);
			msg.setSubject(m_subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail_send_to));
			Transport.send(msg);
			System.out.println("Done Mail Sent!!! ...");
		} 
		catch (Exception e) 
		{
			System.out.println("ERROR OCCURS in Mail.sendMail() method ...");
			System.out.println("ERROR : "+e);
		}
	}

	/** 
	 * @author Sayali Mohite
	 * @author Shabaj Momin
	 *
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator 
	{
		public PasswordAuthentication getPasswordAuthentication()
		 {
			System.out.println("in getPasswordAuthentication() ...");
			return new PasswordAuthentication(d_email, d_password);
		}
	}

	/*public static void main(String[] args) 
	{
		System.out.println("Starts ...");
		Mail sendmail=new Mail();
		sendmail.sendMail("prankul.devi@gmail.com","OTP is 12345");
	}*/
}