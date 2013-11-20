package server;

import java.io.*;

import java.net.*;

import java.util.ArrayList;

import javax.swing.JTextArea;

public class Server extends Thread
{
	private ServerSocket hostSocket;
	boolean runServer = true;
	JTextArea serverText;
	JTextArea chatBox;
	private ArrayList<ConnectedClient> workingClients = new ArrayList<ConnectedClient>();
	
	public Server(int _port, JTextArea _serverText, JTextArea _chatBox) throws IOException
	{
			hostSocket = new ServerSocket(_port);		
			serverText = _serverText;
			chatBox = _chatBox;
	}

	public void run() 
	{
		serverText.append("This server is running on port: " + hostSocket.getLocalPort() + "\n");

		while(runServer)
		{
			try 
			{
				serverText.append("Waiting for clients..." + "\n");
				Socket connectedClient = hostSocket.accept();
				serverText.append("A client just connected from: " + connectedClient.getRemoteSocketAddress() + "\n");

				ConnectedClient newClient = new ConnectedClient(connectedClient, workingClients, serverText);				
				workingClients.add(newClient);				
				newClient.start();	
			} 
			catch (IOException e1)
			{
				serverText.append("error adding new client" + "\n");
			}
		}
	}
	
	public void stopServer()
	{
		runServer = false;
		for(ConnectedClient c : workingClients)
		{
			c.stopClientConnection();
		}
		workingClients.clear();
		try
		{
			hostSocket.close();
		} 
		catch (IOException e) 
		{
		}	
	}
}
