package za.co.jefdev.messenger.calc.forex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForexDao {

    // {"USD_ZAR":13.580896,"EUR_ZAR":16.012134}

    @JsonProperty("USD_ZAR")
    private Double usdZar;
    @JsonProperty("EUR_ZAR")
    private Double eurZar;
}
