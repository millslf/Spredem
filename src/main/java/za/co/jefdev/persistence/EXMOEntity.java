package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.messenger.utils.Rest;
import za.co.jefdev.utils.FileReaderWriter;

import java.io.IOException;

public class EXMOEntity extends BaseExchangeEntity {
    public EXMOEntity() throws IOException, ClassNotFoundException {
        JSONObject jsonObject;
        jsonObject = new JSONObject(Rest.makeRequest("https://api.exmo.com/v1/ticker/"));
        for (Object json : jsonObject.keySet()) {
            JSONObject object = jsonObject.getJSONObject(json.toString());
            allPairsDelimited = allPairsDelimited + json.toString().replace("_", "")
                    + object.getString("buy_price") + ",";
        }
        allPairsDelimited = allPairsDelimited.substring(0, allPairsDelimited.length() - 1);
        FileReaderWriter.persistEntities(this);
    }
}
