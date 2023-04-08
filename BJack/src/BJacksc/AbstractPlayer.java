package BJack;

import java.util.ArrayList;
import java.util.List;

import BJack.Card.Rank;


public abstract class AbstractPlayer {
    protected String name;
    protected HitStrategy hitStrategy;
    protected List<Card> hand;

    public AbstractPlayer(String name, HitStrategy hitStrategy) {
        this.name = name;
        this.hitStrategy = hitStrategy;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHandValue() {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }


    public void takeCard(Card card) {
        hand.add(card);
    }

    public boolean shouldHit() {
        return hitStrategy.shouldHit(this);
    }

    public void resetHand() {
        hand.clear();
    }

    public List<Card> getHand() {
        return hand;
    }
    public String handToString(boolean hideFirstCard) {
        StringBuilder stringb = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            if (hideFirstCard && i == 0) {
                stringb.append("[HIDDEN]");
            } else {
                stringb.append(hand.get(i).toString());
            }

            if (i < hand.size() - 1) {
                stringb.append(", "); 
            }
        }
        return stringb.toString(); 
    }

    	
    
}
