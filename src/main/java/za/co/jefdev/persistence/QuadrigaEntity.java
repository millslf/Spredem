package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.utils.FileReaderWriter;
import za.co.jefdev.utils.Rest;

import java.io.IOException;

public class QuadrigaEntity extends BaseExchangeEntity {
    public QuadrigaEntity() throws IOException, ClassNotFoundException {
        JSONObject jsonObject;
        jsonObject = new JSONObject(Rest.makeRequest("https://api.quadrigacx.com/public/info"));
        for (Object json : jsonObject.keySet()) {
            JSONObject object = jsonObject.getJSONObject(json.toString());
            allPairsDelimited = allPairsDelimited + json.toString().replace("_", "").toUpperCase()
                    + object.getString("buy") + ",";
        }
        allPairsDelimited = allPairsDelimited.substring(0, allPairsDelimited.length() - 1);
        FileReaderWriter.persistEntities(this);
    }

}
