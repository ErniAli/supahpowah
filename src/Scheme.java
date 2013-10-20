
import frontend.Parser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**

 @author Erni
 */
public class Scheme
{
      /**
    Main.

    @param args command-line arguments.
    @throws Exception if an error occurred.
    */
   public static void main(String[] args)
           throws Exception
   {
      Parser parse = new Parser();
      parse.parseFile("input.lisp");
   }
}
