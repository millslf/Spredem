package za.co.jefdev.persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.jefdev.messenger.utils.Rest;
import za.co.jefdev.utils.FileReaderWriter;

public class XBTCEEntity extends BaseExchangeEntity {
    public XBTCEEntity() throws Exception {
        JSONArray jsonArray;
        jsonArray = new JSONArray(Rest.makeEasyRequest("https://cryptottlivewebapi.xbtce.net:8443/api/v1/public/ticker"));
        for (Object json : jsonArray) {
            if (json instanceof JSONObject) {
                allPairsDelimited = allPairsDelimited + ((JSONObject) json).getString("Symbol") + ((JSONObject) json).getDouble("BestAsk") + ",";
            }
        }
        allPairsDelimited = allPairsDelimited.substring(0, allPairsDelimited.length() - 1);
        FileReaderWriter.persistEntities(this);
    }


}
