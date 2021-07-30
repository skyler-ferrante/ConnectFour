package model;

/**
 * Checker enum
 * 
 * Has a toString that lists a character representation,
 * and a colorToString that includes ansi color codes. 
 */
public enum Checker {
    EMPTY(" "),             //No ANSI color code
    RED("R", "\033[31m"),   //Red ANSI color code 
    BLACK("B", "\033[90m"); //Bright Black ANSI color code

    private static String blankColor = "\033[0m";

    private String color;
    private String representation;

    private Checker(String representation){
        this.representation = representation;
        color = "";
    }

    private Checker(String representation, String color){
        this.representation = representation;
        this.color = color;
    }

    @Override
    public String toString() {
        return representation;
    }

    public String colorToString(){
        return color+representation+blankColor;
    }
}