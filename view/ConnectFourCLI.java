package view;

import java.util.Scanner;

import model.Checker;
import model.ConnectFour;
import model.ConnectFourException;

/**
 * CLI version of ConnectFour
 */
public class ConnectFourCLI {
    private final static String BARRIER = " | ";
    /**
     * END_OF_LINE will be repeated WIDTH times before use
     */
    private static String END_OF_LINE = "-----";

    /**
     * Print the board to the terminal
     */
    private static void showBoard(ConnectFour model){
        StringBuilder result = new StringBuilder();

        for(int y = model.HEIGHT-1; y>=0; y--){
            for(int x = 0; x<model.WIDTH; x++){
                Checker checker = model.getChecker(x, y);
                result.append(checker);
                result.append(BARRIER);
            }

            result.append("\n");
            result.append(END_OF_LINE);
            result.append("\n");
        }

        System.out.println(result);
    }

    private static int getInt(Scanner s){
        while(!s.hasNextInt()){
            s.next();
        }
        return s.nextInt();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to ConnectFourCLI!");

        ConnectFour model = new ConnectFour();
        Scanner scanner = new Scanner(System.in);
        END_OF_LINE = END_OF_LINE.repeat(model.WIDTH-1);

        while(true){
            Checker player = model.getCurrentPlayer();
            System.out.print(player+" enter move: ");

            int x = getInt(scanner);

            try {
                model.move(x, player);
                showBoard(model);
            } catch (ConnectFourException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
