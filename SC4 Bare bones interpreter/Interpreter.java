package interpreter;
import java.util.*;

public class Interpreter {
	protected String code;
	protected String[] exe;
	protected int endLine;
	protected ArrayList<Variable> var;
	
	//if the interpreter is called from main this is used
	public Interpreter(String _code)
	{
		code = _code;
		var = new ArrayList<Variable>();
	}
	
	//if the interpreter is called recursively this is used
	public Interpreter(String _code, ArrayList<Variable> _var)
	{
		code = _code;
		var = _var;
	}
	
	//main execution loop
	public void runExe()
	{
		//Separate the code by semi-colon
		exe = code.split(";");
		
		//iterate over all lines in the code
		for(int c = 0; c < exe.length; c++)
		{
			//set current line
			String s = exe[c];
			
			//match the command on the current line and execute it
			if(s.matches("(clear )([a-z]|[A-Z])"))
			{
				if(getIndex(s) == -1)
				{
					Variable someVar = new Variable(getVar(s));
					var.add(someVar);
				}
				else 
				{
					var.get(getIndex(s)).nulVal();
				}
				printVar();
			}
			
			if(s.matches("(incr )([a-z]|[A-Z])"))
			{
				var.get(getIndex(s)).incVal();
				printVar();
			}
			
			if(s.matches("(decr )([a-z]|[A-Z])"))
			{
				var.get(getIndex(s)).decVal();
				printVar();
			}
					
			//this is where stuff gets complicated
			if(s.matches("(while )([a-z]|[A-Z])( not 0 do)"))
			{
				//this will execute from the start of the while loop
				wLoop(c);
				
				//this makes sure we start from where the 'while' ends 
				c = endLine;
				printVar();
			}
		}
	}
	
	public void wLoop(int c)
	{
		//now i iterate over the remaining code to find the matching end for this while, by counting any remaining whiles and ends
		int count1 = 0;
		for(int c1 = c+1; c1 < exe.length; c1++)
		{
			String s1 = exe[c1];
			if(s1.matches("(while )([a-z]|[A-Z])( not 0 do)"))
			{
				count1 += 1;
			}
			else if(s1.matches("end"))
			{
				count1 -= 1;
			}
			//when we find the matching end, we have a complete section of code to run in the while
			if(count1 == -1)
			{
				//we make a string containing the new code block
				endLine = c1;
				String newCode = "";
				for(int nc = c+1; nc < c1; nc++)
				{
				 newCode += exe[nc]+";";
				}
				//make a new interpreter for this code block, and give it the variables we already have
				Interpreter nextInterpreter = new Interpreter(newCode, var);
				//this new interpreter is going to execute all the code in its code block until the specified variable is 0
				//if it encounters another while loop, this will happen again, until there is only a normal code block with no whiles inside it
				while(var.get(getIndex(exe[c])).getValue() != 0)
				{
					nextInterpreter.runExe();
				}
				break;
			}
		}
	}
	
	//this gets the index in the array list of variables where the variable your using is
	public int getIndex(String _s)
	{
		int check = -1;
		for(int i = 0; i < var.size(); i++)
		{
			if(var.get(i).getName().equals(getVar(_s)))
			{
				check = i;
			}				
		}
		return check;
	}
	
	//gets the variable your using
	public String getVar(String _s)
	{
		String[] temp = _s.split(" ");
		return temp[1];
	}
	
	//prints all the variables and values
	public void printVar()
	{
		System.out.println("Variables are: ");
		for(Variable v : var)
		{
			System.out.println(v.getName()+"="+v.getValue()+" ");
		}
	}
}
