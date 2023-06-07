package com.tujuhsembilan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import data.constant.BankCompany;
import data.model.ATM;
import data.model.Bank;
import data.model.Customer;
import data.repository.ATMRepo;
import data.repository.BankRepo;

import static com.tujuhsembilan.App.ATMLogic.isLoggedIn;
import static com.tujuhsembilan.logic.ConsoleUtil.*;

public class App {

    private static int accountNumber = 123;
    private static int pin = 123;

    public static void main(String[] args) {
        boolean loop = true;
        while (loop) {
            printClear();
            printDivider();
            int num = 1;
            for (String menu : Arrays.asList(BankCompany.values()).stream()
                    .map(item -> "ATM " + item.getName())
                    .collect(Collectors.toList())) {
                System.out.println(" " + num + ". " + menu);
                num++;
            }
            printDivider("-");
            System.out.println(" 0. EXIT");
            printDivider();

            System.out.print(" > ");
            int selection = in.nextInt() - 1;
            if (selection >= 0 && selection < BankCompany.values().length) {
                new App(BankCompany.getByOrder(selection).getName()).start();
            } else if (selection == -1) {
                loop = false;
            } else {
                System.out.println("Invalid input");
                delay();
            }
        }
    }

    /// --- --- --- --- ---

    static Bank bank = new Bank();
    final ATM atm;

    public App(String bankName) {
        Bank lBank = null;
        ATM lAtm = null;

        Optional<Bank> qBank = BankRepo.findBankByName(bankName);
        if (qBank.isPresent()) {
            Optional<ATM> qAtm = ATMRepo.findATMByBank(qBank.get());
            if (qAtm.isPresent()) {
                lBank = qBank.get();
                lAtm = qAtm.get();
            }
        }

        this.bank = lBank;
        this.atm = lAtm;
    }

    public void start() {
        if (bank != null && atm != null) {
            ATMLogic.login();

        } else {
            System.out.println("Cannot find Bank or ATM");
            delay();
        }
    }

    public static class ATMLogic {
        private static boolean loggedIn = true;
        // ... kode lainnya ...

        public static boolean isLoggedIn(int accountNumber, int pin) {
            return loggedIn;
        }

        public static void login() {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter your PIN: ");
            int pin = scanner.nextInt();

            if (isLoggedIn(accountNumber, pin)) {
                loggedIn = true;
                displayMainMenu();
                System.out.println("Login successful. Access granted.");
            } else {
                loggedIn = false;
                System.out.println("Invalid credentials. Access denied.");
                delay();
            }
        }

        // ... kode lainnya ...
    }

    public static void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Main Menu");
            System.out.println("1. Account Balance Information");
            System.out.println("2. Withdrawal");
            System.out.println("3. Phone Credits Top Up");
            System.out.println("4. Electricity Bills Token");
            System.out.println("5. Account Mutation (Fund Transfer)");
            System.out.println("6. Money Deposit");
            System.out.println("7. Exit");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Membaca karakter baru setelah angka yang dimasukkan

