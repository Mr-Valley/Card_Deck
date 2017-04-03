import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Games.BlackJack;

public class App {
	public static void main(String[] args) {
		BlackJack bj = new BlackJack();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean again = true;
		try {
			while (again) {
				bj.dealCards();

				System.out.println("Enter bet");
				bj.setPlayerBet(Integer.parseInt(reader.readLine()));

				System.out.println("Dealer has: " + bj.getDealerCards().get(0));
				System.out.println("Player has: " + bj.getPlayerCards());
				System.out.println("Total value: " + bj.getPlayerValue());

				// player hits
				while (true) {
					System.out.println("Hit? (y/n)");
					String choice = reader.readLine();
					if (choice.equals("y")) {
						bj.playerDrawCard();
						System.out.println("Player has: " + bj.getPlayerCards());
						System.out.println("Total value: " + bj.getPlayerValue());
						if (bj.getPlayerValue() > 21) {
							System.out.println("Player busted!");
							reader.readLine();
							break;
						}
					} else if (choice.equals("n"))
						break;
				}
				System.out.println(bj.getPlayerValue());
				if (bj.getPlayerValue() <= 21) {
					// dealer hits
					System.out.println("Dealer has: " + bj.getDealerCards());
					System.out.println("Total value: " + bj.getDealerValue());
					while (true) {
						bj.dealerDrawCard();
						System.out.println("Dealer drew");
						System.out.println("Dealer has: " + bj.getDealerCards());
						System.out.println("Total value: " + bj.getDealerValue());
						if (bj.getDealerValue() < bj.getPlayerValue())
							break;
						else if (bj.getDealerValue() > 21) {
							System.out.println("Dealer busted!");
							break;
						}
						reader.readLine();
					}
				}
				System.out.println("\n\n" + bj.checkWinner());
				System.out.println("Player has $" + bj.getPlayerMoney());
				while (true) {
					System.out.println("Play again? (y/n)");
					String choice = reader.readLine();
					if (choice.equals("y"))
						break;
					else {
						again = false;
						break;
					}
				}
			}
			
			
			
		} catch (

		IOException ioe) {
			// todo stabilize
			System.out.println("code broke");
		}
		System.out.println("Done");
	}
}
