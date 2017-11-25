import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a game of chess, containing metadata about the game and the moves
 * made in the game.
 *
 * @author cs1331 instructor
 * @author sdesai88
 * @version 11/25/17
*/
public class ChessGame {

    private StringProperty event = new SimpleStringProperty(this, "NA");
    private StringProperty site = new SimpleStringProperty(this, "NA");
    private StringProperty date = new SimpleStringProperty(this, "NA");
    private StringProperty white = new SimpleStringProperty(this, "NA");
    private StringProperty black = new SimpleStringProperty(this, "NA");
    private StringProperty result = new SimpleStringProperty(this, "NA");
    private StringProperty opening = new SimpleStringProperty(this, "NA");
    private List<String> moves;

    /**
     * Creates an instance of ChessGame with information about the game.
     * @param event : String containing the name of the event
     * @param site : String containing the name of the site of the game
     * @param date : String containing the date the game was played on
     * @param white : String containing the name of the white player
     * @param black : String containing the name of the black player
     * @param result : String containing the result of the game
    */
    public ChessGame(String event, String site, String date,
                     String white, String black, String result) {
        this.event.set(event);
        this.site.set(site);
        this.date.set(date);
        this.white.set(white);
        this.black.set(black);
        this.result.set(result);
        this.opening.set("Not Recognized");
        moves = new ArrayList<>();
    }

    /**
     * Adds a move to the ChessGame
     *
     * @param move : String containing the move
    */
    public void addMove(String move) {
        moves.add(move);
        checkOpening();
    }

    /**
     * Gets the move one index before the specified index
     *
     * @param n : the specified index
     * @return the move
    */
    public String getMove(int n) {
        return moves.get(n - 1);
    }

    /**
     * @return the event
    */
    public String getEvent() {
        return event.get();
    }

    /**
     * @return the site
    */
    public String getSite() {
        return site.get();
    }

    /**
     * @return the date
    */
    public String getDate() {
        return date.get();
    }

    /**
     * @return the white player
    */
    public String getWhite() {
        return white.get();
    }

    /**
     * @return the black player
    */
    public String getBlack() {
        return black.get();
    }

    /**
     * @return the result of the game
    */
    public String getResult() {
        return result.get();
    }

    /**
     * @return the opening to the game
    */
    public String getOpening() {
        return opening.get();
    }

    private void checkOpening() {
        if (moves.size() >= 1) {
            if (moves.get(0).equals("e4 c5")) {
                this.opening.set("Sicilian Defence");
            } else if (moves.get(0).equals("d4 Nf6")) {
                this.opening.set("Indian Defence");
            }
        }

        if (moves.size() >= 2) {
            if (moves.get(0).equals("d4 d5") && moves.get(1).contains("c4")) {
                this.opening.set("Queen's Gambit");
            } else if (moves.get(0).equals("e4 e5")
                && moves.get(1).equals("Nf3 d6")) {
                this.opening.set("Philidor Defence");
            }
        }

        if (moves.size() >= 3) {
            if (moves.get(0).equals("e4 e5")
                && moves.get(1).equals("Nf3 Nc6")
                && moves.get(2).contains("Bb5")) {
                this.opening.set("Ruy Lopez");
            } else if (moves.get(0).equals("e4 e5")
                && moves.get(1).equals("Nf3 Nc6")
                && moves.get(2).equals("Bc4 Bc5")) {
                this.opening.set("Giuoco Piano");
            }
        }
    }
}