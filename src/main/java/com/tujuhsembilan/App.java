package com.tujuhsembilan;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tujuhsembilan.logic.ATMLogic;

import data.constant.BankCompany;
import data.constant.Feature;
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
                /*if (selection == 0 ){
                    System.out.println("Logout Succesfully");
                }*/
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
            // TODO: Continue Here
            boolean loggedIn = true;
            while (loggedIn) {
                printClear();
                printDivider();
                System.out.println("Available Features:");
                int num = 1;
                for (Feature feature : Feature.values()) {
                    System.out.println(" " + num + ". " + feature.name());
                    num++;
                }
                printDivider("-");
                System.out.println(" 0. Logout");
                printDivider();

                System.out.print(" > ");
                int selection = in.nextInt() - 1;
                if (selection >= 0 && selection < Feature.values().length) {
                    Feature selectedFeature = Feature.values()[selection];
                    switch (selectedFeature) {
                        case VIEW_BALANCE:
                            ATMLogic.accountBalanceInformation();
                            break;
                        case WITHDRAW:
                            ATMLogic.moneyWithdrawal();
                            break;
                        case TOP_UP_PHONE_CREDIT:
                            ATMLogic.phoneCreditsTopUp();
                            break;
                        case TOP_UP_ELECTRICITY_BILL:
                            ATMLogic.electricityBillsToken();
                            break;
                        case UNIVERSITY_PAYMENT:
                            // TODO: Implement university payment feature
                            break;
                        case ACCOUNT_MUTATION:
                            ATMLogic.accountMutation();
                            break;
                        case DEPOSIT:
                            ATMLogic.moneyDeposit();
                            break;
                        default:
                            System.out.println("Invalid input");
                            delay();
                    }
                }
            }
        } else {
            System.out.println("Cannot find Bank or ATM");
            delay();
        }
    }
}