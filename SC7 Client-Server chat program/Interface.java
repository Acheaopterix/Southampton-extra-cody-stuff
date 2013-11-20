import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import client.Client;
import server.Server;

//pony censor module

@SuppressWarnings("serial")
public class Interface extends JFrame implements ActionListener
{

	Thread sv;
	Client cl; 
	ArrayList<String> messagesToSend = new ArrayList<String>();
	
	JPanel mainScreen;
	
	JPanel lowerhalf;
	JPanel upperhalf;
	
	JPanel upperhalfLeft;
	JPanel upperhalfRight;
	
	JPanel upperhalfRightUpperClientSetting;
	JPanel upperhalfRightLowerServerSetting;
	
	JScrollPane scrollChat;
	JScrollPane scrollConsole;
	JScrollPane scrollMessage;
	
	JTextArea mesgBox = new JTextArea(1, 0); 
	JTextArea chatBox = new JTextArea(10, 30);
	JTextArea console = new JTextArea(10, 0); 
	
	JButton createClient = new JButton("Open connection");
	JButton logoutClient = new JButton("Logout");
	JButton createServer = new JButton("Host Server");
	JButton stopServer = new JButton("Stop Server");
	JButton sendMessage = new JButton("Send Message");
	
	JTextField serverPort = new JTextField("11393");
	JTextField clientPort = new JTextField("11393");
	JTextField clientAddress = new JTextField("127.0.0.1");
	JTextField clientName = new JTextField("Please Enter A Username");
	
	JLabel serverPortLabel = new JLabel("Enter host port");
	JLabel clientPortLabel = new JLabel("Enter server port");
	JLabel clientAddressLabel = new JLabel("Enter server address");
	JLabel clientNameLabel = new JLabel("Enter username");
	
	public Interface()
	{
		createClient.addActionListener(this);
		logoutClient.addActionListener(this);
		createServer.addActionListener(this);
		stopServer.addActionListener(this);
		sendMessage.addActionListener(this);
		
		setComponentLayouts();
		
		setTextAreaAttributes();
		
		setLowerPanelComponents();
		
		setServerClientOptioncomponents();
		
		setSectionPositions();

		this.add(mainScreen);
	}
	
	@Override
	public void actionPerformed(ActionEvent b)
	{
		if(b.getSource() == createClient)
		{
			if(clientName.getText().equals("Please Enter A Username"))
			{
				console.append("Please Enter A Username" + "\n");
			}
			else
			{
				try 
				{
					cl = new Client(clientAddress.getText(), Integer.parseInt(clientPort.getText()), clientName.getText(), chatBox, messagesToSend);
					cl.runClient();
					createClient.setEnabled(false);
					sendMessage.setEnabled(true);
				} 
				catch (NumberFormatException | IOException e) 
				{
					console.append("Failed to connect to server" + "\n");
				}			
			}
		}
		else if(b.getSource() == logoutClient)
		{
			cl.logout();
			createClient.setEnabled(true);
			sendMessage.setEnabled(false);
		}
		else if(b.getSource() == sendMessage)
		{
			if(createClient.isEnabled() == false)
			{
				String s = mesgBox.getText();
				if(s.equals("") || s.equals(null))
				{
					console.append("Must enter something to send" + "\n");
				}
				else
				{
					messagesToSend.add(s);
					mesgBox.setText("");
				}		
			}	
		}
		else if(b.getSource() == stopServer)
		{
			((Server) sv).stopServer();
			createServer.setEnabled(true);
			stopServer.setEnabled(false);
		}
		else if(b.getSource() == createServer)
		{
			try
			{
			Integer.parseInt(serverPort.getText());
				if(serverPort.getText().length() > 5 || Integer.parseInt(serverPort.getText()) > 65535 || Integer.parseInt(serverPort.getText()) < 1)
				{
					serverPort.setText("Invalid Port Number");
				}
				else
				{
					try 
					{
						sv = new Server(Integer.parseInt(serverPort.getText()), console, chatBox);
						sv.start();
						createServer.setEnabled(false);
						stopServer.setEnabled(true);
					}
					catch (IOException io)
					{
						console.append("Something already running on this port" + "\n");
						createServer.setEnabled(true);
						stopServer.setEnabled(false);
					}	
				}
			}
			catch (NumberFormatException nfe)
			{
				serverPort.setText("Invalid Port Number");
			}
		}
	}
	
