package deck;

import java.util.ArrayList;

//not necessary? 
public class Hand {
	private ArrayList<Card> cards = new ArrayList<Card>();

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
}
