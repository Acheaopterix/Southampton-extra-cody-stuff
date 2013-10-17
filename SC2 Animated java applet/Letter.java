import java.awt.*;
public class Letter {
	
	protected double xVel=1, yVel=1  ; 
	protected int x, y, ox, oy;
	protected String l;
	protected double w,h;
	protected double velocityModifier = 3;
	protected Color colour;
	
	
	public Letter(String letter, int width, int height){
		l = letter;
		w = width;
		h = height;
		setColour();
	}
	public void setColour(){
		switch((int)(Math.random()*6)){
		case 0: colour = new Color(0x9E,0xDB,0xF9);
			break;
		case 1: colour = new Color(0xB6,0x89,0xC8);
			break;
		case 2: colour = new Color(0xF3,0xB6,0xCF);
			break;
		case 3: colour = new Color(0xFD,0xF6,0xAF);
			break;
		case 4: colour = new Color(101,108,185);
			break;
		case 5: colour = new Color(0xC2,0xC7,0xD6);
			break;
		case 6: colour = new Color(0xC2,0xC7,0xD6);
			break;
		}
	}
	public Color getColour(){
		return colour;
	}
	public void setLetter(String letter){
		l = letter;
	}
	public String getLetter(){
		return l;
	}
	public void setPos(int xPos, int yPos){
		x = xPos;
		y = yPos;
		
		//origin values
		ox = xPos;
		oy = yPos;
	}
	public int getXPos(){
		return x;
	}
	public int getYPos(){
		return y;
	}
	public void moveText(){
		int a=x;
		int b=y;
		//check that the letter wont exit the screen
		if((a+=xVel)<(w-37) && (a+=xVel)> 26){
			x+=xVel;
		}else{xVel*=-1;
			setColour();
		}
		if((b+=yVel)<(h-30) && (b+=yVel)> 37){
			y+=yVel;
		}else{yVel*=-1;
		setColour();
		}
		//System.out.println(""+x+" "+y+"");
	}

	//calculates velocity when moving mouse towards or away from mouse
	public void Direction(double mouseXPos, double mouseYPos, boolean click){		
		double xDiff,yDiff;
		double angle;
		
		xDiff =(x - mouseXPos);
		yDiff =	(y - mouseYPos);
				
		angle = Math.atan((yDiff/xDiff));
		
		xDiff = xDiff/Math.abs(xDiff);
		yDiff = yDiff/Math.abs(yDiff);
		
		//determines if click and drag and so towards cursor
		if(click == true){
			xDiff*=-1;
			yDiff*=-1;
		}
					
		xVel = (xDiff*(Math.abs(Math.cos(angle)*velocityModifier)));
		yVel = (yDiff*(Math.abs(Math.sin(angle)*velocityModifier)));
	}
	
	//calculates velocity when leaving screen to random direction
	public void Direction(){
		double rand0,rand1,rand2,rand3;
		
		rand0 = Math.random();
		rand1 = Math.random();
		rand2 = Math.random()*velocityModifier;
		rand3 = Math.random()*velocityModifier;
		
		if(rand0 < 0.5){
			xVel = rand2;
		}
		if(rand1 < 0.5){
			yVel = rand3;
		}	
	}
	
	//calculates velocity when clicking towards origin point
	public void Direction(boolean click){
		if(((x<ox+4)&&(x>ox-4))&&((y<oy+4)&&(y>oy-4))){
			xVel = 0;
			yVel = 0;
		}else{
			double xDiff,yDiff;
			double angle;
				
			xDiff =(x - ox);
			yDiff =	(y - oy);
			
			
			angle = Math.atan((yDiff/xDiff));
			
			xDiff = xDiff/Math.abs(xDiff);
			yDiff = yDiff/Math.abs(yDiff);
		    
			if(click == true){
				xDiff*=-1;
				yDiff*=-1;
			}
					
			xVel = xDiff*(Math.abs(Math.cos(angle)*velocityModifier));
			yVel = yDiff*(Math.abs(Math.sin(angle)*velocityModifier));
		}
	}
}
