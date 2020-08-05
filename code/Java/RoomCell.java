package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/


// line 16 "model.ump"
// line 85 "model.ump"
public class RoomCell extends Cell {

    //------------------------
    // ENUMERATIONS
    //------------------------

//  public enum RoomName { KITCHEN, BALLROOM, CONSERVATORY, DINING_ROOM, BILLIARD_ROOM, LIBRARY, LOUNGE, HALL, STUDY }

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //RoomCell Attributes
    private boolean isDoor;
    private Card.CardName room;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public RoomCell(Board aBoard, int row, int col, boolean aIsDoor, Card.CardName aRoom) {
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

    public boolean setRoom(Card.CardName aRoom) {
        boolean wasSet = false;
        room = aRoom;
        wasSet = true;
        return wasSet;
    }

    public boolean getIsDoor() {
        return isDoor;
    }

    public Card.CardName getRoom() {
        return room;
    }

    /* Code from template attribute_IsBoolean */
    public boolean isIsDoor() {
        return isDoor;
    }

    //Prevented compilation - Elias

//  public void delete()
//  {
//    super.delete();
//  }


    public String toString() {
        return super.toString() + "[" +
                "isDoor" + ":" + getIsDoor() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "room" + "=" + (getRoom() != null ? !getRoom().equals(this) ? getRoom().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}