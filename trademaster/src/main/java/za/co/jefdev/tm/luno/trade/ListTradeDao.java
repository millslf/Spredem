package za.co.jefdev.tm.luno.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.luno.Constants;
import za.co.jefdev.tm.util.DoubleDeserializer;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ListTradeDao {

    private boolean is_buy;
    private String order_id,pair;
    private Timestamp timestamp;
    private Constants.Type type;

    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double base,counter,fee_base,fee_counter,volume;

}
