package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME

enum Category{

   POP("Pop", 0),
   SCIENCE( "Science", 1),
   SPORTS( "Sports", 2),
   ROCK("Rock", 3);

   final int   mod4;
   final String categoryName;

   Category(String categoryName, int mod4){
      this.mod4 = mod4;
      this.categoryName = categoryName;
   }

   @Override
   public String toString(){
      return categoryName;
   }
}

public class GameBetter implements IGame {
   ArrayList players = new ArrayList();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   LinkedList popQuestions = new LinkedList();
   LinkedList scienceQuestions = new LinkedList();
   LinkedList sportsQuestions = new LinkedList();
   LinkedList rockQuestions = new LinkedList();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      players.add(playerName);
      places[howManyPlayers()] = 0;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            PlayRound_MoveAndAsk(roll);
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         PlayRound_MoveAndAsk(roll);
      }

   }

   private void PlayRound_MoveAndAsk(int roll) {
      movePlayer(roll);

      System.out.println(players.get(currentPlayer)
              + "'s new location is "
              + places[currentPlayer]);
      System.out.println("The category is " + currentCategory());
      askQuestion();
   }

   private void movePlayer(int roll) {
      places[currentPlayer] = places[currentPlayer] + roll;
      if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
   }

   private void askQuestion() {
      switch(currentCategory()){
         case POP: System.out.println(popQuestions.removeFirst()); break;
         case SCIENCE: System.out.println(scienceQuestions.removeFirst()); break;
         case SPORTS: System.out.println(sportsQuestions.removeFirst()); break;
         case ROCK: System.out.println(rockQuestions.removeFirst());
      }
   }

   public static Category valueOfLabel(int mod4Label) {
      for (Category category : Category.values()) {
         if (category.mod4==mod4Label) {
            return category;
         }
      }
      return null;
   }

   private Category currentCategory() {

      int currentPlayerPosition = places[currentPlayer];
      return valueOfLabel(currentPlayerPosition%4);
   }

   public boolean wasCorrectlyAnswered() {
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            nextPlayer();

            return winner;
         } else {
            nextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(players.get(currentPlayer)
                 + " now has "
                 + purses[currentPlayer]
                 + " Gold Coins.");

         boolean winner = didPlayerWin();
         nextPlayer();

         return winner;
      }
   }

   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      nextPlayer();
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == 6);
   }
}
