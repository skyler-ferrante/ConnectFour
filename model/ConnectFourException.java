package model;

/**
 * A custom exception that is thrown from the ConnectFour model
 */
public class ConnectFourException extends Exception {
    /**
     * Create a new ConnectFourException with an error message.
     * 
     * @param message Error message of thrown exception.
     */
    public ConnectFourException(String message){
        super(message);
    }
}
