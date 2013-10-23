package frontend;

import java.io.File;

/**
 *
 * @author charles
 */
public class AltParser {
    
    public AltParser() {
    }

    
    public void parseFile(File inputFile){
        
        AltScanner scanner = new AltScanner(inputFile);
        
        Token token;
        while( (token=scanner.getNextToken())!=null ){
            //add the token to the tree and list - assuming it is not a comment
            
            
            
            
            
        }   
    }
}
