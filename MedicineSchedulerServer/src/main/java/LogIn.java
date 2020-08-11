/**<h1>Medicine Scheduler LogIn page</h1>
* It is simply the start of the Medicine Scheduler.
* This application software is built to remind people to take medicines on time.
* And keep a track of their health progress. 
* <p>It is not only an ordinary scheduler application but a software with lots of additional provisions
* as we are providing you facility to monitor whether a patient has taken medicine or not.
* Also one can keep backup of the recently followed schedules which is nothing but a softcopy
* of the prescription given by doctor itself.
* <p>A survey conducted had recently displayed their results on google showed that because of
* not taking medicines time to time we have a death ratio as 1,25000 deaths a year.Day-by-day we are 
* approaching towards progress, but such deadly progress is of no use because after all Health is the only wealth.
* So to have a grid solution over the problem we have built this software.
* <b>Note:</b> Currently it is just a stand alone Conceptual Model.Further it can be extended to a web based System.
*  
* @author  Sayali Mohite
* @author  Shabaj Momin
* @version 1.0
* @since   01-01-2016
*/

import java.awt.*;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

import databaseManager.DBManager;
import placeholderTextField.PlaceholderTextField;

//import de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel;

import java.awt.event.*;

class LogIn extends MouseAdapter implements ActionListener,Runnable,FocusListener
{
	DBManager db= new DBManager();
	JFrame frame;
	PlaceholderTextField logInText;
	JLabel details,forgotPassword,l1,l2,l3,createNew,createNew1,welcome,icon,user_error,pwd_error,partition,image[],createNewIcon,line,pg1,pg2,watch,pills,page[];
	JButton logIn;
	JPasswordField passwordText;
	JPanel panel;
	Thread thread;
	int pageNo=0;
	String arr[]={"11.jpg","12.jpg","13.jpg","14.jpg","21.jpg","22.jpg","23.jpg","24.jpg"};
	String email,str;
	boolean login=false,password=false;
	PlaceholderTextField vanishText;
	Pattern email_pattern;
	Matcher email_matcher;
	
	
	/**
	 * This is the Constructor of the LogIn class
     * All Components used on the LogIn frame are 
     * initialised here.All events are added to the 
     * respective components in this method only
     * In this constructor new child thread is started 
     * to run concurrently with the main thread.
     */
	LogIn() {
		db.loader();
		db.getConnection();
		frame = new JFrame("Log In Screen");
		frame.setSize(1310,800);
		frame.setLayout(null);
		frame.setContentPane(new JLabel(new ImageIcon("src/main/resources/images/Screenbg.jpg")));
		
		partition=new JLabel();
		partition.setBackground(Color.BLACK);
		partition.setBounds(374,0,2,780);
		partition.setVisible(true);
		partition.setOpaque(true);
		
		panel=new JPanel();
		panel.setBounds(0,180,370,780);
		panel.setOpaque(false);
		panel.setLayout(null);
	
		welcome=new JLabel("Welcome To Medicine Scheduler ");
		welcome.setForeground(Color.CYAN);
		welcome.setFont(new Font("Ariel",Font.ITALIC,40));
		welcome.setBounds(400,50,720,100);
		
		details=new JLabel("Enter Your Log In Details");
		details.setForeground(Color.WHITE);
		details.setFont(new Font("Ariel",Font.ITALIC,21));
		details.setBounds(570,320,260,21);
		
		icon=new JLabel(new ImageIcon("src/main/resources/images/LogIn.png"));
		icon.setBounds(631,180,128,128);
		
		logInText=new PlaceholderTextField(30);
		logInText.setBounds(570,360,260,25);
		logInText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logInText.addFocusListener(this);
		logInText.setPlaceholder("Enter Email Id");
				
		user_error=new JLabel("*");
		user_error.setForeground(Color.RED);
		user_error.setFont(new Font("Ariel",Font.BOLD,15));
		user_error.setBounds(840,365,300,15);
		user_error.setVisible(false);
		
		passwordText=new JPasswordField();
		passwordText.setBounds(570,405,260,25);
		passwordText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		passwordText.addFocusListener(this);
		
		pwd_error=new JLabel("*");
		pwd_error.setForeground(Color.RED);
		pwd_error.setFont(new Font("Ariel",Font.BOLD,15));
		pwd_error.setBounds(840,410,300,15);
		pwd_error.setVisible(false);
		
		logIn = new JButton("Sign In");
		logIn.setBounds(650,455,80,35);
		logIn.addActionListener(this);
		logIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		forgotPassword = new JLabel("Forgot Password?");
		forgotPassword.setForeground(Color.WHITE);
		forgotPassword.setFont(new Font("Ariel",Font.BOLD,15));
		forgotPassword.setBounds(623,500,148,15);
		forgotPassword.addMouseListener(this);
		forgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		l1=new JLabel();
		l1.setBounds(623,515,145,1);
		l1.setBackground(Color.WHITE);
		l1.setOpaque(true);
		
		createNewIcon=new JLabel(new ImageIcon("src/main/resources/images/SignUp.png"));
		createNewIcon.setBounds(15,50,100,100);
		createNewIcon.addMouseListener(this);
		createNewIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		createNew = new JLabel("New User ?");
		createNew.setForeground(Color.WHITE);
		createNew.setFont(new Font("Ariel",Font.ITALIC,17));
		createNew.setBounds(120,100,100,17);
		createNew.addMouseListener(this);
		createNew.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		l2=new JLabel();
		l2.setBounds(121,117,90,1);
		l2.setBackground(Color.WHITE);
		l2.setOpaque(true);
		
		createNew1 = new JLabel("Click here to Sign Up..");
		createNew1.setForeground(Color.WHITE);
		createNew1.setFont(new Font("Ariel",Font.ITALIC,17));
		createNew1.setBounds(115,123,190,17);
		createNew1.addMouseListener(this);
		createNew1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		l3=new JLabel();
		l3.setBounds(115,140,190,1);
		l3.setBackground(Color.WHITE);
		l3.setOpaque(true);
		
		line=new JLabel();
		line.setBackground(Color.BLACK);
		line.setBounds(0,165,374,2);
		line.setVisible(true);
		line.setOpaque(true);
		
		page=new JLabel[3];
		
		//scroll page 1
		page[0]=new JLabel();
		page[0].setSize(panel.getMaximumSize());
		page[0].setOpaque(false);
		
		image=new JLabel[8];
		for(int i=0;i<4;i++)
		{
			image[i]=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/"+arr[i]));
		}
		
		image[0].setBounds(7,20,150,112);
		image[1].setBounds(165,20,200,112);
		image[2].setBounds(15,150,120,170);
		image[3].setBounds(150,150,210,170);
				
		pg1=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pg1.jpg"));
		pg1.setBounds(0,300,380,220);
		
		for(int i=0;i<4;i++)
			page[0].add(image[i]);
		page[0].add(pg1);
		
		//scroll page2
		page[1]=new JLabel();
		page[1].setSize(panel.getMaximumSize());
		page[1].setOpaque(false);
		
		for(int i=4;i<8;i++)
		{
			image[i]=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/"+arr[i]));
		}
		
		image[4].setBounds(7,20,150,112);
		image[5].setBounds(165,20,200,112);
		image[6].setBounds(7,150,180,170);
		image[7].setBounds(173,150,229,170);
		
		watch=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/watch.png"));
		watch.setBounds(0,0,150,150);

		pills=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pills.png"));
		pills.setBounds(117,48,80,80);
		
		pg2=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pg2.jpg"));
		pg2.setBounds(0,320,410,300);
		
		pg2.add(watch);
		pg2.add(pills);
		
		for(int i=4;i<8;i++)
			page[1].add(image[i]);
		page[1].add(pg2);

		//scroll page 3
		page[2]=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pg3.jpg"));
		page[2].setBounds(0,0,380,600);
	
		panel.add(page[pageNo]);

		frame.add(createNewIcon);
		frame.add(createNew);
		frame.add(createNew1);
		frame.add(line);
		
		frame.add(panel);
		frame.add(partition);
		frame.add(icon);
		frame.add(welcome);
		frame.add(details);
		frame.add(logInText);
		frame.add(user_error);
		frame.add(pwd_error);
		frame.add(passwordText);
		frame.add(logIn);
		frame.add(forgotPassword);
		frame.add(l1);
		frame.add(l2);
		frame.add(l3);
		frame.setVisible(true);
		
		thread=new Thread(this);
		thread.start();
	}
	
