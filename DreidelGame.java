import java.util.*;
import java.io.*;

public class DreidelGame
{
   

   //The main method!!! All hail the main method
   public static void main(String[] args)
   {   
      
      //Used to accept player input
      Scanner in = new Scanner(System.in);
      
      //Two player objects are created before anything else is done, constructed with default values
      Player p1 = new Player();
      Player p2 = new Player();
      
      //This will store the name entered by the player (only for a moment)

      //This will be used in the loop later on
      String cont = "";
      
      //This will keep track of how many tokens are at stake with each Dreidel spin
      int pot = 0;
      
      
      //Intro block! Welcomes the player(s) and sets off the user's experience by asking for names to be entered
      System.out.println("Shalom! Welcome to the Dreidel Game");
      
      System.out.println("Please enter the first player's name: ");
         //The player's input is taken and immediately passed into the Setter method, meaning it won't have to be stored in the main()
      p1.setName(in.nextLine());
         //And then the Getter method is used to give a personal greeting to player 1
      System.out.println("Thanks for playing, " + p1.getName() + "!");
      
         //Same functionality as above, just repeated for player 2
      System.out.println("Please enter the second player's name: ");      
      p2.setName(in.nextLine());
      System.out.println("Good to see you, " + p2.getName() + "!");
      
      
      //Kick off the actual game by distributing 20 tokens to each player
      System.out.println("Alright, let's play.");      
      p1.receiveTokens(20);
      p2.receiveTokens(20);
      
      //First round of ante works outside of the do-while loop (below) because of slightly different verbiage than subsequent rounds
      System.out.println("Everybody ante up: all players lose one token");
         //This Setter method takes one coin out of each player's pocket
      p1.loseTokens(1);
      p2.loseTokens(1);
         //I found it easier to work with a dependent pot than an independent variable, which would have required a bit more code
         //I bound the pot's value to the player's token-count. This way I can update the value with this simple equation and forget about it otherwise.
         //And the best part is, this doesn't prevent me from leaning on the variable in the playGame() method below.
      pot = 40 - p1.getTokens() - p2.getTokens();
      
      //Print out each player's info and the pot's value, just FYI
      p1.printInfo();
      p2.printInfo();
      System.out.println("Pot tokens: " + pot);
      
      do
      {
         
         //Make sure the player has enough time to review the info, and offers a comfortable level of participation in determining pacing
         System.out.println("Press ENTER to move on to the next player's turn");
         in.nextLine();
            //This is where things get interesting.
            //The playGame() method is run, passing in the Player object 'p1' and whatever value pot is currently set to.
            //The playGame() method returns a Player, and so the mutations made to the variable passed in are then concretized here in the main method.
            //See the playGame() method down below to get more details on how this works.
         p1 = playGame(p1, pot);
         p1.printInfo();
            //And then, once the first player's round is through, the pot is once again updated
         pot = 40 - p1.getTokens() - p2.getTokens();

         
         //This block has the same functionality as the one above, only it applies to the second player.
         System.out.println("Press ENTER to move on to the next player's turn");
         in.nextLine();
         p2 = playGame(p2, pot);
         p2.printInfo();
         pot = 40 - p1.getTokens() - p2.getTokens();

         
         //Conditional check to see if either player has run out of tokens
         if (p1.getTokens() > 0 && p2.getTokens() > 0)
         {
            //And then prompt a decision to stop or keep going (input stored in 'cont')
            System.out.println("Enter Q to quit, or enter any other key to play another round");
            cont = in.nextLine();
         }
         //If the condition fails (one player has run out of tokens), a "game over" message prints, 'cont' is set to "q", and the loop is exited
         else
         {
            System.out.println("Looks like someone's run out of tokens! ");
            cont = "q";
         }
         
         
         //This conditional checks the value of 'cont' before the loop does. The statements within are near-identical to the first round.
         if(!cont.equalsIgnoreCase("q"))
         {
            System.out.println("New round! Ante up: all players lose one token");
            p1.loseTokens(1);
            p2.loseTokens(1);
            pot = 40 - p1.getTokens() - p2.getTokens();
            
            p1.printInfo();
            p2.printInfo();
            System.out.println("Pot tokens: " + pot);
         }
      
      //If anything other than "q" is entered in the above prompt, the game loops back around 
      }while(!cont.equalsIgnoreCase("q"));
      
      //If the loop is exited, this farewell is printed and the program is terminated.
      System.out.println("That's the end of this game, see you next time.");
      
   }
   
   //This playGame() method handles all the actual play, and even the mutations made to each of the Player objects created in the main() method.
   //It works by taking in a Player with all of its values, making the necessary changes, and then returning that updated Player.
   //The result is a player-before-the-round being passed in, and a player-after-the-round being passed out and eventually replacing the old object in the
   //main() method.
   //The 'pot' variable (from the main()) is also used here, but is not returned.
   public static Player playGame(Player whosTurn, int currentPot)
   {
      //These variables comprise the whole function of the Dreidel.
      Random rng = new Random();
      int dreidelSpin = 0;
      
      //Using the passed-in object, a print statement declares who's turn it is.
      System.out.println("It's " + whosTurn.getName() + "'s turn. Time to spin the dreidel.");
      
      //A random number is generated and stored (0-3, for a four-sided dreidel)
      dreidelSpin = rng.nextInt(4);
      
      //And a switch statement is used to determine what happens as a result of that random number
      switch (dreidelSpin)
      {
         case 0:
         {
            //No changes made
            System.out.println("The dreidel spins and lands face up on NUN. How boring! You get nothing and you lose nothing. Next!");
         }
         break;
         
         case 1:
         {
            System.out.println("The dreidel spins and lands on GIMMEL. Lucky you! You've won the whole pot.");
               //Setter method is called to add the value of currentPot (identical to 'pot' in the main()) to the Player object's 'tokens' variable
            whosTurn.receiveTokens(currentPot);
         }
         break;
         
         case 2:
         {
            System.out.println("The dreidel spins and lands on HEY. Help yourself to half the pot, and don't go complaining that you didn't get gimmel!");
            if(currentPot % 2 == 0)
            {
               //Setter method is called to add half the value of currentPot to the Player object's 'tokens' variable
               whosTurn.receiveTokens(currentPot / 2);
            }
            else
            {
               //I read online that you round up with an odd-numbered pot, so that's what this is for
               whosTurn.receiveTokens(currentPot / 2 + 1);
            }
         }
         break;
         
         case 3:
         {

            System.out.println("Oh how unfortunate! SHIN is facing up, that means you have to give up another token to the pot!");
               //Setter method is called to deduct a token from the Player object's 'tokens' variable
            whosTurn.loseTokens(1);
         }
         break;
      }


      //And then after the changes have been made, the 'whosTurn' Player object is returned to the main(), ultimately destined to replace the object
      //that was passed in.
      //Changes made here will also determine 'pot', as the updated 'tokens' value will directly factor into the recalculation of 'pot' at the end of
      //each player's turn.
      return whosTurn;
   
   }
}

/*Looks like that's a wrap for the semester! I hope you will accept my sincere gratitude. This was my first exposure worth mentioning to computer science,
and I cannot thank you enough for making it so successful. Because of your instruction, an uncharted territory has become accessible, and were it not for that
I may never have found that I so enjoy exploring it. I look forward to what comes next semester with CS 1410, and again, I thank you for a very fun and
informative semester.
*/