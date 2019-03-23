import java.io.*;
import java.net.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import sun.misc.*;

import java.security.*;

public class Server extends JFrame 
{
	private JPanel panel;
	private JTextField textfield;  //JTextField 
	private JTextArea textarea;  //JTextArea 
	private JRadioButton normaltext;
	private JRadioButton ciphertext;
	private ButtonGroup radioGroup;
	private Cipher c;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket ss;
	private Socket s;
	private Boolean keyset = true; //In order to input Key only once
	private int inctrl; // Whether input is plain text or not  0: plain text, 1: cipher text, 2:key
	private int outctrl = 0;  // Whether output is plain text or not 0: plain text, 1: cipher text
	static public int destport;
	private SecretKeySpec deskey;

	Base64 base = new Base64();
	
	String port;
	
	public Server()
	{
		super("TCPstalkServer");  //Title : TCPstalkSever

		panel = new JPanel(); // Panel for Radio button and Key input
		panel.setLayout(new FlowLayout());

		textfield = new JTextField();
		textfield.setEditable(false);  
		textfield.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						sendData(event.getActionCommand()); // After finishing input, Send sendData as argument
						textfield.setText(""); //Remove text
					}
				}
		);

		add(textfield, BorderLayout.NORTH); //Textfield - North

		textarea = new JTextArea();
		textarea.setEditable(false); 
		add(new JScrollPane(textarea), BorderLayout.CENTER); //TextArea, Panel Center

		normaltext = new JRadioButton("Normal Text", true);
		normaltext.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)					
					{
						String sendctrl = "ctrl>>0";
						try {
							output.writeObject(sendctrl);
						} catch (IOException e) {}
						outctrl = 0;
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
						} catch (IOException e) {}	
					}
				}
		);

		panel.add(ciphertext);

		radioGroup = new ButtonGroup();
		radioGroup.add(normaltext);
		radioGroup.add(ciphertext);

		add(panel, BorderLayout.SOUTH);

		setSize(400,500); //Panel Size
		setVisible(true);  //Visible
		
		port = JOptionPane.showInputDialog("Enter Port Number");
		destport = Integer.parseInt(port);
	}

	public void runServer() // Almost Main
	{
		try
		{
			ss = new ServerSocket(destport);

			while(true)
			{
				try
				{	
					s = ss.accept(); // waiting for the client

					getStream();  //Stream for input output
					process();  //Stream that got in/out put, send/receive msg
				} 
				catch(Exception e) 
				{
					textarea.append("\nTerminated");
				}		
				finally 
				{
					closeConnection();
				}
			}
		}
		catch (IOException ioe) 
		{
			textarea.append("no socket available");
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

	//In/Out stream
	private void getStream() throws IOException
	{
		output = new ObjectOutputStream(s.getOutputStream()); // send stream to client
		output.flush(); //output initialization

		input = new ObjectInputStream(s.getInputStream()); //Save steam to input
	}

	// After connection with client
	private void process() throws IOException, ClassNotFoundException 
	{
		String message = "Connection successful";
		sendData(message);   

		textfield.setEditable(true); 

		while(true)
		{			
			switch(inctrl)
			{
			case 0:				
				message = (String) input.readObject();
				if(selctrl(message) == 9)
					textarea.append("\nCLIENT>>> " + message);
				break;

			case 1:				
				message = (String) input.readObject();

				if(keyset)
					InputKey();

				if(selctrl(message) == 9)
				{
					textarea.append("\nCLIENT(C)>>> " + message);
					try
					{
						c = Cipher.getInstance("DES");
						c.init(Cipher.DECRYPT_MODE, deskey);

						//BASE64Decoder decoder = new BASE64Decoder();

						byte[] clearmessage = c.doFinal(Base64.decode(message));

						String cleartext = new String(clearmessage);

						textarea.append("\nCLIENT>>> " + cleartext);
					} 
					catch (Exception e)
					{
						break;			
					}
				}									
				break;				
			}	
		}
	}

	private int selctrl(String message)
	{
		//ctrl set
		if(message.length() < 7)
			return 9;		
		else if(message.equals("ctrl>>0"))
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
			return 9; // genetral msg
	}

	private void closeConnection()
	{
		textfield.setEditable(false);

		try
		{
			output.close();
			input.close();
			s.close();
		}
		catch (IOException ioException){}
	}

	private void sendData(String message)  
	{        
		try
		{
			switch(outctrl)
			{
			case 0:        	   
				textarea.append("\nSERVER>>> " + message);

				output.writeObject(message);
				output.flush();
				break;
			case 1:
				try
				{
					textarea.append("\nSERVER>>> " + message);

					c = Cipher.getInstance("DES");
					c.init(Cipher.ENCRYPT_MODE, deskey);

					byte [] cipheroutput = message.getBytes();
					byte [] ciphermessage = c.doFinal(cipheroutput);

					//BASE64Encoder encoder = new BASE64Encoder();
					String ciphertext = base.encode(ciphermessage);

					textarea.append("\nSERVER(C)>>> " + ciphertext);

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


