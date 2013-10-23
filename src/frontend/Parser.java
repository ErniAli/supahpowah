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

/**

 @author Erni
 */
public class Parser
{
   static TreeMap symbolTable = new TreeMap<>();
   static Scanner scanner;

   public Parser()
   {
   }

   public void parseFile(String inputFile) throws Exception
   {
      Scanner scanPrint = new Scanner(inputFile);
      scanPrint.scan(); //printout all the stuff in scanner first

      scanner = new Scanner(inputFile);
      String token;
      System.out.println("\nYOUR ORIGINAL FILE: ");
      //need to call this first for the scanner to work.
      //this thing has a print statement in it to printout the lines.
      scanner.nextChar();
      scanner.addReservedWords();
      scanner.addSpecialSymbols();
      while ((token = scanner.nextToken()) != null)
      {
         //put to Symbol table
         parseToSymbolTable(token);

         //add the token to the tree and list - assuming it is not a comment
      }
      //move this printing to somewhere else
      printSymTab();
   }

   public void parseToSymbolTable(String token) throws Exception
   {
      String type = scanner.typeOf(token);
      if(symTabContains(token))
      {
         //do nothing, already inside the symtab
      }
      else if(token.equals("define"))
      {
         token = scanner.nextToken();
         symbolTable.put(token, "PROCEDURE");
      }
      else if(type.equals("PROCEDURE"))
      {
         symbolTable.put(token, "PROCEDURE");
      }
      else if(type.equals("IDENTIFIER") && !"()".contains(token))
      {
         symbolTable.put(token, "IDENTIFIER");
      }
   }

   public void printSymTab()
   {
      System.out.println("\n SYMBOL TABLE:");
      // Get a set of the entries
      Set set = symbolTable.entrySet();
      // Get an iterator
      Iterator i = set.iterator();
      // Display elements
      while (i.hasNext())
      {
         Map.Entry me = (Map.Entry) i.next();
         System.out.print(me.getKey() + ": ");
         System.out.println(me.getValue());
      }
   }

   public boolean symTabContains(String st)
   {
      return symbolTable.containsKey(st);
   }

   public String symTabValue(String st)
   {
      return symbolTable.get(st).toString();
   }


}
