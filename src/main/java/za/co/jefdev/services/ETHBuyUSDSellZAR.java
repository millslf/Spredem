package za.co.jefdev.services;
import za.co.jefdev.BaseCalc;

public class ETHBuyUSDSellZAR extends BaseCalc {

    Double effectiveSpread;

    public ETHBuyUSDSellZAR() {
    }

    @Override
    public String getMessage() {
        return "BUY ETH AT CEX(USING USD), SELL AT CEX FOR BTC AND THEN ZAR";
    }

    @Override
    public String calcProfit(Double amt){
        Double cexWithFee = amt*(BaseCalc.PROVIDER_BUY_FEE);
        Double actualCostAtFNB = cexWithFee*fnbZarUsd;
        Double numOfETH = amt/ cexETHUSDPrice;
        Double sellAtCurrentRateforBTC = numOfETH * cexBTCforETHAsk;
        Double sellBTCForZAR = sellAtCurrentRateforBTC * lunoBTCAsk;
        Double profit = sellBTCForZAR - actualCostAtFNB;

        return "Buying for: $ " + BaseCalc.formatter.format(amt) + "\n" +
                "Provider(eg CEX) fee at " + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) +"% included: $ " + BaseCalc.formatter.format(cexWithFee) + "\n" +
                "Actual cost at FNB: " + BaseCalc.formatter.format(actualCostAtFNB) + "\n" +
                "Number of ETHER at current rate: ETH " + String.format("%1$,.6f", numOfETH) + "\n" +
                "Number of BTC after BTC-ETH exchange on LUNO: BTC " + String.format("%1$,.6f", sellAtCurrentRateforBTC) + "\n" +
                "Sell BTC at current Luno rate: " + BaseCalc.formatter.format(sellBTCForZAR) + "\n" +
                "Profit: R" + BaseCalc.formatter.format(profit);
    }

    public String printAllRates(){
        effectiveSpread = ((lunoBTCAsk*cexBTCforETHAsk) - cexETHUSDPriceZar)/(lunoBTCAsk*cexBTCforETHAsk)*100-(BaseCalc.PROVIDER_BUY_FEE-1)*100;

        return "Current USDZAR exchange rate: R " + BaseCalc.formatter.format(zarUsd) + "\n" +
                "Current USDZAR exchange rate with FNB " + BaseCalc.formatter.format(((BaseCalc.FNB_FOREX_FEE-1)*100))+ "% foreign exchange charge: R "
                + BaseCalc.formatter.format(fnbZarUsd) + "\n" +
                "CEX ETH buy price: R " + BaseCalc.formatter.format(cexETHUSDPriceZar)+"($" + BaseCalc.formatter.format(cexETHUSDPrice) + ")" + "\n" +
                "CEX ETH-BTC exchange: BTC " + String.format("%1$,.6f", cexBTCforETHAsk)+" per ETH" + "\n" +
                "Luno BTC selling price: R " + BaseCalc.formatter.format(lunoBTCAsk) + "\n" +
                "Effective Spread: " + BaseCalc.formatter.format
                (effectiveSpread) + "%";
    }

    @Override
    public void setSpread() {
        spreadEntity.setBtcBuyEthUSDSellZarSpread(effectiveSpread);
    }

    @Override
    public Double getSpread() {
        return effectiveSpread;
    }

    public Double getOldEmailValue() {
        return oldSpreadVals.getLastEmailspreadBuyEthUSDSellZar();
    }

    public void setOldEmailValue(Double value){
        spreadEntity.setLastEmailspreadBuyEthUSDSellZar(value);
    }
}
