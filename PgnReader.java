import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class that helps with reading .pgn files, finds metadata about the game, and
 * finds the moves of the game
 *
 * @author sdesai88
 * @version 12/01/17
*/
public class PgnReader {

    private String game;
    private String event;
    private String site;
    private String date;
    private String white;
    private String black;
    private String result;
    private String[] moves;

    /**
     * Constructor for PgnReader class; creates an instance of PgnReader with a
     * String containing the .pgn file name.
     *
     * @param file : String with the name of the file
    */
    public PgnReader(String file) {
        this.game = fileContent(file);
        this.event = tagValue("Event");
        this.site = tagValue("Site");
        this.date = tagValue("Date");
        this.white = tagValue("White");
        this.black = tagValue("Black");
        this.result = tagValue("Result");
        this.moves = parseFileForMoves();
    }

    private String fileContent(String path) {
        // NOTE: this method was not written by me, it was provided on the hw1
        // website, probably written by Prof. Simpkins
        Path file = Paths.get(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Add the \n that's removed by readline()
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            System.exit(1);
        }

        return sb.toString();
    }

    private String tagValue(String tagName) {
        if (!(game.contains(tagName))) {
            return "N/A";
        }
        int beginningOfLine = game.indexOf(tagName) - 1;
        int endOfLine = game.indexOf("]", beginningOfLine) + 1;
        String eventLine = game.substring(beginningOfLine, endOfLine);
        String[] splitUpTag = eventLine.split("\"");
        return splitUpTag[1];
    }

    private String[] parseFileForMoves() {
        boolean gameExists = true;
        int tempStartIndex = game.lastIndexOf("]");
        int gameStartIndex = -1;
        String tmpMovesOnly = "", gameMovesOnly = "";
        if (tempStartIndex == -1) {
            gameStartIndex = game.indexOf("1.");
            if (gameStartIndex == -1) {
                gameExists = false;
            } else {
                gameMovesOnly = game.substring(gameStartIndex);
            }
        } else {
            tmpMovesOnly = game.substring(tempStartIndex);
            int first1Index = tmpMovesOnly.indexOf("1.");
            if (first1Index == -1) {
                gameExists = false;
            } else {
                gameMovesOnly = tmpMovesOnly.substring(first1Index);
            }
        }

        if (!gameExists) {
            return new String[0]; // if there is no first move
        }

        String[] tmpMoves = gameMovesOnly.split("\\s+");
        int newLength = 0;
        for (int i = 0; i < tmpMoves.length; i++) {
            if (i % 3 != 0) {
                newLength++;
            }
        }

        String[] moves1 = new String[newLength];
        int moves1Counter = 0;
        for (int i = 0; i < tmpMoves.length; i++) {
            if (i % 3 != 0) {
                moves1[moves1Counter] = tmpMoves[i];
                moves1Counter++;
            }
        }

        String[] realMoves = new String[0];
        if (moves1.length % 2 == 0) {
            realMoves = new String[moves1.length / 2];
        } else {
            realMoves = new String[(moves1.length / 2) + 1];
        }

        int movesCounter = 0;
        for (int i = 0; i < moves1.length; i += 2) {
            if (i != moves1.length - 1) {
                realMoves[movesCounter] = moves1[i] + " " + moves1[i + 1];
                movesCounter++;
            } else {
                realMoves[movesCounter] = moves1[i];
                movesCounter++;
            }
        }

        return realMoves;
    }

    /**
     * @return the event
    */
    public String getEvent() {
        return this.event;
    }

    /**
     * @return the site
    */
    public String getSite() {
        return this.site;
    }

    /**
     * @return the date
    */
    public String getDate() {
        return this.date;
    }

    /**
     * @return white player
    */
    public String getWhite() {
        return this.white;
    }

    /**
     * @return black player
    */
    public String getBlack() {
        return this.black;
    }

    /**
     * @return the result
    */
    public String getResult() {
        return this.result;
    }

    /**
     * @return the moves of the game
    */
    public String[] getMoves() {
        return this.moves;
    }
}