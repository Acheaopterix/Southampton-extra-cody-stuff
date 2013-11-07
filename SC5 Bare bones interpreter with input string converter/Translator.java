package interpreter;

import java.util.*;


public class Translator {
	protected String rawCode;
	protected String formattedCode = "failed";
	protected boolean noerror = true;
	public Translator(String _code)
	{
		rawCode = _code;
	}
	
	public void coverter()
	{
		//split the code by ;
		String[] line = rawCode.split(";");
    	for(String s : line)
    	{
    		if(s.equals("DROP theBass"))
    		{
    			formattedCode += "DROP theBass;";
    		}
    		noerror = true;
    		//check if the code snippet actually contains any commands
   			if(s.contains("clear") || s.contains("incr") || s.contains("decr") || s.contains("while") || s.contains("end"))
			{
   				//remove any extra spaces from lines
     			s = s.replaceAll("  +", " ");
     			ArrayList<String> lineSegments = new ArrayList<String>();
     			
     			for(String _s : s.split(" "))
     			{
     				lineSegments.add(_s);
     			}
     			//put the strings in an arraylist for the sole purpose of using the remove function, no seriously thats literally it 
     			lineSegments.remove("");
     			
     			int temp1 = -1, temp2 = -1;
     			
     			for(int i = 0; i < lineSegments.size(); i++)
     			{	
     				//if the line section is a command store its position
     				if(lineSegments.get(i).equals("clear") || lineSegments.get(i).equals("incr") || lineSegments.get(i).equals("decr"))
     				{
     					temp1 = i;
     				}
     				//if the line section is a while see if its followed by a variable
     				else if(lineSegments.get(i).equals("while"))
     				{
     					if(lineSegments.get(i + 1).matches("([a-z]|[A-Z]|[0-9])+") && lineSegments.get(i + 1).matches("([^clear]|[^incr]|[^decr]|[^while]|[^end]|[^not])+"))
         				{
     						formattedCode = formattedCode + "while " +  lineSegments.get(i + 1) + " not 0 do" + ";";
         				}
     				}
     				//see if the line section is an end
     				else if(lineSegments.get(i).equals("end"))
     				{
     					formattedCode = formattedCode + "end" + ";";
     				}
     				
     				//English simplification presented in the most confusing possible way
     				//if you've found a string of characters and you've found a command and the string of characters you've found it not not or a command and it is the next set of characters along from the command you've just found
     				//consequently this is also the definition of a variable for my interpreter
     				//get the variable after a command
     				if(lineSegments.get(i).matches("([a-z]|[A-Z]|[0-9])+") && lineSegments.get(i).matches("([^clear]|[^incr]|[^decr]|[^while]|[^end]|[^not])+") && temp1 != -1  && i == (temp1 + 1))
     				{
     					temp2 = i;
     				}
     				
     				if( temp1 != -1 && temp2 != -1)
     				{
     					//add a found command/variable combination
     					formattedCode = formattedCode + lineSegments.get(temp1) + " " + lineSegments.get(temp2) + ";";
     					temp1 = -1;
     					temp2 = -1;
     				}
     			}
     			
 				
 			}
			else
			{
				noerror = false; //no command detected
			}
		}
	}
	public String getCode()
	{
		//remove the 'failed' at the start of the formatted code
		formattedCode = formattedCode.replaceAll("failed", "");
		return formattedCode;
	}
}
