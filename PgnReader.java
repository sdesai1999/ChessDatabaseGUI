import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PgnReader {

    private String game;
    private String event;
    private String site;
    private String date;
    private String white;
    private String black;
    private String result;
    private String[] moves;

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

    public String fileContent(String path) {
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
        game = sb.toString();
        return game;
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
            return new String[0];
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

        String[] moves = new String[0];
        if (moves1.length % 2 == 0) {
            moves = new String[moves1.length / 2];
        } else {
            moves = new String[(moves1.length / 2) + 1];
        }

        int movesCounter = 0;
        for (int i = 0; i < moves1.length; i += 2) {
            if (i != moves1.length - 1) {
                moves[movesCounter] = moves1[i] + " " + moves1[i + 1];
                movesCounter++;
            } else {
                moves[movesCounter] = moves1[i];
                movesCounter++;
            }
        }

        return moves;
    }

    public String getEvent() {
        return this.event;
    }

    public String getSite() {
        return this.site;
    }

    public String getDate() {
        return this.date;
    }

    public String getWhite() {
        return this.white;
    }

    public String getBlack() {
        return this.black;
    }

    public String getResult() {
        return this.result;
    }

    public String[] getMoves() {
        return this.moves;
    }
}