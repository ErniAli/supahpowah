package frontend;

import middlelayer.*;

/**
 This is the parser method that asks the scanner for the next token and also
 assembles the symbol tree and code tree

 @author Team Supah Powah, CS 152, Section 02 Erni Ali, Charles Flood, Su Sandi
 */
public class Parser
{
   private SymbolTable symbolTable;
   private CodeTree codeTree;
   static Scanner scanner;

   /**
    This method prints stuff in scanner and also put the tokens into the symbol
    table.

    @param inputFile the string name of the input file
    @throws Exception
    */
   public void parseFile(MidLayerControl mlc) throws Exception
   {

      String inputFile = mlc.getInputFileName();
      this.codeTree = mlc.getCodeTree();
      this.symbolTable = mlc.getSymbolTable();

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
      }

      scanner = new Scanner(inputFile);
      //need to call this first for the scanner to work.
      scanner.nextCharNoPrint();
      while ((token = scanner.getNextToken()) != null)
      {
         //add the token to the tree and list - assuming it is not a comment
         //Note that even comments should be handled by the tree itself
         this.codeTree.addToken(token);
      }

      //don't remember if this is needed...
      mlc.setCodeTree(this.codeTree);
      mlc.setSymbolTable(this.symbolTable);
   }

   /**
    This method parse Tokens into the symbol table.

    @param token the Token that is about to be processed and put into symtab.
    @throws Exception
    */
   public void parseToSymbolTable(Token token) throws Exception
   {
      if (this.symbolTable.symTabContains(token.getValue()))
      {
         //do nothing, already inside the symtab
      }
      //define, then next word must be a procedure.
      else if(token.getValue().equals("define"))
      {
         token = scanner.getNextToken();
         this.symbolTable.setSymTab(token.getValue(), "PROCEDURE");
      }
      else if (token.getType() == Token.TokenType.WORD)
      {
         if (scanner.reservedWords.contains(token.getValue()))
         {
            //do nothing, reserved word
         }
         else if (!"()".contains(token.getValue()))
         {
            this.symbolTable.setSymTab(token.getValue(), "IDENTIFIER");
         }
      }
      else if (token.getType() == Token.TokenType.SYMBOL)
      {
         this.symbolTable.setSymTab(token.getValue(), "PROCEDURE");
      }
   }
}
