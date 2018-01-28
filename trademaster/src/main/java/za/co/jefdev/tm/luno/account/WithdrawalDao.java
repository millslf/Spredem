package za.co.jefdev.tm.luno.account;

import lombok.Data;
import za.co.jefdev.tm.luno.Constants;

@Data
public class WithdrawalDao {

    private Constants.State status;
     String id;
}
