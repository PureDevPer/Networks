import java.io.*;
import java.net.*;
import java.lang.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import sun.misc.*;

import java.security.*;

public class Client extends JFrame
{
	private JPanel panel;
	private JTextField textfield;  //JTextField 
	private JTextArea textarea;  //JTextArea 
	private JRadioButton normaltext;
	private JRadioButton ciphertext;
	private ButtonGroup radioGroup;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private SecretKeySpec deskey;
	private Cipher c;
	private String message="";
	private Socket s;
	private Boolean keyset = true;
	private int inctrl; //Whether input is plain text or not  0: plain text, 1: cipher text
	private int outctrl = 0;  //Whether output is plain text or not 0: plain text, 1: cipher text
	static public int destport;
	private String port;

	Base64 base = new Base64();
	
	public Client( String host) //Client constructor
	{
		super("TCPstalkClient");  //Title is TCPstalkClient
		panel = new JPanel(); //Panel for radio button and key input
		panel.setLayout(new FlowLayout());

		textfield = new JTextField(); 
		textfield.setEditable(false); 
		textfield.addActionListener( 
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						sendData(event.getActionCommand()); //Once input, send sendData as argument
						textfield.setText(""); // textfield initialization
					}
				}
		);

		add(textfield, BorderLayout.NORTH); 

		textarea = new JTextArea(); 
		textarea.setEditable(false);
		add(new JScrollPane(textarea), BorderLayout.CENTER); 

		normaltext = new JRadioButton("Normal Text", true);
		normaltext.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)					
					{					
						try {
							String sendctrl = "ctrl>>0";					
							output.writeObject(sendctrl);
							outctrl = 0;
						} catch (IOException e) {			
							e.printStackTrace();
						}
					}
				}
		);
		panel.add(normaltext);

		ciphertext = new JRadioButton("Cipher Text", false);
		ciphertext.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{			
						if(keyset)
							InputKey();
						try {
							String sendctrl = "ctrl>>1";
							output.writeObject(sendctrl);
							outctrl = 1;			
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		);	
		panel.add(ciphertext);

		radioGroup = new ButtonGroup();
		radioGroup.add(normaltext);
		radioGroup.add(ciphertext);

		add(panel, BorderLayout.SOUTH);

		setSize(400,500); 
		setVisible(true); 
	}

	public void runClient() //actual execution.
	{
		try
		{			
			connect(); 		
			getStream(); // Stream for getting in/out
			process(); //send / recevie msg
		} 
		catch ( Exception e) 
		{
			textarea.append("\nTerminated....");
		}	
		finally 
		{
			closeConnection();
		}
	}

	private void InputKey()
	{
		String Ikey = JOptionPane.showInputDialog("Write 8 letters for KEY");
		if(Ikey.length() == 8)
		{
			deskey = new SecretKeySpec(Ikey.getBytes(), "DES");
			keyset = false;
		}	
		else 
		{
			JOptionPane.showMessageDialog(null, "Write 8 letters for KEY");
			InputKey();
		}
	}

	private  void connect() 
	{
		String desthost1 = "localhost"; 
		String dest = "";
		
		port = JOptionPane.showInputDialog("Enter Server's Port Number");
		destport = Integer.parseInt(port);
		
		InetAddress dest1;
		textarea.append("Looking up address of " + desthost1 + "...");
		try {
			dest = JOptionPane.showInputDialog("Enter Server's IP");
			dest1 = InetAddress.getByName(desthost1);
		}
		catch (UnknownHostException uhe) {
			textarea.append("\nunknown host: " + desthost1);
			return;
		}
		textarea.append("\ngot it!");

		try {
			s = new Socket(dest, destport);
		}
		catch(IOException ioe)
		{
			textarea.append("\nno socket available");
			return;
		}
		textarea.append("\nport=" + s.getLocalPort());
	}

	private void getStream() throws IOException
	{
		output = new ObjectOutputStream(s.getOutputStream());
		output.flush();

		input = new ObjectInputStream(s.getInputStream());
	}

	private void process() throws IOException, ClassNotFoundException 
	{
		textfield.setEditable(true);

		while(true)
		{
			switch(inctrl)
			{
			case 0:				
				message = (String) input.readObject();
				if(selctrl(message) == 9)
					textarea.append("\nSERVER>>> " + message);				
				break;				
			case 1:			
				message = (String) input.readObject();

				if(keyset)
					InputKey();				

				if(selctrl(message) == 9)
				{							
					textarea.append("\nSERVER(C)>>> " + message);
					try
					{
						c = Cipher.getInstance("DES");
						c.init(Cipher.DECRYPT_MODE, deskey);

						//BASE64Decoder decoder = new BASE64Decoder();

						byte[] clearmessage = c.doFinal(Base64.decode(message));

						String cleartext = new String(clearmessage);

						textarea.append("\nSERVER>>> " + cleartext);
					} 
					catch (Exception e) 
					{
						break;			
					}				
					break;										
				}
			}
		}	
	}

	private int selctrl(String message)
	{
		//ctrl set
		if(message.length() < 7)
			return 9;

		if(message.equals("ctrl>>0"))
		{
			inctrl = 0;
			return 8; 
		}
		else if(message.equals("ctrl>>1"))
		{
			inctrl = 1;
			return 8;
		}
		else 
			return 9; 
	}

	private void closeConnection()
	{
		textfield.setEditable(false);

		try
		{
			input.close();
			output.close();
			s.close();
		} catch (IOException ioException) {}
	}

	private void sendData(String message)
	{
		try
		{
			switch(outctrl)
			{
			case 0:        	   
				textarea.append("\nCLIENT>>> " + message);

				output.writeObject(message);
				output.flush();
				break;
			case 1:
				try
				{
					textarea.append("\nCLIENT>>> " + message);

					c = Cipher.getInstance("DES");
					c.init(Cipher.ENCRYPT_MODE, deskey);

					byte [] ciphermessage = c.doFinal(message.getBytes());

					//BASE64Encoder encoder = new BASE64Encoder();
					String ciphertext = Base64.encode(ciphermessage);

					textarea.append("\nCLIENT(C)>>> " + ciphertext); 

					output.writeObject(ciphertext);
					break;	 
				} 
				catch (Exception e) 
				{ 
					break; 
				}         	
			}
		}
		catch (IOException ioe) 
		{
			textarea.append("\nError writing object");
		} 	
	}
}
