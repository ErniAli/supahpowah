/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middlelayer;

import frontend.Scanner;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**

 @author charles
 */
public class SymbolTable
{
   static TreeMap symbolTable = new TreeMap<>();
   static Scanner scanner;

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

   public void setSymTab(String key, String value)
   {
      symbolTable.put(key, value);
   }

   public boolean symTabContains(String st)
   {
      return symbolTable.containsKey(st);
   }

   public String symTabValue(String st)
   {
      return symbolTable.get(st).toString();
   }

   public TreeMap getSymTab()
   {
      return symbolTable;
   }
   
}
