package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.utils.FileReaderWriter;
import za.co.jefdev.utils.Rest;

import java.io.IOException;

public class CoinapultEntity extends BaseExchangeEntity {
    public CoinapultEntity() throws IOException, ClassNotFoundException {
        JSONObject jsonObject;
        jsonObject = new JSONObject(Rest.makeRequest("https://api.coinapult.com/api/ticker?market=EUR_BTC&filter=small"));
                allPairsDelimited = allPairsDelimited + "BTCEUR" +
                        jsonObject.getJSONObject("small").getDouble("ask") + ",";
        jsonObject = new JSONObject(Rest.makeRequest("https://api.coinapult.com/api/ticker?market=USD_BTC&filter=small"));
        allPairsDelimited = allPairsDelimited + "BTCUSD" +
                jsonObject.getJSONObject("small").getDouble("ask") + ",";
        jsonObject = new JSONObject(Rest.makeRequest("https://api.coinapult.com/api/ticker?market=GBP_BTC&filter=small"));
        allPairsDelimited = allPairsDelimited + "BTCGBP" +
                jsonObject.getJSONObject("small").getDouble("ask") + ",";
        allPairsDelimited = allPairsDelimited.substring(0, allPairsDelimited.length() - 1);
        FileReaderWriter.persistEntities(this);
    }
}
