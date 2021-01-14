import java.util.*;
import java.io.*;   

public class Player
{
   
   private String name;
   private int tokens;
   
   //Constructor method sets the name and number of tokens
   public Player()
   {
      name = "";
      tokens = 0;
   }
   
   //Setter method to set the name of a specific Player object in the Driver; uses a String variable passed in, taken from the player's input
   public void setName(String nameInput)
   {
      this.name = nameInput;
   }
   
   //Getter method to return the name of a specific Player object in the Driver
   public String getName()
   {
      return this.name;
   }
   
   //Getter method to return the number of tokens held by a specific Player object in the Driver
   public int getTokens()
   {
      return this.tokens;
   }
   
   //Setter method to give tokens to a specific Player object; uses an int passed in from the driver class
   public void receiveTokens(int numTokens)
   {
      this.tokens = this.tokens + numTokens;
   }
   
   //Setter method to deduct token to a specific Player object; uses an int passed in from the driver class
   public void loseTokens(int numTokens)
   {
      this.tokens = this.tokens - numTokens;
   }
   
   //This method is called to display the stats of a specific Player object
   public void printInfo()
   {
      System.out.println(this.name + " has " + this.tokens + " tokens.");
   }

}