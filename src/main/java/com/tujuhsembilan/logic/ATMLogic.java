package com.tujuhsembilan.logic;

import java.util.Scanner;
import data.model.Bank;
import data.model.Customer;
import data.repository.BankRepo;

public class ATMLogic {
  private static double accountBalance = 0.0;
  private static Bank currentBank;
  private static Customer currentCustomer;

  public static void login() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the ATM!");
    System.out.print("Enter your PIN: ");
    int pin = scanner.nextInt();

    if (verifyPin(pin)) {
      System.out.println("Login successful!");
      showMenu();
    } else {
      System.out.println("Invalid PIN. Please try again.");
    }
  }

  private static boolean verifyPin(int pin) {
    return pin == 111111;
  }

  private static void showMenu() {
    Scanner scanner = new Scanner(System.in);
    boolean loggedIn = true;
    while (loggedIn) {
      System.out.println("\nMenu:");
      System.out.println("1. Account Balance Information");
      System.out.println("2. Money Withdrawal");
      System.out.println("3. Phone Credits Top-Up");
      System.out.println("4. Electricity Bills Token");
      System.out.println("5. Account Mutation");
      System.out.println("6. Money Deposit");
      System.out.println("0. Logout");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 0:
          loggedIn = false;
          break;
        case 1:
          accountBalanceInformation();
          break;
        case 2:
          moneyWithdrawal();
          break;
        case 3:
          phoneCreditsTopUp();
          break;
        case 4:
          electricityBillsToken();
          break;
        case 5:
          accountMutation();
          break;
        case 6:
          moneyDeposit();
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  public static void accountBalanceInformation() {
    System.out.println("" + accountBalance);
  }

  public static void moneyWithdrawal() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the amount to withdraw: Rp");
    double amount = scanner.nextDouble();

    if (amount <= accountBalance) {
      accountBalance -= amount;
      System.out.println("Withdrawal successful. Remaining balance: Rp" + accountBalance);
    } else {
      System.out.println("Insufficient funds. Please try again.");
    }
  }

  public static void phoneCreditsTopUp() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the phone number: ");
    String phoneNumber = scanner.nextLine();

    System.out.print("Enter the amount to top up: Rp");
    double amount = scanner.nextDouble();

    if (amount > 0) {
      System.out.println("Phone credits top-up successful. Phone number: " + phoneNumber);
      System.out.println("Top-up amount: Rp" + amount);
    } else {
      System.out.println("Invalid amount. Please try again.");
    }
  }

  public static void electricityBillsToken() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the electricity bill token: ");
    String token = scanner.nextLine();

    System.out.print("Enter the amount: Rp");
    double amount = scanner.nextDouble();

    if (amount > 0) {
      accountBalance -= amount;
      System.out.println("Electricity bill token successfully processed. Token: " + token);
      System.out.println("Amount: Rp" + amount);
      System.out.println("Remaining balance: Rp" + accountBalance);
    } else {
      System.out.println("Invalid amount. Please try again.");
    }
    ConsoleUtil.delay();
  }

  public static void accountMutation() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the description of the account mutation: ");
    String description = scanner.nextLine();

    System.out.print("Enter the amount: Rp");
    double amount = scanner.nextDouble();

    if (amount != 0) {
      String mutationType = (amount > 0) ? "Credit" : "Debit";
      String sign = (amount > 0) ? "+" : "-";
      String formattedAmount = String.format("%.2f", Math.abs(amount));
      String mutationEntry = mutationType + " " + sign + "Rp" + formattedAmount + ": " + description;

      System.out.println("Account mutation recorded:");
      System.out.println(mutationEntry);
    } else {
      System.out.println("Invalid amount. Please try again.");
    }
    ConsoleUtil.delay();
  }

  public static void moneyDeposit() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the amount to deposit: Rp");
    double amount = scanner.nextDouble();

    if (amount > 0) {
      accountBalance += amount;
      System.out.println("Deposit successful. Updated balance: Rp" + accountBalance);
    } else {
      System.out.println("Invalid amount. Please try again.");
    }
  }
}