            switch (choice) {
                case 1:
                    if (isLoggedIn(accountNumber, pin)) {
                        Customer customer = bank.getCustomers().stream()
                                .filter(c -> c.getAccount().equals("111111111"))
                                .findFirst()
                                .orElse(null);

                        if (customer != null) {
                            System.out.println("Account Balance Information");
                            System.out.println("Account Number: " + customer.getAccount());
                            System.out.println("Full Name: " + customer.getFullName());
                            System.out.println("Balance: " + customer.getBalance());
                        } else {
                            System.out.println("Customer not found.");
                        }
                    } else {
                        System.out.println("Invalid credentials. Access denied.");
                    }
                    delay();
                    break;
                case 2:
                    System.out.println("1. Fast cash");
                    System.out.println("2. Normal cash");
                    System.out.println();
                    System.out.println("Please select a mode for withdrawal");
                    int mode = scanner.nextInt();

                    if (mode == 1) {
                        String Y = "Y";
                        String y = "y";
                        System.out.println("1 - 500");
                        System.out.println("2 - 1000");
                        System.out.println("3 - 2000");
                        System.out.println("4 - 5000");
                        System.out.println("5 - 10000");
                        System.out.println("6 - 15000");
                        System.out.println("7 - 20000");
                        System.out.println();
                        System.out.println("Select one of the denominations of money:");
                        int denomination = scanner.nextInt();

                        BigDecimal withdrawalAmount;

                        switch (denomination) {
                            case 1:
                                withdrawalAmount = BigDecimal.valueOf(500);
                                break;
                            case 2:
                                withdrawalAmount = BigDecimal.valueOf(1000);
                                break;
                            case 3:
                                withdrawalAmount = BigDecimal.valueOf(2000);
                                break;
                            case 4:
                                withdrawalAmount = BigDecimal.valueOf(5000);
                                break;
                            case 5:
                                withdrawalAmount = BigDecimal.valueOf(10000);
                                break;
                            case 6:
                                withdrawalAmount = BigDecimal.valueOf(15000);
                                break;
                            case 7:
                                withdrawalAmount = BigDecimal.valueOf(20000);
                                break;
                            default:
                                System.out.println("Invalid denomination selection.");
                                return;
                        }

                        Customer customer = bank.getCustomers().stream()
                                .filter(c -> c.getAccount().equals("111111111"))
                                .findFirst()
                                .orElse(null);

                        if (customer != null) {
                            BigDecimal balance = customer.getBalance();
                            BigDecimal newBalance = balance.subtract(withdrawalAmount);

                            if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
                                customer.setBalance(newBalance);
                                System.out.println("Cash successfully withdrawn!");
                                BankRepo.updateBalance(customer.getAccount(), newBalance);

                                System.out.println("");
                                System.out.println("Do you wish to print receipt (Y/N)");
                                scanner.nextLine();
                                String s = scanner.nextLine();

                                if (s.equalsIgnoreCase("Y")) {
                                    System.out.println("Account: " + customer.getAccount());
                                    System.out.println("Date: " + LocalDate.now());
                                    System.out.println("Withdrawn: " + withdrawalAmount);
                                    System.out.println("Balance: " + customer.getBalance());
                                }
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        } else {
                            System.out.println("Customer not found.");
                        }
                    }

                    delay();

                                break;
                            case 3:
                                if (isLoggedIn(accountNumber, pin)) {
                                    Customer customer = bank.getCustomers().stream()
                                            .filter(c -> c.getAccount().equals("111111111"))
                                            .findFirst()
                                            .orElse(null);

                                    if (customer != null) {
                                        System.out.println("Top-up Credit");
                                        System.out.println("Enter the phone number: ");
                                        String phoneNumber = scanner.next();

                                        System.out.println("Select the top-up amount:");
                                        System.out.println("1. Rp10.000,00");
                                        System.out.println("2. Rp20.000,00");
                                        System.out.println("3. Rp50.000,00");
                                        System.out.println("4. Rp100.000,00");
                                        int topUpOption = scanner.nextInt();

                                        BigDecimal topUpAmount;
                                        switch (topUpOption) {
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
                                                System.out.println("Invalid top-up option.");
                                                delay();
                                                return;
                                        }

                                        BigDecimal balance = customer.getBalance();
                                        BigDecimal newBalance = balance.subtract(topUpAmount);
                                        BankRepo.setBalance(customer.getAccount(), newBalance);

                                        if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
                                            customer.setBalance(newBalance);
                                            System.out.println("Top-up successful!");
                                            System.out.println("Phone number: " + phoneNumber);
                                            System.out.println("Top-up amount: " + topUpAmount);
                                            System.out.println("Remaining balance: " + customer.getBalance());
                                        } else {
                                            System.out.println("Insufficient balance.");
                                        }
                                    } else {
                                        System.out.println("Customer not found.");
                                    }
                                } else {
                                    System.out.println("Invalid credentials. Access denied.");
                                }
                                delay();
                                break;
                            case 4:
                                // TODO: Implementasikan logika untuk Electricity Bills Token
                                break;
                            case 5:
                                // TODO: Implementasikan logika untuk Account Mutation (Fund Transfer)
                                break;
                            case 6:
                                // TODO: Implementasikan logika untuk Money Deposit
                                break;
                            case 7:
                                System.out.println("Thank you for using the ATM. Goodbye!");
                                return;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }

                        System.out.println(); // Baris kosong untuk pemisah
                    }
            }
        }