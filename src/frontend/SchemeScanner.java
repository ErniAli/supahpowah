/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**

 @author Erni
 */
public class SchemeScanner
{
   Scanner sc, scanLine;
   String line;
   char ch;
   int position = 0;

   /**
    Main.

    @param args command-line arguments.
    @throws Exception if an error occurred.
    */
   public static void main(String[] args) throws Exception
   {
//      SchemeScanner scanner = new SchemeScanner(args[0]);
      SchemeScanner scanner = new SchemeScanner("input.lisp");
      scanner.scan();
   }

   /**
    Constructor.

    @param sourcePath the source path.
    */
   public SchemeScanner(String sourcePath)
   {
      try
      {
         sc = new Scanner(new File(sourcePath));
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
         System.exit(-1);
      }
   }

   public void scan()
   {
      getRidOfComments();

      while (sc.hasNextLine())
      {
         nextToken();
      }
   }

   public void getRidOfComments()
   {
      //handles the comments
      if (sc.hasNextLine())
      {
         line = sc.nextLine();
         if (line.length() > 0 && line != null)
         {
            ch = line.charAt(0);
         }
         while (ch == ';') //comments, ignore
         {
            line = sc.nextLine();
            if (line.length() > 0 && line != null)
            {
               ch = line.charAt(0);
            }
            scanLine = new Scanner(line);
         }
      }
   }

   public void nextToken()
   {
      String token = "";
      if (scanLine.hasNext())
      {
         token = scanLine.next();
         token = removeInlineComment(scanLine, token);
      }
      else if (sc.hasNext())
      {
         line = "";
         token = sc.next();
         token = removeInlineComment(sc, token);

      }
      System.out.println(token);
   }

   public String removeInlineComment(Scanner scan, String tok)
   {
      while (tok.charAt(0) == ';' || tok.equals(";"))
      {
         if(scan.hasNextLine())
            scan.nextLine();
         else
         {
            return sc.next();
         }

         getRidOfComments();
         if(scan.hasNext())
         {
            return scan.next();
         }
      }
      return tok;
   }
}
