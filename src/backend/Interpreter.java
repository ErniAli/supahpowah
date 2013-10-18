package backend;

import java.io.*;
import middlelayer.*;

/**
 * Runs the intermediate code given by the user and prints the output.
 * @author charles
 */
public class Interpreter {
    
    private String inputFileName = null;
    private String outputFileName = null; //may or may not be used

    public Interpreter() {
    }

    //getters and setters
    public String getInputFileName() {
        return inputFileName;
    }
    public String getOutputFileName() {
        return outputFileName;
    }
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
    
    /**
     * Run the interpreter on the given input file.
     */
    public void run(){
        
        if(this.inputFileName==null){
            System.out.println("No input file given");
        }
        
        File f = new File(inputFileName);
        if(!f.exists()){
            System.out.println("No input file given");
        }
        
        //The timestamp can be used to check when the file was last modified
        //If it has not been modified since the last time the interpreter was
        //run, just use the same files that that were used last time
        long timestamp = f.lastModified();
        
        File ctFile = MidLayerControl.getCodeTreeFile(inputFileName, timestamp);
        File stFile = MidLayerControl.getSymbolTableFile(inputFileName, timestamp);
        
        //From here, manipulate the code tree and symbol table
    }
}
