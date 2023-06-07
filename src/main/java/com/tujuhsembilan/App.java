package com.tujuhsembilan;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tujuhsembilan.logic.ATMLogic;

import data.constant.BankCompany;
import data.model.ATM;
import data.model.Bank;
import data.repository.ATMRepo;
import data.repository.BankRepo;

import static com.tujuhsembilan.logic.ConsoleUtil.*;

public class App {

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

    private void showMainMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            printClear();
            printDivider();
            System.out.println("----- Main Menu -----");
            System.out.println("1. Account Balance Information");
            System.out.println("2. Money Withdrawal");
            System.out.println("3. Phone Credits Top-Up");
            System.out.println("4. Electricity Bills Token");
            System.out.println("5. Account Mutation (Fund Transfer)");
            System.out.println("6. Money Deposit");
            System.out.println("0. Logout");
            printDivider();

            System.out.print(" > ");
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    ATMLogic.accountBalanceInformation();
                    break;
                case 2:
                    ATMLogic.moneyWithdrawal();
                    break;
                case 3:
                    ATMLogic.phoneCreditsTopUp();
                    break;
                case 4:
                    ATMLogic.electricityBillsToken();
                    break;
                case 5:
                    ATMLogic.accountMutation();
                    break;
                case 6:
                    ATMLogic.moneyDeposit();
                    break;
                case 0:
                    loggedIn = false; // Keluar dari loop jika pengguna memilih logout
                    break;
                default:
                    System.out.println("Invalid input");
                    delay();
            }
        }
    }



    public void start() {
        if (bank != null && atm != null) {
            ATMLogic.login();
            // TODO: Continue Here
            if (ATMLogic.isLoggedIn()) {
                showMainMenu();
            } else {
                System.out.println("Login failed. Please try again.");
                delay();
            }
        } else {
            System.out.println("Cannot find Bank or ATM");
            delay();
        }
    }

}
