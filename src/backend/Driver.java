package backend;

/**
 Runs the program. This class will ask the user for the scheme file to be run
 through the interpreter.

 @author Team Supah Powah, CS 152, Section 02 Erni Ali, Charles Flood, Su Sandi
 */
public class Driver
{
   public static void main(String argv[]) throws Exception
   {
      Interpreter interpreter = new Interpreter();

      interpreter.setInputFileName("input.lisp");
      interpreter.run();
   }
}
