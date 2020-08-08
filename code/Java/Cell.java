package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

public class Cell {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    private boolean containsItem = false;
    private int row, col;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public int getRow() {return row;}
    public int getCol() {return col;}

    public boolean doesContainItem() {
        return containsItem;
    }

    public void setContainsItem(boolean containsItem) {
        this.containsItem = containsItem;
    }

}