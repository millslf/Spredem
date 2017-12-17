package za.co.jefdev;

import za.co.jefdev.persistence.FileReaderWriter;
import za.co.jefdev.persistence.SpreadEntity;
import org.json.JSONObject;
import org.reflections.Reflections;
import za.co.jefdev.utils.GoogleMail;
import za.co.jefdev.utils.Rest;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

public class BaseCalc {

    public static Double LUNO_BTC_TRANSFER_FEE = new Double("0.0012"),
            FNB_FOREX_FEE = new Double("1.035"),
            PROVIDER_BUY_FEE = new Double("1.035");

    public Double lunoBTCAsk,
            zarEur,
            zarUsd,
            fnbZarEur,
            fnbZarUsd,
            cexBTCforETHAsk,
            cexETHUSDPrice,
            cexETHUSDPriceZar,
            cexETHEURPrice,
            cexETHEURPriceZar,
            lunoBTCBid,
            cexUSDBTCPrice,
            cexUSDBTCBuyPriceZar,
            cexUSDBTCSellPriceZar,
            cexEURBTCPrice,
            cexEURBTCBuyPriceZar,
            cexEURBTCSellPriceZar;

    private Boolean retrievedAllRates = false;

    public static NumberFormat formatter = new DecimalFormat("###,###.00");

    public static SpreadEntity spreadEntity = new SpreadEntity();
    public static SpreadEntity oldSpreadVals = new SpreadEntity();