	/**
	 * Since FocusListener interface is 
	 * implemented by this class focusGained()
	 * method is to be given definition.But since
	 * it is not used it is just provided an empty 
	 * definition.
	 * @param e FocusEvent
	 */
	public void focusGained(FocusEvent e)
	{}
	
	/**Description of focusLost() method
	 * This method is used for validations as
	 * it gets called when the focus is lost by the component.
	 * @param e FocusEvent
	 */
	
	public void focusLost(FocusEvent e)
	{
		if(e.getSource()==logInText && login==false)
		{
			email=logInText.getText();
			if(logInText.getText().trim().equals(""))//if textfield is empty
			{
				user_error.setText("*");
				user_error.setVisible(true);
				login=false;
				logInText.requestFocus();
			}
			else//if textfield is not empty
			{
				email_pattern=Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				email_matcher=email_pattern.matcher(email);
				if(!email_matcher.matches())//if email id is not valid
				{
					user_error.setText("* Invalid email id");
					logInText.setText("");
					user_error.setVisible(true);
					login=false;
					logInText.requestFocus();
				}
				else//valid email id
				{
					login=true;
					user_error.setVisible(false);
				}
			}
		}
		else 
		if(e.getSource()==passwordText && login==true)
		{
			str=String.valueOf(passwordText.getPassword());
			
			if(str.isEmpty())//if empty password
			{
				pwd_error.setText("*");
				pwd_error.setVisible(true);
				passwordText.requestFocus();
				password=false;
			}
			else if(str.length()<8)//if strength is <8
			{
				pwd_error.setText("* Minimum length should be 8 ");
				pwd_error.setVisible(true);
				passwordText.setText(null);
				passwordText.requestFocus();
				password=false;
			}
			else if(str.length()>20)//if strength >20 
			{
				pwd_error.setText("* password length too long");
				pwd_error.setVisible(true);
				passwordText.setText(null);
				passwordText.requestFocus();
				password=false;
			}
			else //if valid password 
			{
				pwd_error.setVisible(false);
				password=true;
			}
		}
	}
	
