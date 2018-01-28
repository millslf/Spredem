package za.co.jefdev.tm.luno.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class GetOrderResponse {

    private Timestamp creation_timestamp,expiration_timestamp,completed_timestamp;
    private String order_id,type,state;

    private Double limit_price,limit_volume,base,counter,fee_base,fee_counter;

}
