
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.JOptionPane;
//174,18,24
//130,104,109
//6,134,9*
//147,77,10
//123,82,100
//120,113,59
//34,68,49
//14,7,102
//114,57,62
//80,10,0*
//18,27,86
//39,117,2*
//10,125,0
//24,80,0
//84,-113,139
//31,-105,93
//0,25,4
//65,-30,30
public class Drawspyro extends Applet implements ActionListener{

		protected int width, height;
		protected int option;
		protected Image backBuffer;
		protected Graphics img;
		
		protected TextField largeRadius, smallRadius, offset;
		protected Button draw, clear, rand;
		protected Checkbox norm, rev, dafuqIsHappeningHere;	
		protected CheckboxGroup options;
		
		protected double t = 0;
		protected Graph spyro = new Graph();
		
	   public void init(){
	      width = getSize().width;
	      height = getSize().height;
	      setBackground(Color.darkGray);
	              
	      backBuffer = createImage(width, height);
	      img = backBuffer.getGraphics();
	      img.setColor(Color.lightGray);
	      img.fillRect(0, 0, width, height/20);
	      img.setColor(Color.darkGray);
	      img.setFont(new Font("Comic Sans MS", 14, 26));
	      img.drawString(""+width+" "+height+"", 25, 3*height/80);
	      gui();
	   }
	   public void drawSpyrograph(){
		   if(norm.getState()){
			   option = 0;
		   }else if(rev.getState()){
			   option = 1;
		   }else{option = 2;}
		   
		   spyro.setVars(getBigR(), getSmlR(), getOffS()); 
		   while(t<100000){
			   t+=1;
			   spyro.calculateCoord(t,option);
			   img.setColor(spyro.getColour((int) t,option));
			   img.drawOval((width/2)+spyro.getX(), (height/2)+spyro.getY(), 1, 1);
			   repaint();		   
		   }
		   t=0;
	   }
	   public void randomise(){
		   Random randomNum = new Random();	   
		   largeRadius.setText(String.valueOf((randomNum.nextInt(151))));
		   smallRadius.setText(String.valueOf((randomNum.nextInt(301)-150)));
		   offset.setText(String.valueOf((randomNum.nextInt(151))));
		   drawSpyrograph();
	   }
	   public void paint(Graphics g){
		   update(g);
	   }
	   public void update(Graphics g){
		   //stop flickering woo hoo! 
		    g.drawImage(backBuffer, 0, 0, this);
	   }
	   public void clear(){
		   img.setColor(Color.lightGray);			   		   		  
		   img.clearRect(0,0,width,height);
		   img.fillRect(0, 0, width, height/20);
		   img.setColor(Color.darkGray);
		   img.setFont(new Font("Comic Sans MS", 14, 26));
		   img.drawString(""+width+" "+height+"", 25, 3*height/80);
		   repaint();
       }
	   public void gui(){
		   setLayout(null);
		   draw = new Button("Draw");
		   clear = new Button("Clear");
		   rand = new Button("Random");
		
		   largeRadius = new TextField("300");
		   smallRadius = new TextField("-105");
		   offset = new TextField("2");
		   
		   options = new CheckboxGroup();
		   
		   norm = new Checkbox("Normal",options,true);
		   rev = new Checkbox("Reversed",options,false);
		   dafuqIsHappeningHere = new Checkbox("I dont even",options,false);	   
		   
		   draw.setBounds(width-200, height/80, 40, 2*height/80);
		   clear.setBounds(width-150, height/80, 40, 2*height/80);
		   rand.setBounds(width-100, height/80, 50, 2*height/80);
		   
		   largeRadius.setBounds(width-width/2-20, height/80, 40, 2*height/80);
		   smallRadius.setBounds(width-width/2+30, height/80, 40, 2*height/80);
		   offset.setBounds(width-width/2+80, height/80, 40, 2*height/80);
		   
		   norm.setBounds(width-(3*width)/4-50, height/80, 60, 2*height/80);
		   rev.setBounds(width-(3*width)/4+12, height/80, 75, 2*height/80);
		   dafuqIsHappeningHere.setBounds(width-(3*width)/4+90, height/80, 80, 2*height/80);
		   norm.setBackground(Color.lightGray);
		   rev.setBackground(Color.lightGray);
		   dafuqIsHappeningHere.setBackground(Color.lightGray);
		   norm.setFont(new Font("Comic Sans MS",0,13));
		   rev.setFont(new Font("Comic Sans MS",0,13));
		   dafuqIsHappeningHere.setFont(new Font("Comic Sans MS",0,13));
		   
		   add(draw);
		   add(clear);
		   add(rand);
		   add(largeRadius);
		   add(smallRadius);
		   add(offset);
		   add(norm);
		   add(rev);
		   add(dafuqIsHappeningHere);
		   
		   draw.addActionListener(this);
		   clear.addActionListener(this);
		   rand.addActionListener(this);
	   }   	
	   public void actionPerformed(ActionEvent a){
			if(a.getSource()==draw)
				drawSpyrograph();
			if(a.getSource()==clear)
				clear();
			if(a.getSource()==rand)
				randomise();
	   }
	   public double getBigR(){
		   try{
			   return Double.parseDouble(largeRadius.getText());
		   }
		   catch (NumberFormatException e){
			   JOptionPane.showMessageDialog(null, "Numbers broke.");
			   return 100;
		   }
	   }
	   public double getSmlR(){
		   try{
			   return Double.parseDouble(smallRadius.getText());
		   }
		   catch (NumberFormatException e){
			   JOptionPane.showMessageDialog(null, "Numbers broke.");
			   return 100;
		   }
	   }
	   public double getOffS(){
		   try{
			   return Double.parseDouble(offset.getText());
		   }
		   catch (NumberFormatException e){
			   JOptionPane.showMessageDialog(null, "Numbers broke.");
			   return 100;
		   }
	   }
}
