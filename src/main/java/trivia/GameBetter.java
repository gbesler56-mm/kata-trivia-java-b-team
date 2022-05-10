package trivia;

import java.util.ArrayList;

import static trivia.Category.valueOfLabel;

public class GameBetter implements IGame {

   private ArrayList<Player> players = new ArrayList<>();
   private final QuestionRepo questionRepo = new QuestionRepo();

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

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

      if (!player.isInPenaltyBox()){
         movePlayer(roll);
         askQuestion();
      }
      if (player.isInPenaltyBox() && (roll%2 == 1)) {
         isGettingOutOfPenaltyBox = true;
         System.out.println(player + " is getting out of the penalty box");
         movePlayer(roll);
         askQuestion();
      }
      if (player.isInPenaltyBox() && (roll % 2 == 0 )) {
         System.out.println(player + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   public boolean wasCorrectlyAnswered() {
      Player player = players.get(currentPlayer);
      boolean winner;
      if (player.inPenaltyBox && !isGettingOutOfPenaltyBox) {
         winner = true;
      } else {
         rewardForCorrectAnswer(player);
         winner = didPlayerWin();
      }
      nextPlayer();
      return  winner;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      players.get(currentPlayer).setInPenaltyBox(true);

      nextPlayer();
      return true;
   }

   private void rewardForCorrectAnswer(Player player) {
      System.out.println("Answer was correct!!!!");
      player.setPurse(player.getPurse() + 1);
      System.out.println(player
              + " now has "
              + player.getPurse()
              + " Gold Coins.");
   }

   private void askQuestion() {
      int currentPlayerPosition = players.get(currentPlayer).getPlace();
      Category currentCategory = valueOfLabel(currentPlayerPosition%4);
      System.out.println(players.get(currentPlayer)
              + "'s new location is "
              + players.get(currentPlayer).getPlace());
      System.out.println("The category is " + currentCategory);
      questionRepo.askQuestion(currentCategory);
   }

   private void movePlayer(int roll) {
      Player currentPlayer = players.get(this.currentPlayer);
      currentPlayer.setPlace((currentPlayer.getPlace() + roll) % 12);
   }

   private void nextPlayer() {
      currentPlayer = (currentPlayer+1) % players.size();
   }

   private boolean didPlayerWin() {
      return !(players.get(currentPlayer).getPurse() == 6);
   }

}