	/**Description of mouseClicked() method
	 * This method is used to capture the mouseClick event
	 * so as to link the respective page.
	 * @param e MouseEvent
	 * @return switches to the SignUp page or ForgotPassword page depending on 'e'.
	 * @see SignUp
	 * @see ForgotPassword 
	 */
	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()== createNew || e.getSource()== createNew1 || e.getSource()== createNewIcon) {
			new SignUp();
			frame.dispose();
		}
		if(e.getSource()== forgotPassword) {
			new ForgotPassword(); 
			frame.dispose();
			//pwd_error.setVisible(false);
			//user_error.setVisible(false);
		}
	}
    
	/**Description of actionPerformed() method
	 * @param e ActionEvent
	 * @exception SQLException throws SQLException for next() method of ResultSet class.
	 * @return validates the input and depending on that returns to HomePage of user. 
	 */
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==logIn)
		{
			if(login && password)
			{
				String query="select * from user1 where email_id='"+email +"' and pwd='"+str+"';";
				ResultSet rs=db.fetchQuery(query);
				try {	
					if(rs.next()) 
					{	
						int id=rs.getInt(1);
						new HomeScreen(id);
						frame.dispose();
					}
					else {
						logInText.setText("");
						passwordText.setText("");
						JOptionPane.showMessageDialog(null,"Invalid Email Id or password");
					}
				}
				catch(Exception f){
					f.printStackTrace();
					f.getMessage();
				}
			}
			else 
			if(!login || !password)
			{
				if(!login)
					logInText.requestFocus();
				else
				if(!password)
					passwordText.requestFocus();
			}
		}
	}
	/**
	 * This is main method which starts the further execution of the software.
	 * @param args[] String array used to take values or input from command line.
	 * @param args Unused.
     * @return Nothing.
	 */
	public static void main(String args[]) {
		/*try 
		{
			UIManager.setLookAndFeel(new SyntheticaBlueLightLookAndFeel());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}*/
		new LogIn();
	}
	
	/**Description of run() method
	 * This method is used to run the child thread 
	 * concurrently along with the main thread.
	 * @exception InterruptedException throws InterruptedException for sleep() method of Thread class.
	 */
	
	public void run()
	{
		//new ServerAccept(); **
		while(true) 
		{
			try
			{
				Thread.sleep(3000);
				if(pageNo<3)
				{
					panel.removeAll();
					panel.add(page[pageNo]);
					panel.repaint();
					pageNo++;
				}
				else
				{
					pageNo=0;
					Thread.sleep(10000);
				}
			}
			catch(Exception e) 
			{
				e.getMessage();
				e.printStackTrace();
			}
		}
	}
}
