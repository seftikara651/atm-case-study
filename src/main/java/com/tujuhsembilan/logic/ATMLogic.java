package main.java.com.tujuhsembilan.logic;
import main.java.data.repository.BankRepo;
import main.java.data.constant.TransactionType;
import main.java.data.model.ATM;
import main.java.data.model.Bank;
import main.java.data.model.Customer;
import main.java.data.model.Transaction;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ATMLogic {
	
	private static final Scanner scanner = new Scanner(System.in);
	
  public static boolean login() {
	  System.out.println("===== Login =====");
      System.out.print("Enter Account Number: ");
      String accountNumber = scanner.nextLine();

      System.out.print("Enter PIN: ");
      String pin = scanner.nextLine();

      if (checkLoginCredentials(accountNumber, pin)) {
          System.out.println("Login successful!");
          return true;
      } else {
          System.out.println("Invalid account number or PIN. Please try again.");
          return false;
      }
  }
  private static boolean checkLoginCredentials(String accountNumber, String pin) {
      Optional<Bank> optionalBank = BankRepo.findBankByAccountNumber(accountNumber);
      if (optionalBank.isPresent()) {
          Bank bank = optionalBank.get();
          Optional<Customer> optionalCustomer = bank.findCustomerByAccount(accountNumber);
          if (optionalCustomer.isPresent()) {
              Customer customer = optionalCustomer.get();
              if (customer.getPin().equals(pin)) {
                  return true;
              }
          }
      }
      return false;
  }

  private Set<Customer> customers = new HashSet<>();
  
  public static void accountBalanceInformation(Bank bank, Customer customer, ATM atm) {
      System.out.println("===== Account Balance Information =====");
      System.out.println("Bank: " + bank.getName());
      System.out.println("Customer: " + customer.getFullName());
      System.out.println("Account Number: " + customer.getAccount());
      System.out.println("Current Balance: " + customer.getBalance());

      Set<Transaction> transactions = bank.findAllTransactionsByAccount(customer.getAccount());
      if (transactions.isEmpty()) {
          System.out.println("No transaction history available.");
      } else {
          System.out.println("Transaction History:");
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
          for (Transaction transaction : transactions) {
              String timestamp = transaction.getTimestamp().format(formatter);
              String type = transaction.getType().name();
              BigDecimal expense = transaction.getAmount();
              System.out.println("Timestamp: " + timestamp);
              System.out.println("Type: " + type);
              System.out.println("Expense: " + expense);
              System.out.println("--------------------");
          }
      }

      System.out.println("ATM Balance: " + atm.getBalance());
  }

  public static void delay(int seconds) {
        try {
          TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
      }

      public static void delay() {
        delay(3);
      }
      
      public static void moneyWithdrawal(Bank bank, Customer customer, ATM atm) {
          System.out.println("===== Money Withdrawal =====");
          System.out.print("Enter withdrawal amount: ");
          BigDecimal withdrawalAmount = scanner.nextBigDecimal();

          if (withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0) {
              System.out.println("Invalid withdrawal amount. Please enter a positive value.");
              return;
          }

          BigDecimal maxExpensePerWithdrawal = bank.getMaxExpensePerWithdrawal();
          if (withdrawalAmount.compareTo(maxExpensePerWithdrawal) > 0) {
              System.out.println("Withdrawal amount exceeds the maximum per transaction limit.");
              return;
          }

          BigDecimal maxExpensePerUserDaily = bank.getMaxExpensePerUserDaily();
          BigDecimal totalDailyWithdrawal = getTotalDailyWithdrawal(customer, bank);
          if (totalDailyWithdrawal.add(withdrawalAmount).compareTo(maxExpensePerUserDaily) > 0) {
              System.out.println("Withdrawal amount exceeds the maximum daily limit for this user.");
              return;
          }

          BigDecimal atmBalance = atm.getBalance();
          if (withdrawalAmount.compareTo(atmBalance) > 0) {
              System.out.println("Insufficient ATM balance to process the withdrawal.");
              return;
          }

          // Perform the withdrawal
          customer.subtract(withdrawalAmount);
          atm.subtract(withdrawalAmount);
          LocalDateTime timestamp = LocalDateTime.now();
          Transaction withdrawalTransaction = createTransaction(customer, TransactionType.WITHDRAWAL, withdrawalAmount, timestamp);
          bank.getTransactions().add(withdrawalTransaction);

          System.out.println("Withdrawal successful!");
          System.out.println("Remaining balance: " + customer.getBalance());
      }

      private static BigDecimal getTotalDailyWithdrawal(Customer customer, Bank bank) {
          LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
          return bank.getTransactions().stream()
                  .filter(transaction -> transaction.getCustomer() == customer)
                  .filter(transaction -> transaction.getType() == TransactionType.WITHDRAWAL)
                  .filter(transaction -> transaction.getTimestamp().isAfter(today))
                  .map(Transaction::getAmount)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
      }

      private static Transaction createTransaction(Customer customer, TransactionType type, BigDecimal expense, LocalDateTime timestamp) {
          Transaction transaction = new Transaction(null, timestamp, customer, type, expense);
          transaction.setId(generateTransactionId());
          transaction.setCustomer(customer);
          transaction.setType(type);
          transaction.setAmount(expense);
          transaction.setTimestamp(timestamp);
          return transaction;
      }

      private static String generateTransactionId() {
    	  Random random = new Random();
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < 8; i++) {
              int digit = random.nextInt(10);
              sb.append(digit);
          }
          return sb.toString();
      }


  public static void phoneCreditsTopUp() {
  }

  public static void electricityBillsToken() {
  }

  public static void accountMutation() {
  }

  public static void moneyDeposit(ATM atm, Bank bank, String accountNumber) {
      System.out.println("===== Deposit Money =====");
      System.out.print("Enter the amount to deposit: ");
      BigDecimal amount = scanner.nextBigDecimal();
      scanner.nextLine(); // Consume newline character

      if (amount.compareTo(BigDecimal.ZERO) <= 0) {
          System.out.println("Invalid amount. Please enter a positive value.");
          return;
      }

      // Update the customer's balance
      Optional<Customer> optionalCustomer = bank.findCustomerByAccount(accountNumber);
      if (optionalCustomer.isPresent()) {
          Customer customer = optionalCustomer.get();
          customer.add(amount);

          // Create a deposit transaction
          String transactionId = generateTransactionId();
          LocalDateTime timestamp = LocalDateTime.now();
          Transaction transaction = new Transaction(transactionId, timestamp, customer, TransactionType.TOP_UP, amount);
          bank.getTransactions().add(transaction);

          // Update the ATM balance
          atm.add(amount);

          System.out.println("Deposit successful!");
          System.out.println("New balance: " + customer.getBalance());
      }
  }
  
public Set<Customer> getCustomers() {
	return customers;
}
public void setCustomers(Set<Customer> customers) {
	this.customers = customers;
}

}
