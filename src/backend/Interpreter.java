package backend;

import java.io.FileNotFoundException;
import java.util.TreeMap;
import middlelayer.*;

/**
 Runs the intermediate code given by the user and prints the output.

 @author Team Supah Powah, CS 152, Section 02 Erni Ali, Charles Flood, Su Sandi
 */
public class Interpreter
{
   private String inputFileName = null;
   private String outputFileName = null; //may or may not be used

   public Interpreter()
   {
   }

   //getters and setters
   public String getInputFileName()
   {
      return inputFileName;
   }

   public String getOutputFileName()
   {
      return outputFileName;
   }

   public void setInputFileName(String inputFileName)
   {
      this.inputFileName = inputFileName;
   }

   public void setOutputFileName(String outputFileName)
   {
      this.outputFileName = outputFileName;
   }

   /**
    Run the interpreter on the given input file.
    */
   public void run()
   {
      MidLayerControl mlc = new MidLayerControl();
      String status = "";
      try
      {
         status = mlc.getMidLayerObjectFileNames(inputFileName);

         System.out.println("\nSYMBOL TABLE:");
         for(int i = 0; i < mlc.getSymStack().size(); i++)
         {
            System.out.println("\nSYMTAB " + i + " :");
            SymbolTable symtab = (SymbolTable) mlc.getSymStack().get(i);
            symtab.printSymTab();
         }

         System.out.println("\nYOUR CODE TREE:");
         mlc.getCodeTree().printTree();

      }
      catch (FileNotFoundException ex)
      {
         System.out.println("Error: " + ex.getMessage());
      }
      System.out.println(status);//this is to test to be sure it is working...

      //From here, manipulate the code tree and symbol table
   }
}
