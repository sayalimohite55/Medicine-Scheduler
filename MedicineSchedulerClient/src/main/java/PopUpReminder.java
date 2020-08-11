import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;


public class PopUpReminder 
{
	JFrame popUpFrame;
	ResultSet r;
	JLabel label[];
	
	public PopUpReminder(int rid,String dose_time,String mediname,String qty,String precautions) 
	{
		popUpFrame=new JFrame("Welcome To Medicine Scheduler");
		popUpFrame.setBounds(400,100,500,400);
		popUpFrame.setLayout(null);
		popUpFrame.setContentPane(new JLabel(new ImageIcon("src/main/resources/images")));
		
		label=new JLabel[4];
		
		label[0]=new JLabel("Time : "+dose_time);
		label[0].setBounds(100,50,200,50);
		
		label[1]=new JLabel("Medicine : "+mediname);
		label[1].setBounds(100,100,200,50);
		
		label[2]=new JLabel("Quantity : "+qty);
		label[2].setBounds(100,150,200,50);
		
		label[3]=new JLabel("Precautions : "+precautions);
		label[3].setBounds(100,200,200,50);
		
		
		for(int i=0;i<4;i++)
			popUpFrame.add(label[i]);
		
		popUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popUpFrame.setVisible(true);
	}
	
}
