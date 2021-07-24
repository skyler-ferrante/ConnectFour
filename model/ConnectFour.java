package model;

/**
 * ConnectFour model
 */
public class ConnectFour {
    public final int HEIGHT;
    public final int WIDTH;

    private Checker[][] board;
    private int[] numberOfMovesByRow;

    public ConnectFour(int height, int width){
        this.HEIGHT = height;
        this.WIDTH = width;

        board = new Checker[HEIGHT][WIDTH];
        numberOfMovesByRow = new int[WIDTH];
    }

    /**
     * Get checker at position x, y
     * 
     * @param x The x position of the checker
     * @param y The y position of the checker
     * @return Checker value at board[x][y]
     */
    public Checker getChecker(int x, int y) throws ConnectFourException{
        return board[x][y];
    }

    /**
     * Places checker in given row
     * @param x The x position of the checker to place
     * @param value Value of checker to place
     * @throws ConnectFourException If the row is already full, or if the row is out of bounds
     */
    public void placeChecker(int x, Checker value) throws ConnectFourException{
        if(x < 0 || x>=WIDTH){
            throw new ConnectFourException("Row "+x+" does not exist");
        }
        else if(numberOfMovesByRow[x] == HEIGHT){
            throw new ConnectFourException("Row "+x+" is already full!");
        }else{
            int y = numberOfMovesByRow[x];
            board[x][y] = value;
            numberOfMovesByRow[x] = y + 1;
        }
    }
}