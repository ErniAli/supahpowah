/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import middlelayer.SymbolTable;

/**

 @author Erni
 */
public class Parser
{
   public SymbolTable symTab = new SymbolTable();
   static Scanner scanner;

   public Parser()
   {
   }

   public void parseFile(String inputFile) throws Exception
   {
      Scanner scanPrint = new Scanner(inputFile);
      scanPrint.scan(); //printout all the stuff in scanner first

      scanner = new Scanner(inputFile);
      Token token;
      //need to call this first for the scanner to work.
      scanner.nextCharNoPrint();
      while ((token = scanner.getNextToken()) != null)
      {
         //put to Symbol table
         parseToSymbolTable(token);
         //add the token to the tree and list - assuming it is not a comment
      }
      //move this printing to somewhere else
   }

   public void parseToSymbolTable(Token token) throws Exception
   {
      if(symTab.symTabContains(token.getValue()))
      {
         //do nothing, already inside the symtab
      }
      else if(token.getType() == Token.TokenType.WORD)
      {
         if(scanner.reservedWords.contains(token.getValue()))
         {
            //do nothing, reserved word
         }
         else if(!"()".contains(token.getValue()))
         {
            symTab.setSymTab(token.getValue(), "IDENTIFIER");
         }
      }
      else if(token.getType() == Token.TokenType.SYMBOL)
      {
         symTab.setSymTab(token.getValue(), "PROCEDURE");
      }
   }
}
