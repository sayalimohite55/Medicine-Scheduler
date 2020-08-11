import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

import databaseManager.DBManager;
import placeholderTextField.PlaceholderTextField;

/**<h1>Medicine Scheduler AddSchedule page</h1>
 * This class is designed to provide a user 
 * friendly form for user to add new schedule.
 * @author Sayali Mohite
 * @author Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */

class AddSchedule implements ActionListener,FocusListener {

	JFrame frame;
	PlaceholderTextField fdate,tdate,desc,doctor;
	JLabel watch,pills,page,welcome,fromDate,toDate,description,prescribedBy,info,date_error,desc_error,doctor_error;
	JButton reset,back,save,add;
	JTable scheduleinfo;
	DefaultTableModel tableModel;
	DBManager DB=new DBManager();
	JScrollPane srl;
	int pk_id,uid;
	String dbfromdate,dbtodate,dbDescription,dbPrescribedBy,colHeads[]={"Id","From Date","To Date","Description","Prescribed By"};
	boolean flag[]={false,false,false,false},startDate=false,endDate=false,toggle=false;
	MyDate date=new MyDate();
	
	/**
	 * This is the Constructor of the AddSchedule class
     * All Components used on the AddSchedule frame are 
     * initialised here.All events are added to the 
     * respective components in this method only.
	 * @param id indicates user id whose schedule is to be entered into database.
	 */
	AddSchedule(int id) 
	{
		System.out.println("Hello sayali");
		DB.loader();
		DB.getConnection();
		uid=id;
		frame=new JFrame();
		frame.setLayout(null);
		frame.setSize(1311,800);
		
		page=new JLabel(new ImageIcon("src/main/resources/images/doctor.jpg"));
		page.setBounds(0,0,1311,800);
		
		welcome=new JLabel("Please Enter Your Schedule");
		welcome.setBounds(480,70,400,40);
		welcome.setFont(new Font("Ariel",Font.ROMAN_BASELINE,30));
		welcome.setForeground(Color.BLUE);
			
		fromDate=new JLabel("Start Date");
		fromDate.setBounds(460,180,100,25);
		fromDate.setForeground(Color.BLUE);
		fromDate.setFont(new Font("Ariel",Font.CENTER_BASELINE,15));

		fdate=new PlaceholderTextField(20);
		fdate.setBounds(580,180,100,25);
		fdate.setPlaceholder("YYYY/MM/DD");
		fdate.addFocusListener(this);
				
		toDate=new JLabel("To Date");
		toDate.setBounds(700,180,70,25);
		toDate.setForeground(Color.BLUE);
		toDate.setFont(new Font("Ariel",Font.CENTER_BASELINE,15));
	
		tdate=new PlaceholderTextField(20);
		tdate.setBounds(780,180,100,25);
		tdate.setPlaceholder("YYYY/MM/DD");
		tdate.addFocusListener(this);

		date_error=new JLabel("*");
		date_error.setBounds(900,180,400,20);
		date_error.setForeground(Color.RED);
		date_error.setFont(new Font("Ariel",Font.ROMAN_BASELINE,15));
		date_error.setVisible(false);
		
		description=new JLabel("Description");
		description.setBounds(460,245,100,25);
		description.setForeground(Color.BLUE);
		description.setFont(new Font("Ariel",Font.CENTER_BASELINE,15));
			
		desc=new PlaceholderTextField(40);
		desc.setBounds(580,245,200,25);
		desc.setPlaceholder("Description");
		desc.addFocusListener(this);
		
		desc_error=new JLabel("*");
		desc_error.setBounds(800,245,300,20);
		desc_error.setForeground(Color.RED);
		desc_error.setFont(new Font("Ariel",Font.ROMAN_BASELINE,15));
		desc_error.setVisible(false);
		
		prescribedBy=new JLabel("Prescribed By");
		prescribedBy.setBounds(460,305,120,25);
		prescribedBy.setForeground(Color.BLUE);
		prescribedBy.setFont(new Font("Ariel",Font.CENTER_BASELINE,15));

		doctor=new PlaceholderTextField(30);
		doctor.setBounds(580,305,150,25);
		doctor.setPlaceholder("Name of Doctor");
		doctor.addFocusListener(this);

		doctor_error=new JLabel("*");
		doctor_error.setBounds(750,305,300,20);
		doctor_error.setForeground(Color.RED);
		doctor_error.setFont(new Font("Ariel",Font.ROMAN_BASELINE,15));
		doctor_error.setVisible(false);

		save=new JButton("Save Schedule");
		save.setBounds(580,365,130,30);
		save.addActionListener(this);
		save.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		tableModel=new DefaultTableModel(colHeads, 0);
		scheduleinfo=new JTable(tableModel)
		{
			private static final long serialVersionUID = 3046483642187695769L;
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		
		populateTableData();
		
		info=new JLabel("Your recent schedules are : ");
		info.setBounds(450,415,400,35);
		info.setForeground(Color.BLUE);
		info.setFont(new Font("Ariel",Font.PLAIN,20));
		
		srl=new JScrollPane(scheduleinfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		srl.setBounds(470,465,600,150);
		srl.setVisible(true);
		srl.addFocusListener(this);
		srl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		add=new JButton("Add Reminder");
		add.setBounds(700,630,130,30);
		add.addActionListener(this);
		add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		back=new JButton(new ImageIcon("src/main/resources/images/home.jpeg"));
		back.setBounds(1150,50,80,80);
		back.addActionListener(this);
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		watch=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/watch.png"));
		watch.setBounds(1070,510,150,150);

		pills=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pills.png"));
		pills.setBounds(1190,580,80,80);
		
		frame.add(watch);
		frame.add(pills);
		
		frame.add(welcome);
		frame.add(fromDate);
		frame.add(fdate);
		frame.add(toDate);
		frame.add(tdate);
		frame.add(date_error);
		frame.add(description);
		frame.add(desc);
		frame.add(desc_error);
		frame.add(prescribedBy);
		frame.add(doctor);
		frame.add(doctor_error);
		frame.add(back);
		frame.add(save);
		frame.add(info);
		frame.add(srl);
		//frame.add(add);
		
		frame.add(page);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		fdate.requestFocus();
	}
	
	/**
	 * This method is used to fetch the data of schedule
	 * table from database into the tabular format.
	 * @exception SQLException next() method of the ResultSet class throws SQLException
	 * @exception SQLException getObject() method of the ResultSet class throws SQLException
	 */
	private void populateTableData() 
	{
		ResultSet data=DB.getDataForScheduleTable(uid);
		try 
		{
			int no=tableModel.getRowCount();
			while(no>0)
			{
				tableModel.removeRow(no-1);
				no--;
			}
			while(data.next()) 
			{
				Object row[]=new Object[6];
				for(int i=0; i<6; i++) 
				{
					row[i]=data.getObject(i+1);
				}
				tableModel.addRow(row);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
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
	{
	}
	
	/**
	 * This method is used to check whether
	 * any flag is having a false value or not,
	 * to decide whether all inputs are given 
	 * correctly or not.
	 * @return integer it returns the index of the flag which is having false value
	 */
	int check()
	{
		for(int i=0;i<4;i++)
		{
			if(flag[i]==false)
				return i;
		}
		return -1;
	}
	
	/**
	 * This method is used to check validations as soon
	 * as the focus is lost by any of the input component.
	 * @param e Object of the FocusEvent.
	 */
	public void focusLost(FocusEvent e)
	{
		if(e.getSource()==fdate || flag[0]==false)
		{
			dbfromdate=fdate.getText();
			if(dbfromdate.isEmpty())//if from date is empty
			{
				flag[0]=false;
				date_error.setText("* Enter start date of schedule");
				date_error.setVisible(true);
				fdate.requestFocus();
			}
			else//if from date is not empty
			{
				startDate=date.validDate(dbfromdate);
				if(startDate)//if date is valid
				{
					flag[0]=true;
					date_error.setVisible(false);
					if(flag[1]==false)
						tdate.requestFocus();
					else
					{
						dbtodate=tdate.getText();
						endDate=date.validDate(dbtodate);
						if(endDate)//if to date is valid
						{
							if(date.validDate(dbfromdate, dbtodate))//if both dates are valid
							{
								flag[0]=true;
								flag[1]=true;
								date_error.setVisible(false);
								desc.requestFocus();
							}
							else //if not
							{
								flag[0]=false;
								flag[1]=false;
								fdate.setText("");
								tdate.setText("");
								date_error.setText("* Invalid start and end date for the schedule");
								date_error.setVisible(true);
								fdate.requestFocus();
							}
						}
						else//if todate is invalid
						{
							tdate.setText("");
							date_error.setText("* Invalid end date of Schedule");
							date_error.setVisible(true);
							flag[1]=false;
							tdate.requestFocus();
						}
					}
				}
				else//if date is invalid
				{
					fdate.setText("");
					date_error.setText("* Invalid start date of Schedule");
					date_error.setVisible(true);
					flag[0]=false;
					fdate.requestFocus();
				}
			}
		}
		else if(e.getSource()==tdate && flag[1]==false && flag[0]==true)
		{
			dbtodate=tdate.getText();
			if(dbtodate.isEmpty())//if to date is empty
			{
				flag[1]=false;
				date_error.setText("* Enter end date of schedule");
				date_error.setVisible(true);
				tdate.requestFocus();
			}
			else//if to date is not empty
			{
				endDate=date.validDate(dbtodate);
				if(endDate)//if to date is valid
				{
					if(date.validDate(dbfromdate, dbtodate))//if both dates are valid
					{
						flag[0]=true;
						flag[1]=true;
						date_error.setVisible(false);
						desc.requestFocus();
					}
					else //if not
					{
						flag[0]=false;
						flag[1]=false;
						fdate.setText("");
						tdate.setText("");
						date_error.setText("* Invalid start and end date for the schedule");
						date_error.setVisible(true);
						fdate.requestFocus();
					}
				}
				else//if todate is invalid
				{
					tdate.setText("");
					date_error.setText("* Invalid end date of Schedule");
					date_error.setVisible(true);
					flag[1]=false;
					tdate.requestFocus();
				}
			}
		}
		else if(e.getSource()==desc && (check()==2 || check()<0))
		{
			dbDescription=desc.getText();
			
			if(dbDescription.isEmpty())//if description is empty
			{
				desc_error.setText("* Enter Description");
				desc_error.setVisible(true);
				flag[2]=false;
				desc.requestFocus();
			}
			else//if not empty
			{
				flag[2]=true;
				desc_error.setVisible(false);
				doctor.requestFocus();
			}
		}
		else
		if(e.getSource()==doctor && (check()==3 || check()<0))
		{
			dbPrescribedBy=doctor.getText();
	
			if(dbPrescribedBy.isEmpty())
			{
				doctor_error.setText("* Enter Doctor Name");
				doctor_error.setVisible(true);
				flag[3]=false;
				doctor.requestFocus();
			}
			else
			{
				flag[3]=true;
				doctor_error.setVisible(false);
			}
		}
	}
	
	/**
	 * @param e Object of ActionEvent class
	 * @exception SQLException prepareStatement() method of the Connection class throws SQLException
	 * @exception SQLException setInt() method of the PreaparedStatement class throws SQLException
	 * @exception SQLException setString() method of the PreaparedStatement class throws SQLException
	 * @exception SQLException executeUpdate() method of the PreaparedStatement class throws SQLException
	 * @exception NumberFormatException parseInt() method of the Integer class throws NumberFormatException
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==save) 
		{
			if(check()==-1)
			{
				try 
				{
					pk_id=DB.getMaxId("select max(sid) from schedule;");
					PreparedStatement ps = DB.con.prepareStatement("insert into schedule values(?,?,?,?,?,?)");
					ps.setInt(1,pk_id);
					ps.setString(2,dbfromdate);
					ps.setString(3,dbtodate);
					ps.setString(4,dbDescription);
					ps.setString(5,dbPrescribedBy);
					ps.setInt(6,uid);	
								
					int num=ps.executeUpdate();
					
					if(num>0) {
						JOptionPane.showMessageDialog(null, "Schedule Added Succesfully");
						fdate.setText("");
						tdate.setText("");
						desc.setText("");
						doctor.setText("");
						
						tableModel.fireTableStructureChanged();
						populateTableData();
						frame.repaint();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Error occur.Please try again..");
						fdate.setText("");
						tdate.setText("");
						desc.setText("");
						doctor.setText("");
					}
				}
				catch(Exception f) {
					f.printStackTrace();
					f.getMessage();
				}
				tableModel.fireTableStructureChanged();
				
				dbDescription=null;
				dbfromdate=null;
				dbPrescribedBy=null;
				dbtodate=null;
				for(int i=0;i<4;i++)
					flag[i]=false;
				frame.repaint();
				
			}
			else
			{
				JOptionPane.showMessageDialog(null, "please fill all details");
				int i=check();
				switch(i)
				{
				case 0:
					fdate.requestFocus();
					break;
				case 1:
					tdate.requestFocus();
					break;
				case 2:
					desc.requestFocus();
					break;
				case 3:
					doctor.requestFocus();
					break;
				}
			}
		}
		else if(e.getSource()==add)
		{
			int row =scheduleinfo.getSelectedRow();
			if(row==-1)
			{
				JOptionPane.showMessageDialog(frame, "please select a row", "no record selected", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				int primarykey = Integer.parseInt(scheduleinfo.getValueAt(row, 0).toString());
				new Reminder(primarykey);
				frame.dispose();
			}
		}
		else
		if(e.getSource()==back)
		{
			new HomeScreen(uid);
			frame.dispose();
		}
	}
		
	/*public static void main(String arg[]) {
		new AddSchedule(2);
		System.out.println();
	}*/
}
