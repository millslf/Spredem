package za.co.jefdev.tm.luno.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import za.co.jefdev.tm.util.DoubleDeserializer;

import java.sql.Timestamp;

@Data
public class OrderDao {

    private Timestamp expiration_timestamp,completed_timestamp;

    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double base,counter,fee_base,fee_counter,limit_price,limit_volume;
    private String order_id,pair,state,type;

}
