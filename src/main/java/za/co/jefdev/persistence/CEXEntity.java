package za.co.jefdev.persistence;

import org.json.JSONObject;
import za.co.jefdev.utils.Rest;

import java.io.IOException;
import java.io.Serializable;

public class CEXEntity  implements Serializable {
    private Double cexUSDBTCPrice, cexEURBTCPrice, cexBTCforETHAsk, cexETHUSDPrice, cexETHEURPrice, cexRUBBTCPrice, cexGBPBTCPrice;

    public CEXEntity() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/BTC/USD"));
            setCexUSDBTCPrice(new Double(jsonObject.getString("lprice")));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/BTC/EUR"));
            setCexEURBTCPrice(new Double(jsonObject.getString("lprice")));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/ETH/BTC"));
            setCexBTCforETHAsk(new Double(jsonObject.getString("lprice")));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/ETH/USD"));
            setCexETHUSDPrice(new Double(jsonObject.getString("lprice")));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/ETH/EUR"));
            setCexETHEURPrice(new Double(jsonObject.getString("lprice")));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/BTC/RUB"));
            setCexRUBBTCPrice(new Double(jsonObject.getString("lprice")));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/BTC/GBP"));
            setCexGBPBTCPrice(new Double(jsonObject.getString("lprice")));
            FileReaderWriter.persistEntities(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Double getCexUSDBTCPrice() {
        return cexUSDBTCPrice;
    }

    public void setCexUSDBTCPrice(Double cexUSDBTCPrice) {
        this.cexUSDBTCPrice = cexUSDBTCPrice;
    }

    public Double getCexEURBTCPrice() {
        return cexEURBTCPrice;
    }

    public void setCexEURBTCPrice(Double cexEURBTCPrice) {
        this.cexEURBTCPrice = cexEURBTCPrice;
    }

    public Double getCexBTCforETHAsk() {
        return cexBTCforETHAsk;
    }

    public void setCexBTCforETHAsk(Double cexBTCforETHAsk) {
        this.cexBTCforETHAsk = cexBTCforETHAsk;
    }

    public Double getCexETHUSDPrice() {
        return cexETHUSDPrice;
    }

    public void setCexETHUSDPrice(Double cexETHUSDPrice) {
        this.cexETHUSDPrice = cexETHUSDPrice;
    }

    public Double getCexETHEURPrice() {
        return cexETHEURPrice;
    }

    public void setCexETHEURPrice(Double cexETHEURPrice) {
        this.cexETHEURPrice = cexETHEURPrice;
    }

    public Double getCexRUBBTCPrice() {
        return cexRUBBTCPrice;
    }

    public void setCexRUBBTCPrice(Double cexRUBBTCPrice) {
        this.cexRUBBTCPrice = cexRUBBTCPrice;
    }

    public Double getCexGBPBTCPrice() {
        return cexGBPBTCPrice;
    }

    public void setCexGBPBTCPrice(Double cexGBPBTCPrice) {
        this.cexGBPBTCPrice = cexGBPBTCPrice;
    }
}
