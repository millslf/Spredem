package za.co.jefdev.tm.luno.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.util.DoubleDeserializer;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TradeDao {
    private Timestamp timestamp;

    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double bid,ask,last_trade,rolling_24_hour_volume;
    private String pair;
}
