package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.messenger.utils.Rest;
import za.co.jefdev.utils.FileReaderWriter;

public class QuadrigaEntity extends BaseExchangeEntity {
    public QuadrigaEntity() throws Exception {
        JSONObject jsonObject;
        jsonObject = new JSONObject(Rest.makeEasyRequest("https://api.quadrigacx.com/public/info"));
        for (Object json : jsonObject.keySet()) {
            JSONObject object = jsonObject.getJSONObject(json.toString());
            allPairsDelimited = allPairsDelimited + json.toString().replace("_", "").toUpperCase()
                    + object.getString("sell") + ",";
        }
        allPairsDelimited = allPairsDelimited.substring(0, allPairsDelimited.length() - 1);
        FileReaderWriter.persistEntities(this);
    }
}
