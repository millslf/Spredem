package za.co.jefdev.tm.luno.transactions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionsDao {
    private List<TransactionDao> trades = new ArrayList<>();
}
