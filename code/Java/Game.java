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
        cards = new ArrayList<>();
        cards.addAll(Arrays.asList(PlayerCard.values()));
        cards.addAll(Arrays.asList(PlayerCard.values()));
        cards.addAll(Arrays.asList(PlayerCard.values()));

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

        while (!moveAPlayer(currentPlayer, numMoves)) {
        }

        board.printBoardAndNotebook(currentPlayer);

        if (currentPlayer.getCell() != startingCell)
            makeSuggestion(currentPlayer);
    }

    // TODO: this method
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

        boolean someoneRefuted = false;
        Iterator<Player> playerIterator = humanPlayers.values().iterator();
        while (playerIterator.next() != currentPlayer) {
        }     // start iterator at currentPlayer

        // for clockwise players
        for (int i = 0; i < humanPlayers.size() - 1 && !someoneRefuted; i++) {
            if (!playerIterator.hasNext())
                playerIterator = humanPlayers.values().iterator();

            Player player = playerIterator.next();
            Set<Card> cardsThePlayerHas = new HashSet<>(suggestionCards);
            for (Card cardName : suggestionCards)
                if (!player.holdsCard(cardName))
                    cardsThePlayerHas.remove(cardName);

            if (cardsThePlayerHas.size() == 1) {
                someoneRefuted = true;
                currentPlayer.addToNotepad(cardsThePlayerHas.iterator().next());
//              add the information to our notebook
            } else if (cardsThePlayerHas.size() > 1) {
                someoneRefuted = true;
//              ask them which card they want to show

                // TODO: Using a Set for cardsThePlayerHas makes this weird. Either we take a string input of the name
                //  and use that, or display the possible options with int values and take an int input, or get them to
                //  use an int input based on the location it is in their displayed notebook. The first is a nuisance
                //  for the player and a bit gross to implement, the second is the easiest for the player but the
                //  grossest to implement with a set, and the third is kinda okay to implement but still not gr8 for the
                //  player.     🤷 🤷 🤷 🤷 🤷
                //  Need a second opinion on it, I've whipped up pseudocode for all, someone pick their poison.

                //OPTION #1: STRING INPUT
//                System.out.println(player.getCardName() + "Enter the card you want to offer: ");
//                Scanner scanner = new Scanner(System.in);
//                String givenCard = scanner.nextLine();      //double-check this pls, got it from google and didn't test
//                Iterator<Card> cardIterator = cardsThePlayerHas.iterator();
//                while (cardIterator.hasNext()) {
//                    Card card = cardIterator.next();
//                    if (card.getClass().getName().equalsIgnoreCase(givenCard)) {
//                        currentPlayer.addToNotepad(card);
//                        break;
//                    } else if (!cardIterator.hasNext()) {
//                        //something asking for another input bc they messed up their input. idk I'm tired.
//                    }
//                }

                //OPTION #2: INT INPUT WITH GIVEN STUFF (I THINK THIS IS BEST NOW THAT I'VE WRITTEN IT - ELIAS)
//                List<Card> cardsButWeCanAccessThemWithInts = new ArrayList<>(cardsThePlayerHas);
//                //print out their options
//                System.out.println("ALL THE CARDS YOU CAN CHOOSE FROM: ");
//                for (int index = 0; index < cardsButWeCanAccessThemWithInts.size(); index++) {
//                    System.out.println(index + ": " + cardsButWeCanAccessThemWithInts.get(index));
//                }
//
//                //ask for their opinion ig, does their opinion really matter in the grand scheme of things???
//                System.out.println(player.getCardName() + "Enter the card you want to offer: ");
//                Scanner scanner = new Scanner(System.in);
//                int givenCard = scanner.nextInt();      //double-check this pls, got it from google and didn't test
//
//                if (givenCard > cardsThePlayerHas.size()) {
//                    //tell them they're bad and their input is wrong somehow, and give them another try like a baby
//                } else {
//                    //hooray they're right we can do it with one line of code
//                    currentPlayer.addToNotepad(cardsButWeCanAccessThemWithInts.get(givenCard));
//                }

                //OPTION #3: INT INPUT BUT WE THROW THEM IN THE DEEP END AND GET THEM TO COUNT FOR THEMSELVES
                //IT REALLY DO BE LIKE OPTION 2 BUT WE ASSUME THEY CAN COUNT IN ORDER
                //also I just realised that I'm assuming cards is in order. Thus, its probably the worst option?

//                //we care about their opinion ig
//                System.out.println(player.getCardName() + "Enter the card you want to offer: ");
//                Scanner scanner = new Scanner(System.in);
//                int givenCard = scanner.nextInt();      //double-check this pls, got it from google and didn't test
//
//                if (givenCard > cards.size()) {
//                    //tell them they're bad and their input is wrong somehow, and give them another try like a baby
//                } else {
//                    //hooray they're right we can do it with one line of code
//                    currentPlayer.addToNotepad(cards.get(givenCard));
//                }


//              add the information to our notebook
            }
            //otherwise we're skipping this player
        }

        if (!someoneRefuted && getBooleanInput("No one could refute your suggestion. Make an accusation?"))
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
        System.out.println("You rolled a " + d1 + " and a " + d2);
        return d1 + d2;
    }

    public boolean moveAPlayer(Player currentPlayer, int moveCount) {
        //takes input for moves -- this looks so janky
        System.out.println("Enter a sequence of moves: W, A, S, D, or 0 to not move: ");
        Scanner reader = new Scanner(System.in);
        String c = reader.next();

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
                } else if (c.charAt(i) == '0') {
                    return true;  // don't need to continue checking if move is valid if there is no move 🤷
                } else {
                    System.out.println("Invalid string to move with. Can only use W, A, S, and D to move, or 0 for no-move.");
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

    public int getIntegerInput(String question, int upperBound) {
        while (true) {
            System.out.print(question + " (0-" + (upperBound - 1) + "): ");

            try {
                int input = inputScanner.nextInt();
                System.out.print("");

                if (input < upperBound)
                    return input;

            } catch (Exception e) {
                inputScanner.next();    // clear it out of the scanner
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

    public void removeCard(Card card) {
        // TODO Auto-generated method stub

    }

    public void addCard(Card card) {
        // TODO Auto-generated method stub

    }
}
