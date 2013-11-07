package interpreter;
//Variable may only be upper or lower case single alphanumerical characters
//'clear' command performs variable declaration, as well as setting a variable to zero, if you wish to create a variable this command must be used
//lines must be written in the following format 'Command variable whatever;' with spaces exactly as given there
public class Main {
	
	public static void main(String[] args) {
		//example 1
		//String mystr = "clear X;incr X;incr X;incr X;while X not 0 do;decr X;end;";
		//example 2, multiplies y  by x , to give z as the answer
		String mystr = ("clear X;incr X;incr X;incr X;incr X;clear Y;incr Y;incr Y;incr Y;clear Z;while X not 0 do;clear W;while Y not 0 do;incr Z;incr W;decr Y;end;while W not 0 do;incr Y;decr W;end;decr X;end;DROP;");
		//...
		//String mystr = ("");

		Translator tra = new Translator(mystr);
		tra.coverter();
		System.out.println(tra.getCode());
		
		Interpreter myInt = new Interpreter(tra.getCode());
		myInt.runExe();
	}
}
