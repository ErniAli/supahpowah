package middlelayer;

import frontend.Token;
import java.util.Stack;

/**

 @author charles
 */
public class CodeTree
{
   private Node root = null;
   private Node currentNode = null;
   private Stack<Node> parentStack = null;

   private class Node
   {
      public Node leftChild = null;
      public Node rightChild = null;
      public String value = null;
   }

   public CodeTree()
   {
      parentStack = new Stack();
   }

   public void addToken(Token t)
   {

      if (t.getType() == Token.TokenType.COMMENT)
      {
         //do nothing, just what comments do.
      }
      else if (t.getType() == Token.TokenType.L_PAREN)
      {
         if (root == null)
         {
            root = new Node();
            currentNode = parentStack.push(root);
         }
         else
         {
            currentNode.leftChild = new Node();
            parentStack.push(currentNode);
            currentNode = currentNode.leftChild;
         }
      }
      else if (t.getType() == Token.TokenType.R_PAREN)
      {
         currentNode = parentStack.pop();
      }
      else
      {
         currentNode.value = t.getValue();
         currentNode.rightChild = new Node();
         currentNode = currentNode.rightChild;
      }
   }

   public void printTree()
   {

      Node cNode = null;
      Stack<Node> st = null;

      System.out.print("(");//has to start with this
      cNode = this.root;
      st.push(root);

      while (!st.empty())
      {
         if (cNode.value != null)
         {
            System.out.print(cNode.value + " ");
            if (cNode.rightChild != null)
            {
               cNode = cNode.rightChild;
            }
            else
            {
               System.out.print(")");
               cNode = st.pop();
            }
         }
         else
         {//has left child
            //print stuff
            System.out.println();
            for (int i = 0; i < st.size(); i++)
            {
               System.out.print("\t");
            }
            System.out.print("(");
            st.push(cNode);
            cNode = cNode.leftChild;
         }
      }
   }
}
