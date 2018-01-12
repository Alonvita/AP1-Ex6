/**
 * Name:    Alon Vita
 * ID:      311233431
 * Github:  https://github.com/Alonvita
 */

import java.util.Scanner;

/**
 * java_ex2 Class -- Main.
 */
public class java_ex2 {
    public static void main(String[] args) {
        // parse file into a map
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        Board board;

        try {
            board = new Board(size);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // create a game object
        ReversiGame g = new ReversiGame(board);

        // start game
        g.start();
    }
}
