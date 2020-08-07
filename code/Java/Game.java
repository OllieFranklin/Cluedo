package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/


import Java.Card.*;

import java.io.File;
import java.util.*;
import java.util.Scanner;

// line 48 "model.ump"
// line 120 "model.ump"
public class Game {

    public static PlayerCard[] PLAYER_ORDER = {
            PlayerCard.MISS_SCARLET,
            PlayerCard.COLONEL_MUSTARD,
            PlayerCard.MRS_WHITE,
            PlayerCard.REVEREND_GREEN,
            PlayerCard.MRS_PEACOCK,
            PlayerCard.PROFESSOR_PLUM
    };

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Game Associations
    private Board board;
    private final List<Card> cards;
    private Map<PlayerCard, Player> humanPlayers;

    // the murder circumstances
    PlayerCard murderer = null;
    WeaponCard murderWeapon = null;
    RoomCard crimeScene = null;

    private final Scanner inputScanner;   // for getting user input
    private boolean gameOver = false;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    //I think this version of the Game constructor isn't as useful as the other one - Elias

//  public Game(Board aBoard)
//  {
//    if (aBoard == null || aBoard.getGame() != null)
//    {
//      throw new RuntimeException("Unable to create Game due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//    board = aBoard;
//    cards = new ArrayList<Card>();
//    players = new ArrayList<Player>();
//  }

    public Game() {
        inputScanner = new Scanner(System.in);

        board = new Board(new File("data/cell-data.txt"), new File("data/printed-board.txt"));
        cards = Arrays.asList(Card.values());

        System.out.println("Welcome to Cluedo!");
        initHumanPlayers();
        dealCards();

        Iterator<Player> currentPlayer = humanPlayers.values().iterator();
        while (!gameOver) {
            if (!currentPlayer.hasNext())
                currentPlayer = humanPlayers.values().iterator();

            playATurn(currentPlayer.next());
        }
    }

    public void initHumanPlayers() {
        while (true) {
            humanPlayers = new LinkedHashMap<>();

            System.out.println("Choose which characters will be controlled by players");
            for (PlayerCard card : PLAYER_ORDER) {
                if (getBooleanInput("Play as " + card + "?")) {
                    humanPlayers.put(card, board.getPlayer(card));
                }
            }

            if (humanPlayers.size() >= 3)
                break;

            System.out.println("Must have at least 3 players. Come back when you have more friends\n");
        }
    }

    public void playATurn(Player currentPlayer) {

        if (currentPlayer.isOut())
            return;

        System.out.println("\n\n--------" + currentPlayer.getCard().toString().toUpperCase() + "'S TURN--------");
        board.printBoardAndNotebook(currentPlayer);

        if (currentPlayer.wasTeleported()) {
            currentPlayer.setWasTeleported(false);
            System.out.println("\n\nYou were moved to this room due to another player's suggestion");
            if (getBooleanInput("Skip your move and make a suggestion about this room?")) {
                makeSuggestion(currentPlayer);
                return;
            }
        }

        // record player's room (null if they're in a hallway)
        RoomCard roomBeforeMove = currentPlayer.getRoomName();

        System.out.println("\n");
        int numMoves = rollDice();

        while (!moveAPlayer(currentPlayer, numMoves)) {
        }

        board.printBoardAndNotebook(currentPlayer);

        RoomCard roomAfterMove = currentPlayer.getRoomName();

        if (roomAfterMove != null && roomAfterMove != roomBeforeMove)
            makeSuggestion(currentPlayer);
    }


