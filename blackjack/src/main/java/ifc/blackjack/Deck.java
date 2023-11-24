package ifc.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Integer> deck;
    
    public Deck() {
        this.initializeDeck();
    }

    void initializeDeck() {
        deck = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            deck.add(i);
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        Random rand = new Random();
        for (int i = 0; i < deck.size(); i++) {
            int randomIndex = i + rand.nextInt(deck.size() - i);
            int temp = deck.get(i);
            deck.set(i, deck.get(randomIndex));
            deck.set(randomIndex, temp);
        }
    }

    public int removeCard(){
        int cardNumber;
        return cardNumber = this.deck.remove(0);
    }
}
