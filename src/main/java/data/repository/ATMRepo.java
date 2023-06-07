package main.java.data.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import main.java.data.constant.BankCompany;
import main.java.data.model.ATM;
import main.java.data.model.Bank;

public class ATMRepo {

    private static final long DEFAULT_ATM_BALANCE = 15_000_000;

    private static final Set<ATM> store = new HashSet<>();

    static {
        Optional<Bank> briBank = BankRepo.findBankByName(BankCompany.BRI.getName());
        briBank.ifPresent(bank -> {
            ATM briATM = new ATM();
            briATM.setBank(bank);
            briATM.setBalance(BigDecimal.valueOf(DEFAULT_ATM_BALANCE));
            store.add(briATM);
        });

        Optional<Bank> bniBank = BankRepo.findBankByName(BankCompany.BNI.getName());
        bniBank.ifPresent(bank -> {
            ATM bniATM = new ATM();
            bniATM.setBank(bank);
            bniATM.setBalance(BigDecimal.valueOf(DEFAULT_ATM_BALANCE));
            store.add(bniATM);
        });

        Optional<Bank> mandiriBank = BankRepo.findBankByName(BankCompany.MANDIRI.getName());
        mandiriBank.ifPresent(bank -> {
            ATM mandiriATM = new ATM();
            mandiriATM.setBank(bank);
            mandiriATM.setBalance(BigDecimal.valueOf(DEFAULT_ATM_BALANCE));
            store.add(mandiriATM);
        });

        Optional<Bank> bcaBank = BankRepo.findBankByName(BankCompany.BCA.getName());
        bcaBank.ifPresent(bank -> {
            ATM bcaATM = new ATM();
            bcaATM.setBank(bank);
            bcaATM.setBalance(BigDecimal.valueOf(DEFAULT_ATM_BALANCE));
            store.add(bcaATM);
        });
    }

    public static Optional<ATM> findATMByBank(Bank bank) {
        return store.stream().filter(item -> bank.equals(item.getBank())).findAny();
    }
}
