package com.tujuhsembilan;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tujuhsembilan.logic.ATMLogic;

import data.constant.BankCompany;
import data.model.ATM;
import data.model.Bank;
import data.model.Customer;
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
            for (Object menu : Arrays.asList(BankCompany.values()).stream()
                    .map(item -> "ATM " + item.getName())
                    .collect(Collectors.toList())) {
                System.out.println(" " + num + ". " + menu);
                num++;
            }
//            printDivider("-");
            System.out.println(" 0. EXIT");
            printDivider();

            System.out.print(" > ");
            int selection = in.nextInt() - 1;
            if (selection >= 0 && selection < BankCompany.values().length) {
                new App(BankCompany.getByOrder(selection)).start();
            } else if (selection == -1) {
                loop = false;
            } else {
                System.out.println("Invalid input");
                delay();
            }
        }
    }

    /// --- --- --- --- ---

    Bank bank;
    final ATM atm;

    public App(BankCompany bankName) {
        Bank lBank = null;
        ATM lAtm = null;

        Optional<Bank> qBank = BankRepo.findBankByName(bankName.name());
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
//            ATMLogic.login();
            boolean loggedIn = ATMLogic.login();

            if (loggedIn) {
                boolean transactionLoop = true;
                while (transactionLoop) {
                    printClear();
                    printDivider();
                    System.out.println("Welcome to " + bank.getName() + " ATM");
                    printDivider();

                    System.out.println("1. Account Balance Information");
                    System.out.println("2. Money Withdrawal");
                    System.out.println("3. Phone Credits Top-Up");
                    System.out.println("4. Electricity Bills Token");
                    System.out.println("5. Account Mutation (Fund Transfer)");
                    System.out.println("6. Money Deposit");
                    System.out.println("0. Exit");
                    printDivider();

                    System.out.print(" > ");
                    int selection = in.nextInt();
                    Customer customer = new Customer();
                    switch (selection) {
                        case 1:
                            ATMLogic.accountBalanceInformation(customer);
                            delay();
                            break;
                        case 2:
                            ATMLogic.moneyWithdrawal(customer);
                            delay();
                            break;
                        case 3:
//                            Sementara diberikan default null untuk percobaan
                            String phoneNumber = null;
                            BigDecimal topAmount = null;
                            ATMLogic.phoneCreditsTopUp(customer, phoneNumber, topAmount);
                            delay();
                            break;
                        case 4:
                            String billNum = null;
                            BigDecimal tokenNum = null;
                            ATMLogic.electricityBillsToken(customer, billNum, tokenNum);
                            delay();
                            break;
                        case 5:
                            String targetAccount = null;
                            BigDecimal transferAmount = null;

                            ATMLogic.accountMutation(customer, BankCompany.getByOrder(1), targetAccount, transferAmount);
                            delay();
                            break;
                        case 6:
                            BigDecimal depositeAmount = null;
//                            if (bank == BankCompany.BNI || bank == BankCompany.MANDIRI) {
                                BigDecimal amount = null;
                                depositeAmount = null;
                                ATMLogic.moneyDepositBNI(customer, amount);
//                            } else {
//                                ATMLogic.moneyDeposit(customer, depositeAmount);
//                            }
                            delay();
                            break;
                        case 0:
                            transactionLoop = false;
                            break;
                        default:
                            System.out.println("Invalid input");
                            delay();
                            break;
                    }
                }
            } else {
                System.out.println("Invalid login credentials");
                delay();
            }
        } else {
            System.out.println("Cannot find Bank or ATM");
            delay();
        }
    }

}
