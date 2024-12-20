package com.amica.games.bridge;

import com.amica.games.Card;
import com.amica.games.Deck;
import java.util.ArrayList;
import java.util.List;

public class Bridge {

    private List<Player> players;
    private int leader;

    public Bridge() {
        players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player());
        }
        leader = 0;
    }

    private int next(int current) {
        return (current + 1) % 4;
    }

    public void deal() {
        Deck deck = new Deck();
        while (deck.cardsLeft() > 0) {
            for (Player player : players) {
                if (deck.cardsLeft() > 0) {
                    player.acceptCard(deck.deal());
                }
            }
        }
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + i + ": " + players.get(i));
        }
    }

    public void play() {
        int[] tricksWon = new int[4];

        for (int i = 0; i < 13; i++) {
            Trick trick = new Trick();
            System.out.print("Player " + leader + " leads ... ");
            for (int j = 0; j < 4; j++) {
                int currentPlayer = (leader + j) % 4;
                Card playedCard = players.get(currentPlayer).play(trick);
                trick.add(playedCard);
                System.out.print(playedCard + (j < 3 ? ", " : "\n"));
            }
            int winner = trick.getWinner();
            tricksWon[(leader + winner) % 4]++;
            leader = (leader + winner) % 4;
        }

        System.out.println("\nTricks won:");
        for (int i = 0; i < 4; i++) {
            System.out.println("Player " + i + ": " + tricksWon[i]);
        }

        System.out.println("\nTeam 1: " + (tricksWon[0] + tricksWon[2]) + " tricks.");
        System.out.println("Team 2: " + (tricksWon[1] + tricksWon[3]) + " tricks.");
    }

    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        bridge.deal();
        bridge.play();
    }
}
