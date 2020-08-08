package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

public class Item {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Item Associations
    private Cell cell;
    private Card card;
    private String printString;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Item(Cell cell, Card card, String printString) {
        this.cell = cell;
        this.card = card;
        this.printString = printString;
        this.cell.setContainsItem(true);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public String toString() { return printString; }

    public String getPrintString() { return printString; }

    public Cell getCell() {
        return cell;
    }

    public Card getCard() {
        return card;
    }

    public void moveToCell(Cell cell) {
        this.cell.setContainsItem(false);
        this.cell = cell;
        cell.setContainsItem(true);
    }

    /**
     * Get the CardName of the room the item is currently in
     * If the item is not in room (i.e. it is in a hall) return null
     *
     * @return The CardName of the room
     */
    public Card.RoomCard getRoomName() {
        if (cell.getClass() == RoomCell.class) {
            return ((RoomCell) cell).getRoom();
        }
        return null;
    }

}