    public static void main(String[] args) throws Exception {
        BaseCalc baseCalc = new BaseCalc();
        baseCalc.getAllRatesfromAPIs();
        oldSpreadVals = FileReaderWriter.loadValues();

        String messageToSend = "";
        String message = "";
        String subject = "";
        String spreadAboveTwo = "";

        try {
            Reflections reflections = new Reflections("za.co.jefdev.services");
            Set<Class<? extends BaseCalc>> allClasses = reflections.getSubTypesOf(BaseCalc.class);

            for (Class name : allClasses) {
                String subMessage = "";
                BaseCalc subCalc;
                subCalc = (BaseCalc) name.newInstance();
                subCalc.setBaseCalcValues(baseCalc.lunoBTCAsk, baseCalc.zarUsd, baseCalc.zarEur, baseCalc.fnbZarUsd, baseCalc.fnbZarEur,
                        baseCalc.cexBTCforETHAsk, baseCalc.cexETHUSDPrice, baseCalc.cexETHUSDPriceZar, baseCalc.lunoBTCBid,
                        baseCalc.cexUSDBTCPrice, baseCalc.cexUSDBTCBuyPriceZar, baseCalc.cexUSDBTCSellPriceZar,
                        baseCalc.cexEURBTCPrice, baseCalc.cexEURBTCBuyPriceZar, baseCalc.cexEURBTCSellPriceZar,
                        baseCalc.cexETHEURPrice, baseCalc.cexETHEURPriceZar, baseCalc.retrievedAllRates);
                subMessage = subMessage + "\n\n---------------------------------------\n";
                subMessage = subMessage + subCalc.getMessage();
                subMessage = subMessage + "\n---------------------------------------\n";
                subCalc.calculateOtherRates();
                subMessage = subMessage + "\n" + subCalc.printAllRates();
                subMessage = subMessage + "\n\n" + subCalc.calcProfit(new Double("3000"));
                subMessage = subMessage + "\n\n" + subCalc.calcProfit(new Double("10000"));
                subCalc.setSpread();
                if (subCalc.getOldEmailValue() != null && subCalc.getSpread().compareTo(subCalc.getOldEmailValue()) >= 0) {
                    messageToSend = messageToSend + subMessage;
                    subject = subject == "" ? subCalc.getMessage() : subject + " ---- AND ---- " + subCalc.getMessage();
                    subCalc.setOldEmailValue(subCalc.getSpread()+0.5);
                }else if (subCalc.getOldEmailValue() == null) {
                    //Arg 0 is startSpread, if spread goes higher, mail will be sent and incremented to a new high.
                    subCalc.setOldEmailValue(new Double(args[0]));
                }else{
                    subCalc.setOldEmailValue(subCalc.getOldEmailValue());
                }
                if (subCalc.getSpread().compareTo(Double.parseDouble("2.0")) >= 0) {
                    spreadAboveTwo = spreadAboveTwo + subMessage;
                }
                message = message + subMessage;
            }
            System.out.println(message);
            if(!spreadAboveTwo.equals("")){
                System.out.println(spreadAboveTwo);
                GoogleMail.sendMail("btcspredem@gmail.com", "jefdev44", "millslf@gmail.com", "Above2", messageToSend);
            }
            FileReaderWriter.persistEntities(spreadEntity);
            if (!messageToSend.equals("")) {
                GoogleMail.sendMail("btcspredem@gmail.com", "jefdev44", "jaspervdbijl@gmail.com," +
                        "ettienneleroux@gmail.com, millsgeo@gmail.com", subject, messageToSend);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BaseCalc() {
    }

    public void setBaseCalcValues(Double lunoBTCAsk, Double zarUsd, Double zarEur, Double fnbZarUsd, Double fnbZarEur, Double cexBTCforETHAsk, Double cexETHPrice, Double cexETHPriceZar,
                                  Double lunoBTCBid, Double cexBTCPrice, Double cexBTCBuyPriceZar, Double cexBTCSellPriceZar,
                                  Double cexEURBTCPrice, Double cexEURBTCBuyPriceZar, Double cexEURBTCSellPriceZar,
                                  Double cexETHEURPrice, Double cexETHEURPriceZar, Boolean retrievedAllRates) {
        this.lunoBTCAsk = lunoBTCAsk;
        this.zarUsd = zarUsd;
        this.zarEur = zarEur;
        this.fnbZarUsd = fnbZarUsd;
        this.fnbZarEur = fnbZarEur;
        this.cexBTCforETHAsk = cexBTCforETHAsk;
        this.cexETHUSDPrice = cexETHPrice;
        this.cexETHUSDPriceZar = cexETHPriceZar;
        this.lunoBTCBid = lunoBTCBid;
        this.cexUSDBTCPrice = cexBTCPrice;
        this.cexUSDBTCBuyPriceZar = cexBTCBuyPriceZar;
        this.cexUSDBTCSellPriceZar = cexBTCSellPriceZar;
        this.cexEURBTCPrice = cexEURBTCPrice;
        this.cexEURBTCBuyPriceZar = cexEURBTCBuyPriceZar;
        this.cexEURBTCSellPriceZar = cexEURBTCSellPriceZar;
        this.cexETHEURPrice = cexETHEURPrice;
        this.cexETHEURPriceZar = cexETHEURPriceZar;
        this.retrievedAllRates = retrievedAllRates;
    }

    public void setSpread() {
    }

    public Double getSpread() {
        return null;
    }

    public Double getOldEmailValue() {
        return null;
    }

    public void setOldEmailValue(Double value) {

    }

    public String getMessage() {
        return "";
    }



    public void getAllRatesfromAPIs() throws IOException {
        if (!retrievedAllRates) {
            JSONObject jsonObject = new JSONObject(Rest.makeRequest("https://free.currencyconverterapi.com/api/v5/convert?q=USD_ZAR,EUR_ZAR&compact=ultra"));
            zarUsd = jsonObject.getDouble("USD_ZAR");
            zarEur = jsonObject.getDouble("EUR_ZAR");
            jsonObject = new JSONObject(Rest.makeRequest("https://api.mybitx.com/api/1/ticker?pair=XBTZAR"));
            lunoBTCBid = new Double(jsonObject.getString("bid"));
            lunoBTCAsk = new Double(jsonObject.getString("ask"));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/BTC/USD"));
            cexUSDBTCPrice = new Double(jsonObject.getString("lprice"));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/BTC/EUR"));
            cexEURBTCPrice = new Double(jsonObject.getString("lprice"));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/ETH/BTC"));
            cexBTCforETHAsk = new Double(jsonObject.getString("lprice"));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/ETH/USD"));
            cexETHUSDPrice = new Double(jsonObject.getString("lprice"));
            jsonObject = new JSONObject(Rest.makeRequest("https://cex.io/api/last_price/ETH/EUR"));
            cexETHEURPrice = new Double(jsonObject.getString("lprice"));
            retrievedAllRates = true;
        }
    }

    public void calculateOtherRates() {
        fnbZarUsd = zarUsd * FNB_FOREX_FEE;
        fnbZarEur = zarEur * FNB_FOREX_FEE;
        cexUSDBTCBuyPriceZar = cexUSDBTCPrice * fnbZarUsd;
        cexUSDBTCSellPriceZar = cexUSDBTCPrice * zarUsd;
        cexEURBTCBuyPriceZar = cexEURBTCPrice * fnbZarEur;
        cexEURBTCSellPriceZar = cexEURBTCPrice * zarEur;
        cexETHUSDPriceZar = cexETHUSDPrice * fnbZarUsd;
        cexETHEURPriceZar = cexETHEURPrice * fnbZarEur;
    }

    public String printAllRates() {
        return "";
    }


    public String calcProfit(Double amt) {
        return "";
    }




}
