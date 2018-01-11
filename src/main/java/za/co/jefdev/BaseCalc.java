package za.co.jefdev;

import org.json.JSONObject;
import org.reflections.Reflections;
import za.co.jefdev.persistence.*;
import za.co.jefdev.utils.FileReaderWriter;
import za.co.jefdev.utils.InstantiateClasses;
import za.co.jefdev.utils.Rest;
import za.co.jefdev.utils.Util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
        UserEntity userEntity = new UserEntity();
        oldSpreadVals = (SpreadEntity) FileReaderWriter.loadValues(SpreadEntity.class.getName().toString());
        String message = "";
        try {
            Reflections reflections = new Reflections("za.co.jefdev.services");
            Set<Class<? extends BaseCalc>> allClasses = reflections.getSubTypesOf(BaseCalc.class);

            for (Class name : allClasses) {
                BaseCalc subCalc;
                subCalc = (BaseCalc) name.newInstance();
                String subMessage = subCalc.getMessage() + "\n" + subCalc.printAllRates() + "\n\n" + subCalc.calcProfit();
                subCalc.setSpread();
                if (subCalc.getOldEmailValue() != null && subCalc.getSpread().compareTo(subCalc.getOldEmailValue()) >= 0) {
                    subCalc.setOldEmailValue(subCalc.getSpread() + 0.5);
                    for(UserEntity.User user : userEntity.getUserList()){
                        for(Class subscription:user.getSubscriptions()){
                            if(name.equals(subscription)){
//                                GoogleMail.sendMail("btcspredem@gmail.com", "jefdev44", user.getEmailAddress(), subCalc.getMessage(), subMessage);
                            }
                        }
                    }
                } else if (subCalc.getOldEmailValue() == null) {
                    //Arg 0 is startSpread, if spread goes higher, mail will be sent and incremented to a new high.
                    subCalc.setOldEmailValue(new Double(args[0]));
                } else {
                    subCalc.setOldEmailValue(subCalc.getOldEmailValue());
                }
                message = message + "\n\n---------------\n" + subMessage;
            }
            FileReaderWriter.persistEntities(spreadEntity);
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

    public void getAllRatesfromAPIs() throws IOException, ClassNotFoundException {
        //Free version only allows two currencies at a time and only 60 calls per hour

        List<Thread> threadlist = new ArrayList<>();
        threadlist.add(new Thread(new RateRest(usd, eur, "USD_ZAR", "EUR_ZAR",
                "https://free.currencyconverterapi.com/api/v5/convert?q=USD_ZAR,EUR_ZAR&compact=ultra")));
        threadlist.add(new Thread(new RateRest(rub, gbp, "RUB_ZAR", "GBP_ZAR",
                "https://free.currencyconverterapi.com/api/v5/convert?q=RUB_ZAR,GBP_ZAR&compact=ultra")));
        threadlist.add(new Thread(new InstantiateClasses(LunoEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(CEXEntity.class)));
        Util.runThreads(threadlist);
        FileReaderWriter.persistEntities(usd, eur, rub, gbp);
    }

    public class RateRest implements Runnable {

        BaseCurrencyEntity cur1, cur2;
        String curPair1, curPair2, url;

        public RateRest(BaseCurrencyEntity cur1, BaseCurrencyEntity cur2, String curPair1, String curPair2, String url) {
            this.cur1 = cur1;
            this.cur2 = cur2;
            this.curPair1 = curPair1;
            this.curPair2 = curPair2;
            this.url = url;
        }

        public void run(){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(Rest.makeRequest(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
            cur1.setZar(jsonObject.getDouble(curPair1));
            cur2.setZar(jsonObject.getDouble(curPair2));
        }
    }
}
