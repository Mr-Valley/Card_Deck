package Games;

import java.util.ArrayList;

import deck.*;

public class BlackJack {
	private ArrayList<Card> dealerCards = new ArrayList<Card>();
	private ArrayList<Card> playerCards = new ArrayList<Card>();
	private Deck deck;
	private int playerMoney;
	private int playerBet;

	// default constructor
	public BlackJack() {
		playerMoney = 1000;
		deck = new Deck(6);
		this.dealCards();
	}

	// constructor specifying number of decks
	public BlackJack(int numberOfDecks) {
		playerMoney = 1000;
		deck = new Deck(numberOfDecks);
		this.dealCards();
	}

	// operational methods

	// resets both player and dealer hands and adds 2 cards.
	public void dealCards() {
		deck.shuffle(deck.getNumberOfDecks());
		
		dealerCards.clear();
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
	
	public String checkWinner() {
		deck.debugCards();
		if ((this.getPlayerValue() > this.getDealerValue() && this.getPlayerValue() <= 21) || this.getDealerValue() > 21) {
			playerMoney += playerBet;
			return "Player wins $" + this.playerBet;
		} else if (this.getPlayerValue() < this.getDealerValue()){
			playerMoney -= playerBet;
			return "Player loses $" + this.playerBet;
		} else
			return "Tie";
		
	}

	// draws a card
	public void dealerDrawCard() {
		dealerCards.add(deck.draw());
	}

	public void playerDrawCard() {
		playerCards.add(deck.draw());
	}

	// getters and setters
	
	public int getPlayerMoney() {
		return this.playerMoney;
	}

	public int getPlayerBet() {
		return playerBet;
	}

	public int setPlayerBet(int playerBet) {
		if (playerBet <= this.playerMoney)
			this.playerBet = playerBet;
		else 
			this.playerBet = this.playerMoney;
		return this.playerBet;
	}

	public boolean isPlayerBlackJack() {
		return this.isBlackJack(this.playerCards);
	}

	public boolean isDealerBlackJack() {
		return this.isBlackJack(this.dealerCards);
	}

	public int getPlayerValue() {
		return this.getHandValue(this.playerCards);
	}

	public int getDealerValue() {
		return this.getHandValue(this.dealerCards);
	}

	public ArrayList<Card> getDealerCards() {
		return dealerCards;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}
}
