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
        System.out.println("Enter a board size: ");
        System.out.println("\t 1. 6x6");
        System.out.println("\t 2. 8x8");
        System.out.println("\t 2. 10x10");
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
