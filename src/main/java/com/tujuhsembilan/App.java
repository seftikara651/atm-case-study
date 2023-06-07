package main.java.com.tujuhsembilan;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import main.java.com.tujuhsembilan.logic.ATMLogic;
import main.java.data.constant.BankCompany;
import main.java.data.model.ATM;
import main.java.data.model.Bank;
import main.java.data.model.Customer;
import main.java.data.repository.ATMRepo;
import main.java.data.repository.BankRepo;

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
            printDivider('=');
            System.out.println(" 0. EXIT");
            printDivider();

            System.out.print(" > ");
            int selection = in.nextInt() - 1;
            if (selection >= 0 && selection < BankCompany.values().length) {
                new App(BankCompany.getByOrder(selection).getName()).start(null);
            } else if (selection == -1) {
                loop = false;
            } else {
                System.out.println("Invalid input");
                delay();
            }
        }
    }

    private static void printDivider(Character character) {
    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 35; i++) {
          sb.append(character);
        }
        System.out.println(sb.toString());
		
	}
    public static final Scanner in = new Scanner(System.in);

    private static void printDivider() {
    	printDivider('=');
	}
	/// --- --- --- --- ---

    private static void printClear() {
    	System.out.print("\033[H\033[2J");
        System.out.flush();
		
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

    public void start(Customer customer) {
        if (bank != null && atm != null) {
        	boolean loggedIn = ATMLogic.login();
            if (loggedIn) {
                boolean loop = true;
                while (loop) {
                    printClear();
                    printDivider();
                    System.out.println("===== ATM Menu =====");
                    System.out.println("1. Account Balance Information");
                    System.out.println("2. Money Withdrawal");
                    System.out.println("3. Phone Credits Top-Up");
                    System.out.println("4. Electricity Bills Token");
                    System.out.println("5. Account Mutation");
                    System.out.println("6. Money Deposit");
                    System.out.println("0. Logout");
                    printDivider('=');
                    System.out.print(" > ");
                    int selection = in.nextInt();

                    switch (selection) {
	                    case 1:
	                        ATMLogic.accountBalanceInformation(bank, customer, atm);
	                        break;
	                    case 2:
	                        ATMLogic.moneyWithdrawal(bank, customer, atm);
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
	                        ATMLogic.moneyDeposit(atm, bank, customer.getAccount());
	                        break;
                        case 0:
                            loop = false;
                            loggedIn = false;
                            break;
                        default:
                            System.out.println("Invalid selection. Please try again.");
                            delay();
                    }
                }
        } else {
            System.out.println("Cannot find Bank or ATM");
            delay();
        }
    }

}
