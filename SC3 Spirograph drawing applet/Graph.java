import java.awt.*;
public class Graph {
	protected Color colour;
	
	protected double R = 300;
	protected double r = -110;
	protected double O = 3;
	protected double Ro=0;
	protected double ro=0;
	protected double Oo=0;
	protected int modeo=0;
	protected int num1,num2 = 0;
	protected int x;
	protected int y;
	public void setVars(double a,double b, double c){
		R=a;
		r=b;
		O=c;
	}
	public Color getColour(int t,int mode){
		calcCol(t,mode);
		switch(num2){
		case 0: return colour = new Color(0x9E,0xDB,0xF9);
		case 1: return colour = new Color(0xB6,0x89,0xC8);			
		case 2: return colour = new Color(0xF3,0xB6,0xCF);			
		case 3: return colour = new Color(0xFD,0xF6,0xAF);			
		case 4: return colour = new Color(101,108,185);			
		case 5: return colour = new Color(0xC2,0xC7,0xD6);			
		default: return Color.white;
		}
	}
	public void calcCol(int t, int mode){
		if(Ro != R || ro != r || Oo != O || modeo != mode){
			num2+=1;
			Ro = R;
			ro = r;
			Oo = O;
			modeo = mode;
		}
		if(num2 == 6){
			num2 = 0;
		}
	}
	public void calculateCoord(double t, int mode){
		switch(mode){
		case 0:
			x = (int) ((R+r)*Math.cos(t) - (r+O)*Math.cos(((R+r)/r)*t));
			y = (int) ((R+r)*Math.sin(t) - (r+O)*Math.sin(((R+r)/r)*t));
			break;
		case 1:
			x = (int) ((R+r)*Math.cos(t) - (r+O)*Math.sin(((R+r)/r)*t));
			y = (int) ((R+r)*Math.sin(t) - (r+O)*Math.cos(((R+r)/r)*t));
			break;
		case 2:
			x = (int) ((R+r)*Math.cos(t) - (r+O)*Math.cos(((R+r)/r)*t));
			y = (int) ((R+r)*Math.sin(t) - (r+O)*Math.cos(((R+r)/r)*t));
			break;
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
