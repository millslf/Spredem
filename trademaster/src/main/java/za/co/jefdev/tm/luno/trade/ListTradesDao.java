package za.co.jefdev.tm.luno.trade;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListTradesDao {
    private List<ListTradeDao> trades = new ArrayList<>();
}
