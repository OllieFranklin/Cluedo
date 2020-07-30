package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/



// line 22 "model.ump"
// line 90 "model.ump"
public class Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Associations
  private Cell cell;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(Cell aCell, Board aBoard)
  {
    if (!setCell(aCell))
    {
      throw new RuntimeException("Unable to create Item due to aCell. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create item due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Cell getCell()
  {
    return cell;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCell(Cell aNewCell)
  {
    boolean wasSet = false;
    if (aNewCell != null)
    {
      cell = aNewCell;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    if (aBoard == null)
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      existingBoard.removeItem(this);
    }
    board.addItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    cell = null;
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeItem(this);
    }
  }

}