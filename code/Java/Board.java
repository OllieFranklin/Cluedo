package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// line 2 "model.ump"
// line 66 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  public static String[] printedBoard;

  //Board Associations
  private Cell[][] cells;
  private List<Item> items;
  private Game game;



  //------------------------
  // CONSTRUCTOR
  //------------------------

  //----- I commented this out because I don't think we need it - Elias

//  public Board(Game aGame)
//  {
//    cells = new Cell[25][24];
//    items = new ArrayList<Item>();
//    if (aGame == null || aGame.getBoard() != null)
//    {
//      throw new RuntimeException("Unable to create Board due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//    game = aGame;
//  }
//
//  public Board()
//  {
//    cells = new Cell[25][24];
//    items = new ArrayList<Item>();
//    game = new Game(this);
//  }

  //-----

  public Board(File dataFile, File printedBoardFile) {
    try {
      Scanner sc = new Scanner(dataFile);
      cells = new Cell[25][24];

      int col = 0;

      while (sc.hasNext()) {
        int row = 0;
        String line = sc.nextLine();
        for (int i = 0; i < 48; i += 2) {
          char c1 = line.charAt(i);
          char c2 = line.charAt(i + 1);
          if (c1 == 'R' || c1 == 'D') {
            RoomCell.RoomName roomType = RoomCell.RoomName.values()[Character.getNumericValue(c2)];
            cells[col][row] = new RoomCell(this, c1 == 'D', roomType);
          } else if (c1 == '░' || c1 == '▒') {
            cells[col][row] = new HallCell(this, c1 == '▒');
          } else {
            cells[col][row] = new EmptyCell(this);
          }
          row++;
        }
        col++;
      }

      sc = new Scanner(printedBoardFile);
      printedBoard = new String[25];
      int row = 0;
      while (sc.hasNext()) {
        printedBoard[row++] = sc.nextLine();
      }

    } catch (FileNotFoundException e) {
      System.out.println("No valid file found");
      e.printStackTrace();
    }

  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */

  // I commented out all the below code because it seems useless - Elias

//  public Cell getCell(int index)
//  {
//    Cell aCell = cells.get(index);
//    return aCell;
//  }
//
//  public List<Cell> getCells()
//  {
//    List<Cell> newCells = Collections.unmodifiableList(cells);
//    return newCells;
//  }
//
//  public int numberOfCells()
//  {
//    int number = cells.size();
//    return number;
//  }
//
//  public boolean hasCells()
//  {
//    boolean has = cells.size() > 0;
//    return has;
//  }
//
//  public int indexOfCell(Cell aCell)
//  {
//    int index = cells.indexOf(aCell);
//    return index;
//  }
//  /* Code from template association_GetMany */
//  public Item getItem(int index)
//  {
//    Item aItem = items.get(index);
//    return aItem;
//  }
//
//  public List<Item> getItems()
//  {
//    List<Item> newItems = Collections.unmodifiableList(items);
//    return newItems;
//  }
//
//  public int numberOfItems()
//  {
//    int number = items.size();
//    return number;
//  }
//
//  public boolean hasItems()
//  {
//    boolean has = items.size() > 0;
//    return has;
//  }
//
//  public int indexOfItem(Item aItem)
//  {
//    int index = items.indexOf(aItem);
//    return index;
//  }
//  /* Code from template association_GetOne */
//  public Game getGame()
//  {
//    return game;
//  }
//  /* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfCells()
//  {
//    return 0;
//  }
//  /* Code from template association_AddManyToOne */
//  public Cell addCell()
//  {
//    return new Cell(this);
//  }
//
//  public boolean addCell(Cell aCell)
//  {
//    boolean wasAdded = false;
//    if (cells.contains(aCell)) { return false; }
//    Board existingBoard = aCell.getBoard();
//    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
//    if (isNewBoard)
//    {
//      aCell.setBoard(this);
//    }
//    else
//    {
//      cells.add(aCell);
//    }
//    wasAdded = true;
//    return wasAdded;
//  }
//
//  public boolean removeCell(Cell aCell)
//  {
//    boolean wasRemoved = false;
//    //Unable to remove aCell, as it must always have a board
//    if (!this.equals(aCell.getBoard()))
//    {
//      cells.remove(aCell);
//      wasRemoved = true;
//    }
//    return wasRemoved;
//  }
//  /* Code from template association_AddIndexControlFunctions */
//  public boolean addCellAt(Cell aCell, int index)
//  {
//    boolean wasAdded = false;
//    if(addCell(aCell))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfCells()) { index = numberOfCells() - 1; }
//      cells.remove(aCell);
//      cells.add(index, aCell);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }
//
//  public boolean addOrMoveCellAt(Cell aCell, int index)
//  {
//    boolean wasAdded = false;
//    if(cells.contains(aCell))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfCells()) { index = numberOfCells() - 1; }
//      cells.remove(aCell);
//      cells.add(index, aCell);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addCellAt(aCell, index);
//    }
//    return wasAdded;
//  }
//  /* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfItems()
//  {
//    return 0;
//  }
//  /* Code from template association_AddManyToOne */
//  public Item addItem(Cell aCell)
//  {
//    return new Item(aCell, this);
//  }
//
//  public boolean addItem(Item aItem)
//  {
//    boolean wasAdded = false;
//    if (items.contains(aItem)) { return false; }
//    Board existingBoard = aItem.getBoard();
//    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
//    if (isNewBoard)
//    {
//      aItem.setBoard(this);
//    }
//    else
//    {
//      items.add(aItem);
//    }
//    wasAdded = true;
//    return wasAdded;
//  }
//
//  public boolean removeItem(Item aItem)
//  {
//    boolean wasRemoved = false;
//    //Unable to remove aItem, as it must always have a board
//    if (!this.equals(aItem.getBoard()))
//    {
//      items.remove(aItem);
//      wasRemoved = true;
//    }
//    return wasRemoved;
//  }
//  /* Code from template association_AddIndexControlFunctions */
//  public boolean addItemAt(Item aItem, int index)
//  {
//    boolean wasAdded = false;
//    if(addItem(aItem))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfItems()) { index = numberOfItems() - 1; }
//      items.remove(aItem);
//      items.add(index, aItem);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }
//
//  public boolean addOrMoveItemAt(Item aItem, int index)
//  {
//    boolean wasAdded = false;
//    if(items.contains(aItem))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfItems()) { index = numberOfItems() - 1; }
//      items.remove(aItem);
//      items.add(index, aItem);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addItemAt(aItem, index);
//    }
//    return wasAdded;
//  }
//
//  public void delete()
//  {
//    for(int i=cells.size(); i > 0; i--)
//    {
//      Cell aCell = cells.get(i - 1);
//      aCell.delete();
//    }
//    for(int i=items.size(); i > 0; i--)
//    {
//      Item aItem = items.get(i - 1);
//      aItem.delete();
//    }
//    Game existingGame = game;
//    game = null;
//    if (existingGame != null)
//    {
//      existingGame.delete();
//    }
//  }

}