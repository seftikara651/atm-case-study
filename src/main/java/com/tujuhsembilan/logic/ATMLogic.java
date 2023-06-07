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

    // List of available currency nominals in descending priority based on value
    List<BigDecimal> currencyNominals = Arrays.asList(
            new BigDecimal("100000"),
            new BigDecimal("50000"),
            new BigDecimal("20000"),
            new BigDecimal("10000")
    );

    Map<BigDecimal, Integer> withdrawalResult = new HashMap<>();
    BigDecimal remainingAmount = withdrawalAmount;

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
    for (Map.Entry<BigDecimal, Integer> entry : withdrawalResult.entrySet()) {
      BigDecimal nominal = entry.getKey();
      int count = entry.getValue();
      System.out.println(count + "x of " + nominal);
    }

    selectedCustomer.setBalance(accountBalance.subtract(withdrawalAmount));
    System.out.println("Remaining account balance: " + selectedCustomer.getBalance());
  }

  public static void phoneCreditsTopUp() {
    if (!loggedIn) {
      System.out.println("You are not logged in.");
      return;
    }

    // Implement phone credits top-up logic here
    System.out.println("Phone credits top-up:");
    // ...
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