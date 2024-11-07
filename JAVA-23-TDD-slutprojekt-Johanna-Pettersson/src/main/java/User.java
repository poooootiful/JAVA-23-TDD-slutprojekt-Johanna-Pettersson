public class User {
    private String cardId;
    private int pin;
    private double balance;
    private int failedAttempts;
    private boolean isLocked;

    public User (String id, int pin, double balance) {
        this.cardId = id;
        this.pin = pin;
        this.balance = balance;
        this.failedAttempts = 0;
        this.isLocked =false;
    }

    public String getCardId() {
        return cardId;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public boolean isLocked() {
        return isLocked;
    }


    public void lockCard(){
        this.isLocked=true;
    }

    public void increseFailedAttempts () {
        this.failedAttempts++;
    }
    public void resetFailedAttempts () {
        this.failedAttempts =0;
    }
    public void deposit (double amount) {
        this.balance += amount;
    }
    public void withdraw (double amount) {
        this.balance += amount;
    }
}
