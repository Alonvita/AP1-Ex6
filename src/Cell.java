/**
 * Cell Class.
 */
public enum Cell {
    //---------- ENUM DEFINITIONS ----------
    BLACK("B"),
    WHITE("W"),
    EMPTY("E");

    //---------- LOCAL CLASS VARIABLES ----------
    private String cell;

    //---------- INITIALIZER ----------

    /**
     * Cell(String cell).
     *
     * @param cell string -- a string representing this cell.
     */
    Cell(String cell) {
        this.cell = cell;
    }

    /**
     * toString().
     *
     * @return the string representing the cell type.
     */
    @Override
    public String toString() {
        return cell;
    }
}
