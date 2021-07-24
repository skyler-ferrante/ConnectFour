package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Checker;
import model.ConnectFour;
import model.ConnectFourException;

/**
 * CLI version of ConnectFour
 */
public class ConnectFourCLI {
    //END_OF_LINE will be repeated WIDTH times before use
    private static String END_OF_LINE = "-";
    private final static String BARRIER = " | ";

    /**
     * Print the board to the terminal
     * Uses END_OF_LINE, BARRIER, and toString of Checker enum
     */
    private static void showBoard(ConnectFour model){
        StringBuilder result = new StringBuilder();

        //Start from top left, and go to bottom right
        for(int y = model.HEIGHT-1; y>=0; y--){
            for(int x = 0; x<model.WIDTH; x++){
                Checker checker = model.getChecker(x, y);
                result.append(checker);
                result.append(BARRIER);
            }

            //Line seperator
            result.append("\n");
            result.append(END_OF_LINE);
            result.append("\n");
        }

        System.out.println(result);
    }

    /**
     * Get int from scanner
     * @param s Scanner to get int from
     * @return int from scanner
     */
    private static int getInt(Scanner s){
        while(!s.hasNextInt()){
            s.next();
        }
        return s.nextInt();
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to ConnectFourCLI!");

        ConnectFour model = new ConnectFour();
        // Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(new File("no_win_move_list.txt"));
        
        //Make END_OF_LINE the size of one line
        int lineLength = Checker.EMPTY.toString().length()+BARRIER.length();
        END_OF_LINE = END_OF_LINE.repeat(lineLength*model.WIDTH);

        while(! model.isGameOver()){
            Checker player = model.getCurrentPlayer();
            System.out.println("Move number "+model.getTotalMoves());
            System.out.print(player.name()+" enter move: ");

            int x = getInt(scanner);

            try {
                model.move(x, player);
                showBoard(model);
            } catch (ConnectFourException e) {
                System.out.println(e.getMessage());
            }
        }

        Checker winner = model.getCurrentPlayer();
        if(winner == Checker.EMPTY){
            System.out.println("Board filled, and no one wins!");
        }
        else{
            System.out.println(winner.name()+" wins!");
        }
        System.out.println("Game Over!");
    }
}
