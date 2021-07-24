package model;

public enum Checker {
    EMPTY(" "),
    RED("R"),
    BLACK("B");

    private String representation;
    private Checker(String representation){
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}