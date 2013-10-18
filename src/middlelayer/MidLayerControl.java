package middlelayer;

import java.io.File;
import java.io.FileNotFoundException;
import frontend.Parser;

/**
 * This class is designed solely to handle files that contain the objects in the
 * middle layer. It will be able to cache files to speed up the interpreter if
 * the source code has not changed since the last time the interpreter ran.
 * 
 * @author charles
 */
public class MidLayerControl {
    
    
    public static String getMidLayerObjectFileNames(String inputFileName) throws FileNotFoundException{
        
        //This string will always be returned if no errors are encountered.
        //It will be parsed by the interpreter.
        String returnString = inputFileName + ".codetree " + inputFileName + ".symbols";
        
        File sourceFile = null;
        File codeTableFile = new File(inputFileName + ".codetree");
        File symbolTableFile = new File(inputFileName + ".symbols");
        
        
        if(inputFileName==null || !(sourceFile = new File(inputFileName)).exists()){
            throw new FileNotFoundException("Specified source file not found.");
        }
        
        //check to see if we need to create new code tree and table
        //for the purposes of this assignment, assume that if the inputFile 
        //is newer than intermediate files.
        //conditions for actually running the parser...
        if(!codeTableFile.exists() || !symbolTableFile.exists() ||
                codeTableFile.lastModified()<sourceFile.lastModified() ||
                symbolTableFile.lastModified()<sourceFile.lastModified()){
            
            //Here's where we do all the magic
            Parser.parseFile(inputFileName);
            
        }
        
        return returnString;
    }
    
    
}
