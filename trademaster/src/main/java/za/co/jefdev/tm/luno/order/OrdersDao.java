package za.co.jefdev.tm.luno.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrdersDao {
    private List<OrderDao> orders = new ArrayList<>();
}
