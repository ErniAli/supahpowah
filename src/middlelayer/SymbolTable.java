package middlelayer;

import frontend.Scanner;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 The symbol table structure

 @author Team Supah Powah, CS 152, Section 02 Erni Ali, Charles Flood, Su Sandi
 */
public class SymbolTable
{
   TreeMap symbolTable = new TreeMap<>();
   static Scanner scanner;
   private int nestingLevel;

   public SymbolTable(int currentNestingLevel)
   {
      this.nestingLevel = nestingLevel;
   }

   public int getNestingLevel()
   {
      return nestingLevel;
   }

   public void setNestingLevel(int nestingLevel)
   {
      this.nestingLevel = nestingLevel;
   }



   /**
    method to print the contents of the symbol table
    */
   public void printSymTab()
   {
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

   /**
    Put value to the symbol table

    @param key the key for the tree map
    @param value the value associated with it
    */
   public void setSymTab(String key, String value)
   {
      symbolTable.put(key, value);
   }

   /**
    Checks whether the symbol table contains the given string

    @param st the string to be checked
    @return the boolean value if the string is inside the symtab or not
    */
   public boolean symTabContains(String st)
   {
      return symbolTable.containsKey(st);
   }

   /**
    Get the value based on the key

    @param st the key
    @return the string value
    */
   public String symTabValue(String st)
   {
      return symbolTable.get(st).toString();
   }

   /**
    gets the symbol table itself

    @return the symbol table.
    */
   public TreeMap getSymTab()
   {
      return symbolTable;
   }
}
