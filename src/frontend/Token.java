package frontend;

/**
 Defines the Token enum that says what the token type and value is

 @author Team Supah Powah, CS 152, Section 02 Erni Ali, Charles Flood, Su Sandi
 */
public class Token
{
   //symbol is plus, minus, quote
   public enum TokenType
   {
      COMMENT, L_PAREN, R_PAREN, WORD, NUMBER, SYMBOL
   }
   private String value = null;
   private TokenType type = null;

   public Token(String value, TokenType type)
   {
      this.value = value;
      this.type = type;
   }

   public Token()
   {
   }

   public TokenType getType()
   {
      return type;
   }

   public void setType(TokenType type)
   {
      this.type = type;
   }

   public String getValue()
   {
      return value;
   }

   public void setValue(String value)
   {
      this.value = value;
   }
}
