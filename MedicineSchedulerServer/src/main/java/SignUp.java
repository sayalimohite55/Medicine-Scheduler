/**<h1>Medicine Scheduler SignUp page</h1>
 * SignUp class is designed 
 * for the signUp page to register the 
 * user with Medicine Scheduler.
 *  
 * @author Sayali Mohite
 * @author Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import databaseManager.DBManager;
import placeholderTextField.PlaceholderTextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp implements ActionListener,FocusListener
{
	DBManager DB=new DBManager();
	JFrame signUpFrame;
	PlaceholderTextField fname,lname,phnoText,emailIdText,ansText;
	JLabel watch,pills,icon,details,name,emailId,gender,createPwd,cnfrmPwd,ques,ans,phno,image,fnameerror_label,lnameerror_label,sqerror_label,saerror_label,phnoerror_label,gendererror_label,submiterror_label,emailerror_label,pwd1error_label,pwd2error_label;
	JButton reset,create,back;
	@SuppressWarnings("rawtypes")
	JComboBox quesList;
	JRadioButton male,female;
	ButtonGroup button,user;
	String Question[]={"Select Question","Favourite car?","Best Friend?","Your Hobby?"};
	JPasswordField pwd1,pwd2;
	boolean nm=false,flag[]={false,false,false,false,false,false,false,false,false},prsn=false;
	String dbFirstname,dbSurname,dbEmail,dbGender,dbQue,dbAns,dbPwd1,dbPwd2,dbPhno;	
	Pattern text_pattern,email_pattern,password_pattern;
	Matcher mat_matcher,contact_matcher,email_matcher,text_matcher,password_matcher;
	
	/**
	 * This is the Constructor of the SignUp class
     * All Components used on the SignUp frame are 
     * initialised here.All events are added to the 
     * respective components in this method only
     */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	SignUp() {
		DB.loader();
		DB.getConnection();
		
		signUpFrame = new JFrame("Create New Account");
		signUpFrame.setSize(1310,800);
		signUpFrame.setLayout(new BorderLayout());
		
		image=new JLabel(new ImageIcon("src/main/resources/images/Screenbg.jpg"));
		
		icon=new JLabel(new ImageIcon("src/main/resources/images/CreateNew.jpg"));
		icon.setBounds(40,30,90,150);
		icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		details=new JLabel("Enter Your Details");
		details.setForeground(Color.CYAN);
		details.setFont(new Font("Ariel",Font.BOLD,30));
		details.setBounds(161,120,550,25);
		
		name = new JLabel("Name ");
		name.setForeground(Color.CYAN);
		name.setFont(new Font("Ariel",Font.BOLD,20));
		name.setBounds(40,190,200,25);
		
		fname= new PlaceholderTextField(20);
		fname.setBounds(349,190,200,25);
		fname.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fname.addFocusListener(this);
		fname.setPlaceholder("First Name");
		
		fnameerror_label=new JLabel("*");
		fnameerror_label.setForeground(Color.RED);
		fnameerror_label.setFont(new Font("Arial",Font.BOLD,15));
		fnameerror_label.setVisible(false);
		fnameerror_label.setBounds(560,190,30,20);
		
		lname= new PlaceholderTextField(20);
		lname.setBounds(600,190,200,25);
		lname.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lname.addFocusListener(this);
		lname.setPlaceholder("Last Name");
		
		lnameerror_label = new JLabel("*");
		lnameerror_label.setForeground(Color.red);
		lnameerror_label.setFont(new Font("Arial",Font.BOLD,15));
		lnameerror_label.setVisible(false);
		lnameerror_label.setBounds(810,190,300,20);
		
		emailId = new JLabel("Email Address ");
		emailId.setForeground(Color.CYAN);
		emailId.setFont(new Font("Ariel",Font.BOLD,20));
		emailId.setBounds(40,250,200,25);
					
		emailIdText= new PlaceholderTextField(10);
		emailIdText.setBounds(349,250,300,25);
		emailIdText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		emailIdText.addFocusListener(this);
		emailIdText.setPlaceholder("Enter your Email Id");
		
		emailerror_label=new JLabel("*");
		emailerror_label.setForeground(Color.red);
		emailerror_label.setFont(new Font("Arial",Font.BOLD,15));
		emailerror_label.setVisible(false);
		emailerror_label.setBounds(660,250,200,20);
		
		createPwd = new JLabel("Create New Password ");
		createPwd.setForeground(Color.CYAN);
		createPwd.setFont(new Font("Ariel",Font.BOLD,20));
		createPwd.setBounds(40,305,300,25);
		
		pwd1=new JPasswordField();
		pwd1.setBounds(349,305,300,25);
		pwd1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pwd1.addFocusListener(this);
		
		pwd1error_label=new JLabel("*");
		pwd1error_label.setForeground(Color.red);
		pwd1error_label.setFont(new Font("Arial",Font.BOLD,15));
		pwd1error_label.setVisible(false);
		pwd1error_label.setBounds(660,305,300,20);
		
		cnfrmPwd = new JLabel("Confirm Password ");
		cnfrmPwd.setForeground(Color.CYAN);
		cnfrmPwd.setFont(new Font("Ariel",Font.BOLD,20));
		cnfrmPwd.setBounds(40,360,200,25);

		pwd2=new JPasswordField();
		pwd2.setBounds(349,360,300,25);
		pwd2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pwd2.addFocusListener(this);
		
		pwd2error_label=new JLabel("*");
		pwd2error_label.setForeground(Color.red);
		pwd2error_label.setFont(new Font("Arial",Font.BOLD,15));
		pwd2error_label.setVisible(false);
		pwd2error_label.setBounds(660,360,320,20);
													
		gender=new JLabel("Gender ");
		gender.setForeground(Color.CYAN);
		gender.setFont(new Font("Ariel",Font.BOLD,20));
		gender.setBounds(40,410,200,25);
				
		button=new ButtonGroup();
		male=new JRadioButton("Male");
		female=new JRadioButton("Female");
		button.add(male);
		button.add(female);
		
		male.setBounds(349,410,60,20);
		male.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		male.addFocusListener(this);
		male.setOpaque(false);
		male.setForeground(Color.CYAN);
		
		female.setBounds(450,410,80,20);
		female.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		female.addFocusListener(this);
		female.setOpaque(false);
		female.setForeground(Color.CYAN);
		
		gendererror_label=new JLabel("* Select your gender");
		gendererror_label.setForeground(Color.RED);
		gendererror_label.setVisible(false);
		gendererror_label.setFont(new Font("Arial",Font.BOLD,15));
		gendererror_label.setBounds(540,410,300,20);
		
		phno=new JLabel("Mobile No.");
		phno.setForeground(Color.CYAN);
		phno.setFont(new Font("Ariel",Font.BOLD,20));
		phno.setBounds(40,455,200,25);
		
		phnoText = new PlaceholderTextField(10);
		phnoText.setBounds(349,455,300,25);
		phnoText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		phnoText.addFocusListener(this);
		phnoText.setPlaceholder("Mobile Number");
		
		phnoerror_label=new JLabel("*");
		phnoerror_label.setForeground(Color.RED);
		phnoerror_label.setVisible(false);
		phnoerror_label.setFont(new Font("Arial",Font.BOLD,15));
		phnoerror_label.setBounds(660,455,200,20);
		
		ques=new JLabel("Security Question ");
		ques.setForeground(Color.CYAN);
		ques.setFont(new Font("Ariel",Font.BOLD,20));
		ques.setBounds(40,510,200,25);
					
		quesList=new JComboBox(Question);
		quesList.setBounds(349,510,200,25);
		quesList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		quesList.addFocusListener(this);
		
		sqerror_label=new JLabel("*");
		sqerror_label.setFont(new Font("Arial",Font.BOLD,15));
		sqerror_label.setForeground(Color.red);
		sqerror_label.setVisible(false);
		sqerror_label.setBounds(560,510,300,20);
		
		ans=new JLabel("Answer ");
		ans.setForeground(Color.CYAN);
		ans.setFont(new Font("Ariel",Font.BOLD,20));
		ans.setBounds(40,565,200,25);
		
		ansText=new PlaceholderTextField(20);
		ansText.setBounds(349,565,200, 25);
		ansText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ansText.addFocusListener(this);
		ansText.setPlaceholder("Enter your Security answer");
		
		saerror_label=new JLabel("*");
		saerror_label.setForeground(Color.red);
		saerror_label.setFont(new Font("Arial",Font.BOLD,15));
		saerror_label.setVisible(false);
		saerror_label.setBounds(560,565,300,20);
		
		reset=new JButton("Reset");
		reset.setBounds(349,640,80,30);
		reset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					
		create=new JButton("Create Account");
		create.setBounds(450,640,150,30);
		create.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		back=new JButton("Back");
		back.setBounds(250,640,80,30);
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		create.addActionListener(this);                         //Action Listener
		reset.addActionListener(this);
		back.addActionListener(this);
		
		watch=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/watch.png"));
		watch.setBounds(1070,510,150,150);

		pills=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pills.png"));
		pills.setBounds(1190,580,80,80);
		
		image.add(watch);
		image.add(pills);
		image.add(icon);
		image.add(details);
		image.add(name);
		image.add(fname);
		image.add(fnameerror_label);
		image.add(lname);
		image.add(lnameerror_label);
		image.add(emailId);
		image.add(emailIdText);
		image.add(emailerror_label);
		image.add(createPwd);
		image.add(pwd1);
		image.add(pwd1error_label);
		image.add(cnfrmPwd);
		image.add(pwd2);
		image.add(pwd2error_label);
		image.add(gender);
		image.add(male);
		image.add(female);
		image.add(gendererror_label);
		image.add(phno);
		image.add(phnoText);
		image.add(phnoerror_label);
		image.add(ques);
		image.add(quesList);
		image.add(sqerror_label);
		image.add(ans);
		image.add(ansText);
		image.add(saerror_label);
		image.add(back);
		image.add(reset);
		image.add(create);
		
		image.setVisible(true);
		signUpFrame.add(image);
		
		signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signUpFrame.setVisible(true);
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
	
	/**
	 * This method is used to check whether
	 * any flag is having a false value or not,
	 * to decide whether all inputs are given 
	 * correctly or not.
	 * @return integer
	 */
	int check()
	{
		for(int i=0;i<9;i++)
		{
			if(flag[i]==false)
				return i;
		}
		return -1;
	}
	/**Description of focusLost() method
	 * This method is used for validations as
	 * it gets called when the focus is lost by the component.
	 * @param e FocusEvent
	 */
	
	public void focusLost(FocusEvent e)
	{
		if(e.getSource()==fname && flag[0]==false)
		{
			dbFirstname=fname.getText();
			if(dbFirstname.isEmpty())//if fname is empty
			{
				nm=false;
				fnameerror_label.setText("*");
				fnameerror_label.setVisible(true);
				fname.setText("");
				fname.requestFocus();
				flag[0]=false;
			}
			else//if fname is not empty
			{
				text_pattern=Pattern.compile("[a-zA-Z]{2,15}");
				mat_matcher=text_pattern.matcher(dbFirstname);
				
				if(!mat_matcher.matches())//invalid fname
				{
					nm=false;
					lnameerror_label.setText("* Invalid first name");
					lnameerror_label.setVisible(true);
					fname.setText("");
					fnameerror_label.setVisible(true);
					flag[0]=false;
					fname.requestFocus();
				}
				else//valid fname
				{
					nm=true;
					flag[0]=true;
					lnameerror_label.setVisible(false);
					fnameerror_label.setVisible(false);
					lname.requestFocus();
				}
			}
		}
	    else if(e.getSource()==lname && (check()==1 || check()<0))
		{
	    	dbSurname=lname.getText();
	    	if(dbSurname.isEmpty())//if lname is empty
	    	{
	    		lnameerror_label.setText("*");
	    		lnameerror_label.setVisible(true);
	    		flag[1]=false;
	    		lname.requestFocus();
	    	}
	    	else//if lname is not empty
	    	{
	    		text_pattern=Pattern.compile("[a-zA-Z]{2,20}");
	    		mat_matcher=text_pattern.matcher(dbSurname);
			
	    		if(!mat_matcher.matches())//if invalid lname
	    		{
					if(nm)//if fname is already valid
					{
						lnameerror_label.setText("* Invalid last name");
						lname.setText("");
						lnameerror_label.setVisible(true);
						flag[1]=false;
						lname.requestFocus();
					}
					else//if fname is not given
					{
						fname.setText("");
						lnameerror_label.setText("* Invalid first & last name");
						fnameerror_label.setVisible(true);
						lname.setText("");
						lnameerror_label.setVisible(true);
						flag[0]=false;
						fname.requestFocus();
					}
				}
				else//if lname is valid
				{
					fnameerror_label.setVisible(false);
					lnameerror_label.setVisible(false);
					flag[1]=true;
					emailIdText.requestFocus();
				}
			}
		}
		else if(e.getSource()==emailIdText && (check()==2 || check()<0))
		{
			dbEmail=emailIdText.getText();
		
			if(dbEmail.isEmpty())//if empty email id
			{
				emailerror_label.setText("*");
				emailIdText.setText("");
				emailerror_label.setVisible(true);
				flag[2]=false;
				emailIdText.requestFocus();
			}
			else //if not empty
			{
				email_pattern=Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				email_matcher=email_pattern.matcher(dbEmail);
		
				if(!email_matcher.matches()) //if invalid email id
				{
					emailerror_label.setText("* Invalid Email Id");
					emailerror_label.setVisible(true);
					emailIdText.setText("");
					flag[2]=false;
					emailIdText.requestFocus();
				}
				else//if valid email id
				{
					flag[2]=true;
					emailerror_label.setVisible(false);
					pwd1.requestFocus();
				}
			}
		}
		else if(e.getSource()==pwd1 && (check()==3 || check()<0))
		{
			dbPwd1=String.valueOf(pwd1.getPassword());
			
			if(dbPwd1.isEmpty())//if password is empty
			{
				flag[3]=false;
				pwd1error_label.setText("*");
				pwd1error_label.setVisible(true);
				pwd1.requestFocus();
			}
			else if(dbPwd1.length()<8)//if pwd <8
			{
				flag[3]=false;
				pwd1.setText("");
				pwd1error_label.setText("* Minimum length should be 8");
				pwd1error_label.setVisible(true);
				pwd1.requestFocus();
			}
			else if(dbPwd1.length()>20)//if pwd>20
			{
				flag[3]=false;
				pwd1.setText("");
				pwd1error_label.setText("* Password length too long");
				pwd1error_label.setVisible(true);
				pwd1.requestFocus();
			}
			else//if valid pwd
			{
				flag[3]=true;
				pwd1error_label.setVisible(false);
				pwd2.requestFocus();
			}
		}
		else if(e.getSource()==pwd2 && flag[3]==true && (check()==4 || check()<0))
		{
			dbPwd2=String.valueOf(pwd2.getPassword());
		
			if(dbPwd2.isEmpty())//if 2nd pwd is empty
			{
				pwd2error_label.setText("*");
				pwd2error_label.setVisible(true);
				flag[4]=false;
				pwd2.setText("");
				pwd2.requestFocus();
			}
			else if(!dbPwd1.equals(dbPwd2)) //if pwd1 dont match pwd2
			{
				pwd1error_label.setText("* Password is Mandatory");
				pwd2error_label.setText("* Password confirmation is mandatory");
				pwd1error_label.setVisible(true);
				pwd2error_label.setVisible(true);
				flag[3]=false;
				flag[4]=false;
				pwd2.setText("");
				pwd1.setText("");
				pwd1.requestFocus();
			}
			else//if pwd1 matches pwd2
			{
				flag[3]=true;
				flag[4]=true;
				pwd1error_label.setVisible(false);
				pwd2error_label.setVisible(false);
				male.requestFocus();
			}
		}
		else if(e.getSource()==male  && (check()==5 || check()<0))
		{ 
			if(male.isSelected())//if male is selected
			{
				dbGender="Male";
				prsn=true;
				flag[5]=true;
				gendererror_label.setVisible(false);
				phnoText.requestFocus();
			}
			else
				female.requestFocus();//if not selected
		}
		else if(e.getSource()==female && (check()==5 || check()<0))
		{
			if(female.isSelected())//if female is selected
			{
				dbGender="Female";
				prsn=true;
				flag[5]=true;
				gendererror_label.setVisible(false);
				phnoText.requestFocus();
			}
			else//if female not selected
			if(!prsn)//and if male also not selected
			{
				flag[5]=false;
				gendererror_label.setVisible(true);
				male.requestFocus();
			}
		}
		else if(e.getSource()==phnoText && (check()==6 || check()<0))
		{
			dbPhno=phnoText.getText();
		
			if(dbPhno.isEmpty())//if phno is empty
			{
				phnoerror_label.setText("*");
				flag[6]=false;
				phnoText.requestFocus();
				phnoerror_label.setVisible(true);
			}
			else//if not empty
			{
				text_pattern=Pattern.compile("[0-9]{10}");
				contact_matcher=text_pattern.matcher(dbPhno);
				
				if(!contact_matcher.matches())//if pattern do not match 
				{
					phnoerror_label.setText("*Invalid phone number");
					phnoText.setText("");
					flag[6]=false;
					phnoText.requestFocus();
					phnoerror_label.setVisible(true);
				}
				else//if valid ph no
				{
					phnoerror_label.setVisible(false);
					flag[6]=true;
					quesList.requestFocus();
				}
			}
		}
		else if(e.getSource()==quesList && (check()==7 || check()<0))
		{
			dbQue=quesList.getSelectedItem().toString();
			
			if(dbQue.equals("Select Question"))//if question not selected 
			{
				sqerror_label.setVisible(true);
				flag[7]=false;
				quesList.requestFocus();
			}
			else//if selected
			{
				flag[7]=true;
				sqerror_label.setVisible(false);
				ansText.requestFocus();
			}
		}
		else 
		if(e.getSource()==ansText && (check()==8 || check()<0))
		{
			dbAns=ansText.getText();
		
			if(dbAns.isEmpty()) //if ans empty
			{
				saerror_label.setVisible(true);
				flag[8]=false;
				ansText.requestFocus();
			}
			else//if not empty
			{
				flag[8]=true;
				saerror_label.setVisible(false);
			}
		}
	}
	
	/**Description of actionPerformed() method
	 * @param e ActionEvent
	 * @exception SQLException prepareStatement() method of Connection Class throws SQLException
	 * @exception SQLException setInt() method of PreparedStatement Class throws SQLException
	 * @exception SQLException setString() method of PreparedStatement Class throws SQLException
	 * @exception SQLException executeQuery() method of PreparedStatement Class throws SQLException
	 */
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== reset) {
			fname.setText("");
			lname.setText("");
			emailIdText.setText("");
			pwd1.setText("");
			pwd2.setText("");
			male.setSelected(false);//not working for reset
			female.setSelected(false);
			phnoText.setText("");
			ansText.setText("");
			phnoerror_label.setVisible(false);
			gendererror_label.setVisible(false);
			fnameerror_label.setVisible(false);
			lnameerror_label.setVisible(false);
			pwd1error_label.setVisible(false);
			pwd2error_label.setVisible(false);
			sqerror_label.setVisible(false);
			saerror_label.setVisible(false);
			emailerror_label.setVisible(false);
			for(int i=0;i<9;i++)
				flag[i]=false;
			signUpFrame.repaint();
			fname.requestFocus();
		}
		else if(e.getSource()== create)
		{
			System.out.println( "check my output: "+check());
			if(check()==-1) 
			{
				Statement s;
				ResultSet r;//temporary resultset object to check whether email id already exists or not
				try {
					s = DB.con.createStatement();
					r = s.executeQuery("select * from user1 where email_id='"+dbEmail+"';");//checking if email id already exists
									
					if(!r.next())//if not existing
					{
						int id=DB.getMaxId("select max(uid) from user1;");
						try 
						{
							PreparedStatement ps = DB.con.prepareStatement("insert into user1 values(?,?,?,?,?,?,?,?,?)");
							ps.setInt(1,id);
							ps.setString(2,dbFirstname);
							ps.setString(3,dbSurname);
							ps.setString(4,dbEmail);
							ps.setString(5,dbPwd1);
							ps.setString(6,dbGender);
							ps.setString(7,dbPhno);
							ps.setString(8,dbQue);
							ps.setString(9,dbAns);
							
							int num=ps.executeUpdate();
							if(num>0)
							{
								JOptionPane.showMessageDialog(null, "Account created Succesfully");
								new LogIn();
								signUpFrame.dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Error occured.Please try later..");
							}
						}
						catch(Exception f){
							f.printStackTrace();
							f.getMessage();
						}
					}
					else//if email id already exists
					{
						JOptionPane.showMessageDialog(null, "Email Id already exists..");
						flag[2]=false;
						emailIdText.setText("");
						emailIdText.requestFocus();
					}
				} catch (HeadlessException e1) {
					e1.getMessage();
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.getMessage();
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please fill details properly to sign up with Medicine Scheduler");
				int i=check();
				switch(i)
				{
				case 0:
					fname.requestFocus();
					break;
				case 1:
					lname.requestFocus();
					break;
				case 2:
					emailIdText.requestFocus();
					break;
				case 3:
					pwd1.setText("");
					pwd2.setText("");
					flag[4]=false;
					pwd1.requestFocus();
					break;
				case 4:
					pwd2.setText("");
					pwd1.setText("");
					flag[3]=false;
					pwd1.requestFocus();
					break;
				case 5:
					male.requestFocus();
					break;
				case 6:
					phno.requestFocus();
					break;
				case 7:
					quesList.requestFocus();
					break;
				case 8:
					ansText.requestFocus();
					break;
				}
			}
		}
		else 
			if(e.getSource()==back)
			{
				new LogIn();
				signUpFrame.dispose();
			}
	}
	
/*	public static void main(String args[]) {
		new SignUp();
	}*/
}
