package za.co.jefdev.persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.jefdev.messenger.utils.Rest;
import za.co.jefdev.utils.FileReaderWriter;

public class BitFinexEntity extends BaseExchangeEntity {
    public BitFinexEntity() throws Exception {
        JSONObject jsonObject;
        JSONArray jsonArray;
        String symbols;
        symbols = Rest.makeEasyRequest("https://api.bitfinex.com/v1/symbols").toUpperCase();
        symbols = symbols.replace("[\"", "t").replace("]", "").replace("\"", "").replace(",", ",t");
        jsonArray = new JSONArray(Rest.makeEasyRequest("https://api.bitfinex.com/v2/tickers?symbols=" + symbols));
        for (Object json : jsonArray) {
            allPairsDelimited = allPairsDelimited + ((JSONArray) json).get(0).toString().replace("t", "") +
                    ((JSONArray) json).get(7) + ",";
        }
        allPairsDelimited = allPairsDelimited.substring(0, allPairsDelimited.length() - 1);
        FileReaderWriter.persistEntities(this);
    }

}