	private void setSectionPositions()
	{
		upperhalfRight.add(upperhalfRightUpperClientSetting, BorderLayout.NORTH);
		upperhalfRight.add(upperhalfRightLowerServerSetting, BorderLayout.SOUTH);

		upperhalfLeft.add(scrollChat, BorderLayout.CENTER);
		
		upperhalf.add(upperhalfLeft, BorderLayout.CENTER);
		upperhalf.add(upperhalfRight, BorderLayout.EAST);		

		mainScreen.add(lowerhalf, BorderLayout.SOUTH); 
		mainScreen.add(upperhalf, BorderLayout.CENTER); 
	}
	
	private void setServerClientOptioncomponents()
	{
		upperhalfRightUpperClientSetting.add(clientPortLabel);
		upperhalfRightUpperClientSetting.add(clientPort);
		upperhalfRightUpperClientSetting.add(clientAddressLabel);
		upperhalfRightUpperClientSetting.add(clientAddress);
		upperhalfRightUpperClientSetting.add(clientNameLabel);
		upperhalfRightUpperClientSetting.add(clientName);
		upperhalfRightUpperClientSetting.add(createClient);
		upperhalfRightUpperClientSetting.add(logoutClient);
		
		upperhalfRightLowerServerSetting.add(serverPortLabel);
		upperhalfRightLowerServerSetting.add(serverPort);
		upperhalfRightLowerServerSetting.add(createServer);
		upperhalfRightLowerServerSetting.add(stopServer);
		upperhalfRightLowerServerSetting.add(sendMessage);
	}
	
	private void setLowerPanelComponents()
	{
		lowerhalf.add(scrollConsole, BorderLayout.SOUTH);
		lowerhalf.add(scrollMessage, BorderLayout.NORTH);		
	}
	
	private void setComponentLayouts()
	{
		mainScreen = new JPanel(new BorderLayout());
		
		upperhalf = new JPanel(new BorderLayout());
		lowerhalf = new JPanel(new BorderLayout());
		
		upperhalfLeft = new JPanel(new BorderLayout());
		upperhalfRight = new JPanel(new GridLayout(2, 1, 0, 4));
		
		upperhalfRightUpperClientSetting = new JPanel(new GridLayout(4, 2, 0, 4));
		upperhalfRightLowerServerSetting = new JPanel(new GridLayout(3, 2, 0, 4));
	}
	
	private void setTextAreaAttributes()
	{
		mesgBox.setBackground(Color.LIGHT_GRAY);
		chatBox.setBackground(Color.LIGHT_GRAY);
		console.setBackground(Color.LIGHT_GRAY);
		
		mesgBox.setBorder(BorderFactory.createMatteBorder(4,4,2,4, Color.darkGray));
		chatBox.setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.darkGray));
		console.setBorder(BorderFactory.createMatteBorder(2,4,4,4, Color.darkGray));	
		
		chatBox.setEditable(false);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		
		sendMessage.setEnabled(false);
		stopServer.setEnabled(false);
		
		upperhalfRightUpperClientSetting.setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.darkGray));
		upperhalfRightLowerServerSetting.setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.darkGray));
		
		scrollConsole = new JScrollPane(console);
		scrollConsole.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scrollMessage = new JScrollPane(mesgBox);
		scrollMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scrollChat = new JScrollPane(chatBox);
		scrollChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//move scroll bar to bottom
		DefaultCaret caretMb = (DefaultCaret)mesgBox.getCaret();
		caretMb.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		DefaultCaret caretCh = (DefaultCaret)chatBox.getCaret();
		caretCh.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		DefaultCaret caretCo = (DefaultCaret)console.getCaret();
		caretCo.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		
	}
	
	public static void main(String[] args)
	{
		Interface gui = new Interface();
		gui.setTitle("ClientServer");
		gui.setSize(1000, 600);
		gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gui.setVisible(true);
	}	
}
