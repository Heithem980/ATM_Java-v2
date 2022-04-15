public class User {
	private Card card;
	private int numberOfErrors;

	public User(Card card, int numberOfErrors) {
		this.card = card;
		this.numberOfErrors = numberOfErrors;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getNumberOfErrors() {
		return numberOfErrors;
	}

	public void setNumberOfErrors(int numberOfErrors) {
		this.numberOfErrors = numberOfErrors;
	}

}