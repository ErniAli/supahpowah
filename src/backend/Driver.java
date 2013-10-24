package backend;

/**
 Runs the program. This class will ask the user for the scheme file to be run
 through the interpreter.

 @author charles
 */
import middlelayer.SymbolTable;
import frontend.Parser;

public class Driver
{
   public static void main(String argv[]) throws Exception
   {

//        Interpreter interpreter = new Interpreter();


//        interpreter.setInputFileName("input.lisp");
//        interpreter.run();

      //instatiate the parser.
      //This is needed so that parseFile in Parser is called the the scanner
      //stuff is printed out.
      Parser parse = new Parser();
      parse.parseFile("input.lisp");

      //call the symbol table and tells it to print.
      SymbolTable symTab = new SymbolTable();
      symTab.printSymTab();


   }
}
