import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Board Class.
 */
public class Board extends GridPane {
    //---------- LOCAL CLASS VARIABLES ----------
    private int size;
    private int cellsInUse;
    private Cell[][] board;
    //private MovesObserver movesObserver;
    private CellMatrixGenerator matrixGenerator;
    private List<Position> occupiedCells;

    //---------- INITIALIZERS ----------

    /***
     * Board(Cell[][] board).
     *
     * @param type int -- the board size.
     */
    public Board(int type) {
        initalizeSize(type);
        this.cellsInUse = 0;
        this.matrixGenerator = new CellMatrixGenerator();

        // try to initialize board
        try {
            this.board = matrixGenerator.generateMatrix(this.size);
            this.occupiedCells = initializeOccupiedCells();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        initializeFxml();
    }

    /**
     * Board(Cell[][] matrix).
     *
     * @param matrix Cell[][] -- a cell matrix
     */
    public Board(Cell[][] matrix) {
        this.size = matrix.length;
        this.board = matrix;
        this.matrixGenerator = null;
        this.occupiedCells = initializeOccupiedCells();

        initializeFxml();
    }

    /**
     * initializeFxml().
     */
    public void initializeFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Board.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        // try to load fxml loader
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    //---------- GETTERS ----------

    /**
     * getSize().
     *
     * @return the size of the board.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * getBoard().
     *
     * @return a copy of this game board.
     */
    public Board getBoard() {
        // Local Variables
        Cell[][] cellMatrix = new Cell[size][size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cellMatrix[i][j] = this.board[i][j];
            }
        }

        return new Board(cellMatrix);
    }

    /**
     * getSpaceLeft().
     *
     * @return the number of empty cells on board.
     */
    public int getSpaceLeft() {
        return ((int) Math.pow(this.size, 2)) - this.cellsInUse;
    }

    /**
     * getCellPotentialAsList(Position p, Cell c).
     *
     * @param p Position -- a position on board.
     * @param c Cell -- player's color.
     * @return a list of all cells that are potentially effected in the next move.
     */
    public List<Position> getCellPotentialAsList(Position p, Cell c) {
        // Local Variables
        List<Position> potentialList = new ArrayList<>();
        int dRow, dCol;

        for (dRow = -1; dRow <= 1; ++dRow) {
            for (dCol = -1; dCol <= 1; ++dCol) {
                // get potential in path
                List<Position> potentialInPath = findPotentialInPath(p, dRow, dCol, c);

                // found potential in path
                if (!potentialInPath.isEmpty()) {
                    // add it to potential list
                    potentialList.addAll(potentialInPath);
                }
            }
        }

        return potentialList;
    }

    //---------- PUBLIC FUNCTIONS ----------

