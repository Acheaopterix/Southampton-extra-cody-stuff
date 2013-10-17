
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Draw extends Applet implements MouseMotionListener, MouseListener, KeyListener, Runnable  {

		protected int width, height, mouseX, mouseY;
		protected String word = "A really long sentence so that we get lots of letters to mess with"; 
		protected Image backBuffer;
		protected Graphics img;
		
		Thread loop = null;
		
		protected Letter[] text = new Letter[word.length()];
		
	   public void init() {
	      width = getSize().width;
	      height = getSize().height;
	      setBackground(Color.darkGray);
	      
	      addMouseMotionListener(this);
	      addMouseListener(this);
	      addKeyListener( this );
	      
	      convertStringArray();
	      setCharStartPos();
	      
	      backBuffer = createImage(width, height);
	      img = backBuffer.getGraphics();
	      img.setColor(Color.white);
	      
	      
	   }
	   public void start(){
		   loop = new Thread(this);
		   try {
			   loop.sleep(1000);
		   } catch (InterruptedException e) {
			   e.printStackTrace();
		   }
		   loop.start();
	   }
	   public void run(){
		   while(0!=1){
			   
			   //draw stuff
			   img.setFont(new Font("Comic Sans MS", 14, 20));
			   img.setColor(Color.darkGray);
			   img.fillRect(0, 0, width, height);
			   img.setColor(Color.white);
			   img.drawOval(mouseX-15, mouseY-15, 29, 29);
			   img.drawString(""+mouseX+" "+mouseY+"", 150, 24);
			   img.drawString(""+width+" "+height+"", 25, 24);
			   img.drawRect(25, 25, width-50, height-50);
			   
			   //draw more stuff
			   for(int i = 0 ;i < word.length(); i++){
				   img.setColor(text[i].getColour());
				   img.drawString(text[i].getLetter(), text[i].getXPos(), text[i].getYPos());
			   }			 
			   
			   //dont draw stuff, move it instead
			   for(int i = 0 ;i < word.length(); i++){
				   text[i].moveText();
			   }
			   repaint();
			   
			   //random try catch 
			try {
				loop.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		   }
	   }
	   
	   public void paint(Graphics g){
		   update(g);
	   }
	   public void update(Graphics g){
		   //stop flickering woo hoo! 
		    g.drawImage(backBuffer, 0, 0, this);
	   }
	   
	   //what happens when moving the cursor
	   public void mouseMoved(MouseEvent e){
		   mouseX = e.getX();
		   mouseY = e.getY();
		   
		   for(int i = 0 ;i < word.length(); i++){
			   text[i].Direction(mouseX, mouseY,false);;
		   }
		   repaint();
	   }
	   //what happens when click + dragging
	   public void mouseDragged(MouseEvent e) {
		   mouseX = e.getX();
		   mouseY = e.getY();
		   
		   for(int i = 0 ;i < word.length(); i++){
			   text[i].Direction(mouseX, mouseY, true);
		   }
		   repaint();
	   }
	   //what happens when cursor leaves applet
	   public void mouseExited(MouseEvent e) {
		   for(int i = 0 ;i < word.length(); i++){
			   text[i].Direction();
		   }
		   repaint();
	   }
	   //what happens when clicking
	   public void mousePressed(MouseEvent arg0) {
			   for(int i = 0 ;i < word.length(); i++){			
			   			text[i].Direction(true);			   		
			   }			   		   
	   }
	   
	   //make each character a letter object
	   public void convertStringArray(){
		   for(int i = 0 ;i < word.length(); i++){
			   text[i]=new Letter(word.substring(i,i+1),width,height);
		   }
	   }
	   //set start position of each letter object
	   public void setCharStartPos(){
		   for(int i = 0 ;i < word.length() ; i++){
			   text[i].setPos((i*12)+40, height/2);
		   }
	   }
	     
	  /* public void keyTyped(KeyEvent k){
		   char c = k.getKeyChar();
		   int enter = k.getKeyCode();
		      if(c != KeyEvent.CHAR_UNDEFINED){
		    	  if(enter == KeyEvent.VK_ENTER){
		    		  
		    	  }
		    	  word += c;
		      }
	   }*/
 	      
	   
	   
	   
	   
	   
	   
	   
	   
    @Override
	public void mouseClicked(MouseEvent arg0) {
    }
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent e) {	
	}
}
