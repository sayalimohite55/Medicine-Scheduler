/**<h1>Medicine Scheduler ForgotPassword page</h1>
 * ForgotPassword class is designed for 
 * the page to recover the users password.
 * 
 * @author  Sayali Mohite
 * @author  Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.swing.*;

import databaseManager.DBManager;
import notifications.Mail;
import notifications.SendSms;
import placeholderTextField.PlaceholderTextField;

class ForgotPassword extends WindowAdapter implements ActionListener {
	DBManager db= new DBManager();
	JFrame frame;
	JLabel pills,watch,question,question1,details,details1,logIn,icon;
	PlaceholderTextField ans,logInText;
	JButton submit,back;
	boolean window=true,flag=false;
	String ques,answer,email;
	int cnt=1;
	
	/**
	 * This is the Constructor of the ForgotPassword class
     * All Components used on the ForgotPassword frame are 
     * initialised here.All events are added to the 
     * respective components in this method only
     */
	
	ForgotPassword() {
		db.loader();
		db.getConnection();
		
		frame = new JFrame("Forgot Password?");
		frame.setBounds(400,140,580,430);
		frame.setContentPane(new JLabel(new ImageIcon("src/main/resources/images/forgotbg.jpg")));
		frame.setLayout(null);
		frame.addWindowListener(this);
			
		icon=new JLabel(new ImageIcon("src/main/resources/images/forgot.jpg"));
		icon.setBounds(0,0,280,388);
			
		details=new JLabel("Fill details below to Recover");
		details.setForeground(Color.BLACK);
		details.setFont(new Font("Ariel",Font.BOLD,20));
		details.setBounds(270,50,300,20);
		
		details1=new JLabel("Your Password");
		details1.setForeground(Color.BLACK);
		details1.setFont(new Font("Ariel",Font.BOLD,20));
		details1.setBounds(335,90,290,20);
		
		logIn=new JLabel("Enter log In Id");
		logIn.setForeground(Color.BLACK);
		logIn.setFont(new Font("Ariel",Font.ITALIC,18));
		logIn.setBounds(340,130,180,20);
		
		logInText=new PlaceholderTextField(30);
		logInText.setBounds(310,170,200,30);
		logInText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logInText.setPlaceholder("Enter your Email Id");
		
		question=new JLabel("Security Question: ");
		question.setForeground(Color.BLACK);
		question.setFont(new Font("Ariel",Font.PLAIN,18));
		question.setBounds(270,135,250,20);
		question.setVisible(false);
		
		question1=new JLabel();
		question1.setForeground(Color.BLACK);
		question1.setFont(new Font("Ariel",Font.HANGING_BASELINE,18));
		question1.setBounds(340,170,300,20);
		question1.setVisible(false);
				
		ans=new PlaceholderTextField(30);
		ans.setBounds(300,210,202,25);
		ans.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ans.setVisible(false);
		ans.setPlaceholder("Enter your Security Answer");

		
		submit=new JButton("Proceed>>");
		submit.setBounds(350,210,120,35);
		submit.addActionListener((ActionListener)this);
		submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		back=new JButton(new ImageIcon("src/main/resources/images/back.png"));
		back.setBounds(5,5,50,45);
		back.addActionListener((ActionListener)this);
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		watch=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/watch.png"));
		watch.setBounds(350,240,150,150);

		pills=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pills.png"));
		pills.setBounds(470,300,80,80);
		
		frame.add(watch);
		frame.add(pills);

		
		icon.add(back);
		frame.add(icon);
		frame.add(details);
		frame.add(details1);
		frame.add(logIn);
		frame.add(logInText);
		frame.add(question);
		frame.add(question1);
		frame.add(ans);
		frame.add(submit);
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	/**
	 * This method is overridden as its 
	 * empty definition is already given into
	 * the WindowAdapter class extended by
	 * this class.
	 * In this method when user clicks on to cross
	 * button at head of the window he gets back 
	 * directed to the LogIn page.
	 * @param we WindowEvent 
	 */
	public void windowClosing(WindowEvent we)
	{
		new LogIn();
		frame.dispose();
	}
	
	/**Description of actionPerformed() method
	 * In this method if the valid user is requesting 
	 * for password forgotten then that password is 
	 * recovered to him/her by sending mail on the 
	 * registered email id and phone number.
	 * @param e ActionEvent
	 * @exception SQLException throws SQLException for next() method of ResultSet class. 
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==back)
		{
			new LogIn();
			frame.dispose();
		}
		else 
		if(e.getSource()==submit)
		{
			if(window)//proceed(only email id is entered)
			{
				email=logInText.getText();
				if(email.isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Enter your Log In id");
					flag=true;
				}
				else
					flag=false;
				
				if(!flag) 
				{
					watch.setBounds(439,250,150,150);
					pills.setBounds(390,300,80,80);
					
					String query="select ques from user1 where email_id='"+email+"';";
					ResultSet rs=db.fetchQuery(query);
					try 
					{
						if(rs.next())
						{
							ques=rs.getString(1);
							question1.setText(ques);
							question1.setVisible(true);
							question.setVisible(true);
							ans.setVisible(true);
							submit.setBounds(340,260,120,35);
							logIn.setVisible(false);
							logInText.setVisible(false);
							submit.setText("Submit");
							window=false;
							flag=false;
						}
						else 
						{
							logInText.setText("");
							JOptionPane.showMessageDialog(null,"Invalid Email Id");
						}
					}
					catch(Exception f){
						f.printStackTrace();
						f.getMessage();
					}
				}
			}
			else//submit(ans of the security que is too answered)
			{
				answer=ans.getText();
				if(answer.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Answer field can't be kept empty");
					flag=true;
				}
				else
					flag=false;
				
				if(!flag) 
				{
					if(cnt>3)
					{
						JOptionPane.showMessageDialog(null, "Your attempt limit has been crossed");
						frame.dispose();
					}
					else
					{
						String query="select ans from user1 where email_id='"+email+"' and ques='"+ques+"';";
						ResultSet rs=db.fetchQuery(query);
						
						try 
						{
							if(rs.next()) 
							{	
								String temp=rs.getString(1);
								if(temp.matches(answer))
								{
									String getpwd="select pwd,phno from user1 where email_id='"+email+"' and ques='"+ques+"' and ans='"+temp+"';";
									ResultSet r=db.fetchQuery(getpwd);
									r.next();
									String password=r.getString(1);
									String phone=r.getString(2);
									
									new Mail().sendMail(email,"Your Password is :"+password);//password sent via mail
									new SendSms(phone,"Your Password is :"+password);//password sent via sms
									JOptionPane.showMessageDialog(null, "Your Password will be sent to you via email and sms");
									frame.dispose();
								}
								else 
								{
									ans.setText("");
									JOptionPane.showMessageDialog(null,"Wrong answer");
									cnt++;
								}
							}
						}
						catch(Exception f)
						{
							f.printStackTrace();
							f.getMessage();
						}
					}
				}
			}
		}
	}
		
	/*public static void main(String args[]) {
			new ForgotPassword();
	}*/
}