package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/


import java.io.File;
import java.util.*;
import java.util.Scanner;

import Java.Card.CardName;
import Java.Card.CardType;

// line 48 "model.ump"
// line 120 "model.ump"
public class Game
{

    public static Card.CardName[] PLAYER_ORDER = {
            Card.CardName.MISS_SCARLET,
            Card.CardName.COLONEL_MUSTARD,
            Card.CardName.MRS_WHITE,
            Card.CardName.REVEREND_GREEN,
            Card.CardName.MRS_PEACOCK,
            Card.CardName.PROFESSOR_PLUM
    };

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Associations
  private Board board;
  private final List<Card> cards;
  private Map<Card.CardName, Player> humanPlayers;
  private Set<Card> envelope;

  private final Scanner inputScanner;   // for getting user input

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

  public Game()
  {
    inputScanner = new Scanner(System.in);

    board = new Board(new File("data/cell-data.txt"), new File("data/printed-board.txt"));
    cards = new ArrayList<>();
    for (Card.CardName name : Card.CardName.values())
      cards.add(new Card(name));

    System.out.println("Welcome to Cluedo!");
    initHumanPlayers();
    dealCards();

    // TODO: need exit condition when game is won
    Iterator<Player> currentPlayer = humanPlayers.values().iterator();
    while (true) {
        if (!currentPlayer.hasNext())
            currentPlayer = humanPlayers.values().iterator();

        playATurn(currentPlayer.next());
    }
  }

