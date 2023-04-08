package BJack;

public class DealerHitStrategy implements HitStrategy {
    @Override
    public boolean shouldHit(AbstractPlayer player) {
        return player.getHandValue() < 17;
    }
}
