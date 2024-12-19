package com.amica.games.blackjack;

import com.amica.games.Card;

import java.util.ArrayList;
import java.util.List;

public class DealerHand {
    private final List<Card> cards = new ArrayList<>();

    public DealerHand(Card firstCard, Card secondCard) {
        cards.add(firstCard);
        cards.add(secondCard);
    }

    public int getPoints() {
        int points = 0;
        int aces = 0;

        for (Card card : cards) {
            switch (card.getSpot()) {
                case JACK, QUEEN, KING -> points += 10;
                case ACE -> aces++;
                default -> points += Integer.parseInt(card.getSpot().toString().replace("_", ""));
            }
        }

        while (aces > 0 && points + 10 <= 21) {
            points += 10;
            aces--;
        }
        return points;
    }

    public boolean isBust() {
        return getPoints() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getPoints() == 21;
    }

    public boolean shouldHit() {
        return getPoints() < 17;
    }

    public void hit(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        return "DealerHand{" + "cards=" + cards + '}';
    }
}
