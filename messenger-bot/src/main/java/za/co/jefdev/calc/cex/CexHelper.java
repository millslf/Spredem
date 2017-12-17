package za.co.jefdev.calc.cex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import za.co.jefdev.utils.Call1;

import java.io.IOException;
import java.util.Arrays;

import static za.co.jefdev.utils.Utils.exec;

public class CexHelper {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpEntity<String> entity;
    private ObjectMapper objectMapper =new ObjectMapper();

    public CexHelper() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        entity = new HttpEntity<>("parameters", headers);
    }

    private BtcDao getBtc(String url) {
        return exec(() -> {
            String body = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
            System.out.println(body);
            return objectMapper.readValue(body,BtcDao.class);
        });
    }

    public BtcDao getBtcUsd() {
        return getBtc("https://cex.io/api/last_price/BTC/USD");
    }

    public BtcDao getBtcEur() {
        return getBtc("https://cex.io/api/last_price/BTC/EUR");
    }

    public static void main(String args[]) throws Exception {
        System.out.println(new CexHelper().getBtcUsd());
    }

}
