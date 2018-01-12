/**
 * CellMatrixGenerator Class.
 */
public class CellMatrixGenerator {
    //---------- INITIALIZER ----------
    /**
     * ParseResult ParseBoard(int size) throws Exception.
     *
     * @param size int -- the size of the Cell matrix to generate.
     *
     * @return a Cell matrix.
     * @throws Exception for invalid sizes.
     */
    public Cell[][] generateMatrix(int size) throws Exception {
        // Local variables
        if (!(size == 6 || size == 8 || size == 10)) {
            throw new Exception("Bad matrix size argument received");
        }

        // initialize a new Cells matrix
        Cell[][] matrix = new Cell[size][size];

        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                matrix[row][col] = Cell.EMPTY;
            }
        }

        // initialize starting cells
        matrix[size/2][size/2] = Cell.WHITE;
        matrix[size/2 - 1][size/2] = Cell.BLACK;
        matrix[size/2][size/2 - 1] = Cell.BLACK;
        matrix[size/2 - 1][size/2 - 1] = Cell.WHITE;

        return matrix;
    }
}
