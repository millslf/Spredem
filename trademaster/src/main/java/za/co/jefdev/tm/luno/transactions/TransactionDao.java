package za.co.jefdev.tm.luno.transactions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.util.DoubleDeserializer;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionDao {

    private long row_index;
    private Timestamp timestamp;
    @JsonDeserialize(using = DoubleDeserializer.class)
    private double balance,available,balance_delta,available_delta;

    private String currency,description;

}
