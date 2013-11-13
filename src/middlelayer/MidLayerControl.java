package middlelayer;

import java.io.FileNotFoundException;
//import frontend.Parser;
import frontend.Parser;
import java.util.ArrayList;
import java.util.Stack;

/**
 This class is designed solely to handle files that contain the objects in the
 middle layer. It will be able to cache files to speed up the interpreter if the
 source code has not changed since the last time the interpreter ran.

 @author Team Supah Powah, CS 152, Section 02 Erni Ali, Charles Flood, Su Sandi
 */
public class MidLayerControl
{
   //Parser parser = new Parser();
   private Parser parser = new Parser();
   private CodeTree codeTree = new CodeTree();
   private SymbolTable symbolTable;
   private String inputFileName;

   public String getMidLayerObjectFileNames(String inputFileName)
            throws FileNotFoundException
   {
      this.inputFileName = inputFileName;
      String returnString = "Finished";
      //This string will always be returned if no errors are encountered.
      //It will be parsed by the interpreter.
      /*String returnString = inputFileName + ".codetree " + inputFileName + ".symbols";

       File sourceFile = null;
       File codeTableFile = new File(inputFileName + ".codetree");
       File symbolTableFile = new File(inputFileName + ".symbols");
       */

      /*    if (inputFileName == null || !(sourceFile = new File(inputFileName)).exists())
       {
       throw new FileNotFoundException("Specified source file not found.");
       }

       //check to see if we need to create new code tree and table
       //for the purposes of this assignment, assume that if the inputFile
       //is newer than intermediate files.
       //conditions for actually running the parser...
       if (!codeTableFile.exists() || !symbolTableFile.exists()
       || codeTableFile.lastModified() < sourceFile.lastModified()
       || symbolTableFile.lastModified() < sourceFile.lastModified())
       {*/
      try
      {
         //Here's where we do all the magic
         parser.parseFile(this);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }


      //}

      return returnString;
   }

   public CodeTree getCodeTree()
   {
      return codeTree;
   }
//
//   public SymbolTable getSymbolTable()
//   {
//      return symbolTable;
//   }

   public SymbolTable getSymbolTable(int nestingLevel, ArrayList<SymbolTable> symTabStack)
   {
      if(nestingLevel < symTabStack.size() - 1)
      {
         symbolTable = symTabStack.get(nestingLevel);
      }
      else
      {
         symbolTable = new SymbolTable(nestingLevel);
      }
      return symbolTable;
   }

   public void setCodeTree(CodeTree codeTree)
   {
      this.codeTree = codeTree;
   }

   public void setSymbolTable(SymbolTable symbolTable)
   {
      this.symbolTable = symbolTable;
   }

   public String getInputFileName()
   {
      return inputFileName;
   }

   public ArrayList getSymStack()
   {
      return parser.getSymTabStack();
   }
}
