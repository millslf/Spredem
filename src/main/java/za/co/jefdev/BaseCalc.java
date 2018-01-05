package za.co.jefdev;

import za.co.jefdev.persistence.*;
import org.json.JSONObject;
import org.reflections.Reflections;
import za.co.jefdev.utils.Rest;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

public class BaseCalc {

    public static Double LUNO_BTC_TRANSFER_FEE = new Double("0.0012"),
            PROVIDER_BUY_FEE = new Double("1.0365");

    private EUREntity eur = new EUREntity();
    private USDEntity usd = new USDEntity();
    private RUBEntity rub = new RUBEntity();
    private GBPEntity gbp = new GBPEntity();

    public static NumberFormat formatter = new DecimalFormat("###,###.00");

    public static SpreadEntity spreadEntity = new SpreadEntity();
    public static SpreadEntity oldSpreadVals = new SpreadEntity();

    public static void main(String[] args) throws Exception {
        BaseCalc baseCalc = new BaseCalc();
        baseCalc.getAllRatesfromAPIs();
        oldSpreadVals = (SpreadEntity) FileReaderWriter.loadValues(SpreadEntity.class.getName().toString());
        CEXEntity cex = new CEXEntity();
        LunoEntity luno = new LunoEntity();

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
                subMessage = subMessage + "\n\n---------------------------------------\n";
                subMessage = subMessage + subCalc.getMessage();
                subMessage = subMessage + "\n---------------------------------------\n";
                subMessage = subMessage + "\n" + subCalc.printAllRates();
                subMessage = subMessage + "\n\n" + subCalc.calcProfit();
                subCalc.setSpread();
                if (subCalc.getOldEmailValue() != null && subCalc.getSpread().compareTo(subCalc.getOldEmailValue()) >= 0) {
                    messageToSend = messageToSend + subMessage;
                    subject = subject == "" ? subCalc.getMessage() : subject + " ---- AND ---- " + subCalc.getMessage();
                    subCalc.setOldEmailValue(subCalc.getSpread() + 0.5);
                } else if (subCalc.getOldEmailValue() == null) {
                    //Arg 0 is startSpread, if spread goes higher, mail will be sent and incremented to a new high.
                    subCalc.setOldEmailValue(new Double(args[0]));
                } else {
                    subCalc.setOldEmailValue(subCalc.getOldEmailValue());
                }
                if (subCalc.getSpread().compareTo(Double.parseDouble("2.0")) >= 0) {
                    spreadAboveTwo = spreadAboveTwo + subMessage;
                }
                message = message + subMessage;
            }
            if (!spreadAboveTwo.equals("")) {
//                System.out.println(spreadAboveTwo);
//                GoogleMail.sendMail("btcspredem@gmail.com", "jefdev44", "millslf@gmail.com", "Above2", spreadAboveTwo);
            }
            FileReaderWriter.persistEntities(spreadEntity);
            if (!messageToSend.equals("")) {
//                GoogleMail.sendMail("btcspredem@gmail.com", "jefdev44", "jaspervdbijl@gmail.com," +
//                        "ettienneleroux@gmail.com, millsgeo@gmail.com, heindrich_leroux@yahoo.com", subject, messageToSend);

            }
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BaseCalc() {
    }

    public void setSpread() {
    }

    public Double getSpread() {
        return null;
    }

    public Double getOldEmailValue() {
        return null;
    }

    public void setOldEmailValue(Double value) {}

    public String getMessage() {
        return "";
    }

    public String printAllRates() {
        return "";
    }

    public String calcProfit() {
        return "";
    }

    public Boolean isMultiTrade() {
        return false;
    }

    public void getAllRatesfromAPIs() throws IOException, ClassNotFoundException {
        //Free version only allows two currencies at a time and only 60 calls per hour
        JSONObject jsonObject = new JSONObject(Rest.makeRequest("https://free.currencyconverterapi.com/api/v5/convert?q=USD_ZAR,EUR_ZAR&compact=ultra"));
        usd.setZar(jsonObject.getDouble("USD_ZAR"));
        eur.setZar(jsonObject.getDouble("EUR_ZAR"));
        jsonObject = new JSONObject(Rest.makeRequest("https://free.currencyconverterapi.com/api/v5/convert?q=RUB_ZAR,GBP_ZAR&compact=ultra"));
        rub.setZar(jsonObject.getDouble("RUB_ZAR"));
        gbp.setZar(jsonObject.getDouble("GBP_ZAR"));
        FileReaderWriter.persistEntities(usd, eur, rub, gbp);
    }
}
