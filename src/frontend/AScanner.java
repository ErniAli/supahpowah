/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import frontend.Token;

/**

 @author charles
 */
public class AScanner
{
   private File inputFile;
   private Scanner s;

   public AScanner(File inputFile)
   {
      this.inputFile = inputFile;

      try
      {
         s = new Scanner(inputFile);
      }
      catch (FileNotFoundException ex)
      {
         //This should never occur because the file is checked before being sent here.
         System.out.println("Unable to initialize scanner.");
      }

   }

   public void getNextToken()
   {
      Token t = new Token();
      Token.TokenType tokenType = null;
      int i = 0;
      char[] buffer;
      Boolean isComment = false;
      int position = 0, lastindex = 0, lindex = 0;

//      lineLoop:
      while (s.hasNext())
      {
         String line = s.nextLine();
         // System.out.println("LINE [" + ++i + "]: " + line);
         buffer = line.toCharArray();
         String currentTokenValue = "";


         for (char c : buffer)
         {
            if (c == ';')
            {
               isComment = true;
               t.setType(Token.TokenType.COMMENT);

            }
            else if (isComment == true)
            {
               currentTokenValue += c;


               position = findIndex(buffer, c, lindex);
               lindex = position;

               if (position == buffer.length - 2)
               {
                  char p = peekAhead(buffer, position);
                  if (c == p)
                  {
//                     System.out.println("comment: " + currentTokenValue);
                     position = 0;
                     lindex = 0;
                     lastindex = 0;
                     isComment = false;
                  }

               }
               if (c == '.' || c == ',' || position == buffer.length - 1)
               {
//                  System.out.println("comment: " + currentTokenValue);
                  position = 0;
                  lindex = 0;
                  lastindex = 0;
                  currentTokenValue = " ";
                  isComment = false;
               }



            }
            else if (Character.isWhitespace(c)) ;
            else if (Character.isLetter(c) || c == '*' || c == '?' || c == '-' && isComment == false)
            {
               t.setType(Token.TokenType.WORD);

               //proc: names like null?, member? not should be recorded as identifier without the special char in the end
               if (c != '?')
               {
                  currentTokenValue += c;
               }

               position = findIndex(buffer, c, lastindex);
               lastindex = position;
               char repeatWord = 0;

               if (position == buffer.length - 1 || peekAhead(buffer, position) == ' ' || peekAhead(buffer, position) == ')' || peekAhead(buffer, position) == c)
               {
                  //if(peekAhead(buffer,position)==c) repeatWord=c;


                  System.out.println("Word Token : " + currentTokenValue);

                  currentTokenValue = " ";
                  //if c is in last char of the line, reset position
                  if (position == buffer.length - 1 || position == buffer.length - 2)
                  {
                     position = 0;
                     lastindex = 0;
                  }
               }


            }
            else if (Character.isDigit(c) && isComment == false)
            {
               t.setType(Token.TokenType.NUMBER);
               //save digit in the symbtable

               System.out.println("Digit: " + c);


            }
            else if (c == '(' && isComment == false)
            {
               t.setType(Token.TokenType.L_PAREN);
               //currentTokenValue+=c;

               System.out.println("L_PAREN: " + c);
               lastindex = 0;


            }
            else if (c == ')' && isComment == false)
            {
               t.setType(Token.TokenType.R_PAREN);
               //currentTokenValue+=c;
               System.out.println("R_PAREN: " + c);
               lastindex = 0;

            }
            else
            {
               t.setType(Token.TokenType.SYMBOL);
               //save special symbol in the symbtable
               System.out.println("special symbol: " + c);


            }

         }

      }

   }

   private int findIndex(char[] buff, char ch, int fromIndex)
   {
      return new String(buff).indexOf(ch, fromIndex);
   }

   private char peekAhead(char[] buff, int pos)
   {

      return buff[pos + 1];
   }

   public static void main(String[] args)
   {
      AScanner scan = new AScanner(new File("input.lisp"));
      scan.getNextToken();

   }
}
