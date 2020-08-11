import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import databaseManager.DBManager;
import placeholderTextField.PlaceholderTextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**<h1>Medicine Scheduler Reminder page</h1>
 * This class is designed to provide a user 
 * friendly form for user to add new reminder
 * into the respective schedule.
 * 
 * @author Sayali Mohite
 * @author Shabaj Momin
 * @version 1.0
 * @since   01-01-2016
 */
public class Reminder implements ActionListener,FocusListener {
	
	JFrame reminderFrame;
	PlaceholderTextField time,medicine,qty,description;
	JButton save,home;
	JLabel watch,pills,welcome,todate,fromdate,icon,page,time_label,medicine_label,qty_label,desc_label,info,medicine_error,time_error,qty_error,desc_error;
	JTable reminderinfo;
	DefaultTableModel tableModel;
	DBManager DB=new DBManager();
	JScrollPane srl;
	String dbTime,dbMedicine,dbQty,dbDesc,colHeads[]={"Id","Time","Medicine Name","Quantity","Precautions"},to_dd,from_dd;
	Pattern timePattern;
	Matcher timeMatcher;
	int sid,uid,cnt=0;
	boolean flag[]={false,false,false,false};

	
	/**
	 * This is the Constructor of the Reminder class
     * All Components used on the Reminder frame are 
     * initialised here.All events are added to the 
     * respective components in this method only.
	 * @param id indicates schedule id in which reminder is to be entered .
	 */
	Reminder(int id) {
		DB.loader();
		DB.getConnection();
		sid=id;
		
		ResultSet r;
		r=DB.fetchQuery("select uid from schedule where sid="+sid);
		try
		{
			r.next();
			uid=r.getInt("uid");
		}
		catch(Exception me)
		{
			me.getMessage();
			me.printStackTrace();
		}
		
		reminderFrame =new JFrame("Welcome To Medicine Scheduler");
		reminderFrame.setSize(1310,800);
		reminderFrame.setLayout(null);
			
		page=new JLabel(new ImageIcon("src/main/resources/images/doctor.jpg"));
		page.setBounds(0,0,1320,800);
		
		icon=new JLabel(new ImageIcon("src/main/resources/images/addScheduleIcon.png"));
		icon.setBounds(705,10,100,100);
		
		home=new JButton(new ImageIcon("src/main/resources/images/home.jpeg"));
		home.setBounds(1150,50,80,80);
		home.addActionListener(this);
		home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
		welcome=new JLabel("Please Fill Details to Add Reminder");
		welcome.setBounds(570,120,500,30);
		welcome.setFont(new Font("Ariel",Font.ROMAN_BASELINE,23));
		welcome.setForeground(Color.gray);
		
		ResultSet rs;
		rs=DB.fetchQuery("select fromdate,todate from schedule where sid="+sid+";");
		try
		{
			rs.next();
			from_dd=rs.getString(1);
			to_dd=rs.getString(2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e.getMessage();
		}

		fromdate=new JLabel("From Date : "+from_dd);
		fromdate.setBounds(540,170,250,20);
		fromdate.setForeground(Color.BLUE);
		fromdate.setFont(new Font("Ariel",Font.TYPE1_FONT,17));
		
		todate=new JLabel("To Date : "+to_dd);
		todate.setBounds(800,170,210,20);
		todate.setForeground(Color.BLUE);
		todate.setFont(new Font("Ariel",Font.TYPE1_FONT,17));
		
		time_label=new JLabel("Time");
		time_label.setBounds(590,230,70,20);
		time_label.setForeground(Color.BLUE);
		time_label.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		
		time=new PlaceholderTextField(20);
		time.setBounds(770,230,150,30);
		time.setPlaceholder("Time(24 hr)");
		time.addFocusListener(this);
		
		time_error=new JLabel("*");//
		time_error.setBounds(930,230,200,20);
		time_error.setForeground(Color.RED);
		time_error.setFont(new Font("Ariel",Font.ITALIC,17));
		time_error.setVisible(false);
		
		medicine_label=new JLabel("Medicine Name");
		medicine_label.setBounds(590,280,150,20);
		medicine_label.setForeground(Color.BLUE);
		medicine_label.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		
		medicine=new PlaceholderTextField(80);
		medicine.setBounds(770,280,200,30);
		medicine.setPlaceholder("Medicine(s)");
		medicine.addFocusListener(this);
		
		medicine_error=new JLabel("*");
		medicine_error.setBounds(990,280,70,20);
		medicine_error.setForeground(Color.RED);
		medicine_error.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		medicine_error.setVisible(false);
		
		qty_label=new JLabel("Quantity");
		qty_label.setBounds(590,330,150,20);
		qty_label.setForeground(Color.BLUE);
		qty_label.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		
		qty=new PlaceholderTextField(30);
		qty.setBounds(770,330,200,30);
		qty.setPlaceholder("Quantity");
		qty.addFocusListener(this);
		
		qty_error=new JLabel("*");
		qty_error.setBounds(990,330,70,20);
		qty_error.setForeground(Color.RED);
		qty_error.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		qty_error.setVisible(false);
		
		desc_label=new JLabel("Description");
		desc_label.setBounds(590,380,150,20);
		desc_label.setForeground(Color.BLUE);
		desc_label.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		
		description=new PlaceholderTextField(100);
		description.setBounds(770,380,200,30);
		description.setPlaceholder("Description");
		description.addFocusListener(this);
		
		desc_error=new JLabel("*");
		desc_error.setBounds(990,380,70,20);
		desc_error.setForeground(Color.RED);
		desc_error.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		desc_error.setVisible(false);
		
		save=new JButton("Save Reminder");
		save.setBounds(700,440,150,30);
		save.addActionListener(this);
		
		tableModel=new DefaultTableModel(colHeads, 0);
		
		reminderinfo=new JTable(tableModel)
		{
			private static final long serialVersionUID = 3046483642187695769L;
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		populateTableData();
		
		srl=new JScrollPane(reminderinfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		srl.setBounds(590,530,600,150);
		srl.setVisible(true);
		
		info=new JLabel("Reminders of this Schedule :");
		info.setBounds(570,490,270,20);
		info.setForeground(Color.darkGray);
		info.setFont(new Font("Ariel",Font.CENTER_BASELINE,17));
		
		page.add(home);
		page.add(info);
		page.add(srl);
		page.add(icon);
		page.add(welcome);
		page.add(todate);
		page.add(fromdate);
		page.add(time_label);
		page.add(time);
		page.add(time_error);
		page.add(medicine_label);
		page.add(medicine);
		page.add(medicine_error);
		page.add(qty_label);
		page.add(qty);
		page.add(qty_error);
		page.add(desc_label);
		page.add(description);
		page.add(desc_error);
		page.add(save);
		
		reminderFrame.add(page);
		reminderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		reminderFrame.setVisible(true);
		time.requestFocus();
	}
	
	/**
	 * This method is used to fetch the data of Reminder
	 * table from database into the tabular format.
	 * @exception SQLException next() method of the ResultSet class throws SQLException
	 * @exception SQLException getObject() method of the ResultSet class throws SQLException
	 */
	private void populateTableData()
	{
		ResultSet data;
		data=DB.getDataForReminderTable(sid);
		try {
			int no=tableModel.getRowCount();
			while(no>0)
			{
				tableModel.removeRow(no-1);
				no--;
			}
			while(data.next()) 
			{
				Object row[]=new Object[6];
				for(int i=0; i<6; i++) {
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
	{}
	
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
		if(e.getSource()==time && flag[0]==false)
		{
			dbTime=time.getText();
			if(dbTime.isEmpty())//if time is empty
			{
				time_error.setText("*");
				time_error.setVisible(true);
				flag[0]=false;
				time.requestFocus();
			}
			else//if not empty
			{
				timePattern=Pattern.compile("^[0-9]{1,2}:[0-9]{1,2}$");
				timeMatcher=timePattern.matcher(dbTime);
				if(!timeMatcher.matches())//if time dont match the pattern
				{
					time_error.setText("* Invalid time");
					time.setText("");
					time_error.setVisible(true);
					flag[0]=false;
					time.requestFocus();
				}
				else//if matches -valid time
				{
					time_error.setVisible(false);
					flag[0]=true;
					medicine.requestFocus();
				}
			}
		}
		else if(e.getSource()==medicine && (check()==1 || check()<0))
		{
			dbMedicine=medicine.getText();
			if(dbMedicine.isEmpty())//if description is empty
			{
				medicine_error.setVisible(true);
				flag[1]=false;
				medicine.requestFocus();
			}
			else//if not empty
			{
				medicine_error.setVisible(false);
				flag[1]=true;
				qty.requestFocus();
			}
		}
		else if(e.getSource()==qty && (check()==2 || check()<0))//&& flag[2]==false)
		{
			dbQty=qty.getText();
			if(dbQty.isEmpty())//if qty is empty
			{
				qty_error.setVisible(true);
				flag[2]=false;
				qty.requestFocus();
			}
			else//if not qty
			{
				qty_error.setVisible(false);
				flag[2]=true;
				description.requestFocus();
			}
		}
		else
		if(e.getSource()==description && (check()==3 || check()<0))//&& flag[3]==false)
		{
			dbDesc=description.getText();
			if(dbDesc.isEmpty())//if description is empty
			{
				desc_error.setVisible(true);
				flag[3]=false;
				description.requestFocus();
			}
			else//if not empty
			{
				desc_error.setVisible(false);
				flag[3]=true;
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
			int pk_id=DB.getMaxId("select max(id) from reminder;");
			pk_id++;
			int num=-1;
			if(check()==-1)
			{
				try 
				{
					System.out.println(pk_id+"\t"+dbTime+"\t"+dbMedicine+"\t"+dbQty+"\t"+dbDesc);
					PreparedStatement ps = DB.con.prepareStatement("insert into reminder values(?,?,?,?,?,?)");
					ps.setInt(1,pk_id);
					ps.setString(2,dbTime);
					ps.setString(3,dbMedicine);
					ps.setString(4,dbQty);
					ps.setString(5,dbDesc);
					ps.setInt(6,sid);	
								
					num=ps.executeUpdate();
					
					System.out.println("value of num is: "+num);
					if(num>0) 
					{
						JOptionPane.showMessageDialog(null, "Reminder Added Succesfully");
						tableModel.fireTableDataChanged();
						populateTableData();
						reminderFrame.repaint();
						for(int i=0;i<4;i++)
							flag[i]=false;
					}
					else
						JOptionPane.showMessageDialog(null, "Error occur.Please try later..");
				}
				catch(Exception f) {
					f.printStackTrace();
					f.getMessage();
				}
				tableModel.fireTableStructureChanged();
				time.setText("");
				medicine.setText("");
				qty.setText("");
				description.setText("");
			}
			else
			{
				int i=check();
				switch(i)
				{
				case 0:
					time.requestFocus();
					break;
				case 1:
					medicine.requestFocus();
					break;
				case 2:
					qty.requestFocus();
					break;
				case 3:
					description.requestFocus();
					break;
				}
			}
		}
		else if(e.getSource()==home)
		{
			new HomeScreen(uid);
			reminderFrame.dispose();
		}
	}
	
	/*public static void main(String args[]) {
		new Reminder(2);
	}*/
}