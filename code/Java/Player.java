package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/


import java.util.*;

// line 26 "model.ump"
// line 97 "model.ump"
public class Player extends Item {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Player Associations
    private Map<Card.CardName, Card> hand;         // the physical cards that the player holds
    private Map<Card.CardName, Card> inferences;   // the cards that the player knows are not the murder circumstances

    private boolean isOut;          // player is out of the game if they've made an incorrect accusation
    private boolean wasTeleported;  // player was teleported into their current room < 1 turn ago

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player(Cell cell, Card.CardName cardName, String printString) {
        super(cell, cardName, printString);
        hand = new HashMap<>();
        inferences = new HashMap<>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    // bruh I tried using the pre-written add card method and it's awful - Ollie
    public void dealCard(Card card) {
        hand.put(card.getName(), card);
        inferences.put(card.getName(), card);
    }

    public boolean holdsCard(Card.CardName cardName) {
        return hand.containsKey(cardName);
    }

    public boolean knowAboutCard(Card.CardName cardName) {
        return inferences.containsKey(cardName);
    }

    public String toString() {
        return super.toString();
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut() {
        isOut = true;
    }

    public boolean wasTeleported() {
        return wasTeleported;
    }

    public void setWasTeleported(boolean wasTeleported) {
        this.wasTeleported = wasTeleported;
    }

    //Prevented compilation - Elias

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
//  public Card getRe(int index)
//  {
//    Card aRe = re.get(index);
//    return aRe;
//  }
//
//  public List<Card> getRe()
//  {
//    List<Card> newRe = Collections.unmodifiableList(re);
//    return newRe;
//  }
//
//  public int numberOfRe()
//  {
//    int number = re.size();
//    return number;
//  }
//
//  public boolean hasRe()
//  {
//    boolean has = re.size() > 0;
//    return has;
//  }
//
//  public int indexOfRe(Card aRe)
//  {
//    int index = re.indexOf(aRe);
//    return index;
//  }
//  /* Code from template association_GetMany */
//  public Game getGame(int index)
//  {
//    Game aGame = games.get(index);
//    return aGame;
//  }
//
//  public List<Game> getGames()
//  {
//    List<Game> newGames = Collections.unmodifiableList(games);
//    return newGames;
//  }
//
//  public int numberOfGames()
//  {
//    int number = games.size();
//    return number;
//  }
//
//  public boolean hasGames()
//  {
//    boolean has = games.size() > 0;
//    return has;
//  }
//
//  public int indexOfGame(Game aGame)
//  {
//    int index = games.indexOf(aGame);
//    return index;
//  }
//  /* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfCards()
//  {
//    return 0;
//  }
//  /* Code from template association_AddManyToOne */
//  public Card addCard(Card.CardType aType, String aName, Player aNotebook, Game aGame)
//  {
//    return new Card(aType, aName, this, aNotebook, aGame);
//  }
//
//  public boolean addCard(Card aCard)
//  {
//    boolean wasAdded = false;
//    if (cards.contains(aCard)) { return false; }
//    Player existingHand = aCard.getHand();
//    boolean isNewHand = existingHand != null && !this.equals(existingHand);
//    if (isNewHand)
//    {
//      aCard.setHand(this);
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
//    //Unable to remove aCard, as it must always have a hand
//    if (!this.equals(aCard.getHand()))
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
//  public static int minimumNumberOfRe()
//  {
//    return 0;
//  }
//  /* Code from template association_AddManyToOne */
//  public Card addRe(Card.CardType aType, String aName, Player aHand, Game aGame)
//  {
//    return new Card(aType, aName, aHand, this, aGame);
//  }
//
//  public boolean addRe(Card aRe)
//  {
//    boolean wasAdded = false;
//    if (re.contains(aRe)) { return false; }
//    Player existingNotebook = aRe.getNotebook();
//    boolean isNewNotebook = existingNotebook != null && !this.equals(existingNotebook);
//    if (isNewNotebook)
//    {
//      aRe.setNotebook(this);
//    }
//    else
//    {
//      re.add(aRe);
//    }
//    wasAdded = true;
//    return wasAdded;
//  }
//
//  public boolean removeRe(Card aRe)
//  {
//    boolean wasRemoved = false;
//    //Unable to remove aRe, as it must always have a notebook
//    if (!this.equals(aRe.getNotebook()))
//    {
//      re.remove(aRe);
//      wasRemoved = true;
//    }
//    return wasRemoved;
//  }
//  /* Code from template association_AddIndexControlFunctions */
//  public boolean addReAt(Card aRe, int index)
//  {
//    boolean wasAdded = false;
//    if(addRe(aRe))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfRe()) { index = numberOfRe() - 1; }
//      re.remove(aRe);
//      re.add(index, aRe);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }
//
//  public boolean addOrMoveReAt(Card aRe, int index)
//  {
//    boolean wasAdded = false;
//    if(re.contains(aRe))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfRe()) { index = numberOfRe() - 1; }
//      re.remove(aRe);
//      re.add(index, aRe);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addReAt(aRe, index);
//    }
//    return wasAdded;
//  }
//  /* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfGames()
//  {
//    return 0;
//  }
//  /* Code from template association_AddManyToManyMethod */
//  public boolean addGame(Game aGame)
//  {
//    boolean wasAdded = false;
//    if (games.contains(aGame)) { return false; }
//    games.add(aGame);
//    if (aGame.indexOfPlayer(this) != -1)
//    {
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = aGame.addPlayer(this);
//      if (!wasAdded)
//      {
//        games.remove(aGame);
//      }
//    }
//    return wasAdded;
//  }
//  /* Code from template association_RemoveMany */
//  public boolean removeGame(Game aGame)
//  {
//    boolean wasRemoved = false;
//    if (!games.contains(aGame))
//    {
//      return wasRemoved;
//    }
//
//    int oldIndex = games.indexOf(aGame);
//    games.remove(oldIndex);
//    if (aGame.indexOfPlayer(this) == -1)
//    {
//      wasRemoved = true;
//    }
//    else
//    {
//      wasRemoved = aGame.removePlayer(this);
//      if (!wasRemoved)
//      {
//        games.add(oldIndex,aGame);
//      }
//    }
//    return wasRemoved;
//  }
//  /* Code from template association_AddIndexControlFunctions */
//  public boolean addGameAt(Game aGame, int index)
//  {
//    boolean wasAdded = false;
//    if(addGame(aGame))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfGames()) { index = numberOfGames() - 1; }
//      games.remove(aGame);
//      games.add(index, aGame);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }
//
//  public boolean addOrMoveGameAt(Game aGame, int index)
//  {
//    boolean wasAdded = false;
//    if(games.contains(aGame))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfGames()) { index = numberOfGames() - 1; }
//      games.remove(aGame);
//      games.add(index, aGame);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addGameAt(aGame, index);
//    }
//    return wasAdded;
//  }
//
//  public void delete()
//  {
//    for(int i=cards.size(); i > 0; i--)
//    {
//      Card aCard = cards.get(i - 1);
//      aCard.delete();
//    }
//    for(int i=re.size(); i > 0; i--)
//    {
//      Card aRe = re.get(i - 1);
//      aRe.delete();
//    }
//    ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
//    games.clear();
//    for(Game aGame : copyOfGames)
//    {
//      aGame.removePlayer(this);
//    }
//    super.delete();
//  }

}