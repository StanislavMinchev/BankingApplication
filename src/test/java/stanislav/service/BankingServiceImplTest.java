package stanislav.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import stanislav.domain.Account;
import stanislav.domain.Transaction;
import stanislav.dto.SendMoneyRequest;
import stanislav.exceptions.AccountNonExistentException;
import stanislav.exceptions.InsufficientFundsException;
import stanislav.repository.AccountRepository;
import stanislav.repository.TransactionRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class BankingServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BankingServiceImpl bankingService;

    private Account account1, account2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        account1 = new Account();
        account2 = new Account();

        account1.setAccountId(0);
        account1.setHolderName("Stanislav");
        account1.setBalance(new BigDecimal("10"));

        account2.setAccountId(1);
        account2.setHolderName("Minchev");
        account2.setBalance(new BigDecimal("7.2345"));

        Mockito.when(accountRepository.findAccountByAccountId(0)).thenReturn(account1);
        Mockito.when(accountRepository.findAccountByAccountId(1)).thenReturn(account2);
        Mockito.when(transactionRepository.saveAndFlush(any())).thenReturn(new Transaction());
    }

    @Test
    void sendMoney() throws AccountNonExistentException, InsufficientFundsException {
        SendMoneyRequest request = new SendMoneyRequest();
        request.setAmount(new BigDecimal("1.2"));
        request.setFromAccount(0);
        request.setToAccount(1);

        bankingService.sendMoney(request);

        assertEquals(account1.getBalance(), new BigDecimal("8.8"));
        assertEquals(account2.getBalance(), new BigDecimal("8.4345"));
    }
}