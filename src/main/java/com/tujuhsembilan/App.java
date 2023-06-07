package com.tujuhsembilan;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import data.constant.BankCompany;
import data.model.ATM;
import data.model.Bank;
import data.repository.ATMRepo;
import data.repository.BankRepo;

import static com.tujuhsembilan.logic.ConsoleUtil.*;

public class App {

    private int accountNumber = 123;
    private int pin = 123;

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

    final Bank bank;
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
                    // TODO: Implementasikan logika untuk Account Balance Information
                    break;
                case 2:
                    // TODO: Implementasikan logika untuk Withdrawal
                    break;
                case 3:
                    // TODO: Implementasikan logika untuk Phone Credits Top Up
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

