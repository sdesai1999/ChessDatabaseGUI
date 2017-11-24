import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChessGame {

    private StringProperty event = new SimpleStringProperty(this, "NA");
    private StringProperty site = new SimpleStringProperty(this, "NA");
    private StringProperty date = new SimpleStringProperty(this, "NA");
    private StringProperty white = new SimpleStringProperty(this, "NA");
    private StringProperty black = new SimpleStringProperty(this, "NA");
    private StringProperty result = new SimpleStringProperty(this, "NA");
    private StringProperty opening = new SimpleStringProperty(this, "NA");
    private List<String> moves;

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

    public void addMove(String move) {
        moves.add(move);
        checkOpening();
    }

    public String getMove(int n) {
        return moves.get(n - 1);
    }

    public String getEvent() {
        return event.get();
    }

    public String getSite() {
        return site.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getWhite() {
        return white.get();
    }

    public String getBlack() {
        return black.get();
    }

    public String getResult() {
        return result.get();
    }

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












