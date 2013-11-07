package interpreter;

public class Variable {
	protected String name;
	protected int value;
	//construct variable with a name and set value to be 0
	public Variable(String _s)
	{
		name = _s;
		nulVal();
	}
	//the clears, increments, and decrements the variable value respectively 
	public void nulVal()
	{
		value = 0;
	}
	
	public void incVal()
	{
		value += 1;
	}
	
	public void decVal()
	{
		value -= 1;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getValue()
	{
		return value;
	}
}
