package BJack;

public class HumanPlayer extends AbstractPlayer {
    private int balance;

    public HumanPlayer(String name, HitStrategy hitStrategy, int startingBalance) {
        super(name, hitStrategy);
        this.balance = startingBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
