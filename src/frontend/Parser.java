package frontend;

import java.util.ArrayList;
import java.util.Stack;
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
   static Stack parentStack = new Stack();
   static ArrayList<SymbolTable> symTabStack = new ArrayList<>();
   private int currentNestingLevel = 0;

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
      getGlobalSymTab();
      this.symbolTable = mlc.getSymbolTable(currentNestingLevel++, symTabStack);

      Scanner scanPrint = new Scanner(inputFile);
      scanPrint.scan(); //printout all the stuff in scanner first

      scanner = new Scanner(inputFile);
      Token token;
      //need to call this first for the scanner to work.
      scanner.nextCharNoPrint();
      while ((token = scanner.getNextToken()) != null)
      {
         if ("()".contains(token.getValue()))
         {
            putToStack(token, parentStack);
         }
         //put to Symbol table
         parseToSymbolTable(token, this.symbolTable, mlc);
         if (isBalanced(parentStack))
         {
            if (currentNestingLevel > symTabStack.size() - 1)
            {
               symTabStack.add(this.symbolTable);
            }
            currentNestingLevel = 1;
            this.symbolTable = mlc.getSymbolTable(currentNestingLevel++, symTabStack);
         }
      }

      //do cleanup
      removeEmptyStack();

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
   public void parseToSymbolTable(Token token, SymbolTable symTab, MidLayerControl mlc) throws Exception
   {
      if (lookUpGlobal(token))
      {
         //do nothing, already in the stack
      }
      else if (symTab.symTabContains(token.getValue()))
      {
         //do nothing, already inside the symtab
      }
      //define, then next word must be a procedure.
      else if (token.getValue().equals("define"))
      {
         token = scanner.getNextToken();
         symTab.setSymTab(token.getValue(), "PROCEDURE");
         if (currentNestingLevel > symTabStack.size() - 1)
         {
            symTabStack.add(this.symbolTable);
         }
         this.symbolTable = mlc.getSymbolTable(currentNestingLevel++, symTabStack);
      }
      else if (token.getValue().equals("lambda"))
      {
         token = scanner.getNextToken();
         if (token.getType() == Token.TokenType.L_PAREN)
         {
            token = scanner.getNextToken();
            while (token.getType() != Token.TokenType.R_PAREN)
            {
               symTab.setSymTab(token.getValue(), "IDENTIFIER");
               token = scanner.getNextToken();
            }
            if (currentNestingLevel > symTabStack.size() - 1)
            {
               symTabStack.add(this.symbolTable);
            }
            this.symbolTable = mlc.getSymbolTable(currentNestingLevel++, symTabStack);
         }
         else
         {
            symTab.setSymTab(token.getValue(), "IDENTIFIER");
            if (currentNestingLevel > symTabStack.size() - 1)
            {
               symTabStack.add(this.symbolTable);
            }
            this.symbolTable = mlc.getSymbolTable(currentNestingLevel++, symTabStack);
         }
      }
      else if (token.getValue().equals("let"))
      {
         Stack letStack = new Stack();
         token.setValue("(");
         token.setType(Token.TokenType.L_PAREN);
         putToStack(token, letStack);
         token = scanner.getNextToken();
         while (token.getType() == Token.TokenType.L_PAREN)
         {
            putToStack(token, parentStack);
            putToStack(token, letStack);
            token = scanner.getNextToken();
         }
         while (!isBalanced(letStack))
         {
            //if not in any of the symbol tables
            if (token.getType() != Token.TokenType.L_PAREN && token.getType() != Token.TokenType.R_PAREN)
            {
               if (!lookUpSymTabStack(token))
               {
                  //put it in the symbol table
                  symTab.setSymTab(token.getValue(), "IDENTIFIER");
               }
            }
            token = scanner.getNextToken();
            if ("()".contains(token.getValue()))
            {
               putToStack(token, letStack);
               putToStack(token, parentStack);
            }
         }
         if (currentNestingLevel > symTabStack.size() - 1)
         {
            symTabStack.add(this.symbolTable);
         }
         this.symbolTable = mlc.getSymbolTable(currentNestingLevel++, symTabStack);
      }
      else if (token.getType() == Token.TokenType.WORD)
      {
         if (scanner.reservedWords.contains(token.getValue()))
         {
            //do nothing, reserved word
         }
         else if (!"()".contains(token.getValue()))
         {
            symTab.setSymTab(token.getValue(), "IDENTIFIER");
         }
      }
      else if (token.getType() == Token.TokenType.SYMBOL)
      {
         symTab.setSymTab(token.getValue(), "PROCEDURE");
      }
   }

   boolean isBalanced(Stack st)
   {
      return st.isEmpty();
   }

   void putToStack(Token token, Stack st)
   {
      if (token.getType() == Token.TokenType.L_PAREN)
      {
         st.push("(");
      }
      else
      {
         st.pop();
      }
   }

   public ArrayList getSymTabStack()
   {
      return symTabStack;
   }

   public void getGlobalSymTab()
   {
      this.symbolTable = new SymbolTable(currentNestingLevel);
      this.symbolTable.setSymTab("car", "PROCEDURE");
      this.symbolTable.setSymTab("+", "PROCEDURE");
      this.symbolTable.setSymTab("cons", "PROCEDURE");
      this.symbolTable.setSymTab("-", "PROCEDURE");
      this.symbolTable.setSymTab("/", "PROCEDURE");
      this.symbolTable.setSymTab("*", "PROCEDURE");
      this.symbolTable.setSymTab("cdr", "PROCEDURE");
      symTabStack.add(this.symbolTable);
   }

   public boolean lookUpSymTabStack(Token token)
   {
      for (int i = 0; i < symTabStack.size(); i++)
      {
         SymbolTable sym = symTabStack.get(i);
         if (sym.symTabContains(token.getValue()))
         {
            return true;
         }
      }
      return false;
   }

   public boolean lookUpGlobal(Token token)
   {
      SymbolTable sym = symTabStack.get(0);
      if (sym.symTabContains(token.getValue()))
      {
         return true;
      }
      return false;
   }

   public void removeEmptyStack()
   {
      for (int i = 0; i < symTabStack.size(); i++)
      {
         SymbolTable sym = symTabStack.get(i);
         if (sym.size() == 0)
         {
            symTabStack.remove(i);
         }
      }
   }
}
