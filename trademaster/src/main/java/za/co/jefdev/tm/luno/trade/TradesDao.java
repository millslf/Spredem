package za.co.jefdev.tm.luno.trade;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TradesDao {
    private List<TradesDao> trades = new ArrayList<>();
}
