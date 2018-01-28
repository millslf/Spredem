package za.co.jefdev.tm.luno.transactions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PendingTransactionsDao {
    private String id;
    private List<TransactionDao> pending = new ArrayList<>();
}
