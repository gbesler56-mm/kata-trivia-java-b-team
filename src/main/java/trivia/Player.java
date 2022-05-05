package trivia;

public class Player {

    private String playerName;
    int place;
    int purse;
    boolean inPenaltyBox;

    public Player(String playerName) {
        this.playerName = playerName;

    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }



    public void setPlace(int place) {
        this.place = place;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    @Override
    public String toString() {
        return playerName;
    }
}
