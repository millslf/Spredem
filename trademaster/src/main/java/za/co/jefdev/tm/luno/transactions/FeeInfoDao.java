package za.co.jefdev.tm.luno.transactions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.util.DoubleDeserializer;
import lombok.Data;

@Data
public class FeeInfoDao {

    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double maker_fee,taker_fee,thirty_day_volume;
}
