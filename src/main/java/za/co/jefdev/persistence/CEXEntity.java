package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.utils.Rest;
import java.io.IOException;

public class CEXEntity extends BaseExchangeEntity{
    public CEXEntity() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_prices/USD/EUR/RUB/GBP/BTC"));
            for(Object json:jsonObject.getJSONArray("data")){
                if ( json instanceof JSONObject ) {
                    allPairsDelimited = allPairsDelimited + ((JSONObject)json).getString("symbol1") +
                            ((JSONObject)json).getString("symbol2") + ((JSONObject)json).getString("lprice") + ",";

                }
            }
            allPairsDelimited = allPairsDelimited.substring(0, allPairsDelimited.length() -1);
            FileReaderWriter.persistEntities(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
