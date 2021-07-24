package model;

/**
 * ConnectFour model
 */
public class ConnectFour{
    public final int HEIGHT;
    public final int WIDTH;

    private Checker[][] board;
    private int[] numberOfMovesByRow;

    private Checker currentPlayer;

    public ConnectFour(int height, int width){
        this.HEIGHT = height;
        this.WIDTH = width;

        board = new Checker[WIDTH][HEIGHT];
        numberOfMovesByRow = new int[WIDTH];

        currentPlayer = Checker.RED;

        reset();
    }

    public ConnectFour(){
        //Default game has height 6, width 7
        this(6, 7);
    }

    /**
     * Get checker at position x, y
     * 
     * @param x The x position of the checker
     * @param y The y position of the checker
     * @return Checker value at board[x][y]
     */
    public Checker getChecker(int x, int y){
        return board[x][y];
    }

    /**
     * Places checker in given row
     * @param x The x position of the checker to place
     * @param value Value of checker to place
     * @throws ConnectFourException If the row is already full, or if the row is out of bounds
     */
    public void move(int x, Checker value) throws ConnectFourException{
        //Check bounds
        if(x < 0 || x>=WIDTH){
            throw new ConnectFourException("Row "+x+" does not exist");
        }
        //Check if row is full
        else if(numberOfMovesByRow[x] == HEIGHT){
            throw new ConnectFourException("Row "+x+" is already full!");
        }

        //Make move
        int y = numberOfMovesByRow[x];
        board[x][y] = value;
        numberOfMovesByRow[x] = y+1;

        //Switch current player
        currentPlayer = (currentPlayer == Checker.RED) ? Checker.BLACK : Checker.RED;
    }

    /**
     * Get the current players turn
     * @return Current Players checker color
     */
    public Checker getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Reset board to empty
     */
    public void reset(){
        for(int x = 0; x < board.length; x++){
            for(int y = 0; y < board[0].length; y++){
                board[x][y] = Checker.EMPTY;
            }
        }
    }
}