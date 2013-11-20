package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextArea;

public class ConnectedClient extends Thread 
{
	private Socket connectedClient;
	boolean clientConnected = true;
	static boolean doge = false;
	
	JTextArea serverText;
	
	private DataInputStream in;
	private DataOutputStream out; 
	
	private ArrayList<ConnectedClient> allClients;
	private ArrayList<ConnectedClient> clientsToRemove = new ArrayList<ConnectedClient>();
	
	public ConnectedClient(Socket _cc, ArrayList<ConnectedClient> listOfClients, JTextArea _serverText) throws IOException
	{
		connectedClient = _cc;
		allClients = listOfClients;
		in = new DataInputStream(connectedClient.getInputStream());
		out = new DataOutputStream(connectedClient.getOutputStream());
		serverText = _serverText;
	}
	
	public void run() 
	{
		while(clientConnected )
		{
			try 
			{		  	        
				String msg = in.readUTF();		

				if(msg.equals("//logout"))
				{
					msg = "<Server> user diconnected from the server";
					for(ConnectedClient c : allClients)
					{
			        	try
			        	{
			        		c.writeMessage(msg);
			        	}
			        	catch (IOException ioe)
			        	{		        		
			        		clientsToRemove.add(c);	        		
			        	}
					}
					cleanUp();	
				}
				else if(msg.contains("//doge"))
				{
					doge = !doge;
				}
				else
				{
					for(ConnectedClient c : allClients)
					{
						try
						{
							c.writeMessage(msg);
							if(doge)
							{
								c.writeMessage("<Server> " + doge());
							}
						}
						catch (IOException ioe)
						{		        		
							clientsToRemove.add(c);	        		
						}
					}	 
				}
		        
		        if(clientsToRemove.isEmpty() == false)
		        {
		        	serverText.append("removing diconnected client(s)..." + "\n");
		        	allClients.removeAll(clientsToRemove);
		        	serverText.append("client(s) removed" + "\n");
		        	clientsToRemove.clear();
		        }
			} 
			catch (IOException e)
			{
				serverText.append("client error, closing connection to" + connectedClient.getLocalSocketAddress() + "\n");
		        try
		        {
		        	out.writeUTF("An error occurred, you have been diconnected, soz :/");
		        	cleanUp();
		        }
		        catch (IOException ioe)
		        {
		        	clientConnected = false;
		        }
			}
		}
	}
	
	public void writeMessage(String msg) throws IOException
	{	
		out.writeUTF(msg);		
	}
	
	private void cleanUp() throws IOException
	{
		allClients.remove(this);
    	in.close();
    	out.close();
    	connectedClient.close();
    	clientConnected = false;
	}
	
	public void stopClientConnection() 
	{
		try
		{
			in.close();
	    	out.close();
	    	connectedClient.close();
	    	clientConnected = false;
		}
		catch (IOException e)
		{			
		}
	}
	
	public String doge()
	{
		Random rand = new Random();
		switch(rand.nextInt(10))
		{
			case 0: return "Wow"; 
			case 1: return "So conversation"; 
			case 2: return "Much network"; 
			case 3: return "Very multiuser"; 
			case 4: return "So fast";
			case 5: return "Much error checking";
			case 6: return "Much reference";
			case 7: return "Wow";
			case 8: return "So server side";
			case 9: return "Very functional";
			default: return "Doge is so error, wow";
		}
	}
}
