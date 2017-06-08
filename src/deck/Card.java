package deck;

import java.util.Random;

public class Card {
	private int value;
	private int suit;

	public Card() {
		Random rand = new Random();
		this.value = rand.nextInt(13) + 1;
		this.suit = rand.nextInt(3);

	}

	public Card(int value, int suit) {
		this.setValue(value);
		this.setSuit(suit);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (value > 0 && value <= 13)
			this.value = value;
		else {
			System.out.println("Invalid card, overwriting");
			this.value = 2;
		}
	}

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		if (suit >= 0 && suit < 5)
			this.suit = suit;
		else {
			System.out.println("Invalid card, overwriting");
			this.suit = 0;
		}
	}

	// returns a formatted string that can be directly displayed
	public String toString() {
		String cardName = "";
		switch (this.value) {
		case 1:
			cardName += "Ace";
			break;
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			cardName += this.value;
			break;
		case 11:
			cardName += "Jack";
			break;
		case 12:
			cardName += "Queen";
			break;
		case 13:
			cardName += "King";
			break;
		}

		switch (this.suit) {
		case 0:
			cardName += " of Clubs";
			break;
		case 1:
			cardName += " of Diamonds";
			break;
		case 2:
			cardName += " of Hearts";
			break;
		case 3:
			cardName += " of Spades";
			break;
		}

		return cardName;
	}

	// @Override
	// public String toString() {
	// return "Card [value=" + value + ", suit=" + suit + "]";
	// }

}
