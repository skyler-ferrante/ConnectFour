package model;

/**
 * ConnectFour model
 */
public class ConnectFour{
    public final int HEIGHT;
    public final int WIDTH;
    
    private Checker[][] board;
    private int[] numberOfMovesByRow;
    private int moveNumber;
    private boolean isGameOver;

    private Checker currentPlayer;

    public ConnectFour(int height, int width){
        this.HEIGHT = height;
        this.WIDTH = width;

        board = new Checker[WIDTH][HEIGHT];
        numberOfMovesByRow = new int[WIDTH];
        moveNumber = 1;

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
     * Get the current players turn
     * @return Current Players checker color
     */
    public Checker getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTotalMoves() {
        return moveNumber;
    }

    /**
     * Reset board to empty
     */
    public void reset(){
        isGameOver = false;
        
        for(int x = 0; x < board.length; x++){
            for(int y = 0; y < board[0].length; y++){
                board[x][y] = Checker.EMPTY;
            }
        }
    }

    /**
     * Check if the current player has 4 in a row in a direction from the last move.
     * 
     * @param x X position of last move
     * @param y Y poistion of last move
     * @param xAdder Number to add to X position in each iteration of loop (should be 1 or 0)
     * @param yAdder Number to add to Y position in each iteration of loop (should be 1 or 0)
     * @return True if 4 in a row is found, False if 4 in a row not found.
     */
    private boolean checkFourInARowInDirection(int x, int y, int xAdder, int yAdder){
        Checker currentPlayer = board[x][y];
        
        for(int i = 0; i<3; i++){
            x += xAdder;
            y += yAdder;
            
            if(x >= WIDTH || x < 0 || y >= HEIGHT || y < 0){
                return false;
            }

            if(board[x][y] != currentPlayer){
                return false;
            }
        }

        return true;
    }

    /**
     * Determine if the current move caused the current player to win.
     * Must be called before current player is changed.
     * 
     * @param x X position of last move
     * @param y Y position of last move
     * @return True if last move caused a 4 in a row
     * and false otherwise.
     */
    private boolean wasLastMoveWinning(int x, int y){     
        return checkFourInARowInDirection(x, y, 0, -1)   || //Check Down
               checkFourInARowInDirection(x, y, -1, 0)   || //Check Left
               checkFourInARowInDirection(x, y, 1, 0)    || //Check Right

               checkFourInARowInDirection(x, y, -1, -1)  || //Check DownLeft
               checkFourInARowInDirection(x, y, 1, -1)   || //Check DownRight

               checkFourInARowInDirection(x, y, -1, 1)   || //Check UpLeft 
               checkFourInARowInDirection(x, y, 1, 1);      //Check UpRight;
    }
    
    /**
     * Places checker in given row
     * @param x The x position of the checker to place
     * @param value Value of checker to place
     * @throws ConnectFourException If the row is already full, or if the row is out of bounds
     */
    public void move(int x, Checker value) throws ConnectFourException{
        if(isGameOver){
            throw new ConnectFourException("Game already over, please reset.");
        }

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
        moveNumber++;
        if(wasLastMoveWinning(x, y)){
            isGameOver = true;
            return;
        }

        //Switch current player
        currentPlayer = (currentPlayer == Checker.RED) ? Checker.BLACK : Checker.RED;
    }

    public boolean isGameOver(){
        return isGameOver;
    }
}