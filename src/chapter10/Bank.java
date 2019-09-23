package chapter10;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Bank {
    public static final int N_ACCOUNTS = 10;
    private static final List<Account> accounts = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        createAccounts();

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        //do transfers in 2 threads
        threadPool.submit(() -> doMoneyTransfers());
        threadPool.submit(() -> doMoneyTransfers());
    }

    private static void doMoneyTransfers() {
        Random random = new Random();
        while (true) {
            Account from = accounts.get(random.nextInt(N_ACCOUNTS));
            Account to = accounts.get(random.nextInt(N_ACCOUNTS));

            if (from != to) {
                transferMoney(from, to, new BigDecimal(random.nextInt(100)));
            }
        }
    }

    private static void createAccounts() {
        Random random = new Random();

        IntStream.rangeClosed(0, N_ACCOUNTS).forEach(i -> {
            accounts.add(new Account(String.format("#%s", i), new BigDecimal(random.nextInt(2000))));
        });
    }

    private static void transferMoney(Account from, Account to, BigDecimal amount) {
        System.out.printf("transfer Eur %s from %s to %s%n", amount, from, to);
        synchronized (from) {
            synchronized (to) {
                from.debit(amount);
                to.credit(amount);
                System.out.printf("Eur %s transferred%n", amount);
            }
        }
    }

    static class Account {
        private String accountNumber;
        private BigDecimal amount;

        public Account(String number, BigDecimal amount) {
            this.accountNumber = number;
            this.amount = amount;
        }

        public void debit(BigDecimal debitAmount) {
            this.amount.subtract(debitAmount);
        }

        public void credit(BigDecimal creditAmount) {
            this.amount.add(creditAmount);
        }

        @Override
        public String toString() {
            return this.accountNumber;
        }
    }
}


