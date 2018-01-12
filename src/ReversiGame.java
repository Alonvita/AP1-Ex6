import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ReversiGame Class.
 */
public class ReversiGame {
    //---------- LOCAL CLASS VARIABLES ----------
    private Board board;
    private TurnsManager manager;

    //---------- INITIALIZER ----------

    /**
     * ReversiGame(Board b).
     *
     * @param b Board -- a game board.
     */
    public ReversiGame(Board b) {
        this.board = b;
        this.manager = new TurnsManager(this.board);
    }

    //---------- GETTERS ----------

    /**
     * getBoard().
     *
     * @return the board played by this game.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * start().
     */
    public void start() {

        board.draw();
        /*while (this.board.getSpaceLeft() > 0) {
            // play a move
            this.board.moveMade(this.manager.nextMove(), this.manager.getCurrentPlayerColor());
            // end turn
            manager.endTurn();
        }
        endGame();*/
    }

    /**
     * endGame().
     */
    private void endGame() {
        System.out.println(calculateWinner());
    }

    /**
     * calculateWinner().
     *
     * @return "W" if white player is the winner or "B" if black is.
     */
    private String calculateWinner() {
        int whiteScore = 0;
        int blackScore = 0;

        for(int i = 0; i < board.getSize(); ++i) {
            for (int j = 0; j < board.getSize(); ++j) {
                if(board.getCellColor(i, j) == Cell.WHITE) {
                    ++whiteScore;
                    continue;
                }
                ++blackScore;
            }
        }

        String winner = whiteScore > blackScore ? "W" : "B";
        try {
            Files.write(Paths.get("output.txt"), winner.getBytes());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println(e.getMessage());
        }

        return winner;
    }
}
