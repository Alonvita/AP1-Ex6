import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovesEvaluator {

    /**
     * MovesEvaluator().
     */
    MovesEvaluator() {}

    /**
     * calculateAvailableMoves(Board gameBoard, Cell playerColor).
     *
     * @param gameBoard   Board -- a game board
     * @param playerColor Cell -- a player color
     * @return a list of available moves for this player.
     */
    public List<Position> calculateAvailableMoves(Board gameBoard, Cell playerColor) {
        List<Position> cellsInUse = gameBoard.getOccupiedCells();
        List<Position> availableMoves = new ArrayList<>();

        for (Position p : cellsInUse) {
            Cell currentCell = gameBoard.getCellColor(p.getRow(), p.getCol());

            if (playerColor != currentCell) {
                // Get empty neighbors for current position
                List<Position> emptyNeighbors = gameBoard.getEmptyNeighbors(p);

                for(Position neighbor : emptyNeighbors) {
                    List<Position> neighborPotential = gameBoard.getCellPotentialAsList(neighbor, playerColor);

                    if(!neighborPotential.isEmpty()) {
                        availableMoves.add(neighbor);
                    }
                }

            }
        }

        // clear duplicates
        Set<Position> posSet = new HashSet<>();
        posSet.addAll(availableMoves);
        availableMoves.clear();
        availableMoves.addAll(posSet);

        return availableMoves;
    }
}