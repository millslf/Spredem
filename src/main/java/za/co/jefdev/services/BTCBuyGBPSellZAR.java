package za.co.jefdev.services;

import za.co.jefdev.BaseCalc;
import za.co.jefdev.persistence.*;

import java.io.IOException;

public class BTCBuyGBPSellZAR extends BaseCalc {

    LunoEntity lunoEntity = null;
    CEXEntity cexEntity = null;
    GBPEntity currency = null;

    private static Double LIMIT = new Double(2000);

    public BTCBuyGBPSellZAR() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        lunoEntity = (LunoEntity) FileReaderWriter.loadValues(LunoEntity.class.getName().toString());
        cexEntity = (CEXEntity) FileReaderWriter.loadValues(CEXEntity.class.getName().toString());
        currency = (GBPEntity) FileReaderWriter.loadValues(GBPEntity.class.getName().toString());
    }

    Double effectiveSpread;

    @Override
    public String calcProfit(){
        Double cexWithFee = LIMIT*(BaseCalc.PROVIDER_BUY_FEE);
        Double actualCostAtFNB = cexWithFee*currency.getFnbZar();
        Double numOfBTC = LIMIT/ cexEntity.getPair("BTCGBP");
        Double numOfBTCAfterTransfer = numOfBTC- BaseCalc.LUNO_BTC_TRANSFER_FEE;
        Double sellAtCurrentRate = numOfBTCAfterTransfer * lunoEntity.getLunoBTCAsk();
        Double profit = sellAtCurrentRate - actualCostAtFNB;

        return "Buying for: GBP " + BaseCalc.formatter.format(LIMIT) + "\n" +
        "Provider(eg CEX) fee at " + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) +"% included: GBP " + BaseCalc.formatter.format(cexWithFee) + "\n" +
        "Actual cost at FNB: " + BaseCalc.formatter.format(actualCostAtFNB) + "\n" +
        "Number of Bitcoins at current rate: BTC " + String.format("%1$,.6f", numOfBTC) + "\n" +
        "Number of Bitcoins after Luno transfer fee: BTC " + String.format("%1$,.6f", numOfBTCAfterTransfer) + "\n" +
        "Sell at current Luno rate: " + BaseCalc.formatter.format(sellAtCurrentRate) + "\n" +
        "Profit: R" + BaseCalc.formatter.format(profit);

    }

    @Override
    public void setSpread() {
        spreadEntity.setBtcBuyGbpSellZarSpread(effectiveSpread);
    }

    @Override
    public Double getSpread() {
        return effectiveSpread;
    }

    @Override
    public String getMessage() {
        return "BUY BITCOIN AT CEX, SELL AT LUNO(GBP)";
    }

    public String printAllRates(){
        effectiveSpread = (lunoEntity.getLunoBTCAsk() -
                (cexEntity.getPair("BTCGBP") *currency.getFnbZar()))/lunoEntity.getLunoBTCAsk()*100-(BaseCalc.PROVIDER_BUY_FEE-1)*100;

        return "Current GBPZAR exchange rate: R " + BaseCalc.formatter.format(currency.getZar()) + "\n" +
        "Current GBPZAR exchange rate with FNB " + BaseCalc.formatter.format(((currency.FNB_FOREX_FEE-1)*100))+ "% foreign exchange charge: R "
                + BaseCalc.formatter.format(currency.getFnbZar()) + "\n" +
        "CEX buy price(Excluding provider fee): R " + BaseCalc.formatter.format(cexEntity.getPair("BTCGBP"))+
                "(GBP" + BaseCalc.formatter.format(cexEntity.getPair("BTCGBP")) + ")" + "\n" +
        "Luno selling price: R " + BaseCalc.formatter.format(lunoEntity.getLunoBTCAsk()) + "\n" +
        "Spread with "  + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) + "% charge(1 BTC): "
                + BaseCalc.formatter.format(effectiveSpread) + "%";

    }

    public Double getOldEmailValue() {
        return oldSpreadVals.getLastEmailspreadBuyGbpSellZar();
    }

    public void setOldEmailValue(Double value){
        spreadEntity.setLastEmailspreadBuyGbpSellZar(value);
    }

}
