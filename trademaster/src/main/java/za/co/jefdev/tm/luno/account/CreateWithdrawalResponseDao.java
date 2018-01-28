package za.co.jefdev.tm.luno.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import za.co.jefdev.tm.luno.Constants;
import za.co.jefdev.tm.util.DoubleSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateWithdrawalResponseDao {

    private Constants.WithdrawalType type;
    private String beneficiary_id;

    @JsonSerialize(using = DoubleSerializer.class)
    private Double amount;

    public static void main(String args[]) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(new CreateWithdrawalResponseDao(
                Constants.WithdrawalType.ZAR_EFT,"jkl",200.0)));
    }
}
