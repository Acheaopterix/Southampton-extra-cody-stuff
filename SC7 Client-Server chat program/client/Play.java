package client;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JTextArea;

public class Play extends Thread
{
	JTextArea chatBox;
	public Play(JTextArea _chatBox) {
		chatBox = _chatBox;
	}

	public void run()
	{		        
        try
        {
        	InputStream aud = getClass().getResourceAsStream("/WoodenToasterBeyondHerGarden.wav");
        	InputStream buf = new BufferedInputStream(aud);
        	
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(buf));
            clip.start();
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);
            clip.close();
        }
        catch (Exception e)
        {
        	chatBox.append(e.toString());
        }
	}
}
