package client;

import java.io.*;

import java.net.Socket;

import java.util.ArrayList;

import javax.swing.JTextArea;

public class Client
{
	private Socket clientSocket;
	private String username;
	
	ArrayList<String> messagesToSend;
	
	JTextArea chatBox;
	
	DataOutputStream out; 
	DataInputStream in;
	IncomingMessages IM; 
	OutgoingMessages OM;
	
	public Client(String _serverName, int _port, String _username, JTextArea _chatBox, ArrayList<String> _messagesToSend) throws IOException
	{
		clientSocket = new Socket(_serverName, _port);
		username = _username;
		out = new DataOutputStream(clientSocket.getOutputStream());
		in = new DataInputStream(clientSocket.getInputStream());
		chatBox = _chatBox;
		messagesToSend = _messagesToSend;
	}
	
	protected void cleanUp()
	{
		try
		{
			in.close();
			out.close();
			clientSocket.close();
			chatBox.append("Disconnected from server" + "\n");
		}
		catch (IOException e)
		{			
		}
	}
	public Socket getSocket()
	{
		return clientSocket;
	}
	
	public String getUsername()
	{
		return username;
	}

	public void runClient()
	{
		chatBox.append("Connected to: " + clientSocket.getRemoteSocketAddress() + "\n");
		IM = new IncomingMessages(this, in, chatBox);
		OM = new OutgoingMessages(this, out, messagesToSend);
		IM.start();
		OM.start();
	}
	public void logout()
	{
		OM.logout();
		IM.logout();
		cleanUp();
	}
}
