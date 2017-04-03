package deck;

import java.util.*;

public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();
	private int numberOfDecks;
	
	public Deck() {
		this.shuffle(1);
	}
	
	public Deck(int numberOfDecks) {
		this.shuffle(numberOfDecks);
	}
	
	//fills deck with cards, in order
	public void shuffle(int numberOfDecks) {
		this.numberOfDecks = numberOfDecks;
		cards.clear();
		for(int i=0;i<this.numberOfDecks;i++) {
			for (int j=1;j<=13;j++) {
				cards.add(new Card(j,0));
				cards.add(new Card(j,1));
				cards.add(new Card(j,2));
				cards.add(new Card(j,3));
			}
		}
	}
	
	//draws card by skipping a random number of cards in an ordered deck, and removing that card
	public Card draw() {
		Random rand = new Random();
		int cardIndex = rand.nextInt(cards.size());
		Card drawnCard = cards.get(cardIndex);
		cards.remove(cardIndex);
		
		return drawnCard;
	}

	public int getNumberOfDecks() {
		return numberOfDecks;
	}

	public int getNumberOfRemainingCards() {
		return cards.size();
	}
	
	public void debugCards() {
		//create and fill array to track contents of cards
		int remainingCards[][] = new int[4][13];
		for (int i=0;i<remainingCards.length;i++) 
			for (int j=0;j<remainingCards[i].length;j++)
				remainingCards[i][j] = 0;
		
		//count all cards present in the array
		for (int i=0;i<cards.size();i++)
			remainingCards[cards.get(i).getSuit()][cards.get(i).getValue()-1]++;
		
		//display a table of all cards
		System.out.println("\tA\t2\t3\t4\t5\t6\t7\t8\t9\t10\tJ\tQ\tK");
		for (int i=0;i<remainingCards.length;i++) {
			System.out.print(i + "\t");
			for (int j=0;j<remainingCards[i].length;j++)
				System.out.print(remainingCards[i][j] + "\t");
			System.out.println();
		}
	}
}