  public void initHumanPlayers() {
      while (true) {
          humanPlayers = new LinkedHashMap<>();

          System.out.println("Choose which characters will be controlled by players");
          for (Card.CardName card : PLAYER_ORDER) {
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

      if (currentPlayer.wasTeleported()) {
          currentPlayer.setWasTeleported(false);
          System.out.println("\n\nYou were moved to this room due to another player's suggestion");
          if (getBooleanInput("Skip your move and make a suggestion about this room? (y/n): ")) {
              makeSuggestion(currentPlayer);
              return;
          }
      }

      // record player's starting cell (to check if it moves into a new room)
      Cell startingCell = currentPlayer.getCell();

      board.printBoardAndNotebook(currentPlayer);

      System.out.println("\n");
      int numMoves = rollDice();

      while (!moveAPlayer(currentPlayer, numMoves)){}

      board.printBoardAndNotebook(currentPlayer);

      // TODO: figure out if the player is in a new room
      if (!currentPlayer.getCell().equals(startingCell))
          makeSuggestion(currentPlayer);
      //TODO: if nobody contests the suggestion, ask if they want to make an accusation
      if (getBooleanInput("Make an accusation? ")) {
    	  makeAccusation(currentPlayer);
      }
  }

  // TODO: this method
  public void makeSuggestion(Player currentPlayer) {
	  System.out.println("Make a Suggestion: ");
	  
//      pick a weapon
	  System.out.println("Select which weapon to use: ");
//      pick a suspect
	  System.out.println("Select which suspect to use: ");
//
//      if (weapon not in room)
	  
//      move weapon into room
//      if (suspect not in room) {
//          move suspect into room
//          suspect.justTeleported = true
//      }
//
//      for (each player clockwise of current player) {
//          if (player has 1 of the cards) {
//              don't need to ask them anything
//              add the information to our notebook
//              break
//          } else if (player has >1 of the cards) {
//              ask them which card they want to show
//              add the information to our notebook
//              break
//          }
//      }
//
//      if (no one refuted the suggestion && we want to make an accusation)
//      makeAccusation();

  }

    // TODO: this method
    public void makeAccusation(Player currentPlayer) {
    	System.out.println("Make an Accusation: ");
    	
    	for(Card c: envelope) {
    		System.out.println(c.getName());
    		
    	}
    	
    	CardName suspect = checkSuspect();
    	System.out.println("suspect: " + suspect.name());
    	
    	CardName room = checkRoom();
    	System.out.println("room: " + room.name());
    	CardName weapon = checkWeapon();
    	System.out.println("weapon: " + weapon.name());

    	
    	int correctCount=0;
    	for(Card c: envelope) {
    		
    		if(c.getName().equals(suspect)){
    			correctCount++;
    		}
    		else if(c.getName().equals(room)){
    			correctCount++;
    		}
    		else if(c.getName().equals(weapon)) {
    			correctCount++;
    		}
    	
    	}
    	if(correctCount==3) {
    		// player wins! game is over
    		System.out.println("Player " + currentPlayer + " wins!");
    	}
    	else {
    		//player loses (they can't play any more)
    		System.out.println("Wrong accusation " + currentPlayer + " is out of the game!");
    	}
    	
    }
    
	public CardName checkSuspect() {
		//pick a suspect
		System.out.println("Select which suspect to use: ");
		Scanner scanSuspect = new Scanner(System.in);
		String suspect = scanSuspect.next();
//		scanSuspect.close();commented out as it causes it to bug out

		switch (suspect) {
		case "CM":
			return Card.CardName.COLONEL_MUSTARD;
		case "PP":
			return Card.CardName.PROFESSOR_PLUM;
		case "RG":
			return Card.CardName.REVEREND_GREEN;
		case "MP":
			return Card.CardName.MRS_PEACOCK;
		case "MS":
			return Card.CardName.MISS_SCARLET;
		case "MW":
			return Card.CardName.MRS_WHITE;
		default:
			System.out.println("No such character");
			break;
		}
		return null;

	}
    public CardName checkRoom() {
//      pick a room (any room)
  	System.out.println("Select which room to use: ");
    	Scanner scanRoom = new Scanner(System.in);
    	String room = scanRoom.next();
//    	scanRoom.close();commented out as it causes it to bug out
    	

    	switch (room) {
		case "kitchen":
			return Card.CardName.KITCHEN;
		case "ballroom":
			return Card.CardName.BALLROOM;
		case "conservatory":
			return Card.CardName.CONSERVATORY;
		case "diningroom":
			return Card.CardName.DINING_ROOM;
		case "billiardroom":
			return Card.CardName.BILLIARD_ROOM;
		case "library":
			return Card.CardName.LIBRARY;
		case "Lounge":
			return Card.CardName.LOUNGE;
		case "hall":
			return Card.CardName.HALL;
		case "study":
			return Card.CardName.STUDY;
		default:
			System.out.println("No such room");
			break;
		}
		return null;
	
	}
    public CardName checkWeapon() {
//      pick a weapon
    	System.out.println("Select which weapon to use: ");
    	Scanner scanWeapon = new Scanner(System.in);
    	String weapon = scanWeapon.next();
    	//scanWeapon.close();commented out as it causes it to bug out
    	
    	switch (weapon) {
		case "dagger":
			return Card.CardName.DAGGER;
		case "candlestick":
			return Card.CardName.CANDLESTICK;
		case "revolver":
			return Card.CardName.REVOLVER;
		case "rope":
			return Card.CardName.ROPE;
		case "leadpipe":
			return Card.CardName.LEAD_PIPE;
		case "spanner":
			return Card.CardName.SPANNER;
		default:
			System.out.println("No such weapon");
			break;
		}
		return null;
	}
  
  public int rollDice() {
      Random random = new Random();
      int d1 = 1 + random.nextInt(5);
      int d2 = 1 + random.nextInt(5);
	  System.out.println("You rolled a " + d1 + " and a " + d2);
      return d1 + d2;
  }

  public boolean moveAPlayer(Player currentPlayer, int moveCount) {
      //takes input for moves -- this looks so janky
      System.out.println("Enter a sequence of moves: W, A, S, D: ");
      Scanner reader = new Scanner(System.in);
      String c = reader.next();
      

      // can remove this at some point
      char[] moveList = new char[moveCount];
      for (int i = 0; i < c.length(); i++) {
          moveList[i] = c.charAt(i);
          System.out.println(moveList[i]);//debug
      }

      List<Cell> allCellsTraversed = new ArrayList<>();
      // main move logic
      for (int i = 0; i < c.length(); i++) {
          allCellsTraversed.add(currentPlayer.getCell());
          Cell newCell;
          // catching ArrayIndexOutOfBoundExceptions to deal with edges of the board
          try {
              if (c.charAt(i) == 'W' || c.charAt(i) == 'w') {
                  newCell = board.getCell(currentPlayer.getCell().getRow() - 1, currentPlayer.getCell().getCol());
              } else if (c.charAt(i) == 'A' || c.charAt(i) == 'a') {
                  newCell = board.getCell(currentPlayer.getCell().getRow(), currentPlayer.getCell().getCol() - 1);
              } else if (c.charAt(i) == 'S' || c.charAt(i) == 's') {
                  newCell = board.getCell(currentPlayer.getCell().getRow() + 1, currentPlayer.getCell().getCol());
              } else if (c.charAt(i) == 'D' || c.charAt(i) == 'd') {
                  newCell = board.getCell(currentPlayer.getCell().getRow() - 1, currentPlayer.getCell().getCol() + 1);
              } else {
                  System.out.println("Invalid string to move with. Can only use W, A, S, and D to move.");
                  return false;
              }

              if (isValidMove(currentPlayer.getCell(), newCell) && !allCellsTraversed.contains(newCell)) {
                  currentPlayer.moveToCell(newCell);
              } else {
                  System.out.println("Invalid cell to move to.");
                  return false;
              }
          } catch (ArrayIndexOutOfBoundsException plsnomoveoutofboard) {
              System.out.println("Cannot move off the board.");
              return false;
          }
      }
      reader.close();
      return true;  // if it gets here, we've successfully moved for a whole move.
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
      String input = inputScanner.next();
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

  /**
   * Establish murder circumstances and deal cards to players
   */
  public void dealCards() {

    Collections.shuffle(cards);

    envelope = new HashSet<>();
    Set<Card.CardType> typesNotInEnvelope = new HashSet<>(Set.of(Card.CardType.values()));
    Iterator<Player> playerIterator = humanPlayers.values().iterator();

    // start iterator at random position (random "dealer" so Miss Scarlet doesn't get all the cards :P)
    for (int i=0; i<Math.random() * humanPlayers.size(); i++)
        playerIterator.next();

    for (Card card : cards) {
      if (typesNotInEnvelope.contains(card.getName().getType())) {
        envelope.add(card);
        typesNotInEnvelope.remove(card.getName().getType());
      } else {
        if (!playerIterator.hasNext())
          playerIterator = humanPlayers.values().iterator();
        playerIterator.next().dealCard(card);
      }
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

public void removeCard(Card card) {
	// TODO Auto-generated method stub
	
}

public void addCard(Card card) {
	// TODO Auto-generated method stub
	
}
}
