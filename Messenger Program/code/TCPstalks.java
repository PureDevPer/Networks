import javax.swing.JFrame;

public class TCPstalks 
{
	public static void main(String[] args) 
	{
		Server app = new Server(); 
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.runServer(); 
	}
}
