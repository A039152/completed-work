package com.amica.games.bridge;

import com.amica.games.Card;
import java.util.*;

// Bridge Player
public class Player {

    private Map<Card.Suit, SortedSet<Card>> cards;

    public Player() {
        cards = new TreeMap<>();
        for (Card.Suit suit : Card.Suit.values()) {
            cards.put(suit, new TreeSet<>(Collections.reverseOrder()));
        }
    }

    public void acceptCard(Card card) {
        cards.get(card.getSuit()).add(card);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card card : cards.get(suit)) {
                sb.append(card.getSpot()).append(" ");
            }
            sb.append("of ").append(suit).append(" -- ");
        }
        return sb.toString();
    }

    public Card play(Trick trick) {
        if (trick.isEmpty()) {
            // Lead with the highest card from the longest suit
            System.out.println("DEBUG: Leading card selection process.");
            Card highest = null;
            int longestSuitSize = 0;
            for (Card.Suit suit : cards.keySet()) {
                int suitSize = cards.get(suit).size();
                if (suitSize > longestSuitSize || (suitSize == longestSuitSize && !cards.get(suit).isEmpty() && cards.get(suit).first().compareTo(highest) > 0)) {
                    longestSuitSize = suitSize;
                    highest = cards.get(suit).first();
                }
            }
            System.out.println("DEBUG: Leading with " + highest + " from the longest suit.");
            cards.get(highest.getSuit()).remove(highest);
            return highest;
        } else {
            // Follow the leading suit
            Card.Suit ledSuit = trick.get(0).getSuit();
            System.out.println("DEBUG: Following suit: " + ledSuit);
            if (!cards.get(ledSuit).isEmpty()) {
                // Find the lowest card in the leading suit
                Card lowestInSuit = cards.get(ledSuit).last();
                // Find the highest card in the leading suit
                Card highestInSuit = cards.get(ledSuit).first();
                // Determine if the player can win
                boolean canWin = true;
                for (Card card : trick) {
                    if (card.getSuit() == ledSuit && card.compareTo(highestInSuit) > 0) {
                        canWin = false;
                        break;
                    }
                }
                if (canWin) {
                    System.out.println("DEBUG: Attempting to win with the highest card in suit: " + highestInSuit);
                    cards.get(ledSuit).remove(highestInSuit);
                    return highestInSuit;
                } else {
                    System.out.println("DEBUG: Cannot win, playing lowest card: " + lowestInSuit);
                    cards.get(ledSuit).remove(lowestInSuit);
                    return lowestInSuit;
                }
            } else {
                // Play the lowest card overall when void in the leading suit
                System.out.println("DEBUG: Void in " + ledSuit + ", playing lowest card overall.");
                Card lowest = null;
                for (Card.Suit suit : cards.keySet()) {
                    if (!cards.get(suit).isEmpty()) {
                        Card candidate = cards.get(suit).last();
                        if (lowest == null || candidate.compareTo(lowest) < 0) {
                            lowest = candidate;
                        }
                    }
                }
                System.out.println("DEBUG: Playing lowest card: " + lowest);
                cards.get(lowest.getSuit()).remove(lowest);
                return lowest;
            }
        }
    }


}
