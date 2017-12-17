package za.co.jefdev.calc.cex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class BtcDao {

    private String lprice;
    public String curr1,curr2;

}
