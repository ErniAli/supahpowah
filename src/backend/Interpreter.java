package backend;

import java.io.FileNotFoundException;
import middlelayer.*;

/**
 Runs the intermediate code given by the user and prints the output.

 @author charles
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
   public void run() throws Exception
   {
      MidLayerControl mLC = new MidLayerControl();
      String files = "";
      try
      {
         files = mLC.getMidLayerObjectFileNames(inputFileName);
      }
      catch (FileNotFoundException ex)
      {
         System.out.println("Error: " + ex.getMessage());
      }
      System.out.println(files);//this is to test to be sure it is working...

      //From here, manipulate the code tree and symbol table
   }
}