    /**
     * draw().
     */
    public void draw() {
        this.getChildren().clear();

        int height = (int) this.getPrefHeight();
        int width = (int) this.getPrefWidth();

        int cellHeight = height / board.length;
        int cellWidth = width / board.length;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Local variables
                double circleCenterOffset = cellHeight / 2;
                double radius = cellHeight / 2;

                // create the empty cell rectangle
                this.add(new Rectangle(cellWidth, cellHeight, Color.WHITE), j, i);

                // white player's cell - add a circle
                if (board[i][j] == Cell.WHITE) {
                    this.add(
                            new Circle(
                                    i + circleCenterOffset,
                                    j - circleCenterOffset,
                                    radius,
                                    Color.WHITE),
                            j, i);
                }

                // black player's cell - add a circle
                if (board[i][j] == Cell.BLACK) {
                    this.add(
                            new Circle(
                                    i + circleCenterOffset,
                                    j - circleCenterOffset,
                                    radius,
                                    Color.BLACK),
                            j, i);
                }
            }
        }
    }

    /**
     * moveMade(Position p, Cell value).
     *
     * @param p     Position -- a position made.
     * @param value Cell -- move's color.
     */
    public void moveMade(Position p, Cell value) {
        // Local Variables
        int row = p.getRow();
        int col = p.getCol();

        // cell out of bounds -- player had no moves
        if (!cellOnBoard(row, col))
            return;

        List<Position> cellsEffected = getCellsPotentialAsList(p, value);

        // clear duplicates
        Set<Position> hs = new HashSet<>();
        hs.addAll(cellsEffected);
        cellsEffected.clear();
        cellsEffected.addAll(hs);

        // change cell's value
        this.board[row][col] = value;

        // update occupied cells
        this.occupiedCells.add(p);
        cellsInUse++;

        // change effected cells colors
        for (Position effectedCell : cellsEffected) {
            // change the cell color
            changeCellColor(effectedCell);
        }
    }

    /**
     * getCellColor(int row, int col).
     *
     * @param row int -- row.
     * @param col int -- col.
     * @return the color of the board at (row,col).
     */
    public Cell getCellColor(int row, int col) {
        if (this.board[row][col] == Cell.WHITE) {
            return Cell.WHITE;
        }

        if (this.board[row][col] == Cell.BLACK) {
            return Cell.BLACK;
        }

        return Cell.EMPTY;
    }

    /**
     * getCellColor(Position p).
     *
     * @param p Position -- a position
     * @return the color of the board at (row,col).
     */
    public Cell getCellColor(Position p) {
        return getCellColor(p.getRow(), p.getCol());
    }

    /**
     * getOccupiedCells().
     *
     * @return a copied list of the cells occupied on this board.
     */
    public List<Position> getOccupiedCells() {
        List<Position> cellsInUse = new ArrayList<>();
        cellsInUse.addAll(this.occupiedCells);

        return cellsInUse;
    }

    /**
     * getEmptyNeighbors(Position p).
     *
     * @param p Position -- a position.
     * @return a list of empty neighbors (as positions) around a given position.
     */
    public List<Position> getEmptyNeighbors(Position p) {
        // Local Variables
        List<Position> emptyNeighborsList = new ArrayList<>();
        int row = p.getRow();
        int col = p.getCol();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                // inner loop variables
                int dRow = row + i;
                int dCol = col + j;

                if (cellOnBoard(dRow, dCol)) {
                    // cell on board -> get it's color
                    Cell c = this.board[dRow][dCol];

                    if (c == Cell.EMPTY) {
                        // empty val -> push cell's index to the list
                        emptyNeighborsList.add(new Position(dRow, dCol));
                    }
                }
            }
        }

        return emptyNeighborsList;
    }

    //---------- PRIVATE FUNCTIONS ----------

    /**
     * initializeSize(int type).
     *
     * @param type int -- the board type
     */
    private void initalizeSize(int type) {
        if (type == 1) {
            this.size = 6;
            return;
        }

        if (type == 3) {
            this.size = 10;
            return;
        }

        this.size = 8;
    }

    /**
     * cellPotential(Position p, Cell color)
     *
     * @param p     Position -- a position on board.
     * @param color Cell -- cell's color
     * @return the cell potential as a List<Position>
     */
    public List<Position> cellPotential(Position p, Cell color) {
        return getCellsPotentialAsList(p, color);
    }

    /**
     * getCellsPotentialAsList(Position p, Cell value).
     *
     * @param p     Position -- a position on board.
     * @param color Cell -- player color.
     * @return a list of positions, representing the cell potential.
     */
    private List<Position> getCellsPotentialAsList(Position p, Cell color) {
        // Local Variables
        List<Position> potentialList = new ArrayList<>();
        int dRow, dCol;

        for (dRow = -1; dRow <= 1; ++dRow) {
            for (dCol = -1; dCol <= 1; ++dCol) {
                List<Position> potentialInPath = findPotentialInPath(p, dRow, dCol, color);

                if (!potentialInPath.isEmpty()) {
                    // add potential
                    potentialList.addAll(potentialInPath);
                }
            }
        }
        return potentialList;
    }

    /**
     * findPotentialInPath(Position p, int dRow, int dCol, Cell color).
     *
     * @param p     Position -- a position on board.
     * @param dRow  int -- row delta.
     * @param dCol  int -- column delta.
     * @param color Cell -- cell color.
     * @return a list of potential cells to effect in a given row/col delta.
     */
    private List<Position> findPotentialInPath(Position p, int dRow, int dCol, Cell color) {
        // Local Variables
        List<Position> potentialInPath = new ArrayList<>();
        int row = p.getRow() + dRow;
        int col = p.getCol() + dCol;
        int counter = 0;

        // delta is 0 - no need to check anything
        if (dRow == 0 && dCol == 0)
            return potentialInPath;

        for (; cellOnBoard(row, col); row += dRow, col += dCol) {
            // hit an empty cell
            if (board[row][col] == Cell.EMPTY) {
                // clear potential
                potentialInPath.clear();
                return potentialInPath;
            }

            // player type is found -> return
            if (board[row][col] == color) {
                if (counter == 0)
                    potentialInPath.clear();
                return potentialInPath;
            }

            // getting so far means that this cell is an opponent cell -> add it!
            Position opponentCell = new Position(row, col);
            potentialInPath.add(opponentCell);
            ++counter;
        }

        if (!cellOnBoard(row, col))
            potentialInPath.clear();

        return potentialInPath;
    }

    /**
     * changeCellColor(Position p).
     *
     * @param p Position -- a position on the board.
     */
    private void changeCellColor(Position p) {
        // Local Variables
        Cell color = this.board[p.getRow()][p.getCol()];

        // change color
        if (color == Cell.BLACK) {
            this.board[p.getRow()][p.getCol()] = Cell.WHITE;
        } else {
            this.board[p.getRow()][p.getCol()] = Cell.BLACK;
        }
    }

    /**
     * cellOnBoard(int row, int col).
     *
     * @param row int -- row.
     * @param col int -- col.
     * @return true if cell is on board, or false otherwise.
     */
    private boolean cellOnBoard(int row, int col) {
        return (0 <= row && row < this.size && 0 <= col && col < this.size);
    }

    /**
     * initializeOccupiedCells().
     *
     * @return a list of occupied cells.
     */
    private List<Position> initializeOccupiedCells() {
        // Local Variables
        List<Position> occupiedCells = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Cell currentColor = board[i][j];
                if (currentColor != Cell.EMPTY) {
                    occupiedCells.add(new Position(i, j));
                    cellsInUse++;
                }
            }
        }
        return occupiedCells;
    }
}