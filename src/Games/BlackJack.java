package Games;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import deck.*;

public class BlackJack {
	private ArrayList<Card> dealerCards = new ArrayList<Card>();
	private ArrayList<Card> playerCards = new ArrayList<Card>();
	private Deck deck;
	private int playerMoney;
	private int playerBet;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private static boolean DEBUG = false;

	// default constructor
	public BlackJack() {
		playerMoney = 1000;
		deck = new Deck(6);
	}

	// constructor specifying number of decks
	public BlackJack(int numberOfDecks) {
		playerMoney = 1000;
		deck = new Deck(numberOfDecks);
	}

	// master method for game loop
	public void begin() {
		boolean again = true;
		try {
			while (again) {
				this.dealCards();
				System.out.println("Enter bet");
				this.setPlayerBet(Integer.parseInt(this.reader.readLine()));

				// debug for testing splits
				this.playerCards.get(0).setValue(this.playerCards.get(1).getValue());

				this.clearAndDisplayHands();

				// prompt for double split
				if (this.playerCards.get(0).getValue() == this.playerCards.get(1).getValue()) {
					System.out.println("Split?");
					while (true) {
						if (this.reader.readLine().equals("y")) {
							// backup second card
							Card card = this.playerCards.get(1);
							this.playerCards.remove(1);
							this.playerDrawCard();

							// play game with first hand
							this.clearAndDisplayHands();
							this.playGame();
							System.out.println("\n\nSwitching to second hand");
							this.reader.readLine();

							// prepare second hand
							this.playerCards.clear();
							this.playerCards.add(card);
							this.playerDrawCard();

							// play game with second hand
							this.clearAndDisplayHands();
							this.playGame();
							break;
						} else if (this.reader.readLine().equals("n")) {
							this.playGame();
							break;
						}
					}
				}

				while (true) {
					System.out.println("Play again? (y/n)");
					String choice = this.reader.readLine();
					if (choice.equals("y"))
						break;
					else {
						again = false;
						break;
					}
				}
			}
			this.reader.close();

		} catch (IOException ioe) {
			// TODO stabilize
			System.out.println("code broke");
		}
		System.out.println("Done");
	}

	// main game methods

	// game
	private void playGame() {
		// do hits
		this.doPlayerTurn();
		this.doDealerTurn();

		System.out.println("\n\n" + this.checkWinner());
		System.out.println("Player has $" + this.playerMoney);
	}

	// player's turn
	private void doPlayerTurn() {
		try {
			while (true) {
				System.out.println("Hit? (y/n)");
				String choice = this.reader.readLine();
				if (choice.equals("y")) {
					this.playerDrawCard();
					System.out.println("Player has: " + this.playerCards);
					System.out.println("Total value: " + this.getPlayerValue());
					if (this.getPlayerValue() > 21) {
						System.out.println("Player busted!");
						this.reader.readLine();
						break;
					}
				} else if (choice.equals("n"))
					break;
			}
		} catch (

		IOException ioe) {
			// TODO stabilize
			System.out.println("code broke");
		}
	}

	// dealer's turn
	private void doDealerTurn() {
		try {
			if (this.getPlayerValue() <= 21) {
				// dealer hits
				System.out.println("Dealer has: " + this.dealerCards);
				System.out.println("Total value: " + this.getDealerValue());
				while (true) {
					this.dealerDrawCard();
					System.out.println("Dealer drew");
					System.out.println("Dealer has: " + this.dealerCards);
					System.out.println("Total value: " + this.getDealerValue());
					if (this.getDealerValue() < this.getPlayerValue())
						break;
					else if (this.getDealerValue() > 21) {
						System.out.println("Dealer busted!");
						break;
					}
					reader.readLine();
				}
			}
		} catch (

		IOException ioe) {
			// TODO stabilize
			System.out.println("code broke");
		}
	}

	// operational methods

	// clears screen and displays both player's hands
	private void clearAndDisplayHands() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("Dealer has: " + this.dealerCards.get(0));
		System.out.println("Player has: " + this.playerCards);
		System.out.println("Total value: " + this.getPlayerValue());
	}

	// resets both player and dealer hands and adds 2 cards.
	private void dealCards() {
		deck.shuffle(deck.getNumberOfDecks());

		dealerCards.add(deck.draw());
		dealerCards.add(deck.draw());

		playerCards.clear();
		playerCards.add(deck.draw());
		playerCards.add(deck.draw());
	}

	// outputs the highest value of the hand while not going over 21
	private int getHandValue(ArrayList<Card> hand) {
		int cardValues[] = new int[22];
		int numberOfAces = 0;
		int totalValue = 0;

		for (int i = 0; i < hand.size(); i++)
			cardValues[i] = hand.get(i).getValue();
		for (int i : cardValues) {
			switch (i) {
			case 0:
			case 1:
				numberOfAces++; // intentional overflow, keeps track of aces if
								// needed to be added later
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				totalValue += i;
				break;
			case 11:
			case 12:
			case 13:
				totalValue += 10;
				break;
			}
		}

		// logic for adding aces to total value
		while (numberOfAces == 0 && totalValue > 21) {
			if (totalValue + 10 <= 21) {
				numberOfAces--;
				totalValue += 10;
			} else
				numberOfAces = 0;
		}

		return totalValue;
	}

	// returns true if the hand is a blackjack (2 cards, one ace and one card
	// valued at 10
	private boolean isBlackJack(ArrayList<Card> hand) {
		if (hand.size() == 2 && this.getHandValue(hand) == 21)
			return true;
		else
			return false;
	}

	private String checkWinner() {
		if (BlackJack.DEBUG)
			deck.debugCards();
		if ((this.getPlayerValue() > this.getDealerValue() && this.getPlayerValue() <= 21)
				|| this.getDealerValue() > 21) {
			playerMoney += playerBet;
			return "Player won $" + this.playerBet;
		} else if (this.getPlayerValue() < this.getDealerValue()) {
			playerMoney -= playerBet;
			return "Player loses $" + this.playerBet;
		} else
			return "Tie";

	}

	// draws a card
	private void dealerDrawCard() {
		dealerCards.add(deck.draw());
	}

	private void playerDrawCard() {
		playerCards.add(deck.draw());
	}

	// getters and setters

	private int setPlayerBet(int playerBet) {
		if (playerBet <= this.playerMoney)
			this.playerBet = playerBet;
		else
			this.playerBet = this.playerMoney;
		return this.playerBet;
	}

	private boolean isPlayerBlackJack() {
		return this.isBlackJack(this.playerCards);
	}

	private boolean isDealerBlackJack() {
		return this.isBlackJack(this.dealerCards);
	}

	private int getPlayerValue() {
		return this.getHandValue(this.playerCards);
	}

	private int getDealerValue() {
		return this.getHandValue(this.dealerCards);
	}

}
