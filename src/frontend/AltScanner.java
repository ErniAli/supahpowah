/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import frontend.Token;
/**
 *
 * @author charles
 */
public class AltScanner {
    
    private File inputFile;
    private Scanner s;

    public AltScanner(File inputFile) {
        this.inputFile = inputFile;
        
        try {
            s = new Scanner(inputFile);
        } catch (FileNotFoundException ex) {
            //This should never occur because the file is checked before being sent here.
            System.out.println("Unable to initialize scanner.");
        }
        
    }
    
    
    
    public Token getNextToken(){
        Token t = new Token();
        Token.TokenType tokenType = null;
        
        char[] buffer;
        
        while(s.hasNext()){
            String line = s.nextLine();
            buffer = line.toCharArray();
            String currentTokenValue = "";
            for (char c : buffer){
                
                if(t.getType()==null){//the first character
                    if(isSpace(c)){continue;} //got to get to an actual character
                    if(isSemiColon(c)){t.setType(Token.TokenType.COMMENT);currentTokenValue+=c;}
                    else if(isLetter(c)){t.setType(Token.TokenType.WORD);currentTokenValue+=c;}
                    else if(isNumber(c)){t.setType(Token.TokenType.NUMBER);currentTokenValue+=c;}
                    else if(c=='('){t.setType(Token.TokenType.L_PAREN);t.setValue("("); return t;}
                    else if(c==')'){t.setType(Token.TokenType.R_PAREN);t.setValue(")"); return t;}
                    else{t.setType(Token.TokenType.SYMBOL);}
                }
                
                if(t.getType()==Token.TokenType.COMMENT){
                    //do nothing, because we don't care what comments there are.
                    currentTokenValue+=c;
                }
                
                if (t.getType()==Token.TokenType.NUMBER){
                    if(isNumber(c) || isPeriod(c)){
                        currentTokenValue+=c;
                    }else{
                        t.setValue(currentTokenValue);
                        return t;
                    }
                }
                
                if(t.getType()==Token.TokenType.WORD){
                    if(isLetter(c) || isNumber(c) || isStar(c)){
                        currentTokenValue+=c;
                    }else{
                        t.setValue(currentTokenValue);
                        return t;
                    }
                    
                }
                
            }
            //buffer is empty, return whatever the current token is
            
        }
        
        return null;//return null at the end so the parser knows to stop
    }
    
    private boolean isLetter(char c){
        int asciiValue = (int) c;
        if((asciiValue>=65 && asciiValue<=90) || (asciiValue>=97 && asciiValue<=122)){
            return true;
        }
        return false;
    }
    private boolean isNumber(char c){
        int asciiValue = (int) c;
        if(asciiValue>=48 && asciiValue<=57){return true;}
        return false;
    }
    private boolean isSpace(char c){
        int asciiValue = (int) c;
        if(asciiValue==32){return true;}
        return false;
    }
    private boolean isSemiColon(char c){
        int asciiValue = (int) c;
        if(asciiValue==59){return true;}
        return false;
    }
    private boolean isPeriod(char c){
        int asciiValue = (int) c;
        if(asciiValue==46){return true;}
        return false;
    }
    private boolean isStar(char c){
        int asciiValue = (int) c;
        if(asciiValue==42){return true;}
        return false;}
    
}
