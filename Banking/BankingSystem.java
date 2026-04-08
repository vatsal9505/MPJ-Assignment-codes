import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// ---------- User Defined Exceptions ----------
class InvalidCustomerIdException extends Exception {
    public InvalidCustomerIdException(String message) {
        super(message);
    }
}

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class MinimumBalanceException extends Exception {
    public MinimumBalanceException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// ---------- Customer Class ----------
class Customer implements Serializable {
    private int cid;
    private String cname;
    private double amount;

    public Customer(int cid, String cname, double amount) {
        this.cid = cid;
        this.cname = cname;
        this.amount = amount;
    }

    public int getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Customer ID: " + cid + ", Name: " + cname + ", Balance: Rs. " + amount;
    }
}

// ---------- Main Class ----------
public class BankingSystem {
    static final String FILE_NAME = "customers.txt";
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Customer> customers = new ArrayList<>();

    // ---------- Validation Methods ----------
    static void validateCustomerId(int cid) throws InvalidCustomerIdException {
        if (cid < 1 || cid > 20) {
            throw new InvalidCustomerIdException("Customer ID must be between 1 and 20.");
        }
    }

    static void validatePositiveAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive.");
        }
    }

    static void validateMinimumOpeningBalance(double amount) throws MinimumBalanceException {
        if (amount < 1000) {
            throw new MinimumBalanceException("Account must be created with minimum Rs. 1000.");
        }
    }

    static Customer findCustomer(int cid) {
        for (Customer c : customers) {
            if (c.getCid() == cid) {
                return c;
            }
        }
        return null;
    }

    // ---------- File Handling ----------
    static void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Customer c : customers) {
                bw.write(c.getCid() + "," + c.getCname() + "," + c.getAmount());
                bw.newLine();
            }
            System.out.println("Records saved to file successfully.");
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int cid = Integer.parseInt(parts[0]);
                    String cname = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    customers.add(new Customer(cid, cname, amount));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    // ---------- Banking Operations ----------
    static void createAccount() {
        try {
            System.out.print("Enter Customer ID: ");
            int cid = sc.nextInt();
            sc.nextLine();

            validateCustomerId(cid);

            if (findCustomer(cid) != null) {
                System.out.println("Customer with this ID already exists.");
                return;
            }

            System.out.print("Enter Customer Name: ");
            String cname = sc.nextLine();

            System.out.print("Enter Opening Balance: ");
            double amount = sc.nextDouble();

            validatePositiveAmount(amount);
            validateMinimumOpeningBalance(amount);

            Customer c = new Customer(cid, cname, amount);
            customers.add(c);

            System.out.println("Account created successfully.");
            saveToFile();

        } catch (InvalidCustomerIdException | InvalidAmountException | MinimumBalanceException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type. Please enter correct values.");
            sc.nextLine();
        }
    }

    static void deposit() {
        try {
            System.out.print("Enter Customer ID: ");
            int cid = sc.nextInt();

            Customer c = findCustomer(cid);
            if (c == null) {
                System.out.println("Customer not found.");
                return;
            }

            System.out.print("Enter Deposit Amount: ");
            double amt = sc.nextDouble();

            validatePositiveAmount(amt);

            c.setAmount(c.getAmount() + amt);
            System.out.println("Deposit successful. Updated Balance = Rs. " + c.getAmount());
            saveToFile();

        } catch (InvalidAmountException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type.");
            sc.nextLine();
        }
    }

    static void withdraw() {
        try {
            System.out.print("Enter Customer ID: ");
            int cid = sc.nextInt();

            Customer c = findCustomer(cid);
            if (c == null) {
                System.out.println("Customer not found.");
                return;
            }

            System.out.print("Enter Withdrawal Amount: ");
            double amt = sc.nextDouble();

            validatePositiveAmount(amt);

            if (amt > c.getAmount()) {
                throw new InsufficientFundsException("Withdrawal amount exceeds total balance.");
            }

            c.setAmount(c.getAmount() - amt);
            System.out.println("Withdrawal successful. Updated Balance = Rs. " + c.getAmount());
            saveToFile();

        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type.");
            sc.nextLine();
        }
    }

    static void displayAll() {
        if (customers.isEmpty()) {
            System.out.println("No customer records found.");
            return;
        }

        System.out.println("\n--- Customer Records ---");
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    // ---------- Main Menu ----------
    public static void main(String[] args) {
        loadFromFile();

        int choice;
        do {
            System.out.println("\n===== Banking System Menu =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display All Customers");
            System.out.println("5. Save Records to File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        displayAll();
                        break;
                    case 5:
                        saveToFile();
                        break;
                    case 6:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 to 6.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a numeric choice.");
                sc.nextLine();
                choice = 0;
            }

        } while (choice != 6);

        sc.close();
    }
}
