package stanislav.service;

import org.springframework.stereotype.Component;
import stanislav.dto.SendMoneyRequest;
import stanislav.exceptions.AccountNonExistentException;
import stanislav.exceptions.InsufficientFundsException;

@Component
public interface BankingService {

    void sendMoney(SendMoneyRequest request) throws InsufficientFundsException, AccountNonExistentException;

    String getAccountBalanceAndTransactions(int accountId) throws AccountNonExistentException;
}
