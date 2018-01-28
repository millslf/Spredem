package za.co.jefdev.tm.luno.order;

import lombok.Data;

@Data
public class CreateLimitOrderRequest {

    private String pair,type,base_account_id,counter_account_id;
    private double volume,price;

}
