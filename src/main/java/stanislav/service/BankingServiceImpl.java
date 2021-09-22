package stanislav.service;

import org.springframework.stereotype.Component;
import stanislav.domain.Account;
import stanislav.domain.Transaction;
import stanislav.dto.SendMoneyRequest;
import stanislav.exceptions.AccountNonExistentException;
import stanislav.exceptions.InsufficientFundsException;
import stanislav.repository.AccountRepository;
import stanislav.repository.TransactionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Component
public class BankingServiceImpl implements BankingService {

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    public BankingServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Method used to send money from one account to another
     * @param request request that contains needed information for the transfer:
     *                from which account
     *                to which account
     *                amount to transfer
     * @throws InsufficientFundsException if the sender doesn't have enough money in the account to complete the transfer
     * @throws AccountNonExistentException if the account doesn't exist in the database
     */
    @Override
    public void sendMoney(SendMoneyRequest request) throws InsufficientFundsException, AccountNonExistentException {
        Account fromAccount = accountRepository.findAccountByAccountId(request.getFromAccount());
        if(null == fromAccount) {
            throw new AccountNonExistentException("Account entered as 'fromAccount' doesn't exist!");
        }

        Account toAccount = accountRepository.findAccountByAccountId(request.getToAccount());
        if(null == toAccount) {
            throw new AccountNonExistentException("Account entered as 'toAccount' doesn't exist!");
        }

        if(fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("The account you're trying to send money from has insufficient funds!");
        }

        //adjust balances of both accounts
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));

        Transaction transaction = new Transaction(request.getFromAccount(), request.getToAccount(), request.getAmount());

        accountRepository.saveAllAndFlush(Arrays.asList(fromAccount, toAccount));
        transactionRepository.saveAndFlush(transaction);
    }

    /**
     * Method used to get an account's balance and list of transactions
     * @param accountId id of the account
     * @return a String containing the account's balance and a list of transactions
     * @throws AccountNonExistentException if the account doesn't exist in the database
     */
    @Override
    public String getAccountBalanceAndTransactions(int accountId) throws AccountNonExistentException {
        BigDecimal balance = accountRepository.findAccountBalance(accountId);
        if(null == balance) {
            throw new AccountNonExistentException("Account doesn't exist!");
        }

        List<Transaction> transactions = transactionRepository.findTransactionsByAccountID(accountId);

        StringBuilder stringToReturn = new StringBuilder("Account summary:\n");
        //Round the balance to 2 digits before displaying
        stringToReturn.append("Balance: ").append(balance.setScale(2, RoundingMode.HALF_EVEN)).append("\n");
        if(transactions.isEmpty()) {
            stringToReturn.append("No transactions found for the account.");
        } else {
            transactions.forEach(x -> {
                //round the balance to 2 digits before displaying
                x.setAmount(x.getAmount().setScale(2, RoundingMode.HALF_EVEN));
                stringToReturn.append(x.toString());
                stringToReturn.append("\n");
            });
        }
        return stringToReturn.toString();
    }
}