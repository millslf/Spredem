package za.co.jefdev.tm.luno.account;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.util.DoubleDeserializer;

public class ReceiveAddressDao {
    private String asset,address;

    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double total_received,total_unconfirmed;
}
