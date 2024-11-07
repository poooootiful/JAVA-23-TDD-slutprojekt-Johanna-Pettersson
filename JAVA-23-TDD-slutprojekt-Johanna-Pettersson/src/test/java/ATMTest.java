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
        User user = new User("123", 4562, 854.25);
        when(bank.getUserById("123")).thenReturn(user);
        when(bank.isCardLocked("123")).thenReturn(false);

        boolean result = atm.insertCard("123");

        assertTrue(result);
        verify(bank).getUserById("123");
        verify(bank).isCardLocked("123");
    }

    @Test
    @DisplayName("Enter pin test")
    public void testEnterPin() {
        User user = new User("123", 4562, 854.25);
        when(bank.getUserById("123")).thenReturn(user);
        atm.insertCard("123");

        assertTrue(atm.enterPin(4562));
        assertEquals(0, user.getFailedAttempts());
    }

    @Test
    @DisplayName("CheckBalance test")
    public void testCheckBalance() {
        User user = new User("123", 5931, 600.0);
        when(bank.getUserById("123")).thenReturn(user);

        atm.insertCard("123");
        atm.enterPin(5931);

        assertEquals(600.0, atm.checkBalance(), 0.01);
    }

    @Test
    @DisplayName("Deposit test with verify")
    public void testDeposit() {
        User user = new User("123", 4562, 625.45);
        when(bank.getUserById("123")).thenReturn(user);
        atm.insertCard("123");
        atm.enterPin(4562);

        atm.deposit(75);

        assertEquals(700.45, user.getBalance(), 0.01);
    }

    @Test
    @DisplayName("Withdraw test with verify")
    public void testWithdrawSufficientBalance() {
        User user = new User("123", 6583, 900.0);
        when(bank.getUserById("123")).thenReturn(user);
        atm.insertCard("123");
        atm.enterPin(6583);

        assertTrue(atm.withdraw(35.0));
        assertEquals(865.0, user.getBalance(), 0.01);
    }
}
