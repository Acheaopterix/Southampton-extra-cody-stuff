package client;

import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JTextArea;

public class IncomingMessages extends Thread
{
	Client client;
	DataInputStream in;
	JTextArea chatBox;
	boolean notSocketClosed = true;
	
	public IncomingMessages(Client _client, DataInputStream _in, JTextArea _chatBox)
	{
		client = _client;
		in = _in;
		chatBox = _chatBox;
	}
	
	public void run()
	{
		while(notSocketClosed )
		{
			try
			{	
				String msg = in.readUTF();
				
				if(msg.contains("//drop"))
				{
					playSound();
				}
				
				chatBox.append(msg + "\n"); 
				//System.err.println("clientside " + in.readUTF());  
			}
			catch(IOException e)
			{
				chatBox.append("If you pressed logout, the following doesnt apply to you" + "\n" + "There was an error reading from the server, the server may have shutdown, please press logout" + "\n");
				notSocketClosed = false;
				return;
			}
		}
	}
	public void logout()
	{
		notSocketClosed = false;
	}
	
	public void playSound()
	{		        
        Play p = new Play(chatBox);
        p.start();
	}
}
