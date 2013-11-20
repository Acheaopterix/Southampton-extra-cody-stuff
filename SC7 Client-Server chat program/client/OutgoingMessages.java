package client;

import java.io.DataOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

public class OutgoingMessages extends Thread
{
	DataOutputStream out; 
	Client client;
	boolean notLogout = true;
	ArrayList<String> messagesToSend;
	ArrayList<String> messagesSent = new ArrayList<String>();
	
	public OutgoingMessages(Client _client, DataOutputStream _out, ArrayList<String> _messagesToSend)
	{
		client = _client;
		out = _out;
		messagesToSend = _messagesToSend;
	}
	
	public void run()
	{		
		while(notLogout )
		{
			try
			{	 
				if(messagesToSend.isEmpty() == false)
				{
					for(String s : messagesToSend)
					{
						final Date date = new Date() ;
						final SimpleDateFormat timeStamp = new SimpleDateFormat("hh:mm:ss a");
						out.writeUTF(" <" + timeStamp.format(date) + "> " + client.getUsername() + ": " + s); 
						messagesSent.add(s);
					}
				}			
				
				if(messagesSent.isEmpty() == false)
				{
					messagesToSend.removeAll(messagesSent);
					messagesSent.clear();
				}
			}
			catch(IOException e)
			{
				System.err.println("client error out");
			}
		}
	}
	
	public void logout()
	{
		try
		{
			out.writeUTF("//logout");
		} 
		catch (IOException e) 
		{
		} 
		notLogout = false;
	}
}
