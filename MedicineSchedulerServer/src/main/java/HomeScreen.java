/**<h1>Medicine Scheduler Home page</h1>
 * This is the Home page of user.
 * User can add schedule and 
 * reminders as corresponding buttons 
 * are provided on this screen.
 * 
 * @author  Sayali Mohite
 * @author  Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import databaseManager.DBManager;


/**
 * This class is designed to display the 
 * HomePage separate for every separate user.
 * From this HomePage user can manage his schedules
 * and corresponding reminders.
 */

public class HomeScreen implements ActionListener {
	JFrame frame;
	JLabel page,icon,verticalHeader,info,welcome1,welcome2,watch,pills;
	JButton add,delete,view,addReminder,logOut;
	JTable scheduleinfo,reminderinfo;
	DefaultTableModel tableModel1,tableModel2;
	DBManager DB=new DBManager();
	JScrollPane schedule_srl,reminder_srl;
	int uid,sid;	
	String fname,lname,colHeads_schedule[]={"Id","From Date","To Date","Description","Prescribed By"},colHeads_reminder[]={"Id","Time","Medicine","Quantity","Precautions"};
	boolean flag=false;
	
	/**
	 * * This is the Constructor of the HomeScreen class
     * All Components used on the LogIn frame are 
     * initialised here.All events are added to the 
     * respective components in this method only.
	 * @param id id is the user id using which only that users screen is displayed
	 */
	HomeScreen(int id) {
		DB.loader();
		DB.getConnection();
		uid=id;
		frame= new JFrame("Home Screen");
		frame.setLayout(null);
		frame.setSize(1310,800);
			
		verticalHeader = new JLabel(new ImageIcon("src/main/resources/images/header.jpg"));
		verticalHeader.setBounds(0,0,240,800);
		verticalHeader.setBorder(new LineBorder(Color.WHITE));
				
		page=new JLabel(new ImageIcon("src/main/resources/images/doctor1.jpg"));
		page.setBounds(0,0,1311,800);
		
		icon= new JLabel(new ImageIcon("src/main/resources/images/HomeIcon.jpg"));
		icon.setBounds(40,10,145,146);
		icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		ResultSet r;
		r=DB.fetchQuery("select fname,lname from user1 where uid="+uid+";");
		try
		{
			r.next();
			fname=r.getString(1);
			lname=r.getString(2);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			e.getMessage();
		}
		
		welcome1=new JLabel("Dear "+fname+" "+lname+",");
		welcome1.setBounds(250,55,300,30);
		welcome1.setForeground(Color.blue);
		welcome1.setFont(new Font("Ariel",Font.HANGING_BASELINE,30));
		
		welcome2=new JLabel("Welcome To Medicine Scheduler..");
		welcome2.setBounds(300,100,500,30);
		welcome2.setForeground(Color.BLUE);
		welcome2.setFont(new Font("Ariel",Font.HANGING_BASELINE,30));
		
		add=new JButton("Add Schedule");
		add.addActionListener(this);
		add.setBounds(45,250,150,30);
		add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		delete=new JButton("Delete Schedule");
		delete.addActionListener(this);
		delete.setBounds(45,300,150,30);
		delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		view=new JButton("View Schedule");
		view.addActionListener(this);
		view.setBounds(45,350,150,30);
		view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		addReminder=new JButton("Add Reminder");
		addReminder.addActionListener(this);
		addReminder.setBounds(45,400,150,30);
		addReminder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		info=new JLabel("Your Schedules are: ");
		info.setForeground(Color.BLUE);
		info.setFont(new Font("Ariel",Font.ITALIC,25));
		info.setBounds(250,230,500,50);
		
		logOut=new JButton("Sign Out");
		logOut.addActionListener(this);
		logOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logOut.setBounds(45,450,150,30);
		
		tableModel1=new DefaultTableModel(colHeads_schedule, 0);
		scheduleinfo=new JTable(tableModel1)
		{
			private static final long serialVersionUID = 3046483642187695769L;
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		populateTableData();
			
		schedule_srl=new JScrollPane(scheduleinfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		schedule_srl.setVisible(true);
		schedule_srl.setBounds(300,300,590,250);
		schedule_srl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
		tableModel2=new DefaultTableModel(colHeads_reminder, 0);
		reminderinfo=new JTable(tableModel2)
		{
			private static final long serialVersionUID = 3046483642187695769L;
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
			
		reminder_srl=new JScrollPane(reminderinfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		reminder_srl.setVisible(false);
		reminder_srl.setBounds(300,300,590,250);
		reminder_srl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
		verticalHeader.add(icon);
		verticalHeader.add(add);
		verticalHeader.add(delete);
		verticalHeader.add(view);
		verticalHeader.add(addReminder);
		verticalHeader.add(logOut);
		
		watch=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/watch.png"));
		watch.setBounds(30,510,150,150);

		pills=new JLabel(new ImageIcon("src/main/resources/images/panelScroll/pills.png"));
		pills.setBounds(150,570,80,80);
		
		verticalHeader.add(watch);
		verticalHeader.add(pills);

		page.add(verticalHeader);
		page.add(welcome1);
		page.add(welcome2);
		page.add(schedule_srl);
		page.add(reminder_srl);
		page.add(info);
						
		frame.add(page);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * This method is used to fetch the data of schedule and reminder
	 * table from database into the tabular format.
	 * @exception SQLException next() method of the ResultSet class throws SQLException
	 * @exception SQLException getObject() method of the ResultSet class throws SQLException
	 */
	private void populateTableData() 
	{
		if(flag)//reminder
		{
			ResultSet data=DB.getDataForReminderTable(sid);
			try 
			{
				int no=tableModel2.getRowCount();
				while(no>0)
				{
					tableModel2.removeRow(no-1);
					no--;
				}
				while(data.next()) 
				{
					Object row[]=new Object[6];
					for(int i=0; i<5; i++) 
					{
						row[i]=data.getObject(i+1);
					}
					tableModel2.addRow(row);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
		else//schedule
		{
			ResultSet data=DB.getDataForScheduleTable(uid);
			try 
			{
				while(data.next()) 
				{
					Object row[]=new Object[6];
					for(int i=0; i<6; i++) 
					{
						row[i]=data.getObject(i+1);
					}
					tableModel1.addRow(row);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
	}
	
	/**
	 * @param e Object of ActionEvent class
	 * @exception NumberFormatException parseInt() method of the Integer class throws NumberFormatException
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==add) 
		{
			new AddSchedule(uid);
			frame.dispose();
		}
		else if(e.getSource()==delete) 
		{
			if(flag)//reminder
			{
				int row =reminderinfo.getSelectedRow();
				if(row==-1)
					JOptionPane.showMessageDialog(frame, "please select a row", "no record selected", JOptionPane.ERROR_MESSAGE);
				else
				{
					int primarykey = Integer.parseInt(reminderinfo.getValueAt(row, 0).toString());
					if(JOptionPane.showConfirmDialog(frame, "Are You Sure You want to Delete this Reminder?","Delete",JOptionPane.OK_CANCEL_OPTION)==0)
					{
						String query="delete from reminder where id="+primarykey+";";
						if(DB.deleteRecord(query))
						{
							tableModel2.removeRow(row);
							tableModel2.fireTableStructureChanged();
							JOptionPane.showMessageDialog(frame,"Reminder deleted","Info", JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(frame,  "Reminder not deleted","Info", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			else//schedule
			{
				int row =scheduleinfo.getSelectedRow();
				if(row==-1)
				{
					JOptionPane.showMessageDialog(frame, "please select a row", "no record selected", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					int primarykey = Integer.parseInt(scheduleinfo.getValueAt(row, 0).toString());
					if(JOptionPane.showConfirmDialog(frame, "Are You Sure You want to Delete this Schedule?","Delete",JOptionPane.OK_CANCEL_OPTION)==0)
					{
						String query="delete from reminder where sid="+primarykey+";";
						DB.deleteRecord(query);
					
						query="delete from schedule where sid="+primarykey+";";
						if(DB.deleteRecord(query))
						{
							tableModel1.removeRow(row);
							tableModel1.fireTableStructureChanged();
							JOptionPane.showMessageDialog(frame,"Schedule deleted","Info", JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(frame,  "Schedule not deleted","Info", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
		else if(e.getSource()==view) 
		{
			if(flag)//back to schedule list
			{
				flag=false;
				reminder_srl.setVisible(false);
				view.setText("View Schedule");
				info.setText("Your Schedules are: ");
				schedule_srl.setVisible(true);
				add.setVisible(true);
				delete.setText("Delete Schedule");
			}
			else
			{
				int row =scheduleinfo.getSelectedRow();
				if(row==-1)
				{
					JOptionPane.showMessageDialog(frame, "please select a row", "no record selected", JOptionPane.ERROR_MESSAGE);
				}
				else//displaying reminders belonging to particular schedule
				{
					schedule_srl.setVisible(false);
					add.setVisible(false);
					flag=true;
					int primarykey = Integer.parseInt(scheduleinfo.getValueAt(row,0).toString());
					sid=primarykey;
					populateTableData();
					tableModel2.fireTableStructureChanged();
					reminder_srl.setVisible(true);
					info.setText("Reminders of the selected Schedule are:");
					view.setText("Schedule List");
					delete.setText("Delete Reminder");
				}
			}
		}
		else if(e.getSource()==addReminder) 
		{
			if(flag)
			{
				new Reminder(sid);
				frame.dispose();
			}
			else
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
		}
		else 
		if(e.getSource()==logOut)
		{
			new LogIn();
			frame.dispose();
		}
	}
	
	/*public static void main(String[] args) {
		new HomeScreen(2);
	}*/
}