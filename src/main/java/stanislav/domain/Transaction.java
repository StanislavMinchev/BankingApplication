package stanislav.domain;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @Column(name = "from_account_id")
    private int fromAccountId;

    @Column(name = "to_account_id")
    private int toAccountId;

    @Column(name = "amount")
    private BigDecimal amount;

    public Transaction() {

    }

    public Transaction(int fromAccountId, int toAccountId, BigDecimal amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccount) {
        this.fromAccountId = fromAccount;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccount) {
        this.toAccountId = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  "Transaction Id=" + transactionId +
                ", From Account=" + fromAccountId +
                ", To Account=" + toAccountId +
                ", Amount=" + amount;

    }
}
