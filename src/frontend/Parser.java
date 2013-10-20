/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

/**

 @author Erni
 */
public class Parser
{
   public static void parseFile(String fileName) throws Exception
   {
      Scanner scanner = new Scanner(fileName);
      scanner.scan();
   }

   public void parseToSymbolTable()
   {
      
   }
}
