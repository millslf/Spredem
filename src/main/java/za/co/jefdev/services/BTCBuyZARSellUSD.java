//package za.co.jefdev.services;
//import za.co.jefdev.BaseCalc;
//
//public class BTCBuyZARSellUSD extends BaseCalc {
//
//    private static Double LIMIT = new Double(3000);
//
//    Double effectiveSpread;
//
//    public BTCBuyZARSellUSD() {
//    }
//
//    @Override
//    public String getMessage() {
//        return "BUY BITCOIN AT LUNO, SELL AT CEX";
//    }
//
//    @Override
//    public String calcProfit(){
//        Double numOfBTC = (LIMIT*zarUsd)/lunoBTCBid;
//        Double btcAfterTransfer = numOfBTC- BaseCalc.LUNO_BTC_TRANSFER_FEE;
//        Double sellAmount = btcAfterTransfer * cexUSDBTCSellPriceZar;
//        Double profit = sellAmount - (LIMIT * zarUsd);
//
//        return "Buying for: $ " + BaseCalc.formatter.format(LIMIT) + "\n" +
//        "Number of Bitcoins at current rate: BTC " + String.format("%1$,.6f", numOfBTC) + "\n" +
//        "Number of Bitcoins after Luno transfer fee: BTC " + String.format("%1$,.6f", btcAfterTransfer) + "\n" +
//        "Sell at current Cex rate: " + BaseCalc.formatter.format(sellAmount) + "\n" +
//        "Profit: R" + BaseCalc.formatter.format(profit);
//    }
//
//    public String printAllRates(){
//        effectiveSpread = ((cexUSDBTCPrice *zarUsd) - lunoBTCBid)/(cexUSDBTCPrice *zarUsd)*100;
//
//        return "Current USDZAR exchange rate: R " + BaseCalc.formatter.format(zarUsd) + "\n" +
//        "CEX BTC price: R " + BaseCalc.formatter.format(cexUSDBTCSellPriceZar)+"($" + BaseCalc.formatter.format(cexUSDBTCPrice) + ")" + "\n" +
//                "Luno BTC bid price: R " + BaseCalc.formatter.format(lunoBTCBid) + "\n" +
//                "Spread: "
//                + BaseCalc.formatter.format(effectiveSpread) + "%";
//    }
//
//    @Override
//    public void setSpread() {
//        spreadEntity.setBtcBuyZarSellUsdSpread(effectiveSpread);
//    }
//
//    @Override
//    public Double getSpread() {
//        return effectiveSpread;
//    }
//
//    public Double getOldEmailValue() {
//        return oldSpreadVals.getLastEmailspreadBuyZarSellUsd();
//    }
//
//    public void setOldEmailValue(Double value){
//        spreadEntity.setLastEmailspreadBuyZarSellUsd(value);
//    }
//
//    public Boolean isMultiTrade() {
//        return false;
//    }
//
//}
