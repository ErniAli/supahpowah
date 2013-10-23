/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

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

   public void parseFile(String fileName) throws Exception
   {
      Scanner scanner = new Scanner(fileName);
      
      
      scanner.scan();
      printSymTab();
      //print it here for now
   }

   public void parseToSymbolTable(String key, String value)
   {
      symbolTable.put(key, value);
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
