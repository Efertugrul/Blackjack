package BJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
    	if (cards.isEmpty()) {
    		throw new IllegalStateException("Deck is empty");
    		}
    		return cards.remove(cards.size() - 1);
    		}
    public boolean isEmpty() {
    	return cards.isEmpty();
    }
    }
