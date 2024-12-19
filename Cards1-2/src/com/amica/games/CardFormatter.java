package com.amica.games;

public class CardFormatter {

    public static String abbreviationOf(Card.Spot spot) {
        String spotString = spot.toString().replace("_", "");
        return spotString.length() > 1 ? spotString : spotString.charAt(0) + "";
    }

    public static String abbreviationOf(Card card) {
        return abbreviationOf(card.getSpot()) + card.getSuit().toString().charAt(0);
    }

    public static String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }

    public static String nameOf(Card.Suit suit) {
        return capitalize(suit.toString());
    }

    public static String nameOf(Card.Spot spot) {
        return capitalize(spot.toString().replace("_", ""));
    }

    public static String nameOf(Card card) {
        return nameOf(card.getSpot()) + " of " + nameOf(card.getSuit());
    }
}
