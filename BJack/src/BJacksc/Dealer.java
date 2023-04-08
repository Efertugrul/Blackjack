package BJack;

public class Dealer extends AbstractPlayer {

    public Dealer(String name, HitStrategy hitStrategy) {
        super(name, hitStrategy);
    }

    @Override
    public boolean shouldHit() {
        return getHandValue() < 17;
    }

  
}
