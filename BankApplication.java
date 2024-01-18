package bankpackage;
import java.io.*;
import java.util.*;


class Bank {
    private String accountNumber;
    private double balance;

    public Bank(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance for " + accountNumber + ": " + balance);
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(accountNumber + ".txt")) {
            writer.write(accountNumber + "\n");
            writer.write(Double.toString(balance));
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static Bank loadFromFile(String accountNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountNumber + ".txt"))) {
            String loadedAccountNumber = reader.readLine();
            double loadedBalance = Double.parseDouble(reader.readLine());
            return new Bank(loadedAccountNumber, loadedBalance);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
            return null;
        }
    }
}

public class BankApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter account number: ");
        String accountNumber = sc.next();

        Bank account = Bank.loadFromFile(accountNumber);

        if (account == null) {
            System.out.print("Enter initial balance: ");
            double initialBalance = sc.nextDouble();
            account = new Bank(accountNumber, initialBalance);
        }

        while (true) {
            System.out.println("\nWelcome to the Basic Bank Application");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Save and Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = sc.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = sc.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;
                case 3:
                    account.checkBalance();
                    break;
                case 4:
                    account.saveToFile();
                    System.out.println("Data saved. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
