import deck.Card;
import deck.Deck;

public class App {
	public static void main(String[] args){
	 	Deck deck = new Deck();
	 	for (int i=0;i<52;i++) {
	 		System.out.println(deck.draw().toPrettyString());
	 		
	 	}
	 	
	}
}
