package za.co.jefdev.tm.luno.account;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.util.DoubleDeserializer;
import lombok.Data;

@Data
public class BalanceDao {
    private String account_id;
    private String asset,name;
    @JsonDeserialize(using = DoubleDeserializer.class)
    private double balance,reserved,unconfirmed;
}
