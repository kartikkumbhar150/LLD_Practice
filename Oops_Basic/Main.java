public class Main {
    public static void main(String[] args) {

        SavingsAccount account = new SavingsAccount(
                101,
                "Kartik",
                50000
        );

        account.displayAccountDetails();

        System.out.println();

        account.deposit(5000);

        System.out.println("After Deposit:");
        account.checkBalance();

        account.withdraw(10000);

        System.out.println("After Withdrawal:");
        account.checkBalance();
    }
}
abstract class BankAccount{
    private int accountNumber;
    private String holderName;
    private int balance;

    public BankAccount(int accountNumber, String holderName, int balance){
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public abstract void deposit(int money);
    public abstract void withdraw(int money);
    public abstract void checkBalance();

    public void displayAccountDetails(){
        System.out.print(accountNumber);
        System.out.print(" "+ holderName+ " "+ balance);
    }

    public int getAccountNumber(){
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber){
        int curr = accountNumber;
        int count = 0;

        while(curr >= 0){
            count++;
            curr = curr/10;
        }
        if(count < 16){
            System.out.println("Invalid accountNumber");
        }
        else{
            this.accountNumber = accountNumber;
        }

    }
    public int getBalance(){
        return balance;
    }

    public String getHolderName(){
        return holderName;
    }
    public void setHolderName(String holderName){
        this.holderName = holderName;
    }
    public void setBalance(int balance){
        if(balance < 0){
            System.out.println("Balance cannot be negative");
        }
        else{
            this.balance = balance;
        }
    }

}

class SavingsAccount extends BankAccount{

    public SavingsAccount(int accountNumber, String holderName, int balance) {
        super(accountNumber, holderName, balance);
    }

    @Override
    public void deposit(int money){
        int b = getBalance();

        setBalance(money + b);
    }

    @Override
    public void withdraw(int money) {
        int b = getBalance();
        setBalance(b-money);

    }

    @Override
    public void checkBalance() {
        System.out.println(getBalance());
    }
}
