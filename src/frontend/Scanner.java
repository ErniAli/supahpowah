package frontend;

/**

 @author Erni
 */
import java.io.*;
import java.util.ArrayList;

public class Scanner
{
   Parser parse = new Parser();
   private char ch;    // current input character
   ArrayList<String> reservedWords = new ArrayList();
   ArrayList<String> specialSymbol = new ArrayList();
   //add here if you want to check procedure example: member?, null?

   public void addReservedWords()
   {
      reservedWords.add("let*");
      reservedWords.add("and");
      reservedWords.add("begin");
      reservedWords.add("cond");
      reservedWords.add("define");
      reservedWords.add("else");
      reservedWords.add("if");
      reservedWords.add("lambda");
      reservedWords.add("letrec");
      reservedWords.add("not");
      reservedWords.add("or");
      reservedWords.add("quote");
      reservedWords.add("'");
      reservedWords.add("");
   }

   public void addSpecialSymbols()
   {
      specialSymbol.add("+");
      specialSymbol.add("-");
      specialSymbol.add("*");
      specialSymbol.add("/");
      specialSymbol.add("%");
      specialSymbol.add("^");
      specialSymbol.add("=");
      specialSymbol.add(">");
      specialSymbol.add("<");
      specialSymbol.add(">=");
      specialSymbol.add("<=");
   }

   /**
    Extract the next token from the source file.

    @return name of the next token
    @throws Exception if an error occurs.
    */
   private String nextToken()
           throws Exception
   {
      // Skip blanks.
      while (Character.isWhitespace(ch))
      {
         nextChar();
      }

      while (ch == ';')
      {
         line = reader.readLine();
         nextChar();
      }

      // At EOF?
      if (ch == 0)
      {
         return null;
      }

      //build the string
      StringBuilder buffer = new StringBuilder();

      while (!(" ".contains(Character.toString(ch))))
      {
         buffer.append(ch);  // build token string
         nextChar();
      }

      return buffer.toString();
   }

   /**
    Scan the source file.

    @throws Exception if an error occurs.
    */
   public void scan()
           throws Exception
   {
      addReservedWords();
      addSpecialSymbols();
      nextChar();

      while (ch != 0)
      {  // EOF?
         String token = nextToken();

         //ignore the parentheses
         if (token != null && !("()".contains(token)))
         {
//            System.out.println(token);
            token = token.replaceAll("[()]", "");
            String afterQuote = "";
            char tokenAt0 = token.charAt(0);
            if (tokenAt0 == '\'')
            {
               afterQuote = token.substring(1);
               token = "'";

               if (afterQuote.length() > 0)
               {
                  System.out.print("=====> \"" + token + "\" ");
                  String tokenType = typeOf(token);
                  System.out.println(tokenType);
                  token = afterQuote;
               }
            }
            System.out.print("=====> \"" + token + "\" ");
            String tokenType = typeOf(token);
            System.out.println(tokenType);
         }
      }
   }

   /**
    Return the character type.

    @param ch the character.
    @return the type.
    */
   String typeOf(String st) throws Exception
   {
      //handle define, the word after it is a procedure

      if (st.equals("define"))
      {
         String tokenType = "RESERVED WORD";
         System.out.println(tokenType);

         st = nextToken();
         System.out.print("=====> \"" + st + "\" ");
         parse.parseToSymbolTable(st, "PROCEDURE");
         return "PROCEDURE";
      }
      else if(parse.symTabContains(st))
      {
         return parse.symTabValue(st);
      }
      else if (reservedWords.contains(st))
      {
         return "RESERVED WORD";
      }
      else if (Character.isDigit(st.charAt(0)))
      {
         return "DIGIT";
      }
      else if (specialSymbol.contains(st))
      {
         parse.parseToSymbolTable(st, "PROCEDURE");
         return "PROCEDURE";
      }
      else
      {
         //put into symbol table
         parse.parseToSymbolTable(st, "IDENTIFIER");
         return "IDENTIFIER";
      }
      //add here if you want to check procedure example: member?, null?
   }
   private BufferedReader reader;
   private String line = null;
   private int lineNumber = 0;
   private int linePos = -1;

   /**
    Get the next character form the source file.

    @throws Exception if an error occurred.
    */
   void nextChar()
           throws Exception
   {
      if ((line == null) || (++linePos >= line.length()))
      {
         line = reader.readLine();
         if (line != null)
         {
            while (line.length() > 0 && line.charAt(0) == ';')
            {
               System.out.println("[" + ++lineNumber + "] " + line);
               line = reader.readLine();
            }
            System.out.println("[" + ++lineNumber + "] " + line);
            line += " ";
            linePos = 0;
            ch = line.charAt(0);
         }
         else
         {
            ch = 0;
         }
      }
      else
      {
         while (line.length() > 0 && line.charAt(linePos) == ';')
         {
            line = reader.readLine();
            linePos = 0;
            System.out.println("[" + ++lineNumber + "] " + line);
            nextChar();
         }
         ch = line.charAt(linePos);
      }
   }

   /**
    Constructor.

    @param sourcePath the source path.
    */
   public Scanner(String sourcePath)
   {
      try
      {
         reader = new BufferedReader(new FileReader(sourcePath));
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
         System.exit(-1);
      }
   }
}
