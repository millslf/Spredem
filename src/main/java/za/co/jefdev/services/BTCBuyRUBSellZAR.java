package za.co.jefdev.services;

import za.co.jefdev.BaseCalc;
import za.co.jefdev.persistence.CEXEntity;
import za.co.jefdev.persistence.LunoEntity;
import za.co.jefdev.persistence.RUBEntity;
import za.co.jefdev.utils.FileReaderWriter;

import java.io.IOException;

public class BTCBuyRUBSellZAR extends BaseCalc {

    LunoEntity lunoEntity = null;
    CEXEntity cexEntity = null;
    RUBEntity currency = null;

    private static Double LIMIT = new Double(180000);

    public BTCBuyRUBSellZAR() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        lunoEntity = (LunoEntity) FileReaderWriter.loadValues(LunoEntity.class.getName().toString());
        cexEntity = (CEXEntity) FileReaderWriter.loadValues(CEXEntity.class.getName().toString());
        currency = (RUBEntity) FileReaderWriter.loadValues(RUBEntity.class.getName().toString());
    }

    Double effectiveSpread;

    @Override
    public String calcProfit(){
        Double cexWithFee = LIMIT*(BaseCalc.PROVIDER_BUY_FEE);
        Double actualCostAtFNB = cexWithFee* currency.getFnbZar();
        Double numOfBTC = LIMIT/ cexEntity.getPair("BTCRUB");
        Double numOfBTCAfterTransfer = numOfBTC- BaseCalc.LUNO_BTC_TRANSFER_FEE;
        Double sellAtCurrentRate = numOfBTCAfterTransfer * lunoEntity.getLunoBTCAsk();
        Double profit = sellAtCurrentRate - actualCostAtFNB;

        return "Buying for: RUB " + BaseCalc.formatter.format(LIMIT) + "\n" +
        "Provider(eg CEX) fee at " + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) +"% included: RUB " + BaseCalc.formatter.format(cexWithFee) + "\n" +
        "Actual cost at FNB: " + BaseCalc.formatter.format(actualCostAtFNB) + "\n" +
        "Number of Bitcoins at current rate: BTC " + String.format("%1$,.6f", numOfBTC) + "\n" +
        "Number of Bitcoins after Luno transfer fee: BTC " + String.format("%1$,.6f", numOfBTCAfterTransfer) + "\n" +
        "Sell at current Luno rate: " + BaseCalc.formatter.format(sellAtCurrentRate) + "\n" +
        "Profit: R" + BaseCalc.formatter.format(profit);

    }

    @Override
    public void setSpread() {
        spreadEntity.setBtcBuyRubSellZarSpread(effectiveSpread);
    }

    @Override
    public Double getSpread() {
        return effectiveSpread;
    }

    @Override
    public String getMessage() {
        return "BUY BITCOIN AT CEX, SELL AT LUNO(RUB)";
    }

    public String printAllRates(){
        effectiveSpread = (lunoEntity.getLunoBTCAsk() -
                (cexEntity.getPair("BTCRUB") * currency.getFnbZar()))/lunoEntity.getLunoBTCAsk()*100-(BaseCalc.PROVIDER_BUY_FEE-1)*100;

        return "Current RUBZAR exchange rate: R " + BaseCalc.formatter.format(currency.getZar()) + "\n" +
        "Current RUBZAR exchange rate with FNB " + BaseCalc.formatter.format(((currency.FNB_FOREX_FEE-1)*100))+ "% foreign exchange charge: R "
                + BaseCalc.formatter.format(currency.getFnbZar()) + "\n" +
        "CEX buy price(Excluding provider fee): R " + BaseCalc.formatter.format(cexEntity.getPair("BTCRUB") * currency.getFnbZar())
                +"(RUB" + BaseCalc.formatter.format(cexEntity.getPair("BTCRUB")) + ")" + "\n" +
        "Luno selling price: R " + BaseCalc.formatter.format(lunoEntity.getLunoBTCAsk()) + "\n" +
        "Spread with "  + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) + "% charge(1 BTC): "
                + BaseCalc.formatter.format(effectiveSpread) + "%";

    }

    public Double getOldEmailValue() {
        return oldSpreadVals.getLastEmailspreadBuyRubSellZar();
    }

    public void setOldEmailValue(Double value){
        spreadEntity.setLastEmailspreadBuyRubSellZar(value);
    }

    public Boolean isMultiTrade() {
        return false;
    }

}
