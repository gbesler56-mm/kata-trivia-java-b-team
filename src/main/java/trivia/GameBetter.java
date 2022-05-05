package trivia;

import java.util.ArrayList;

// REFACTOR ME

public class GameBetter implements IGame {

   ArrayList<Player> players = new ArrayList<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   private final QuestionRepo questionRepo = new QuestionRepo();

   public GameBetter() {
      questionRepo.init();
   }

   public boolean addPlayerToGame(String playerName) {

      players.add(new Player(playerName));

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public void roll(int roll) {
      final Player player = players.get(currentPlayer);
      System.out.println(player + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (player.isInPenaltyBox()) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(player + " is getting out of the penalty box");
            moveAndAsk(roll);
         } else {
            System.out.println(player + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         moveAndAsk(roll);
      }

   }

   private void moveAndAsk(int roll) {
      movePlayer(roll);

      System.out.println(players.get(currentPlayer)
              + "'s new location is "
              + players.get(currentPlayer).getPlace());
      System.out.println("The category is " + currentCategory());
      questionRepo.askQuestion(currentCategory());
   }

   private void movePlayer(int roll) {
      Player currentPlayer = players.get(this.currentPlayer);

      currentPlayer.setPlace((currentPlayer.getPlace() + roll)%12);

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

      int currentPlayerPosition = players.get(currentPlayer).getPlace();
      return valueOfLabel(currentPlayerPosition%4);
   }

   public boolean wasCorrectlyAnswered() {
      Player player = players.get(currentPlayer);

      if (player.inPenaltyBox && !isGettingOutOfPenaltyBox) {
            nextPlayer();
            return true;

      } else {
         rewardForCorrectAnswer(player);

         boolean winner = didPlayerWin();
         nextPlayer();

         return winner;
      }
   }

   private void rewardForCorrectAnswer(Player player) {
      System.out.println("Answer was correct!!!!");
      player.setPurse(player.getPurse() + 1);
      System.out.println(player
              + " now has "
              + player.getPurse()
              + " Gold Coins.");
   }

   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      players.get(currentPlayer).setInPenaltyBox(true);

      nextPlayer();
      return true;
   }


   private boolean didPlayerWin() {
      return !(players.get(currentPlayer).getPurse() == 6);
   }

}
