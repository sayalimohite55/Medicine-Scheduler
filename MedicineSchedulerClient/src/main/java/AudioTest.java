import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class AudioTest 
{
	public AudioTest() 
	{
		try
		{
			System.out.println("in audio play");
			FileInputStream fis=new FileInputStream("/home/space/Downloads/iphoneorig_7sRzMJqj.mp3");
			//Player player = Manager.createPlayer(new MediaLocator(new File("/home/kartik/sound.mp3").toURI().toURL()));
			Player player=new Player(fis);  
			player.play();
			player.close();
			System.out.println("audio end");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}