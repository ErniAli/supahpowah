package middlelayer;

import frontend.Token;
import java.util.ArrayList;
import java.util.Stack;

/**

 @author charles
 */
public class CodeTree
{
    
    private ArrayList<Node> definitions = null;
    
    
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
      definitions = new ArrayList<>();
   }

   public void addToken(Token t)
   {

      if (t.getType() == Token.TokenType.COMMENT)
      {
         //do nothing, just what comments do.
      }
      else if (t.getType() == Token.TokenType.L_PAREN)
      {
          if(parentStack.size()==0){
              root = new Node();
              currentNode = parentStack.push(root);
              definitions.add(root);
          }else{
            currentNode.leftChild = new Node();
            parentStack.push(currentNode);
            currentNode = currentNode.leftChild;
         }
      }
      else if (t.getType() == Token.TokenType.R_PAREN)
      {
         currentNode = parentStack.pop();
         if(!(definitions.get(definitions.size()-1)==currentNode)){
         currentNode.rightChild = new Node();
         currentNode = currentNode.rightChild;}
      }
      else
      {
         currentNode.value = t.getValue();
         currentNode.rightChild = new Node();
         currentNode = currentNode.rightChild;
      }
      //printTreeTest();
   }

   
   public void printTreeTest(){
       Node cNode = null;
       Stack<Node> st = new Stack<>();
       
       cNode = definitions.get(0);
       
       printNode(cNode);
       
       System.out.println("-----------------------------------");
       
   }
   
   private void printNode(Node n){
       
       boolean goLeft = false, goRight = false;
       
       System.out.print("Node: "+n.hashCode());
       if(n.value!=null){System.out.println("\tValue: "+n.value);}
       if(n.leftChild!=null){
           System.out.println("  Left Child: "+n.leftChild.hashCode());
           goLeft = true;
       }
       if(n.rightChild!=null){
           System.out.println("  Right Child: "+n.rightChild.hashCode());
           goRight = true;
       }
       System.out.print("\n\n");
       
       if(goLeft){printNode(n.leftChild);}
       if(goRight){printNode(n.rightChild);}
   }
   
   
   public void printTree()
   {
      
      for(Node n: definitions){
          printTree(n, 0);
          System.out.println("-----------------------");
      }

   }
   
   
   private void printTree(Node n, int level){
       Node cNode = n; boolean branch=false;
       System.out.println();
       for(int i=0;i<level;i++){System.out.print("   ");}
       System.out.print("(");
       while(cNode.rightChild!=null){
           if(cNode.value!=null){System.out.print(cNode.value + " ");}
           if(cNode.leftChild!=null){branch=true; printTree(cNode.leftChild,level+1);}
           cNode = cNode.rightChild;
       }
       if(branch){for(int i=0;i<level;i++){System.out.print("   ");}}
       System.out.println(")");
   }
}
