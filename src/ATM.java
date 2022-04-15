
public class ATM {

    public int getNumberOfErrors(String cardid) {
        return bank.getNumberOfErrors(cardid);
    }
    public void setNumberOfErrors(String cardid)
    {
        bank.setNumberOfErrors(cardid, numberOfErrors);
    }
            
	enum Status {
		READY,
		CARD_INSERTED,
		PIN_INSERTED,
		ACTION_READY,
		FINISH
	}
	
	Bank bank;
	Status status;
	boolean authenticated;
	Card current_card;
	User current_user;
	int current_amount;
	String cardId;
	String input;
        int numberOfErrors;
	
	public ATM(Bank _bank)
	{
		bank = _bank;
		status = Status.READY;
		authenticated = false;
		current_amount = 0;
		current_user = null;
		current_card = null;
                numberOfErrors = 0;
	}
	
	private void ATM() {
		status = Status.READY;
		authenticated = false;
		current_amount = 0;
		current_user = null;
		current_card = null;
		numberOfErrors = 0;
	}
	
	public void Exit()
	{
            if(status != Status.READY)
                this.ATM();
	}

	public boolean InsertCard(String _cardId)
	{
            numberOfErrors = bank.getNumberOfErrors(_cardId);
            
            if (numberOfErrors > 0) {
		System.out.print("You have [" + numberOfErrors + "] previous unsuccessful attempts. You have ["
                    + (3 - numberOfErrors) + "] chance(s) left.\n");
            }
            
            cardId = _cardId;
            return true;
	}
	public boolean InsertPin(String pin)
	{
            if(numberOfErrors >= 3)
            {
                System.out.print("Your account is locked\n");
                return false;
            }
            
            current_card = new Card(cardId,pin);
            if(bank.authenticateUser(current_card))
            {
                current_amount = bank.checkAmount(current_card);
                authenticated = true;
                return true;
            }
            ++numberOfErrors;

            bank.setNumberOfErrors(cardId, numberOfErrors);

            if (numberOfErrors > 0) {
                System.out.print("You have [" + numberOfErrors + "] previous unsuccessful attempts. You have ["
                    + (3 - numberOfErrors) + "] chance(s) left.\n");
            }

            return false;
        }
	public boolean Withdraw(int amount)
	{		
            if(!authenticated)
                return false;
            
           if(amount < 0)
            {
                System.out.print("Deposit amount can't be negative.\n");
                return false;
            }
            
            boolean ret = bank.withdrawAmount(current_card, amount);
            if(ret)
                current_amount -= amount; 
            status = Status.ACTION_READY;
            return ret;
	}
	public boolean Deposit(int amount)
	{
            if(!authenticated)
                return false;
            if(amount < 0)
            {
                System.out.print("Deposit amount can't be negative.\n");
                return false;
            }
            
            bank.depositAmount(current_card, amount);
            status = Status.ACTION_READY;
            return true;
	}
	public int CheckAmount()
	{
            if(!authenticated)
                return 0;

            status = Status.ACTION_READY;
            return current_amount;		
	}
	public void PrintStatus()
	{
		if(null != status)
		switch (status) {
                case READY:
                    System.out.println("BANKOMAT READY Insert card please");
                    break;
                case PIN_INSERTED:
                    System.out.println("PLEASE SELECT ONE OF ACTIONS: (W)ithdraw, (D)eposit, (C)hange pin or");
                    System.out.println("Press (X) to exit");
                    break;
                case CARD_INSERTED:
                    System.out.println("PLEASE INPUT PIN or");
                    System.out.println("Press (X) to exit");
                    break;
                case ACTION_READY:
                    System.out.println("Processing transaction");
                    System.out.println("Press (X) for fast exit");
                    break;
                default:
                    break;
            }
	}
	
	public void Greet()
	{
		System.out.print("You are connected to " + Bank.getConnectedBankName() + "\n");
	}
}
