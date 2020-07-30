package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/



// line 8 "model.ump"
// line 74 "model.ump"
public class Cell
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cell Associations
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  //Below code also doesn't seem necessary, and prevented compiling. - Elias

//  public Cell(Board aBoard)
//  {
//    boolean didAddBoard = setBoard(aBoard);
//    if (!didAddBoard)
//    {
//      throw new RuntimeException("Unable to create cell due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetOneToMany */

  //Below code prevented compilation, not all of it seems necessary too - Elias

//  public boolean setBoard(Board aBoard)
//  {
//    boolean wasSet = false;
//    if (aBoard == null)
//    {
//      return wasSet;
//    }
//
//    Board existingBoard = board;
//    board = aBoard;
//    if (existingBoard != null && !existingBoard.equals(aBoard))
//    {
//      existingBoard.removeCell(this);
//    }
//    board.addCell(this);
//    wasSet = true;
//    return wasSet;
//  }
//
//  public void delete()
//  {
//    Board placeholderBoard = board;
//    this.board = null;
//    if(placeholderBoard != null)
//    {
//      placeholderBoard.removeCell(this);
//    }
//  }

}