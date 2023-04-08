package BJack;

import java.util.Scanner;

public class HumanHitStrategy implements HitStrategy {
    private Scanner scanner;

    public HumanHitStrategy() {
        scanner = new Scanner(System.in);
    }

    @Override
    public boolean shouldHit(AbstractPlayer player) {
        System.out.println("Do you want to hit? (yes/no):");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("yes");
    }
}
