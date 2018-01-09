package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.utils.Rest;
import java.io.IOException;

public class QuadrigaEntity extends BaseExchangeEntity{
    public QuadrigaEntity() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(Rest.makeRequest("https://api.quadrigacx.com/public/info"));
            for(Object json:jsonObject.keySet()){
                JSONObject object = jsonObject.getJSONObject(json.toString());
                allPairsDelimited = allPairsDelimited + json.toString().replace("_","").toUpperCase()
                        + object.getString("buy") + ",";
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
