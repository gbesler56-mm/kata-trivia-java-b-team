package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// REFACTOR ME
public class GameBetter implements IGame {

    public static final int NUMBER_OF_CARDS = 50;

    public static final int NUMBER_OF_CELLS = 12;

    private final List<Player> players = new ArrayList<>();
    private final int[] purses = new int[6];
    private final List<String> popQuestions = new LinkedList<>();
    private final List<String> scienceQuestions = new LinkedList<>();
    private final List<String> sportsQuestions = new LinkedList<>();
    private final List<String> rockQuestions = new LinkedList<>();
    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;

    public GameBetter() {
        for (int i = 0; i < NUMBER_OF_CARDS; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add("Science Question " + i);
            sportsQuestions.add("Sports Question " + i);
            rockQuestions.add("Rock Question " + i);
        }
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        purses[howManyPlayers()] = 0;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(currentPlayer() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer().isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(currentPlayer() + " is getting out of the penalty box");

                regularRoll(roll);
            } else {
                System.out.println(currentPlayer() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            regularRoll(roll);
        }
    }

    private void regularRoll(int roll) {
        movePlayer(roll);
        System.out.println(currentPlayer() + "'s new location is " + currentPlayer().getPlace());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void movePlayer(int roll) {
        currentPlayer().roll(roll);

//        players.get(currentPlayer).getPlace() += roll;
//        if (players.get(currentPlayer).getPlace() >= NUMBER_OF_CELLS) {
//            players.get(currentPlayer).getPlace() -= NUMBER_OF_CELLS;
//        }
    }

    private void askQuestion() {
        if (currentCategory().equals("Pop")) {
            System.out.println(popQuestions.remove(0));
        }
        if (currentCategory().equals("Science")) {
            System.out.println(scienceQuestions.remove(0));
        }
        if (currentCategory().equals("Sports")) {
            System.out.println(sportsQuestions.remove(0));
        }
        if (currentCategory().equals("Rock")) {
            System.out.println(rockQuestions.remove(0));
        }
    }

    private String currentCategory() {
        if (currentPlayer().getPlace() == 0) return "Pop";
        if (currentPlayer().getPlace() == 4) return "Pop";
        if (currentPlayer().getPlace() == 8) return "Pop";
        if (currentPlayer().getPlace() == 1) return "Science";
        if (currentPlayer().getPlace() == 5) return "Science";
        if (currentPlayer().getPlace() == 9) return "Science";
        if (currentPlayer().getPlace() == 2) return "Sports";
        if (currentPlayer().getPlace() == 6) return "Sports";
        if (currentPlayer().getPlace() == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer().isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayerIndex]++;
                currentPlayer().addCoin();
                System.out.println(currentPlayer()
                                   + " now has "
                                   + purses[currentPlayerIndex]
                                   + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

                return winner;
            } else {
                currentPlayerIndex++;
                if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayerIndex]++;
            currentPlayer().addCoin();
            System.out.println(currentPlayer()
                               + " now has "
                               + purses[currentPlayerIndex]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayerIndex++;
            if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;

            return winner;
        }
    }

    private Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer() + " was sent to the penalty box");
        currentPlayer().setInPenaltyBox(true);

        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayerIndex] == 6);
    }
}
