package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/


// line 11 "model.ump"
// line 80 "model.ump"
public class HallCell extends Cell {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //HallCell Attributes
    private boolean isEntrance;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public HallCell(Board aBoard, int row, int col, boolean aIsEntrance) {
        super(row, col);
        isEntrance = aIsEntrance;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setIsEntrance(boolean aIsEntrance) {
        boolean wasSet = false;
        isEntrance = aIsEntrance;
        wasSet = true;
        return wasSet;
    }

    public boolean getIsEntrance() {
        return isEntrance;
    }
    /* Code from template attribute_IsBoolean */

    //Prevented compilation - Elias

//  public void delete()
//  {
//    super.delete();
//  }


    public String toString() {
        return super.toString() + "[" +
                "isEntrance" + ":" + getIsEntrance() + "]";
    }
}