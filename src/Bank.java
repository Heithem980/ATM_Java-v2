import java.util.ArrayList;
import java.util.List;

public class Bank {

	private static String bankName = "ABC Bank.";

	List<User> users = new ArrayList<>();

	public Bank(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// This method is used to get connected bank name
	public static String getConnectedBankName() {
		return bankName;
	}

	// This method is used to authenticate user
	public boolean authenticateUser(Card card) {

		for (int i = 0; i < users.size(); i++) {
			Card bankCard = users.get(i).getCard();
			if (bankCard.getId().equals(card.getId()) && bankCard.getPin().equals(card.getPin())) {
				if (users.get(i).getNumberOfErrors() < 3) {
					return true;
				}
			}
		}
		return false;
	}

	// This method is used to check current account balance
	public int checkAmount(Card card) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getCard().getId().equals(card.getId())
					&& users.get(i).getCard().getPin().equals(card.getPin())) {
				return users.get(i).getCard().getAmount();
			}
		}
		return 0;
	}

	// This method is used to withdraw amount
	public boolean withdrawAmount(Card card, int amount) {
		if (amount <= 0 || checkAmount(card) - amount < 0) {
			return false;
		} else {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getCard().getId().equals(card.getId())) {
					users.get(i).getCard().setAmount(users.get(i).getCard().getAmount() - amount);
					return true;
				}
			}
			return false;
		}
	}

	// This method is used to deposit amount
	public boolean depositAmount(Card card, int amount) {
		if (amount <= 0) {
			return false;
		} else {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getCard().getId().equals(card.getId())) {
					users.get(i).getCard().setAmount(users.get(i).getCard().getAmount() + amount);
					return true;
				}
			}
			return false;
		}
	}

	// This method is used to get how many times user entered incorrect pin
	public int getNumberOfErrors(String id) {
		for (int i = 0; i < users.size(); i++) {
			Card bankCard = users.get(i).getCard();
			if (bankCard.getId().equals(id)) {
				return users.get(i).getNumberOfErrors();
			}
		}
		return 0;
	}
        
        public boolean setNumberOfErrors(String id,int numberOfErrors) {
		for (int i = 0; i < users.size(); i++) {
			Card bankCard = users.get(i).getCard();
			if (bankCard.getId().equals(id)) {
				users.get(i).setNumberOfErrors(numberOfErrors);
                                return true;
			}
		}
		return false;
	}

}
