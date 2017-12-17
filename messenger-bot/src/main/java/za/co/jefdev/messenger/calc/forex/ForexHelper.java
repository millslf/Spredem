package za.co.jefdev.messenger.calc.forex;

import lombok.Data;
import org.springframework.web.client.RestTemplate;

@Data
public class ForexHelper {

    // https://free.currencyconverterapi.com/api/v5/convert?q=USD_ZAR,EUR_ZAR&compact=ultra
    private RestTemplate restTemplate = new RestTemplate();

    public ForexDao get() {
        return restTemplate.getForObject("https://free.currencyconverterapi.com/api/v5/convert?q=USD_ZAR,EUR_ZAR&compact=ultra", ForexDao.class);
    }

    public static void main(String args[]) throws Exception {
        System.out.println(new ForexHelper().get());
    }


}
