package com.amica.games.poker;

import com.amica.games.Card;
import java.util.*;

public class Player extends TreeSet<Card> {

    public enum Rank {
        NO_PAIR, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH,
        FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH
    }

    private static class CompareBySpot implements Comparator<Card> {
        @Override
        public int compare(Card c1, Card c2) {
            int spotComparison = Integer.compare(c1.getSpot().ordinal(), c2.getSpot().ordinal());
            return spotComparison != 0 ? spotComparison : c1.getSuit().ordinal() - c2.getSuit().ordinal();
        }
    }

    public Player(Collection<Card> cards) {
        super(new CompareBySpot());
        addAll(cards);
    }

    public Rank getRank() {
        Map<Card.Spot, Integer> counts = getCounts();
        Collection<Integer> values = counts.values();

        if (values.contains(4)) return Rank.FOUR_OF_A_KIND;
        if (values.contains(3)) {
            return values.contains(2) ? Rank.FULL_HOUSE : Rank.THREE_OF_A_KIND;
        }
        if (values.contains(2)) {
            long pairCount = values.stream().filter(v -> v == 2).count();
            return pairCount == 2 ? Rank.TWO_PAIR : Rank.ONE_PAIR;
        }

        boolean flush = this.stream().map(Card::getSuit).distinct().count() == 1;
        List<Card> sortedCards = new ArrayList<>(this);
        boolean straight = sortedCards.get(sortedCards.size() - 1).getSpot().ordinal() -
                sortedCards.get(0).getSpot().ordinal() == 4 &&
                new HashSet<>(this).size() == 5;

        if (flush && straight) return Rank.STRAIGHT_FLUSH;
        if (flush) return Rank.FLUSH;
        if (straight) return Rank.STRAIGHT;

        return Rank.NO_PAIR;
    }

    private Map<Card.Spot, Integer> getCounts() {
        Map<Card.Spot, Integer> counts = new HashMap<>();
        for (Card card : this) {
            counts.put(card.getSpot(), counts.getOrDefault(card.getSpot(), 0) + 1);
        }
        return counts;
    }

    public int getNumberToDraw() {
        Map<Card.Spot, Integer> counts = getCounts();
        Rank rank = getRank();

        switch (rank) {
            case NO_PAIR:
                return Math.min(5, this.size()); // Replace all cards if no pair
            case ONE_PAIR:
                return Math.min(3, Math.max(0, this.size() - 2)); // Keep the pair, replace three cards
            case TWO_PAIR:
                return Math.min(1, Math.max(0, this.size() - 4)); // Keep two pairs, replace one card
            case THREE_OF_A_KIND:
                return Math.min(2, Math.max(0, this.size() - 3)); // Keep three of a kind, replace two cards
            case STRAIGHT:
            case FLUSH:
            case FULL_HOUSE:
            case FOUR_OF_A_KIND:
            case STRAIGHT_FLUSH:
                return 0; // No cards need to be drawn for these hands
            default:
                return 0;
        }
    }

    public void draw(List<Card> newCards) {
        int drawCount = Math.min(getNumberToDraw(), newCards.size()); // Ensure we only draw as many as available

        // Debugging: Log the adjusted draw count
        System.out.println("DEBUG: Expected to draw " + getNumberToDraw() + " cards, but drawing " + drawCount + " cards due to deck size.");

        // Keep only the cards contributing to the current rank
        Map<Card.Spot, Integer> counts = getCounts();
        List<Card> toKeep = new ArrayList<>();
        Rank rank = getRank();

        switch (rank) {
            case ONE_PAIR:
                for (Card card : this) {
                    if (counts.get(card.getSpot()) == 2) {
                        toKeep.add(card);
                    }
                }
                break;
            case TWO_PAIR:
            case THREE_OF_A_KIND:
                for (Card card : this) {
                    if (counts.get(card.getSpot()) > 1) {
                        toKeep.add(card);
                    }
                }
                break;
            case STRAIGHT:
            case FLUSH:
            case FULL_HOUSE:
            case FOUR_OF_A_KIND:
            case STRAIGHT_FLUSH:
                toKeep.addAll(this); // Keep all cards
                break;
            default:
                break;
        }

        // Clear existing cards and add the ones we want to keep
        this.clear();
        this.addAll(toKeep);

        // Safely add cards from the newCards list without exceeding the drawCount
        if (drawCount > 0) {
            for (int i = 0; i < drawCount; i++) {
                if (i < newCards.size()) {
                    this.add(newCards.get(i)); // Add only available cards
                }
            }
        }

        // Debugging: Log the final hand and rank after the draw
        System.out.println("DEBUG: After draw, hand is " + this + ", rank: " + getRank());
    }

}
