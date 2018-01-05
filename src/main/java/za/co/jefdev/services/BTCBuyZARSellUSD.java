package za.co.jefdev.services;
import za.co.jefdev.BaseCalc;
import za.co.jefdev.persistence.*;

import java.io.IOException;

public class BTCBuyZARSellUSD extends BaseCalc {

    LunoEntity lunoEntity = null;
    CEXEntity cexEntity = null;
    USDEntity currency = null;

    private static Double LIMIT = new Double(3000);

    Double effectiveSpread;

    public BTCBuyZARSellUSD() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        lunoEntity = (LunoEntity) FileReaderWriter.loadValues(LunoEntity.class.getName().toString());
        cexEntity = (CEXEntity) FileReaderWriter.loadValues(CEXEntity.class.getName().toString());
        currency = (USDEntity) FileReaderWriter.loadValues(USDEntity.class.getName().toString());
    }

    @Override
    public String getMessage() {
        return "BUY BITCOIN AT LUNO, SELL AT CEX(USD)";
    }

    @Override
    public String calcProfit(){
        Double numOfBTC = (LIMIT*currency.getZar())/lunoEntity.getLunoBTCBid();
        Double btcAfterTransfer = numOfBTC- BaseCalc.LUNO_BTC_TRANSFER_FEE;
        Double sellAmount = btcAfterTransfer * cexEntity.getPair("BTCUSD")*currency.getZar();
        Double profit = sellAmount - (LIMIT * currency.getFnbZar());

        return "Buying for: $ " + BaseCalc.formatter.format(LIMIT) + "(R " + BaseCalc.formatter.format(LIMIT * currency.getZar()) +")" + "\n" +
        "Number of Bitcoins at current rate: BTC " + String.format("%1$,.6f", numOfBTC) + "\n" +
        "Number of Bitcoins after Luno transfer fee: BTC " + String.format("%1$,.6f", btcAfterTransfer) + "\n" +
        "Sell at current Cex rate: " + BaseCalc.formatter.format(sellAmount) + "\n" +
        "Profit: R" + BaseCalc.formatter.format(profit);
    }

    public String printAllRates(){
        effectiveSpread = ((cexEntity.getPair("BTCUSD") *currency.getZar()) - lunoEntity.getLunoBTCBid())/((cexEntity.getPair("BTCUSD") *currency.getZar())*100);

        return "Current USDZAR exchange rate: R " + BaseCalc.formatter.format(currency.getZar()) + "\n" +
        "CEX BTC price: R " + BaseCalc.formatter.format(cexEntity.getPair("BTCUSD"))+"($" + BaseCalc.formatter.format(cexEntity.getPair("BTCUSD")) + ")" + "\n" +
                "Luno BTC bid price: R " + BaseCalc.formatter.format(lunoEntity.getLunoBTCBid()) + "\n" +
                "Spread: "
                + BaseCalc.formatter.format(effectiveSpread) + "%";
    }

    @Override
    public void setSpread() {
        spreadEntity.setBtcBuyZarSellUsdSpread(effectiveSpread);
    }

    @Override
    public Double getSpread() {
        return effectiveSpread;
    }

    public Double getOldEmailValue() {
        return oldSpreadVals.getLastEmailspreadBuyZarSellUsd();
    }

    public void setOldEmailValue(Double value){
        spreadEntity.setLastEmailspreadBuyZarSellUsd(value);
    }

    public Boolean isMultiTrade() {
        return false;
    }

}