    public void makeSuggestion(Player currentPlayer) {

        // sanity check, although this shouldn't happen
        if (currentPlayer.getRoomName() == null)
            return;

        System.out.println("Make a Suggestion: ");

        PlayerCard suspectGuess = (PlayerCard) pickOption("Choose a suspect:", PlayerCard.values());
        WeaponCard weaponGuess = (WeaponCard) pickOption("Choose a weapon:", WeaponCard.values());
        RoomCard roomGuess = (RoomCard) currentPlayer.getRoomName();
        Set<Card> suggestionCards = new HashSet<>(Arrays.asList(suspectGuess, weaponGuess, roomGuess));

        // move the suspect into the room if they're not already there
        if (!board.itemInRoom(suspectGuess, roomGuess)) {
            board.moveItemToRoom(suspectGuess, roomGuess);
            board.getPlayer(suspectGuess).setWasTeleported(true);
        }

        // move the weapon into the room if it's not already there
        if (!board.itemInRoom(weaponGuess, roomGuess)) {
            board.moveItemToRoom(weaponGuess, roomGuess);
        }

        Iterator<Player> playerIterator = humanPlayers.values().iterator();
        while (playerIterator.next() != currentPlayer) {
        }     // start iterator at currentPlayer

        // for each player clockwise of currentPlayer
        for (int i = 0; i < humanPlayers.size() - 1; i++) {
            if (!playerIterator.hasNext())
                playerIterator = humanPlayers.values().iterator();

            Player player = playerIterator.next();

            // figure out which (if any) of the suggested cards a player has
            List<Card> cardsThePlayerHas = new ArrayList<>();
            for (Card card : suggestionCards)
                if (player.holdsCard(card))
                    cardsThePlayerHas.add(card);

            // if they have 1 of the suggested cards, they must show it to currentPlayer
            if (cardsThePlayerHas.size() == 1) {
                currentPlayer.addToNotepad(cardsThePlayerHas.get(0));
                System.out.println(player + " shows " + currentPlayer + " a card");
                return;
            }

            // if they have 2 or more of the suggested cards, they can choose which one to show
            if (cardsThePlayerHas.size() > 1) {

                System.out.println(player + ", you have more than one of the suggested cards. Which one would you like to show to " + currentPlayer + "?");

                for (int j = 0; j < cardsThePlayerHas.size(); j++)
                    System.out.printf("[%d] %s%n", j, cardsThePlayerHas.get(j));
                int cardIndex = getIntegerInput("Pick a card:", cardsThePlayerHas.size());

                currentPlayer.addToNotepad(cardsThePlayerHas.get(cardIndex));
                return;
            }
        }

        if (getBooleanInput("No one could refute your suggestion. Make an accusation?"))
            makeAccusation(currentPlayer);
    }

    public void makeAccusation(Player currentPlayer) {
        System.out.println("Make an Accusation: ");

        Card suspectGuess = pickOption("Choose a suspect:", PlayerCard.values());
        Card weaponGuess = pickOption("Choose a weapon:", WeaponCard.values());
        Card roomGuess = pickOption("Choose a room:", RoomCard.values());

        if (suspectGuess.equals(murderer) && weaponGuess.equals(murderWeapon) && roomGuess.equals(crimeScene)) {
            System.out.println("Player " + currentPlayer + " wins!");
            gameOver = true;
        } else {
            System.out.println("Wrong accusation " + currentPlayer + " is out of the game!");
            currentPlayer.setOut();
        }
    }

    public Card pickOption(String question, Card[] cards) {

        for (int i = 0; i < cards.length; i++)
            System.out.printf("[%d] %s%n", i, cards[i]);

        return cards[getIntegerInput(question, cards.length)];
    }

    public int rollDice() {
        Random random = new Random();
        int d1 = 1 + random.nextInt(5);
        int d2 = 1 + random.nextInt(5);
        System.out.printf("You rolled %d and %d (%d spaces total)%n", d1, d2, d1+d2);
        return d1 + d2;
    }

