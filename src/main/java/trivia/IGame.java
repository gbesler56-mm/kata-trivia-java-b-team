package trivia;

public interface IGame {

	boolean addPlayerToGame(String playerName);

	void roll(int roll);

	boolean wasCorrectlyAnswered();

	boolean wrongAnswer();

}