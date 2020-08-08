package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

import Java.Card.RoomCard;

public class RoomCell extends Cell {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //RoomCell Attributes
    private boolean isDoor;
    private RoomCard room;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public RoomCell(int row, int col, boolean aIsDoor, RoomCard aRoom) {
        super(row, col);
        isDoor = aIsDoor;
        room = aRoom;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setIsDoor(boolean aIsDoor) {
        boolean wasSet = false;
        isDoor = aIsDoor;
        wasSet = true;
        return wasSet;
    }

    public boolean setRoom(RoomCard aRoom) {
        boolean wasSet = false;
        room = aRoom;
        wasSet = true;
        return wasSet;
    }

    public boolean getIsDoor() {
        return isDoor;
    }

    public RoomCard getRoom() {
        return room;
    }

    public String toString() {
        return super.toString() + "[" +
                "isDoor" + ":" + getIsDoor() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "room" + "=" + (getRoom() != null ? !getRoom().equals(this) ? getRoom().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}