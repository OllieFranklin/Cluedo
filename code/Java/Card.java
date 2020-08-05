package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

import java.util.ArrayList;

// line 40 "model.ump"
// line 113 "model.ump"
public class Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    public enum CardType {PLAYER, WEAPON, ROOM}

    // TODO: I CAN'T THINK OF A NICE WAY TO DO THIS - OLLIE

    public enum CardName {

        // player cards
        COLONEL_MUSTARD(CardType.PLAYER, "Colonel Mustard"),
        PROFESSOR_PLUM(CardType.PLAYER, "Professor Plum"),
        REVEREND_GREEN(CardType.PLAYER, "Reverend Green"),
        MRS_PEACOCK(CardType.PLAYER, "Mrs Peacock"),
        MISS_SCARLET(CardType.PLAYER, "Miss Scarlet"),
        MRS_WHITE(CardType.PLAYER, "Mrs White"),

        // weapon cards
        DAGGER(CardType.WEAPON, "Dagger"),
        CANDLESTICK(CardType.WEAPON, "Candlestick"),
        REVOLVER(CardType.WEAPON, "Revolver"),
        ROPE(CardType.WEAPON, "Rope"),
        LEAD_PIPE(CardType.WEAPON, "Lead Pipe"),
        SPANNER(CardType.WEAPON, "Spanner"),

        // room cards
        KITCHEN(CardType.ROOM, "Kitchen"),
        BALLROOM(CardType.ROOM, "Ballroom"),
        CONSERVATORY(CardType.ROOM, "Conservatory"),
        DINING_ROOM(CardType.ROOM, "Dining Room"),
        BILLIARD_ROOM(CardType.ROOM, "Billiard Room"),
        LIBRARY(CardType.ROOM, "Library"),
        LOUNGE(CardType.ROOM, "Lounge"),
        HALL(CardType.ROOM, "Hall"),
        STUDY(CardType.ROOM, "Study");

        private final CardType type;
        private final String name;

        CardName(CardType type, String name) {
            this.type = type;
            this.name = name;
        }

        public CardType getType() {
            return type;
        }

        public String toString() {
            return name;
        }

        public static CardName[] values(CardType type) {
            ArrayList<CardName> output = new ArrayList<>();

            for (CardName c : values())
                if (c.getType() == type)
                    output.add(c);

            return output.toArray(new CardName[0]);
        }
    }

    //Card Attributes
    private CardName name;

    //Card Associations
    private Player hand;
    private Player notebook;
    private Game game;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    //Card constructor needed, but changes to Game will need to be made - Elias

    // this will do for now - Ollie
    public Card(CardName name) {
        this.name = name;
    }

    public CardName getName() {
        return name;
    }

//  public Card(CardType aType, String aName, Player aHand, Player aNotebook, Game aGame)
//  {
//    type = aType;
//    name = aName;
//    boolean didAddHand = setHand(aHand);
//    if (!didAddHand)
//    {
//      throw new RuntimeException("Unable to create card due to hand. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//    boolean didAddNotebook = setNotebook(aNotebook);
//    if (!didAddNotebook)
//    {
//      throw new RuntimeException("Unable to create re due to notebook. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//    boolean didAddGame = setGame(aGame);
//    if (!didAddGame)
//    {
//      throw new RuntimeException("Unable to create card due to game. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//  }

    //------------------------
    // INTERFACE
    //------------------------

    //Prevented compiling - Elias

//  public boolean setType(CardType aType)
//  {
//    boolean wasSet = false;
//    type = aType;
//    wasSet = true;
//    return wasSet;
//  }
//
//  public boolean setName(String aName)
//  {
//    boolean wasSet = false;
//    name = aName;
//    wasSet = true;
//    return wasSet;
//  }
//
//  public CardType getType()
//  {
//    return type;
//  }
//
//  public String getName()
//  {
//    return name;
//  }
//  /* Code from template association_GetOne */
//  public Player getHand()
//  {
//    return hand;
//  }
//  /* Code from template association_GetOne */
//  public Player getNotebook()
//  {
//    return notebook;
//  }
//  /* Code from template association_GetOne */
//  public Game getGame()
//  {
//    return game;
//  }
//  /* Code from template association_SetOneToMany */
//  public boolean setHand(Player aHand)
//  {
//    boolean wasSet = false;
//    if (aHand == null)
//    {
//      return wasSet;
//    }
//
//    Player existingHand = hand;
//    hand = aHand;
//    if (existingHand != null && !existingHand.equals(aHand))
//    {
//      existingHand.removeCard(this);
//    }
//    hand.addCard(this);
//    wasSet = true;
//    return wasSet;
//  }
//  /* Code from template association_SetOneToMany */
//  public boolean setNotebook(Player aNotebook)
//  {
//    boolean wasSet = false;
//    if (aNotebook == null)
//    {
//      return wasSet;
//    }
//
//    Player existingNotebook = notebook;
//    notebook = aNotebook;
//    if (existingNotebook != null && !existingNotebook.equals(aNotebook))
//    {
//      existingNotebook.removeRe(this);
//    }
//    notebook.addRe(this);
//    wasSet = true;
//    return wasSet;
//  }
//  /* Code from template association_SetOneToMany */
//  public boolean setGame(Game aGame)
//  {
//    boolean wasSet = false;
//    if (aGame == null)
//    {
//      return wasSet;
//    }
//
//    Game existingGame = game;
//    game = aGame;
//    if (existingGame != null && !existingGame.equals(aGame))
//    {
//      existingGame.removeCard(this);
//    }
//    game.addCard(this);
//    wasSet = true;
//    return wasSet;
//  }
//
//  public void delete()
//  {
//    Player placeholderHand = hand;
//    this.hand = null;
//    if(placeholderHand != null)
//    {
//      placeholderHand.removeCard(this);
//    }
//    Player placeholderNotebook = notebook;
//    this.notebook = null;
//    if(placeholderNotebook != null)
//    {
//      placeholderNotebook.removeRe(this);
//    }
//    Game placeholderGame = game;
//    this.game = null;
//    if(placeholderGame != null)
//    {
//      placeholderGame.removeCard(this);
//    }
//  }
//
//
//  public String toString()
//  {
//    return super.toString() + "["+
//            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
//            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
//            "  " + "hand = "+(getHand()!=null?Integer.toHexString(System.identityHashCode(getHand())):"null") + System.getProperties().getProperty("line.separator") +
//            "  " + "notebook = "+(getNotebook()!=null?Integer.toHexString(System.identityHashCode(getNotebook())):"null") + System.getProperties().getProperty("line.separator") +
//            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
//  }
}