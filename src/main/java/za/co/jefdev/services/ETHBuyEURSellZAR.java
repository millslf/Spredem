//package za.co.jefdev.services;
//import za.co.jefdev.BaseCalc;
//
//public class ETHBuyEURSellZAR extends BaseCalc {
//
//    private static Double LIMIT = new Double(3000);
//
//    Double effectiveSpread;
//
//    public ETHBuyEURSellZAR() {
//    }
//
//    @Override
//    public String getMessage() {
//        return "BUY ETH AT CEX(USING EUR), SELL AT CEX FOR BTC AND THEN ZAR";
//    }
//
//    @Override
//    public String calcProfit(){
//        Double cexWithFee = LIMIT*(BaseCalc.PROVIDER_BUY_FEE);
//        Double actualCostAtFNB = cexWithFee*fnbZarEur;
//        Double numOfETH = LIMIT/ cexETHEURPrice;
//        Double sellAtCurrentRateforBTC = numOfETH * cexBTCforETHAsk;
//        Double sellBTCForZAR = sellAtCurrentRateforBTC * lunoBTCAsk;
//        Double profit = sellBTCForZAR - actualCostAtFNB;
//
//        return "Buying for: EUR " + BaseCalc.formatter.format(LIMIT) + "\n" +
//                "Provider(eg CEX) fee at " + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) +"% included: EUR " + BaseCalc.formatter.format(cexWithFee) + "\n" +
//                "Actual cost at FNB: " + BaseCalc.formatter.format(actualCostAtFNB) + "\n" +
//                "Number of ETHER at current rate: ETH " + String.format("%1$,.6f", numOfETH) + "\n" +
//                "Number of BTC after BTC-ETH exchange on LUNO: BTC " + String.format("%1$,.6f", sellAtCurrentRateforBTC) + "\n" +
//                "Sell BTC at current Luno rate: " + BaseCalc.formatter.format(sellBTCForZAR) + "\n" +
//                "Profit: R" + BaseCalc.formatter.format(profit);
//    }
//
//    public String printAllRates(){
//        effectiveSpread = ((lunoBTCAsk*cexBTCforETHAsk) - cexETHEURPriceZar)/(lunoBTCAsk*cexBTCforETHAsk)*100-(BaseCalc.PROVIDER_BUY_FEE-1)*100;
//
//        return "Current EURZAR exchange rate: R " + BaseCalc.formatter.format(zarEur) + "\n" +
//                "Current EURZAR exchange rate with FNB " + BaseCalc.formatter.format(((BaseCalc.FNB_FOREX_FEE-1)*100))+ "% foreign exchange charge: R "
//                + BaseCalc.formatter.format(fnbZarEur) + "\n" +
//                "CEX ETH buy price: R " + BaseCalc.formatter.format(cexETHEURPriceZar)+"(EUR" + BaseCalc.formatter.format(cexETHEURPrice) + ")" + "\n" +
//                "CEX ETH-BTC exchange: BTC " + String.format("%1$,.6f", cexBTCforETHAsk)+" per ETH" + "\n" +
//                "Luno BTC selling price: R " + BaseCalc.formatter.format(lunoBTCAsk) + "\n" +
//                "Effective Spread: " + BaseCalc.formatter.format
//                (effectiveSpread) + "%";
//    }
//
//    @Override
//    public void setSpread() {
//        spreadEntity.setBtcBuyEthEURSellZarSpread(effectiveSpread);
//    }
//
//    @Override
//    public Double getSpread() {
//        return effectiveSpread;
//    }
//
//    public Double getOldEmailValue() {
//        return oldSpreadVals.getLastEmailspreadBuyEthEURSellZar();
//    }
//
//    public void setOldEmailValue(Double value){
//        spreadEntity.setLastEmailspreadBuyEthEURSellZar(value);
//    }
//
//    public Boolean isMultiTrade() {
//        return true;
//    }
//}
