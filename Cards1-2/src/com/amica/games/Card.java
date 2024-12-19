package com.amica.games;

public class Card {
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    public enum Spot {
        _2, _3, _4, _5, _6, _7, _8, _9, _10, JACK, QUEEN, KING, ACE
    }

    private final Suit suit;
    private final Spot spot;

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
}