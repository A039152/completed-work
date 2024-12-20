package com.amica.games;

/**
 * Represents a single playing card from a traditional poker or bridge deck.
 *
 * @author Will Provost
 */
// Card Enhancements
public class Card implements Comparable<Card> {

    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
    public enum Spot { _2, _3, _4, _5, _6, _7, _8, _9, _10, JACK, QUEEN, KING, ACE }

    private Suit suit;
    private Spot spot;

    public Card(Suit suit, Spot spot) {
        this.suit = suit;
        this.spot = spot;
    }

    public Suit getSuit() {
        return suit;
    }

    public Spot getSpot() {
        return spot;
    }

    private int getOrdinal() {
        return suit.ordinal() * Spot.values().length + spot.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card)) return false;
        Card other = (Card) obj;
        return this.getOrdinal() == other.getOrdinal();
    }

    @Override
    public int hashCode() {
        return getOrdinal();
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.getOrdinal(), other.getOrdinal());
    }

    @Override
    public String toString() {
        return spot + " of " + suit;
    }
}
