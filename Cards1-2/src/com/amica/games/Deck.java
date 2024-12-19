package com.amica.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Spot spot : Card.Spot.values()) {
                cards.add(new Card(suit, spot));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public List<Card> deal(int numberOfCards) {
        List<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < numberOfCards && !cards.isEmpty(); i++) {
            dealtCards.add(deal());
        }
        return dealtCards;
    }

    public boolean isCardAvailable() {
        return !cards.isEmpty();
    }

    public int cardsLeft() {
        return cards.size();
    }
}