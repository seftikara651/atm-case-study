package com.tujuhsembilan.logic;

import data.constant.BankCompany;
import data.constant.CurrencyNominal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import data.model.Customer;

import java.math.BigDecimal;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ATMLogic {

  public static boolean login() {
//      System.out.println("Login successful!");
//    return true;
    Scanner scanner = new Scanner(System.in);
    System.out.println("===== LOGIN =====");
    System.out.print("Enter your account number: ");
    String accountNumber = scanner.nextLine();;
    System.out.print("Enter your PIN: ");
    String pin = scanner.nextLine();;

    // Cari pelanggan dengan nomor akun dan PIN yang sesuai
//    Optional<Customer> customerOptional = store.getCustomers().stream()
//            .filter(customer -> customer.getAccount().equals(accountNumber) && customer.getPin().equals(pin))
//            .findFirst();
//
//    if (customerOptional.isPresent()) {
//      Customer customer = customerOptional.get();
//      System.out.println("Login successful! Welcome, " + customer.getFullName() + ".");
      return true;
//    } else {
//      System.out.println("Invalid account number or PIN. Please try again.");
//      return false;
//    }
  }

  public static void accountBalanceInformation(Customer customer) {
    BigDecimal balance = customer.getBalance();
    System.out.println("Account Information:");
    System.out.println("Account Number: " + customer.getAccount());
    System.out.println("Full Name: " + customer.getFullName());
    System.out.println("Current Balance: " + balance);
  }

  public static void moneyWithdrawal(Customer customer) {
    BigDecimal balance = customer.getBalance();

    BigDecimal withdrawalAmount = null;
    if (balance.compareTo(withdrawalAmount) >= 0) {
      List<CurrencyNominal> currencyNominals = new ArrayList<>((Integer) CurrencyNominal.getAllNominals());
      currencyNominals.sort(Comparator.comparing(CurrencyNominal::getValue).reversed());

      List<String> withdrawalDetails = new ArrayList<>();
      BigDecimal remainingAmount = withdrawalAmount;

      for (CurrencyNominal nominal : currencyNominals) {
        int numberOfNotes = remainingAmount.divideToIntegralValue(nominal.getValue()).intValue();
        if (numberOfNotes > 0) {
          withdrawalDetails.add(numberOfNotes + "x of Nominal " + nominal.getValue());
          BigDecimal withdrawalValue = nominal.getValue().multiply(BigDecimal.valueOf(numberOfNotes));
          remainingAmount = remainingAmount.subtract(withdrawalValue);
        }
      }

      if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
        balance = balance.subtract(withdrawalAmount);
        customer.setBalance(balance);

        System.out.println("Withdrawal of " + withdrawalAmount + " successful.");
        System.out.println("Withdrawal details:");
        for (String detail : withdrawalDetails) {
          System.out.println(detail);
        }
        System.out.println("Remaining account balance: " + balance);
      } else {
        System.out.println("Cannot withdraw the specified amount. Please try again with different nominal combination.");
      }
    } else {
      System.out.println("Insufficient funds for withdrawal.");
    }
  }

  public static void phoneCreditsTopUp(Customer customer, String phoneNumber, BigDecimal topUpAmounts) {
//    BigDecimal balance = customer.getBalance();
//
//    if (balance.compareTo(topUpAmount) >= 0) {
//      balance = balance.subtract(topUpAmount);
//      customer.setBalance(balance);
//
//      System.out.println("Successfully topped up phone credits for " + phoneNumber + " with " + topUpAmount);
//      System.out.println("Remaining account balance: " + balance);
//    } else {
//      System.out.println("Insufficient funds for top-up.");
//    }
    Scanner scanner = new Scanner(System.in);
    // Menampilkan daftar jumlah top-up
    System.out.println("Phone Credits Top-Up");
    System.out.println("1. Rp10.000,00");
    System.out.println("2. Rp20.000,00");
    System.out.println("3. Rp50.000,00");
    System.out.println("4. Rp100.000,00");

    System.out.print("Select the top-up amount (1-4): ");
    int selection = scanner.nextInt();

    // Memvalidasi pilihan
    if (selection < 1 || selection > 4) {
      System.out.println("Invalid selection");
      scanner.close();
      return;
    }

    BigDecimal topUpAmount;
    switch (selection) {
      case 1:
        topUpAmount = BigDecimal.valueOf(10000);
        break;
      case 2:
        topUpAmount = BigDecimal.valueOf(20000);
        break;
      case 3:
        topUpAmount = BigDecimal.valueOf(50000);
        break;
      case 4:
        topUpAmount = BigDecimal.valueOf(100000);
        break;
      default:
        topUpAmount = BigDecimal.ZERO;
        break;
    }

    System.out.print("Enter the target phone number: ");
    phoneNumber = scanner.next();

    // Menampilkan informasi top-up pulsa
    System.out.println("Phone Credits Top-Up Summary");
    System.out.println("Target phone number: " + phoneNumber);
    System.out.println("Top-up amount: Rp" + topUpAmount);
    System.out.println("Remaining account balance: Rp" + getRemainingBalanceTopUp(topUpAmount));

    scanner.close();
  }

  private static BigDecimal getRemainingBalanceTopUp(BigDecimal topUpAmount) {
    // Implementasikan logika perhitungan sisa saldo akun di sini
    BigDecimal accountBalance = BigDecimal.valueOf(500000);
    return accountBalance.subtract(topUpAmount);
  }

  public static void electricityBillsToken(Customer customer, String billNumber, BigDecimal tokenAmount) {
//    BigDecimal balance = customer.getBalance();
//    if (balance.compareTo(tokenAmount) >= 0) {
//      balance = balance.subtract(tokenAmount);
//      customer.setBalance(balance);
//
//      System.out.println("Successfully purchased electricity bills token for bill number " + billNumber + " with " + tokenAmount);
//      System.out.println("Remaining account balance: " + balance);
//    } else {
//      System.out.println("Insufficient funds for purchasing token.");
//    }
    Scanner scanner = new Scanner(System.in);

    // Menampilkan daftar jumlah pembelian token
    System.out.println("Electricity Bills Token");
    System.out.println("1. Rp50.000,00");
    System.out.println("2. Rp100.000,00");
    System.out.println("3. Rp200.000,00");
    System.out.println("4. Rp500.000,00");

    System.out.print("Select the token amount (1-4): ");
    int selection = scanner.nextInt();

    // Memvalidasi pilihan
    if (selection < 1 || selection > 4) {
      System.out.println("Invalid selection");
      scanner.close();
      return;
    }

    switch (selection) {
      case 1:
        tokenAmount = BigDecimal.valueOf(50000);
        customer.setBalance(tokenAmount);
        break;
      case 2:
        tokenAmount = BigDecimal.valueOf(100000);
        customer.setBalance(tokenAmount);
        break;
      case 3:
        tokenAmount = BigDecimal.valueOf(200000);
        customer.setBalance(tokenAmount);
        break;
      case 4:
        tokenAmount = BigDecimal.valueOf(500000);
        customer.setBalance(tokenAmount);
        break;
      default:
        tokenAmount = BigDecimal.ZERO;
        customer.setBalance(tokenAmount);
        break;
    }

    System.out.print("Enter the target bill number: ");
    billNumber = scanner.next();

    // Menampilkan informasi pembelian token
    System.out.println("Electricity Bills Token Summary");
    System.out.println("Token amount: Rp" + tokenAmount);
    System.out.println("Target bill number: " + billNumber);
    System.out.println("Remaining account balance: Rp" + getRemainingBalance(tokenAmount));

    scanner.close();
  }

  private static BigDecimal getRemainingBalance(BigDecimal tokenAmount) {
    // Implementasikan logika perhitungan sisa saldo akun di sini
    BigDecimal accountBalance = BigDecimal.valueOf(1000000);
    return accountBalance.subtract(tokenAmount);
  }


  public static void accountMutation(Customer customer, BankCompany bank, String targetAccount, BigDecimal transferAmount) {
    BigDecimal balance = customer.getBalance();

    if (balance.compareTo(transferAmount) >= 0) {
      balance = balance.subtract(transferAmount);
      customer.setBalance(balance);

      System.out.println("Successfully transferred " + transferAmount + " to " + bank.getName() + " account " + targetAccount);
      System.out.println("Remaining account balance: " + balance);
    } else {
      System.out.println("Insufficient funds for transfer.");
    }
  }

  public static void moneyDeposit(Customer customer, BigDecimal depositAmount) {
    BigDecimal balance = customer.getBalance();

    balance = balance.add(depositAmount);
    customer.setBalance(balance);

    System.out.println("Successfully deposited " + depositAmount + " into the account");
    System.out.println("Current account balance: " + balance);
  }

  public static void moneyDepositBNI(Customer customer, BigDecimal depositAmount) {
//    Terdapat code selanjutnya untuk step deposit bank BNI, jika waktu memungkinkan

    moneyDeposit(customer, depositAmount);
  }

  public static void moneyDepositMandiri(Customer customer, BigDecimal depositAmount) {
//    Terdapat code selanjutnya untuk step deposit bank Mandiri, jika waktu memungkinkan

    moneyDeposit(customer, depositAmount);
  }

}
