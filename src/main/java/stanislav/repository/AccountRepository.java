package stanislav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stanislav.domain.Account;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountByAccountId(int accountId);

    @Query(value = "SELECT BALANCE FROM ACCOUNTS WHERE ACCOUNT_ID = ?1",
            nativeQuery = true)
    BigDecimal findAccountBalance(int accountId);
}
