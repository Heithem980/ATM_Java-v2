public class Card {
	private String id;
	private String pin;
	private int amount;

	public Card(String id, String pin) {
		this.id = id;
		this.pin = pin;
                this.amount = 0;
	}

	public Card(String id, String pin, int amount) {
		this.id = id;
		this.pin = pin;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
}