import java.util.List;

/**
 * TurnsManager Class.
 */
public class TurnsManager {
    //---------- LOCAL CLASS VARIABLES ----------
    private Board board;
    private Player[] players;
    private boolean playerTurn;
    private MovesEvaluator movesEvaluator;
    private List<Position> availableMoves;

    //---------- INITIALIZER ----------
    public TurnsManager(Board b) {
        this.board = b;
        this.movesEvaluator = new MovesEvaluator();
        this.playerTurn = evaluateColor(Cell.WHITE);

        // initialize players and available moves
        initializePlayers();
        evaluateAvailableMovesForThisTurn();
    }

    //---------- GETTERS ----------

    /**
     * getCurrentPlayerColor().
     *
     * @return current player color.
     */
    public Cell getCurrentPlayerColor() {
        return playerTurnToCellType();
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * endTurn().
     */
    public void endTurn() {
        this.playerTurn = !this.playerTurn;
        evaluateAvailableMovesForThisTurn();
    }

    /**
     * nextMove().
     *
     * @return the position of the next move.
     */
    public Position nextMove() {
        // Local Variables
        Position playerMove;
        int nextToPlay = playerTurn ? 1 : 0;

        do {
            playerMove = (this.players)[nextToPlay].move();
        } while (!moveIsLegal(playerMove));

        return (playerMove);
    }

    //---------- PRIVATE FUNCTIONS ----------

    /**
     * evaluateAvailableMovesForThisTurn().
     */
    private void evaluateAvailableMovesForThisTurn() {
        // Local Variables
        Cell playerTurn = playerTurnToCellType();

        this.availableMoves.clear();
        this.availableMoves = this.movesEvaluator.calculateAvailableMoves(board, playerTurn);
    }

    /**
     * moveIsLegal(Position move).
     *
     * @param move Position -- a move.
     * @return true if the move is one of the available moves for this turn.
     */
    private boolean moveIsLegal(Position move) {
        // check move within boundaries. Basically this is redundant, might save some calculations...
        if (move.getRow() < 0)
            return false;
        if (move.getCol() < 0)
            return false;
        if (move.getRow() > board.getSize())
            return false;
        if (move.getCol() > board.getSize())
            return false;

        // check move is an available move
        for(Position availableMove : this.availableMoves) {
            if (move.getRow() == availableMove.getRow()) {
                if (move.getCol() == availableMove.getCol()) { return true; }
            }
        }

        // the move is within board borders yet is NOT an available move.
        return false;
    }

    /**
     * evaluateColor(Heuristic heuristic).
     *
     * @param color Cell -- a player color.
     * @return player color as an integer: 0 for white or 1 for black.
     */
    private boolean evaluateColor(Cell color) {
        if (color == Cell.WHITE) {
            return true;
        }
        return false;
    }

    /**
     * initializePlayers().
     */
    private void initializePlayers() {
        // Local Variables
        this.players = new Player[2];

        // initialize players
        this.players[1] = new HumanPlayer(Cell.WHITE);
        this.players[0] = new HumanPlayer(Cell.BLACK);
    }

    /**
     * playerTurnToCellType().
     *
     * @return the player color.
     */
    private Cell playerTurnToCellType() {
        if (this.playerTurn)
            return Cell.WHITE;
        return Cell.BLACK;
    }
}