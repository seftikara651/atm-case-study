package com.tujuhsembilan.logic;

import data.model.Bank;
import data.model.Customer;
import data.repository.BankRepo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ATMLogic {
  private static final Scanner in = new Scanner(System.in);
  private static boolean loggedIn = false;
  private static int accountNumber;
  private static Customer selectedCustomer;


  public static void login() {
    if (loggedIn) {
      System.out.println("You are already logged in.");
      return;
    }

    System.out.print("Enter your account number: ");
    accountNumber = in.nextInt();
    System.out.print("Enter your PIN: ");
    int pin = in.nextInt();

    if (checkLogin(accountNumber, pin)) {
      loggedIn = true;
      System.out.println("Login successful.");
    } else {
      System.out.println("Invalid account number or PIN. Please try again.");
    }
  }

  public static boolean isLoggedIn() {
    return loggedIn;
  }

  public static void accountBalanceInformation() {
    if (!loggedIn) {
      System.out.println("You are not logged in.");
      return;
    }

    BigDecimal balance = selectedCustomer.getBalance();
    System.out.println("Account balance: " + balance);
  }

  public static void moneyWithdrawal() {
    if (!loggedIn) {
      System.out.println("You are not logged in.");
      return;
    }

    System.out.println("Money withdrawal:");
    System.out.print("Enter withdrawal amount: ");
    BigDecimal withdrawalAmount = in.nextBigDecimal();

    BigDecimal accountBalance = selectedCustomer.getBalance();

    if (withdrawalAmount.compareTo(accountBalance) > 0) {
      System.out.println("Insufficient funds. Your account balance is " + accountBalance);
      return;
    }

    Map<BigDecimal, Integer> withdrawalResult = new HashMap<>();
    BigDecimal remainingAmount = withdrawalAmount;

    List<BigDecimal> currencyNominals = Arrays.asList(
            new BigDecimal("100000"),
            new BigDecimal("50000"),
            new BigDecimal("20000"),
            new BigDecimal("10000")
    );

    for (BigDecimal nominal : currencyNominals) {
      int count = remainingAmount.divideToIntegralValue(nominal).intValue();
      if (count > 0) {
        withdrawalResult.put(nominal, count);
        remainingAmount = remainingAmount.remainder(nominal);
      }
    }

    if (remainingAmount.compareTo(BigDecimal.ZERO) != 0) {
      System.out.println("Cannot dispense the exact withdrawal amount. Withdrawal canceled.");
      return;
    }

    System.out.println("Withdrawal details:");
    withdrawalResult.forEach((nominal, count) -> System.out.println(count + "x of " + nominal));

    selectedCustomer.setBalance(accountBalance.subtract(withdrawalAmount));
    System.out.println("Remaining account balance: " + selectedCustomer.getBalance());
  }




  public static void phoneCreditsTopUp() {
    if (!loggedIn) {
      System.out.println("You are not logged in.");
      return;
    }

    System.out.print("Enter phone number: ");
    String phoneNumber = in.next();

    System.out.println("Select top-up amount:");
    System.out.println("1. Rp10.000,00");
    System.out.println("2. Rp20.000,00");
    System.out.println("3. Rp50.000,00");
    System.out.println("4. Rp100.000,00");
    System.out.print("Enter your choice: ");
    int choice = in.nextInt();

    BigDecimal topUpAmount;
    switch (choice) {
      case 1:
        topUpAmount = new BigDecimal("10000");
        break;
      case 2:
        topUpAmount = new BigDecimal("20000");
        break;
      case 3:
        topUpAmount = new BigDecimal("50000");
        break;
      case 4:
        topUpAmount = new BigDecimal("100000");
        break;
      default:
        System.out.println("Invalid choice. Top-up canceled.");
        return;
    }

    BigDecimal accountBalance = selectedCustomer.getBalance();

    if (accountBalance.compareTo(topUpAmount) < 0) {
      System.out.println("Insufficient funds. Your account balance is " + accountBalance);
      return;
    }

    selectedCustomer.setBalance(accountBalance.subtract(topUpAmount));
    System.out.println("Top-up successful!");
    System.out.println("Remaining account balance: " + selectedCustomer.getBalance());
  }

  public static void electricityBillsToken() {
    if (!loggedIn) {
      System.out.println("You are not logged in.");
      return;
    }

    // Implement electricity bills token logic here
    System.out.println("Electricity bills token:");
    // ...
  }

  public static void accountMutation() {
    if (!loggedIn) {
      System.out.println("You are not logged in.");
      return;
    }

    // Implement account mutation logic here
    System.out.println("Account mutation:");
    // ...
  }

  public static void moneyDeposit() {
    if (!loggedIn) {
      System.out.println("You are not logged in.");
      return;
    }

    // Implement money deposit logic here
    System.out.println("Money deposit:");
    // ...
  }

  private static boolean checkLogin(int accountNumber, int pin) {
    Optional<Bank> bank = BankRepo.findBankByName("BRI");
    if (bank.isPresent()) {
      Bank selectedBank = bank.get();
      Optional<Customer> customer = selectedBank.getCustomers().stream()
              .filter(c -> c.getAccount().equals(String.valueOf(accountNumber)) && c.getPin().equals(String.valueOf(pin)))
              .findFirst();
      if (customer.isPresent()) {
        selectedCustomer = customer.get();
      }
      return customer.isPresent();
    }
    return false;
  }

}