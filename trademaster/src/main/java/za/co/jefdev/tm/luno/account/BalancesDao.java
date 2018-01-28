package za.co.jefdev.tm.luno.account;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BalancesDao {

    private List<BalanceDao> balance = new ArrayList<>();
}
