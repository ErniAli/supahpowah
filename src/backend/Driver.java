package backend;

/**
 Runs the program. This class will ask the user for the scheme file to be run
 through the interpreter.

 @author charles
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
