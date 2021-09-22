package stanislav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stanislav.domain.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT * FROM TRANSACTIONS WHERE FROM_ACCOUNT_ID = ?1 OR TO_ACCOUNT_ID = ?1",
            nativeQuery = true)
    List<Transaction> findTransactionsByAccountID(int accountId);
}
