import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ATMTest {

    @Mock
    private Bankinterface bank;

    @InjectMocks
    private ATM atm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        atm = new ATM(bank);
    }

    @Test
    @DisplayName("Test the inserted card")
    public void testInsertedCard() {
        //Make user for use in test
        User user = new User("123", 4562, 854.25);

        //Mock´s getting user by id from bank and returns the user
        when(bank.getUserById("123")).thenReturn(user);

        //Mocks the card to not locked so test can run on it
        when(bank.isCardLocked("123")).thenReturn(false);

        //Make a result for card being inserted
        boolean result = atm.insertCard("123");
        //Asserts a true test on result
        assertTrue(result);

        //Test that the userid is in the bank and if it's locked
        verify(bank).getUserById("123");
        verify(bank).isCardLocked("123");
    }

    @Test
    @DisplayName("Enter pin test")
    public void testEnterPin() {
        //Make a user to use in the test
        User user = new User("123", 4562, 854.25);

        //Mock´s getting user by id from bank and returns the user
        when(bank.getUserById("123")).thenReturn(user);

        //Inserts the card for test purpose (needed for test to assert true)
        atm.insertCard("123");

        //Test the pin being correct
        assertTrue(atm.enterPin(4562));

        //Test if the user has 0 failed attempts from trying to type the pin
        assertEquals(0, user.getFailedAttempts());
    }

    @Test
    @DisplayName("CheckBalance test")
    public void testCheckBalance() {
        //Make a user to use in the test
        User user = new User("123", 5931, 600.0);

        //Mock´s getting user by id from bank and returns the user
        when(bank.getUserById("123")).thenReturn(user);

        //Inserts the card for test purpose (needed for test to work)
        atm.insertCard("123");

        //Enter pin for test purpose (needed for test to work)
        atm.enterPin(5931);

        //Test if balance is 600
        assertEquals(600.0, atm.checkBalance(), 0.01);
    }

    @Test
    @DisplayName("Deposit test with verify")
    public void testDeposit() {
        //Make a user to use in the test
        User user = new User("123", 4562, 625.45);

        //Mock´s getting user by id from bank and returns the user
        when(bank.getUserById("123")).thenReturn(user);

        //Inserts the card for test purpose (needed for test to work)
        atm.insertCard("123");

        //Enter pin for test purpose (needed for test to work)
        atm.enterPin(4562);

        //An amount to deposit to use in the test (needed for test to work)
        atm.deposit(75);

        //Test if deposit worked
        assertEquals(700.45, user.getBalance(), 0.01);
    }

    @Test
    @DisplayName("Withdraw test with verify")
    public void testWithdrawSufficientBalance() {
        //Make a user by id from bank
        User user = new User("123", 6583, 900.0);

        //Mock´s getting user by id from bank and returns the user
        when(bank.getUserById("123")).thenReturn(user);

        //Insert card and Enter pin for test purpose (needed for test to go tru)
        atm.insertCard("123");
        atm.enterPin(6583);

        //Tests the withdrawn function
        assertTrue(atm.withdraw(35.0));

        //Test the result of the withdrawn
        assertEquals(865.0, user.getBalance(), 0.01);
    }
}
