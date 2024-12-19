package com.amica.games.blackjack;

import com.amica.games.Card;

public class Player {
    private int bank;

    public Player(int stake) {
        this.bank = stake;
    }

    public Player() {
        this(10000);
    }

    public int getBank() {
        return bank;
    }

    public void winOrLose(int amount) {
        bank += amount;
    }

    public PlayerHand play(Card firstCard, Card dealerCard) {
        return new PlayerHand(this, 10, firstCard, dealerCard);
    }
}