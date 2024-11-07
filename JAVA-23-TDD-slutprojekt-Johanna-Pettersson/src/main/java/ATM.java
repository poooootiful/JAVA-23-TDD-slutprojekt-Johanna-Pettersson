public class ATM {
    private Bankinterface bank;
    private User currentUser;

    public ATM (Bankinterface bank) {
        this.bank = bank;
    }

    public boolean insertCard (String userid) {
        if (bank.isCardLocked(userid)) {
            System.out.println("Card is locked");
            return false;
        }

        currentUser = bank.getUserById(userid);
        return currentUser !=null;
    }
    public boolean enterPin (int pin) {
        if (currentUser == null) return false;
        if (currentUser.getPin()==pin) {
            currentUser.resetFailedAttempts();
            return true;
        }else {
            currentUser.increseFailedAttempts();;
            if (currentUser.getFailedAttempts() >= 3) {
                currentUser.lockCard();
                System.out.println("Card is locked");
            }
            return false;
        }
    }
    public  double checkBalance () {
        if (currentUser == null) return 0.0;
        return currentUser.getBalance();
    }
    public void deposit (double amount) {
        if (currentUser == null) return;
        currentUser.deposit(amount);
    }
    public boolean withdraw (double amount) {
        if (currentUser == null) return false;

        if (currentUser.getBalance() >= amount) {
            currentUser.withdraw(amount);
            return true;
        }else {
            System.out.println("Insufficient balance");
            return false;
        }
    }

}
