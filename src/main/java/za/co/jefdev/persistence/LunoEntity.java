package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.messenger.utils.Rest;
import za.co.jefdev.utils.FileReaderWriter;

import java.io.Serializable;

public class LunoEntity implements Serializable {

    Double lunoBTCBid, lunoBTCAsk;

    public LunoEntity() throws Exception {
        JSONObject jsonObject = null;
        jsonObject = new JSONObject(Rest.makeEasyRequest("https://api.mybitx.com/api/1/ticker?pair=XBTZAR"));
        lunoBTCBid = new Double(jsonObject.getString("bid"));
        lunoBTCAsk = new Double(jsonObject.getString("ask"));
        FileReaderWriter.persistEntities(this);
    }

    public Double getLunoBTCBid() {
        return lunoBTCBid;
    }

    public void setLunoBTCBid(Double lunoBTCBid) {
        this.lunoBTCBid = lunoBTCBid;
    }

    public Double getLunoBTCAsk() {
        return lunoBTCAsk;
    }

    public void setLunoBTCAsk(Double lunoBTCAsk) {
        this.lunoBTCAsk = lunoBTCAsk;
    }
}
