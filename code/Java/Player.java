package Java;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

import java.util.*;
import Java.Card.*;

public class Player extends Item {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Player Associations

    private Set<Card> hand;
    private Set<Card> notepad;

    private boolean isOut;          // player is out of the game if they've made an incorrect accusation
    private boolean wasTeleported;  // player was teleported into their current room < 1 turn ago

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player(Cell cell, PlayerCard cardName, String printString) {
        super(cell, cardName, printString);

        hand = new HashSet<>();
        notepad = new HashSet<>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public void dealCard(Card card) {
        hand.add(card);
        notepad.add(card);
    }

    public boolean holdsCard(Card cardName) {
        return hand.contains(cardName);
    }

    public boolean knowsAboutCard(Card cardName) { return notepad.contains(cardName); }

    public void addToNotepad(Card card) {
        notepad.add(card);
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

    public String toString() { return super.getCard().toString(); }

}