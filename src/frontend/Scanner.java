package frontend;

/**
 This is the scanner method that scans the source files and return tokens.

 @author Team Supah Powah, CS 152, Section 02 Erni Ali, Charles Flood, Su Sandi
 */
import java.io.*;
import java.util.ArrayList;

public class Scanner
{
   private char ch;    // current input character
   ArrayList<String> reservedWords = new ArrayList();
   ArrayList<String> specialSymbol = new ArrayList();
   //add here if you want to check procedure example: member?, null?

   // the array list with the reserved words
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
   }
   //array list with the symbols

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

    @return String name of the next token
    @throws Exception if an error occurs.
    */
   public String nextToken()
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

      if ("'()".contains(Character.toString(ch)))
      {
         buffer.append(ch);
         nextChar();
         return buffer.toString();
      }

      while (!(" )".contains(Character.toString(ch))))
      {
         buffer.append(ch);  // build token string
         nextChar();
      }


      return buffer.toString();
   }

   /**
    Scan the source file and also does all the printing

    @throws Exception if an error occurs.
    */
   public void scan()
           throws Exception
   {
      nextChar();

      while (ch != 0)
      {  // EOF?
         String token = nextToken();

         //We don't want to printout the parenthesis
         if (token != null && !"()".contains(token))
         {
            String tokenType = "";
            System.out.print("=====> \"" + token + "\" ");
            if (token.equals("define"))
            {
               tokenType = "RESERVED WORD";
               System.out.println(tokenType);
               token = nextToken();

               System.out.print("=====> \"" + token + "\" ");
               tokenType = "PROCEDURE";
            }
            else
            {
               tokenType = typeOf(token);
            }
            System.out.println(tokenType);
         }
      }
   }

   /**
    Return the type of the token in String.

    @param String the token.
    @return the type.
    */
   String typeOf(String st) throws Exception
   {
      //handle define, the word after it is a procedure
      //to ensure that everything after define is still procedure
      if (reservedWords.contains(st))
      {
         return "RESERVED WORD";
      }
      else if (Character.isDigit(st.charAt(0)))
      {
         return "DIGIT";
      }
      else if (specialSymbol.contains(st))
      {
         return "PROCEDURE";
      }
      else
      {
         return "IDENTIFIER";
      }
      //add here if you want to check procedure example: member?, null?
   }
   private BufferedReader reader;
   private String line = null;
   private int lineNumber = 0;
   private int linePos = -1;

   /**
    Get the next character form the source file. This method also does some
    printing.

    @throws Exception if an error occurred.
    */
   void nextChar() throws Exception
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
      addReservedWords();
      addSpecialSymbols();
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

   //this method doesn't have any print that will get in the way
   void nextCharNoPrint() throws Exception
   {
      if ((line == null) || (++linePos >= line.length()))
      {
         line = reader.readLine();
         if (line != null)
         {
            while (line.length() > 0 && line.charAt(0) == ';')
            {
               line = reader.readLine();
            }
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
            nextChar();
         }
         ch = line.charAt(linePos);
      }
   }

   /**
    Extract the next token from the source file and does no printing.

    @return token, the Token itself with the value and type
    @throws Exception if an error occurs.
    */
   public Token getNextToken() throws Exception
   {
      // Skip blanks.
      while (Character.isWhitespace(ch))
      {
         nextCharNoPrint();
      }

      while (ch == ';')
      {
         line = reader.readLine();
         nextCharNoPrint();
      }

      // At EOF?
      if (ch == 0)
      {
         return null;
      }

      //build the string
      StringBuilder buffer = new StringBuilder();

      if ("'()".contains(Character.toString(ch)))
      {
         buffer.append(ch);
         nextCharNoPrint();
         return determineToken(buffer.toString());
      }

      while (!(" )".contains(Character.toString(ch))))
      {
         buffer.append(ch);  // build token string
         nextCharNoPrint();
      }

      return determineToken(buffer.toString());
   }

   /**
    This method takes in a string token and returns a Token type with the value
    and type.

    @param st, the string of the token.
    @return the token itself with the value and type
    */
   public Token determineToken(String st)
   {
      Token tok = new Token();
      tok.setValue(st);
      if (st.equals("("))
      {
         tok.setType(Token.TokenType.L_PAREN);
      }
      else if (st.equals(")"))
      {
         tok.setType(Token.TokenType.R_PAREN);
      }
      else if (Character.isDigit(st.charAt(0)))
      {
         tok.setType(Token.TokenType.NUMBER);
      }
      else if (specialSymbol.contains(st))
      {
         tok.setType(Token.TokenType.SYMBOL);
      }
      else
      {
         tok.setType(Token.TokenType.WORD);
      }
      return tok;
   }
}
