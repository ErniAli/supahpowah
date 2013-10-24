package backend;

/**
 * Runs the program.
 * This class will ask the user for the scheme file to be run through the
 * interpreter.
 * @author charles
 */

import middlelayer.SymbolTable;
import frontend.Parser;
public class Driver {

    public static void main(String argv[]) throws Exception{

//        Interpreter interpreter = new Interpreter();


//        interpreter.setInputFileName("input.lisp");
//        interpreter.run();

        //instatiate the parser.
        Parser parse  = new Parser();
        parse.parseFile("input.lisp");

        SymbolTable symTab = new SymbolTable();
        symTab.printSymTab();


    }

}
