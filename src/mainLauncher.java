import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class mainLauncher {
	static Scanner sc = new Scanner(System.in);
	
	static Bank bank;
	static ATM atm;
        
        static String str_buf;
	
	private static void initBank()
	{
		// creating 4 cards
		Card card1 = new Card("1111222233334444", "1111", 1000);
		Card card2 = new Card("2222111133334444", "2222", 0);
		Card card3 = new Card("3333111122224444", "3333", 250);
		Card card4 = new Card("4444111122223333", "4444", 500);

		// creating 4 users
		User user1 = new User(card1, 2);
		User user2 = new User(card2, 0);
		User user3 = new User(card3, 1);
		User user4 = new User(card4, 0);
		
		// creating list of 4 users
		List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));

		// adding users to bank
		bank  = new Bank(users);
	}
	
	private static void initATM(Bank _bank)
	{
		atm = new ATM(_bank);
	}
	
	// program execution starts from here
	public static void main(String[] args) {
		initBank();
		initATM(bank);
		work(sc);
	}
        static private void work(Scanner sc)
        {
            while(true)
            {
                atm.Greet();
                atm.PrintStatus();
                //get card id
                if(getNextInput(sc) == false)
                        break;
                String id = str_buf;
                atm.InsertCard(id);
                atm.PrintStatus();

                boolean isAccountLocked = false;
                boolean finish = false;
                while (!finish) {
                        System.out.print("Enter card pin: \n");
                        
                        String pin;
                        int attempts = atm.getNumberOfErrors(id);

                        while( true )
                        {
                            pin = sc.next();
                            ++attempts;
                            if(atm.InsertPin(pin))
                                break;

                            if(attempts >= 3)
                            {
                                System.out.println("No more attempts left, try again in few minutes");
                                atm.Exit();
                                finish = true;
                                break;
                                
                            }
                        }
                        if(attempts >= 3)
                        {
                            System.out.print("Incorrect pin entered 3 or more times, account locked.\n");
                            isAccountLocked = true;
                            break;
                        }
                }
			
			if (!isAccountLocked) {

                            System.out.print("Login Successful!\n");

                            System.out.print("Current bank balance: " + Integer.toString(atm.CheckAmount()) + "\n");

                            System.out.print("Enter amount to withdraw: \n");
                            int withdrawAmount = sc.nextInt();

                            if (!atm.Withdraw(withdrawAmount))
                                    System.out.print("There is not that much money in the bank. Current balance is: " + Integer.toString(atm.CheckAmount()) + "\n");
                            else
                                    System.out.print("Withdraw successful for amount: " + Integer.toString(withdrawAmount) + "\n");
                            
                            atm.PrintStatus();
                            System.out.print("\nEnter amount to deposit: \n");
                            int depositAmount = sc.nextInt();

                            if (atm.Deposit(depositAmount))
                                System.out.print("Deposit successful for amount: " + depositAmount+"\n");

                            atm.PrintStatus();
                            System.out.print("\nCurrent bank balance: " + Integer.toString(atm.CheckAmount()) + "\n");
                            
                            atm.Exit();
			}
                        else
                        {
                                atm.Exit();
				System.out.print("Thank you and good bye!\n");
                                break;
			}			
		}
        }
        static private boolean getNextInput(Scanner sc)
	{
		str_buf = sc.next();
		return !"X".equals(str_buf);
	}
}
