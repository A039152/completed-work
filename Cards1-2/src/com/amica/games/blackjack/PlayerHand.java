package com.amica.games.blackjack;

import com.amica.games.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {
    private final Player owner;
    private final int wager;
    private final List<Card> cards = new ArrayList<>();
    private final Card dealerCard;
    private int id;
    private boolean doubled;

    public PlayerHand(Player owner, int wager, Card firstCard, Card dealerCard) {
        this.owner = owner;
        this.wager = wager;
        this.dealerCard = dealerCard;
        cards.add(firstCard);
    }

    public Player getOwner() {
        return owner;
    }

    public int getWager() {
        return wager;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public boolean isDoubled() {
        return doubled;
    }

    public Blackjack.Action secondCard(Card newCard) {
        cards.add(newCard);

        int handPoints = getPoints();
        Card visibleDealerCard = dealerCard;

        // Check for pairs that can be split
        if (cards.size() == 2 && cards.get(0).getSpot() == cards.get(1).getSpot()) {
            Card.Spot matchingSpot = cards.get(0).getSpot();
            if (matchingSpot == Card.Spot._2 || matchingSpot == Card.Spot._3 ||
                    matchingSpot == Card.Spot._6 || matchingSpot == Card.Spot._7 ||
                    matchingSpot == Card.Spot._8 || matchingSpot == Card.Spot._9 ||
                    matchingSpot == Card.Spot.ACE) {
                return Blackjack.Action.SPLIT;
            }
        }

        // Check for surrender condition
        if (handPoints == 16 && visibleDealerCard.getSpot() == Card.Spot._9) {
            return Blackjack.Action.SURRENDER;
        }

        if (handPoints == 10 || handPoints == 11) {
            doubled = true;
            return Blackjack.Action.DOUBLE;
        }

        // Default to no specific action
        return Blackjack.Action.NONE;
    }



    public int getPoints() {
        int totalPoints = 0;
        int acesCount = 0;

        // Loop through the cards to calculate the total value
        for (Card currentCard : cards) {
            if (currentCard.getSpot().toString().startsWith("_")) {
                // Add the numeric value of numbered cards
                totalPoints += Integer.parseInt(currentCard.getSpot().toString().substring(1));
            } else if (currentCard.getSpot() == Card.Spot.ACE) {
                // Count Aces and initially add 1 point for each
                acesCount++;
                totalPoints += 1;
            } else {
                // Face cards add 10 points
                totalPoints += 10;
            }
        }

        // Adjust Aces to maximize their value without exceeding 21
        while (acesCount > 0 && totalPoints + 10 <= 21) {
            totalPoints += 10;
            acesCount--;
        }

        return totalPoints;
    }


    public boolean isBust() {
        return getPoints() > 21;
    }

    public boolean isBlackjack() {
        boolean result = cards.size() == 2 && getPoints() == 21;
        System.out.println("DEBUG: Is blackjack: " + result + " (Points: " + getPoints() + ")");
        return result;
    }


    public boolean shouldHit() {
        int handTotal = getPoints(); // Get the player's hand value
        Card dealerVisibleCard = dealerCard;

        // Always hit for totals below 12
        if (handTotal < 12) {
            return true;
        }

        // Never hit for totals above 16
        if (handTotal > 16) {
            return false;
        }

        // For totals between 12 and 16, evaluate the dealer's card
        if (handTotal >= 12 && handTotal <= 16) {
            // Hit if the dealer's card is 7 or higher
            return dealerVisibleCard.getSpot().ordinal() >= 7;
        }

        // Default action for unclear scenarios
        return false;
    }


    public void hit(Card card) {
        cards.add(card);
    }

    public void winOrLose(int amount) {
        owner.winOrLose(amount);
    }

    public void dubble(Card card) {
        hit(card);
    }

    public List<PlayerHand> split() {
        List<PlayerHand> newHands = new ArrayList<>();
        newHands.add(new PlayerHand(owner, wager, cards.get(0), dealerCard));
        newHands.add(new PlayerHand(owner, wager, cards.get(1), dealerCard));
        return newHands;
    }

    @Override
    public String toString() {
        return "PlayerHand{" + "owner=" + owner + ", wager=" + wager + ", cards=" + cards + '}';
    }
}
