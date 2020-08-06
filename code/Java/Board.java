package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import Java.Card.*;

// line 2 "model.ump"
// line 66 "model.ump"
public class Board {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    public static String[] printedBoard;

    //Board Associations
    private Cell[][] cells;
    private Map<Card, Item> items;
//    private Map<Card.CardName, Weapon> weapons;
    private Map<RoomCard, Set<RoomCell>> cellsPerRoom;
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

        // creates room objects and associates cell sets with them
        initCells(dataFile, printedBoardFile);

        items = new HashMap<>();
//        weapons = new HashMap<>();

        // create player objects in their starting positions
        items.put(PlayerCard.COLONEL_MUSTARD, new Player(cells[17][0], PlayerCard.COLONEL_MUSTARD, "CM"));
        items.put(PlayerCard.PROFESSOR_PLUM, new Player(cells[19][23], PlayerCard.PROFESSOR_PLUM, "PP"));
        items.put(PlayerCard.REVEREND_GREEN, new Player(cells[0][14], PlayerCard.REVEREND_GREEN, "RG"));
        items.put(PlayerCard.MRS_PEACOCK, new Player(cells[6][23], PlayerCard.MRS_PEACOCK, "MP"));
        items.put(PlayerCard.MISS_SCARLET, new Player(cells[24][7], PlayerCard.MISS_SCARLET, "MS"));
        items.put(PlayerCard.MRS_WHITE, new Player(cells[0][9], PlayerCard.MRS_WHITE, "MW"));

        // shuffles rooms so weapon placement is randomised each game
        List<WeaponCard> unusedWeapons = new ArrayList<>(Arrays.asList(WeaponCard.values()));
        List<RoomCard> shuffledRooms = new ArrayList<>(Arrays.asList(RoomCard.values()));
        Collections.shuffle(unusedWeapons);
        Collections.shuffle(shuffledRooms);

        // places weapons randomly in rooms
        // TODO: THIS ALWAYS PLACES WEAPONS IN TOP-LEFT OF ROOM, COULD CHANGE TO BE MORE RANDOM?
        //  WEAPON PRINTSTRING CURRENTLY FIRST 2 LETTERS FOR EASE, COULD HAVE BETTER PRINTSTRINGS
        for (RoomCard room : shuffledRooms) {
            if (unusedWeapons.isEmpty()) {
                break;
            } // stop placing weapons if they've all been placed
            for (int row = 0; row < 25; row++) {
                for (int col = 0; col < 24; col++) {
                    Cell thisCell = cells[row][col];
                    if (thisCell.getClass().equals(RoomCell.class) && ((RoomCell) thisCell).getRoom().equals(room)) {
                        WeaponCard nextWeapon = unusedWeapons.remove(0);
                        String printString = nextWeapon.name().substring(0, 2);
                        items.put(nextWeapon, new Weapon(thisCell, nextWeapon, printString));

                        // Did this to break both loops, perhaps could be done better?
                        row = 25;
                        col = 24;
                    }
                }
            }
        }

    }

    public void moveItemToRoom(Card itemName, RoomCard roomName) {

        if (itemName.getClass() == RoomCard.class)
            return;

        for (RoomCell cell : cellsPerRoom.get(roomName)) {
            if (!cell.doesContainItem()) {
                items.get(itemName).moveToCell(cell);
                break;
            }
        }
    }

    public void initCells(File dataFile, File printedBoardFile) {
        try {
            cellsPerRoom = new HashMap<>();
            Scanner sc = new Scanner(dataFile);
            cells = new Cell[25][24];

            int row = 0;

            while (sc.hasNext()) {
                int col = 0;
                String line = sc.nextLine();
                for (int i = 0; i < 48; i += 2) {
                    char c1 = line.charAt(i);
                    char c2 = line.charAt(i + 1);
                    if (c1 == 'R' || c1 == 'D') {
                        RoomCard roomType = RoomCard.values()[Character.getNumericValue(c2)];
                        RoomCell roomCell = new RoomCell(this, row, col, c1 == 'D', roomType);
                        cells[row][col] = roomCell;

                        // also add the RoomCell to our map of room names to room cells
                        if (!cellsPerRoom.containsKey(roomType))
                            cellsPerRoom.put(roomType, new LinkedHashSet<>());
                        cellsPerRoom.get(roomType).add(roomCell);

                    } else if (c1 == '░' || c1 == '▒') {
                        cells[row][col] = new HallCell(this, row, col, c1 == '▒');
                    } else {
                        cells[row][col] = new EmptyCell(this, row, col);
                    }
                    col++;
                }
                row++;
            }

            sc = new Scanner(printedBoardFile);
            printedBoard = new String[25];
            row = 0;
            while (sc.hasNext()) {
                printedBoard[row++] = sc.nextLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("No valid file found");
            e.printStackTrace();
        }
    }

    /**
     * Print the board, and the notebook of a given player
     *
     * @param player The player whose notebook we're printing
     */
    public void printBoardAndNotebook(Player player) {

        // construct a StringBuilder array from String literal array
        StringBuilder[] output = new StringBuilder[printedBoard.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = new StringBuilder(printedBoard[i]);
        }

        // replace player/weapon cells with their Strings
        try {
            for (Item w : items.values())
                replaceCell(w.getCell().getRow(), w.getCell().getCol(), w.toString(), output);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // TODO: Look at this. It's an ugly line
        //  I wanted this, but couldn't get it to work even with weird casting:
        //  new ArrayList<Card>(Arrays.asList(PlayerCard.values())).addAll(Arrays.asList(WeaponCard.values()).addAll(Arrays.asList(RoomCard.values())))
        //  so I settled with a 2-line solution. Magic it better please
        List<Card> cardNames = new ArrayList<Card>(Arrays.asList(PlayerCard.values()));
        cardNames.addAll(Arrays.asList(WeaponCard.values()));
        cardNames.addAll(Arrays.asList(RoomCard.values()));
        int offset = 2;
        for (int i = 0; i < cardNames.size(); i++) {
            output[i + offset].append(player.knowAboutCard(cardNames.get(i)) ? "X" : "?");

            if (i != cardNames.size() - 1 && cardNames.get(i).getClass() != cardNames.get(i+1).getClass())
                offset++;
        }

        System.out.println("\n\n" + "PLAYER: " + player.getCardName().toString().toUpperCase());

        for (StringBuilder sb : output)
            System.out.println(sb);
    }

    /**
     * Replace a given cell in the StringBuilder array with a new String
     *
     * @param row         The row to make the replacement
     * @param col         The col index of the cell (NOT AN INDEX INTO THE STRING)
     * @param replacement The String to replace (must be 2 characters long)
     * @param text        The StringBuilder array to make the replacement on
     * @throws Exception If the String is not 2 characters long
     */
    private static void replaceCell(int row, int col, String replacement, StringBuilder[] text) throws Exception {
        if (replacement.length() != 2)
            throw new Exception("Wrong sized replacement String");
        text[row].replace(col * 2, col * 2 + replacement.length(), replacement);
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public boolean itemInRoom(Card itemName, RoomCard roomName) {
        // TODO: Work out a way of checking that itemName aint a RoomCard without null return, or ditch the check

        // I think that does the above? returning false seems good, right? - Ollie
        return itemName.getClass() == RoomCard.class && items.get(itemName).getRoomName() == roomName;
    }

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetMany */

    // I commented out all the below code because it seems useless - Elias

    public Player getPlayer(PlayerCard cardName) {
        return (Player)items.get(cardName);
    }

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