import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ReversiBoardController Class.
 */
public class ReversiBoardController implements Initializable {
    @FXML
    private HBox root;
    private Cell[][] matrix = {
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}};

    /**
     * initialize(URL location, ResourceBundle resources).
     *
     * @param location URL -- url;
     * @param resources ResourceBundle -- resources.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Local Variables
        Board board = new Board(this.matrix);
        board.setPrefHeight(400);
        board.setPrefWidth(400);
        root.getChildren().add(0, board);
        board.draw();
    }
}
