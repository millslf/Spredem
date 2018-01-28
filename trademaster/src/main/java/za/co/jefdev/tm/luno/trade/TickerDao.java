package za.co.jefdev.tm.luno.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.util.DoubleDeserializer;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TickerDao {

    private Timestamp timestamp;

    @JsonDeserialize(using = DoubleDeserializer.class)
    private double bid,ask,last_trade,rolling_24_hour_volume;
    private String pair;
}
