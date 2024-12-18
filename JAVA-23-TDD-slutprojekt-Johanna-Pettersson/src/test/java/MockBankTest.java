import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MockBankTest {
    private  MockBank bank;
    private User user;

    @BeforeEach
    public void setup () {
        bank = new MockBank();
        user = new User("123",5642,9652.56);
        bank.users.put("123",user);
    }

    @Test
    @DisplayName("get user by id")
    public void testGetUserId () {
        //Variable
        User retriveuser = bank.getUserById("123");

        //Test getting the user from the bank by user id and check it to not be null
        assertNotNull(retriveuser,"Should be retrieved successfully");
    }

    @Test
    @DisplayName("is card locked?")
    public void testIsCardLocked () {
        //Test if card is locked by default
        assertFalse(bank.isCardLocked("123"),"Card not locked by default");

        //Locks card
        user.lockCard();
        //Test if card is locked
        assertTrue(bank.isCardLocked("123"),"Card is locked after being locked");
    }

    @Test
    @DisplayName("Failled attempts test")
    public void testFailedAttempts () {
        //Pin code fails
        //Test default being 0
        assertEquals(0,user.getFailedAttempts(),"Default should be 0");

        //Increase the failed amount
        user.increseFailedAttempts();
        //Test attempt being 1
        assertEquals(1, user.getFailedAttempts(), "Failed attempts should increase to 1");

        //Same but with 2
        user.increseFailedAttempts();
        assertEquals(2, user.getFailedAttempts(), "Failed attempts should increase to 2");

        //Same but with 3
        user.increseFailedAttempts();
        assertEquals(3, user.getFailedAttempts(), "Failed attempts should increase to 3");

        //Locks the card
        user.lockCard();
        //Test if card is locked
        assertTrue(bank.isCardLocked("123"),"Card should be locked after 3 attempts");

    }

    @Test
    @DisplayName("deposit test")
    public void testDeposit () {
        //Variables
        double initialBalance = user.getBalance();
        double depositAmount = 50.0;

        //Test that desposit goes tru correcly
        user.deposit(depositAmount);
        assertEquals(initialBalance+depositAmount, user.getBalance(),0.01, "Balance should increase by deposit amount");

    }

    @Test
    @DisplayName("withdraw test")
    public void testWithdraw () {
        //Variables
        double initialBalance = user.getBalance();
        double withdrawAmount = 50.0;

        //Test if withdraw goes tru correcly
        user.withdraw(withdrawAmount);
        assertEquals(initialBalance-withdrawAmount, user.getBalance(),0.01, "Balance should decrease by withdraw amount");
    }
}