package BJack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {
    private Dealer dealer;
    private List<HumanPlayer> players;
    private Deck deck;
    private int seriousness;

    public BlackjackGame(int seriousness, int startingBalance) {
        this.seriousness = seriousness;
        dealer = new Dealer("Dealer", new DealerHitStrategy());
        players = new ArrayList<>();
        players.add(new HumanPlayer("Player", new HumanHitStrategy(), startingBalance));
        deck = new Deck();
    }

    public static int getMaxStartingBalance(int seriousness) {
        return 5000 - ((seriousness - 1) * 450);
    }

    public static int getMinStartingBalance(int seriousness) {
        return (seriousness - 1) * 15 + 5;
    }

    public void playRound() {
        Scanner scanner = new Scanner(System.in);
        boolean keepPlaying = true;

        // Set minimum and maximum bet limits based on table seriousness
        int minBet = Math.max(5, seriousness * 2);
        int maxBet = seriousness * 20;

        while (keepPlaying) {
            // Reset hands and reshuffle the deck
            dealer.resetHand();
            for (HumanPlayer player : players) {
                player.resetHand();
            }
            deck.shuffle();

            // Request bet from player
            System.out.printf("Enter your bet (min: %d, max: %d): ", minBet, maxBet);
            int bet;
            do {
                bet = scanner.nextInt();
                scanner.nextLine(); // Clear newline character from input buffer
                if (bet < minBet || bet > maxBet) {
                    System.out.printf("Invalid bet. Enter a bet between %d and %d: ", minBet, maxBet);
                }
            } while (bet < minBet || bet > maxBet);

            for (HumanPlayer player : players) {
                player.takeCard(deck.drawCard());
                player.takeCard(deck.drawCard());
            }
            dealer.takeCard(deck.drawCard());
            dealer.takeCard(deck.drawCard());

            for (HumanPlayer player : players) {
                System.out.println(player.getName() + "'s turn:");

                while (true) {
                    printGameStatus(false);
                    if (player.shouldHit()) {
                        player.takeCard(deck.drawCard());
                        System.out.println(player.getName() + " hit. Hand value: " + player.getHandValue());
                        if (player.getHandValue() > 21) {
                            System.out.println(player.getName() + " busts!");
                            break; // End the player's turn if they go bust
                        }
                    } else {
                        break;
                    }
                }
            }

            System.out.println("Dealer's turn:");
            while (dealer.shouldHit()) {
                dealer.takeCard(deck.drawCard());
                System.out.println("Dealer hit. Hand value: " + dealer.getHandValue());
            }
            printGameStatus(true);
            if (dealer.getHandValue() > 21) {
                System.out.println("Dealer busts!");
            }

            // Update player balance based on game result
            for (HumanPlayer player : players) {
                if (player.getHandValue() > 21) {
                    System.out.println(player.getName() + " loses!");
                    player.setBalance(player.getBalance() - bet);
                } else if (dealer.getHandValue() > 21 || player.getHandValue() > dealer.getHandValue()) {
                    System.out.println(player.getName() + " wins!");
                    player.setBalance(player.getBalance() + bet);
                } else if (player.getHandValue() == dealer.getHandValue()) {
                    System.out.println(player.getName() + " and Dealer push!");
                } else {
                    System.out.println(player.getName() + " loses!");
                    player.setBalance(player.getBalance() - bet);
                }
                System.out.println(player.getName() + "'s balance: " + player.getBalance());
            }

            System.out.println("Do you want to keep playing? (yes/no):");
            String userInput = scanner.nextLine().trim().toLowerCase();
            keepPlaying = userInput.equals("yes");
        }

        scanner.close();
    }

    private void printGameStatus(boolean showAllDealerCards) {
        System.out.println("Dealer's hand: " + dealer.handToString(!showAllDealerCards) + " (Hand value: " + (showAllDealerCards ? dealer.getHandValue() : "unknown") + ")");
        for (HumanPlayer player : players) {
            System.out.println(player.getName
                    () + "'s hand: " + player.handToString(false) + " (Hand value: " + player.getHandValue() + ")");
                }
            }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the seriousness of the table (1-10): ");
        int seriousness = scanner.nextInt();
        scanner.nextLine();

        int maxStartingBalance = getMaxStartingBalance(seriousness);
        int minStartingBalance = getMinStartingBalance(seriousness);
        System.out.print("Enter your starting balance (between " + minStartingBalance + " and " + maxStartingBalance + "): ");
        int startingBalance = scanner.nextInt();
        scanner.nextLine();

        while (startingBalance < minStartingBalance || startingBalance > maxStartingBalance) {
            System.out.print("Please enter a valid starting balance (between " + minStartingBalance + " and " + maxStartingBalance + "): ");
            startingBalance = scanner.nextInt();
            scanner.nextLine();
        }

        BlackjackGame game = new BlackjackGame(seriousness, startingBalance);
        game.playRound();
    }
    
}

        
