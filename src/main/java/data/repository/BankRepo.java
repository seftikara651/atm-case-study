package main.java.data.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import main.java.data.constant.BankCompany;
import main.java.data.model.Bank;
import main.java.data.model.Customer;

public class BankRepo {

    private static final long DEFAULT_DAILY_EXPENSE = 2_500_000;
    private static final long DEFAULT_WITHDRAWAL_LIMIT = 5_000_000;

    private static final Set<Bank> store = new HashSet<>();

    static {
        Bank bri = new Bank();
        bri.setId(UUID.randomUUID().toString());
        bri.setName(BankCompany.BRI.getName());
        bri.setMaxExpensePerUserDaily(BigDecimal.valueOf(DEFAULT_DAILY_EXPENSE));
        bri.setMaxExpensePerWithdrawal(BigDecimal.valueOf(DEFAULT_WITHDRAWAL_LIMIT));
        store.add(bri);

        Customer briCustomer = new Customer();
        briCustomer.setId(UUID.randomUUID().toString());
        briCustomer.setAccount("111111111");
        briCustomer.setPin("111111");
        briCustomer.setFullName("Nasabah BRI 1");
        briCustomer.setBalance(BigDecimal.valueOf(5_000_000));
        bri.getCustomers().add(briCustomer);

        Bank bni = new Bank();
        bni.setId(UUID.randomUUID().toString());
        bni.setName(BankCompany.BNI.getName());
        bni.setDepositFeature(true);
        bni.setMaxExpensePerUserDaily(BigDecimal.valueOf(DEFAULT_DAILY_EXPENSE));
        bni.setMaxExpensePerWithdrawal(BigDecimal.valueOf(DEFAULT_WITHDRAWAL_LIMIT));
        store.add(bni);

        Customer bniCustomer = new Customer();
        bniCustomer.setId(UUID.randomUUID().toString());
        bniCustomer.setAccount("222222222");
        bniCustomer.setPin("222222");
        bniCustomer.setFullName("Nasabah BNI 1");
        bniCustomer.setBalance(BigDecimal.valueOf(3_000_000));
        bni.getCustomers().add(bniCustomer);

        Bank mandiri = new Bank();
        mandiri.setId(UUID.randomUUID().toString());
        mandiri.setName(BankCompany.MANDIRI.getName());
        mandiri.setDepositFeature(true);
        mandiri.setMaxExpensePerUserDaily(BigDecimal.valueOf(DEFAULT_DAILY_EXPENSE));
        mandiri.setMaxExpensePerWithdrawal(BigDecimal.valueOf(DEFAULT_WITHDRAWAL_LIMIT));
        store.add(mandiri);

        Customer mandiriCustomer = new Customer();
        mandiriCustomer.setId(UUID.randomUUID().toString());
        mandiriCustomer.setAccount("333333333");
        mandiriCustomer.setPin("333333");
        mandiriCustomer.setFullName("Nasabah Mandiri 1");
        mandiriCustomer.setBalance(BigDecimal.valueOf(1_500_000));
        mandiri.getCustomers().add(mandiriCustomer);

        Bank bca = new Bank();
        bca.setId(UUID.randomUUID().toString());
        bca.setName(BankCompany.BCA.getName());
        bca.setMaxExpensePerUserDaily(BigDecimal.valueOf(DEFAULT_DAILY_EXPENSE));
        bca.setMaxExpensePerWithdrawal(BigDecimal.valueOf(DEFAULT_WITHDRAWAL_LIMIT));
        store.add(bca);

        Customer bcaCustomer = new Customer();
        bcaCustomer.setId(UUID.randomUUID().toString());
        bcaCustomer.setAccount("444444444");
        bcaCustomer.setPin("444444");
        bcaCustomer.setFullName("Nasabah BCA 1");
        bcaCustomer.setBalance(BigDecimal.valueOf(240_000));
        bca.getCustomers().add(bcaCustomer);
    }

    public static Optional<Bank> findBankByName(String name) {
        return store.stream().filter(item -> name.equals(item.getName())).findAny();
    }

    public static Optional<Bank> findBankByAccountNumber(String accountNumber) {
        return store.stream()
                .filter(bank -> bank.getCustomers().stream()
                        .anyMatch(customer -> customer.getAccount().equals(accountNumber)))
                .findFirst();
    }
}

