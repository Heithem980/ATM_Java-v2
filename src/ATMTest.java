
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class ATMTest {

	// mocking bank
	Bank bank = mock(Bank.class);
        ATM atm;

	// creating 2 cards
	Card existingCard = new Card("1111222233334444", "1111", 500);
	Card cardwithWrongPin = new Card("1111222233334444", "1212", 1000);
        Card nonexistingCard = new Card("BLA", "BLA", 1000);
        Card existingCard2 = new Card("4444111122223333", "4444",500);
        
        int smallAmount = 100;
        int bigAmount = 1000;

    public ATMTest() {
        this.atm = new ATM(bank);
    }
        
    void _universalInit()
    {
        when(bank.authenticateUser(cardwithWrongPin)).thenReturn(false);
        when(bank.authenticateUser(nonexistingCard)).thenReturn(false);
        when(bank.authenticateUser(existingCard)).thenReturn(true);
        when(bank.authenticateUser(existingCard2)).thenReturn(true);
        
        when(bank.checkAmount(existingCard)).thenReturn(500);
        when(bank.checkAmount(nonexistingCard)).thenReturn(0);
        when(bank.checkAmount(cardwithWrongPin)).thenReturn(0);
        when(bank.checkAmount(existingCard2)).thenReturn(500);
        
        when(bank.depositAmount(existingCard, smallAmount)).thenReturn(true);
        when(bank.depositAmount(existingCard2, smallAmount)).thenReturn(true);
        when(bank.depositAmount(cardwithWrongPin, smallAmount)).thenReturn(false);
        when(bank.depositAmount(nonexistingCard, smallAmount)).thenReturn(false);
        
        when(bank.depositAmount(existingCard, bigAmount)).thenReturn(true);
        when(bank.depositAmount(existingCard2, bigAmount)).thenReturn(true);
        when(bank.depositAmount(cardwithWrongPin, bigAmount)).thenReturn(false);
        when(bank.depositAmount(nonexistingCard, bigAmount)).thenReturn(false);
        
        when(bank.withdrawAmount(existingCard, smallAmount)).thenReturn(true);
        when(bank.withdrawAmount(existingCard, bigAmount)).thenReturn(false);
        when(bank.withdrawAmount(existingCard2, smallAmount)).thenReturn(true);
        when(bank.withdrawAmount(existingCard2, bigAmount)).thenReturn(false);
        
        when(bank.withdrawAmount(cardwithWrongPin, smallAmount)).thenReturn(false);
        when(bank.withdrawAmount(cardwithWrongPin, bigAmount)).thenReturn(false);
        when(bank.withdrawAmount(nonexistingCard, smallAmount)).thenReturn(false);
        when(bank.withdrawAmount(nonexistingCard, bigAmount)).thenReturn(false);
        
        when(bank.getNumberOfErrors(cardwithWrongPin.getId())).thenReturn(3);
        when(bank.getNumberOfErrors(nonexistingCard.getId())).thenReturn(3);
        when(bank.getNumberOfErrors(existingCard.getId())).thenReturn(2);
        when(bank.getNumberOfErrors(existingCard2.getId())).thenReturn(0);
    }

	// test authenticateUser method with valid card details
        @Test void willFailAuthenticate()
        {
            _universalInit();            
            
            atm.Exit();
            atm.InsertCard(cardwithWrongPin.getId());
            assertFalse(atm.InsertPin(cardwithWrongPin.getPin()));
            
            verify(bank, times(1)).authenticateUser(cardwithWrongPin);
        
        }
        
        @Test void willSuccedAuthenticate()
        {
            _universalInit();            
            
            atm.Exit();
            atm.InsertCard(existingCard.getId());
            assertFalse(atm.InsertPin(existingCard.getPin()));
            
            verify(bank, times(1)).authenticateUser(existingCard);
        
        }
        
        @Test void willFailCheckAmount()
        {
            _universalInit();    
            
            atm.Exit();
            atm.InsertCard(cardwithWrongPin.getId());
            atm.InsertPin(cardwithWrongPin.getPin());
             
            assertEquals(atm.CheckAmount(),0);
                 
            verify(bank, times(1)).checkAmount(cardwithWrongPin);
        }
        
        @Test void willSuccedCheckAmount()
        {
            _universalInit();    
            
            atm.Exit();
            atm.InsertCard(existingCard.getId());
            atm.InsertPin(existingCard.getPin());
             
            assertEquals(atm.CheckAmount(),1000);
                 
            verify(bank, times(1)).checkAmount(existingCard);
        }
}
