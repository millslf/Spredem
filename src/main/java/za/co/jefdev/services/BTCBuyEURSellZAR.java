package za.co.jefdev.services;
import za.co.jefdev.BaseCalc;

public class BTCBuyEURSellZAR extends BaseCalc {

    public BTCBuyEURSellZAR() {
    }

    Double effectiveSpread;

    @Override
    public String calcProfit(Double amt){
        Double cexWithFee = amt*(BaseCalc.PROVIDER_BUY_FEE);
        Double actualCostAtFNB = cexWithFee*fnbZarEur;
        Double numOfBTC = amt/ cexEURBTCPrice;
        Double numOfBTCAfterTransfer = numOfBTC- BaseCalc.LUNO_BTC_TRANSFER_FEE;
        Double sellAtCurrentRate = numOfBTCAfterTransfer * lunoBTCAsk;
        Double profit = sellAtCurrentRate - actualCostAtFNB;

        return "Buying for: EUR " + BaseCalc.formatter.format(amt) + "\n" +
        "Provider(eg CEX) fee at " + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) +"% included: EUR " + BaseCalc.formatter.format(cexWithFee) + "\n" +
        "Actual cost at FNB: " + BaseCalc.formatter.format(actualCostAtFNB) + "\n" +
        "Number of Bitcoins at current rate: BTC " + String.format("%1$,.6f", numOfBTC) + "\n" +
        "Number of Bitcoins after Luno transfer fee: BTC " + String.format("%1$,.6f", numOfBTCAfterTransfer) + "\n" +
        "Sell at current Luno rate: " + BaseCalc.formatter.format(sellAtCurrentRate) + "\n" +
        "Profit: R" + BaseCalc.formatter.format(profit);

    }

    @Override
    public void setSpread() {
        spreadEntity.setBtcBuyEurSellZarSpread(effectiveSpread);
    }

    @Override
    public Double getSpread() {
        return effectiveSpread;
    }

    @Override
    public String getMessage() {
        return "BUY BITCOIN AT CEX, SELL AT LUNO(EURO)";
    }

    public String printAllRates(){
        effectiveSpread = (lunoBTCAsk - (cexEURBTCPrice *fnbZarEur))/lunoBTCAsk*100-(BaseCalc.PROVIDER_BUY_FEE-1)*100;

        return "Current EURZAR exchange rate: R " + BaseCalc.formatter.format(zarEur) + "\n" +
        "Current EURZAR exchange rate with FNB " + BaseCalc.formatter.format(((BaseCalc.FNB_FOREX_FEE-1)*100))+ "% foreign exchange charge: R "
                + BaseCalc.formatter.format(fnbZarEur) + "\n" +
        "CEX buy price(Excluding provider fee): R " + BaseCalc.formatter.format(cexEURBTCBuyPriceZar)+"(EUR" + BaseCalc.formatter.format(cexEURBTCPrice) + ")" + "\n" +
        "Luno selling price: R " + BaseCalc.formatter.format(lunoBTCAsk) + "\n" +
        "Spread with "  + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) + "% charge(1 BTC): "
                + BaseCalc.formatter.format(effectiveSpread) + "%";

    }

    public Double getOldEmailValue() {
        return oldSpreadVals.getLastEmailspreadBuyEurSellZar();
    }

    public void setOldEmailValue(Double value){
        spreadEntity.setLastEmailspreadBuyEurSellZar(value);
    }

}
