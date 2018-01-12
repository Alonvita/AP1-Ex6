import java.util.Scanner;

/**
 * HumanPlayer Class.
 */
public class HumanPlayer implements Player {
    //---------- LOCAL CLASS VARIABLES ----------
    private boolean color;

    //---------- INITIALIZER ----------

    /**
     * HumanPlayer(Board b, Cell color).
     *
     * @param color Cell - BLACK or WHITE.
     */
    HumanPlayer(Cell color) {
        if (color == Cell.WHITE)
            this.color = true;

        this.color = false;
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * move().
     */
    public Position move() {
        // Local Variables
        Scanner scanner = new Scanner(System.in);
        String moveAsRawString = scanner.next();
        int row;
        int col;


        // bad argument
        if (moveAsRawString.length() > 3)
            return new Position(-1, -1);

        row = Character.getNumericValue(moveAsRawString.charAt(0));
        col = Character.getNumericValue(moveAsRawString.charAt(2));

        return new Position(row, col);
    }
}