    public boolean moveAPlayer(Player currentPlayer, int moveCount) {
        System.out.print("Enter a sequence of moves (W,A,S,D, or ENTER for no move): ");
        String c = inputScanner.nextLine().toUpperCase();
        System.out.print("");

        if (c.length() > moveCount) {
            System.out.println("Sequence was too long, must be " + moveCount + " characters at most");
            return false;
        }

        Cell currentCell = currentPlayer.getCell();

        List<Cell> allCellsTraversed = new ArrayList<>();
        // main move logic
        for (int i = 0; i < c.length(); i++) {
            allCellsTraversed.add(currentCell);
            Cell newCell;
            // catching ArrayIndexOutOfBoundExceptions to deal with edges of the board
            try {
                if (c.charAt(i) == 'W') {
                    newCell = board.getCell(currentCell.getRow() - 1, currentCell.getCol());
                } else if (c.charAt(i) == 'A') {
                    newCell = board.getCell(currentCell.getRow(), currentCell.getCol() - 1);
                } else if (c.charAt(i) == 'S') {
                    newCell = board.getCell(currentCell.getRow() + 1, currentCell.getCol());
                } else if (c.charAt(i) == 'D') {
                    newCell = board.getCell(currentCell.getRow(), currentCell.getCol() + 1);
                } else {
                    System.out.println("Invalid string to move with. Can only use W, A, S, and D to move.");
                    return false;
                }

                if (isValidMove(currentCell, newCell) && !allCellsTraversed.contains(newCell)) {
                    currentCell = newCell;
                } else {
                    System.out.println("Invalid cell to move to.");
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException plsnomoveoutofboard) {
                System.out.println("Cannot move off the board.");
                return false;
            }
        }

        // only when we get to here should we actually make the move
        currentPlayer.moveToCell(currentCell);
        return true;
    }

    public boolean isValidMove(Cell playerCell, Cell newCell) {
        // helper method to get if a cell is valid or not
        if (newCell.getClass() == EmptyCell.class) {
            return false;
        }

        if (newCell.doesContainItem()) {
            return false;
        }

        if (playerCell.getClass() == HallCell.class) {
            if (newCell.getClass() == HallCell.class) {
                return true;
            } else return ((HallCell) playerCell).getIsEntrance() && ((RoomCell) newCell).getIsDoor();
        } else if (playerCell.getClass() == RoomCell.class) {
            if (newCell.getClass() == RoomCell.class) {
                return true;
            } else return ((RoomCell) playerCell).getIsDoor() && ((HallCell) newCell).getIsEntrance();
        }

        return false; // it shouldn't get here, but just in case
    }

    public boolean getBooleanInput(String question) {
        while (true) {
            System.out.print(question + " (y/n): ");
            String input = inputScanner.nextLine();
            System.out.print("");
            if (input.equalsIgnoreCase("Y")) {
                return true;
            }
            if (input.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Must answer Y or N");
        }
    }

    public int getIntegerInput(String question, int upperBound) {
        while (true) {
            System.out.print(question + " (0-" + (upperBound - 1) + "): ");

            try {
                int input = inputScanner.nextInt();
                inputScanner.nextLine();
                System.out.print("");

                if (input < upperBound)
                    return input;

            } catch (Exception e) {
                inputScanner.nextLine();    // clear it out of the scanner
            }

            System.out.println("Please enter a number from 0-" + (upperBound - 1));
        }
    }

    /**
     * Establish murder circumstances and deal cards to players
     */
    public void dealCards() {

        Collections.shuffle(cards);

        Iterator<Player> playerIterator = humanPlayers.values().iterator();

        // start iterator at random position (random "dealer" so Miss Scarlet doesn't get all the cards :P)
        for (int i = 0; i < Math.random() * humanPlayers.size(); i++)
            playerIterator.next();

        for (Card card : cards) {

            if (card.getClass() == PlayerCard.class && murderer == null) {
                murderer = (PlayerCard) card;
            } else if (card.getClass() == WeaponCard.class && murderWeapon == null) {
                murderWeapon = (WeaponCard) card;
            } else if (card.getClass() == RoomCard.class && crimeScene == null) {
                crimeScene = (RoomCard) card;
            } else {
                if (!playerIterator.hasNext())
                    playerIterator = humanPlayers.values().iterator();
                playerIterator.next().dealCard(card);
            }

//            if (typesNotInEnvelope.contains(card.getName().getType())) {
//                envelope.put(card.getName().getType(), card);
//                typesNotInEnvelope.remove(card.getName().getType());
//            } else {
//                if (!playerIterator.hasNext())
//                    playerIterator = humanPlayers.values().iterator();
//                playerIterator.next().dealCard(card);
//            }
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    //Don't need below code yet, and it prevents compiling, so I commented it out - Elias

//  /* Code from template association_GetOne */
//  public Board getBoard()
//  {
//    return board;
//  }
//  /* Code from template association_GetMany */
//  public Card getCard(int index)
//  {
//    Card aCard = cards.get(index);
//    return aCard;
//  }
//
//  public List<Card> getCards()
//  {
//    List<Card> newCards = Collections.unmodifiableList(cards);
//    return newCards;
//  }
//
//  public int numberOfCards()
//  {
//    int number = cards.size();
//    return number;
//  }
//
//  public boolean hasCards()
//  {
//    boolean has = cards.size() > 0;
//    return has;
//  }
//
//  public int indexOfCard(Card aCard)
//  {
//    int index = cards.indexOf(aCard);
//    return index;
//  }
//  /* Code from template association_GetMany */
//  public Player getPlayer(int index)
//  {
//    Player aPlayer = players.get(index);
//    return aPlayer;
//  }
//
//  public List<Player> getPlayers()
//  {
//    List<Player> newPlayers = Collections.unmodifiableList(players);
//    return newPlayers;
//  }
//
//  public int numberOfPlayers()
//  {
//    int number = players.size();
//    return number;
//  }
//
//  public boolean hasPlayers()
//  {
//    boolean has = players.size() > 0;
//    return has;
//  }
//
//  public int indexOfPlayer(Player aPlayer)
//  {
//    int index = players.indexOf(aPlayer);
//    return index;
//  }
//  /* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfCards()
//  {
//    return 0;
//  }
//  /* Code from template association_AddManyToOne */
//  public Card addCard(Card.CardType aType, String aName, Player aHand, Player aNotebook)
//  {
//    return new Card(aType, aName, aHand, aNotebook, this);
//  }
//
//  public boolean addCard(Card aCard)
//  {
//    boolean wasAdded = false;
//    if (cards.contains(aCard)) { return false; }
//    Game existingGame = aCard.getGame();
//    boolean isNewGame = existingGame != null && !this.equals(existingGame);
//    if (isNewGame)
//    {
//      aCard.setGame(this);
//    }
//    else
//    {
//      cards.add(aCard);
//    }
//    wasAdded = true;
//    return wasAdded;
//  }
//
//  public boolean removeCard(Card aCard)
//  {
//    boolean wasRemoved = false;
//    //Unable to remove aCard, as it must always have a game
//    if (!this.equals(aCard.getGame()))
//    {
//      cards.remove(aCard);
//      wasRemoved = true;
//    }
//    return wasRemoved;
//  }
//  /* Code from template association_AddIndexControlFunctions */
//  public boolean addCardAt(Card aCard, int index)
//  {
//    boolean wasAdded = false;
//    if(addCard(aCard))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfCards()) { index = numberOfCards() - 1; }
//      cards.remove(aCard);
//      cards.add(index, aCard);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }
//
//  public boolean addOrMoveCardAt(Card aCard, int index)
//  {
//    boolean wasAdded = false;
//    if(cards.contains(aCard))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfCards()) { index = numberOfCards() - 1; }
//      cards.remove(aCard);
//      cards.add(index, aCard);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addCardAt(aCard, index);
//    }
//    return wasAdded;
//  }
//  /* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfPlayers()
//  {
//    return 0;
//  }
//  /* Code from template association_AddManyToManyMethod */
//  public boolean addPlayer(Player aPlayer)
//  {
//    boolean wasAdded = false;
//    if (players.contains(aPlayer)) { return false; }
//    players.add(aPlayer);
//    if (aPlayer.indexOfGame(this) != -1)
//    {
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = aPlayer.addGame(this);
//      if (!wasAdded)
//      {
//        players.remove(aPlayer);
//      }
//    }
//    return wasAdded;
//  }
//  /* Code from template association_RemoveMany */
//  public boolean removePlayer(Player aPlayer)
//  {
//    boolean wasRemoved = false;
//    if (!players.contains(aPlayer))
//    {
//      return wasRemoved;
//    }
//
//    int oldIndex = players.indexOf(aPlayer);
//    players.remove(oldIndex);
//    if (aPlayer.indexOfGame(this) == -1)
//    {
//      wasRemoved = true;
//    }
//    else
//    {
//      wasRemoved = aPlayer.removeGame(this);
//      if (!wasRemoved)
//      {
//        players.add(oldIndex,aPlayer);
//      }
//    }
//    return wasRemoved;
//  }
//  /* Code from template association_AddIndexControlFunctions */
//  public boolean addPlayerAt(Player aPlayer, int index)
//  {
//    boolean wasAdded = false;
//    if(addPlayer(aPlayer))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
//      players.remove(aPlayer);
//      players.add(index, aPlayer);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }
//
//  public boolean addOrMovePlayerAt(Player aPlayer, int index)
//  {
//    boolean wasAdded = false;
//    if(players.contains(aPlayer))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
//      players.remove(aPlayer);
//      players.add(index, aPlayer);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addPlayerAt(aPlayer, index);
//    }
//    return wasAdded;
//  }
//
//  public void delete()
//  {
//    Board existingBoard = board;
//    board = null;
//    if (existingBoard != null)
//    {
//      existingBoard.delete();
//    }
//    for(int i=cards.size(); i > 0; i--)
//    {
//      Card aCard = cards.get(i - 1);
//      aCard.delete();
//    }
//    ArrayList<Player> copyOfPlayers = new ArrayList<Player>(players);
//    players.clear();
//    for(Player aPlayer : copyOfPlayers)
//    {
//      aPlayer.removeGame(this);
//    }
//  }


    public static void main(String[] args) {
        new Game();
    }
}
