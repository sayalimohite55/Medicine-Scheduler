import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

public class DetectMotionExample implements WebcamMotionListener
{
	private static int cnt=0;
	WebcamMotionDetector detector;
	String imagename;
	int flag=1;
	
	public DetectMotionExample() 
	{
		detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(100); // one check per 100 ms
		detector.addMotionListener(this);
		detector.start();
	}

	public void motionDetected(WebcamMotionEvent wme) 
	{
		try 
		{
			Webcam.setAutoOpenMode(true);
			BufferedImage image = Webcam.getDefault().getImage();
			imagename="test"+(cnt++)+".png";
			String fname="src/images/"+imagename;
			ImageIO.write(image, "PNG", new File(fname));
			System.out.println("Detected motion I, alarm turn on you have");
			if(flag==1)
			{
				System.out.println(imagename);
				new SendImage(imagename);
				flag=0;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) throws IOException 
	{
		new DetectMotionExample();
		System.in.read(); // keep program open
	}*/
}
