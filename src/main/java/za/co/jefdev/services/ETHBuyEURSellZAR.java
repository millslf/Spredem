package za.co.jefdev.services;

import za.co.jefdev.BaseCalc;
import za.co.jefdev.persistence.CEXEntity;
import za.co.jefdev.persistence.EUREntity;
import za.co.jefdev.persistence.LunoEntity;
import za.co.jefdev.utils.FileReaderWriter;

import java.io.IOException;

public class ETHBuyEURSellZAR extends BaseCalc {

    private static Double LIMIT = new Double(3000);

    Double effectiveSpread;
    LunoEntity lunoEntity = null;
    CEXEntity cexEntity = null;
    EUREntity currency = null;

    public ETHBuyEURSellZAR() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        lunoEntity = (LunoEntity) FileReaderWriter.loadValues(LunoEntity.class.getName().toString());
        cexEntity = (CEXEntity) FileReaderWriter.loadValues(CEXEntity.class.getName().toString());
        currency = (EUREntity) FileReaderWriter.loadValues(EUREntity.class.getName().toString());
    }

    @Override
    public String getMessage() {
        return "BUY ETH AT CEX(USING EUR), SELL AT CEX FOR BTC AND THEN ZAR";
    }

    @Override
    public String calcProfit(){
        Double cexWithFee = LIMIT*(BaseCalc.PROVIDER_BUY_FEE);
        Double actualCostAtFNB = cexWithFee*currency.getFnbZar(null);
        Double numOfETH = LIMIT/ cexEntity.getPair("ETHEUR");
        Double sellAtCurrentRateforBTC = numOfETH * cexEntity.getPair("ETHBTC");
        Double sellBTCForZAR = sellAtCurrentRateforBTC * lunoEntity.getLunoBTCAsk();
        Double profit = sellBTCForZAR - actualCostAtFNB;

        return "Buying for: EUR " + BaseCalc.formatter.format(LIMIT) + "\n" +
                "Provider(eg CEX) fee at " + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100)) +"% included: EUR " + BaseCalc.formatter.format(cexWithFee) + "\n" +
                "Actual cost at FNB: " + BaseCalc.formatter.format(actualCostAtFNB) + "\n" +
                "Number of ETHER at current rate: ETH " + String.format("%1$,.6f", numOfETH) + "\n" +
                "Number of BTC after BTC-ETH exchange on LUNO: BTC " + String.format("%1$,.6f", sellAtCurrentRateforBTC) + "\n" +
                "Sell BTC at current Luno rate: " + BaseCalc.formatter.format(sellBTCForZAR) + "\n" +
                "Profit: R" + BaseCalc.formatter.format(profit);
    }

    public String printAllRates(){
        effectiveSpread = (lunoEntity.getLunoBTCAsk() -
                1/cexEntity.getPair("ETHBTC")*cexEntity.getPair("ETHEUR")*currency.getFnbZar(null)) / lunoEntity.getLunoBTCAsk() * 100 - (BaseCalc.PROVIDER_BUY_FEE - 1) * 100;


        return "Current EURZAR exchange rate: R " + BaseCalc.formatter.format(currency.getZar()) + "\n" +
                "Current EURZAR exchange rate with FNB " + BaseCalc.formatter.format(((BaseCalc.PROVIDER_BUY_FEE-1)*100))+ "% foreign exchange charge: R "
                + BaseCalc.formatter.format(currency.getFnbZar(null)) + "\n" +
                "CEX ETH buy price: R " + BaseCalc.formatter.format(cexEntity.getPair("ETHEUR")*currency.getZar())+"(EUR" + BaseCalc.formatter.format(cexEntity.getPair("ETHEUR")) + ")" + "\n" +
                "CEX ETH-BTC exchange: BTC " + String.format("%1$,.6f", cexEntity.getPair("ETHBTC"))+" per ETH" + "\n" +
                "Luno BTC selling price: R " + BaseCalc.formatter.format(lunoEntity.getLunoBTCAsk()) + "\n" +
                "Effective Spread: " + BaseCalc.formatter.format
                (effectiveSpread) + "%";
    }

    @Override
    public void setSpread() {
        spreadEntity.setBtcBuyEthEURSellZarSpread(effectiveSpread);
    }

    @Override
    public Double getSpread() {
        return effectiveSpread;
    }

    public Double getOldEmailValue() {
        return oldSpreadVals.getLastEmailspreadBuyEthEURSellZar();
    }

    public void setOldEmailValue(Double value){
        spreadEntity.setLastEmailspreadBuyEthEURSellZar(value);
    }

    public Boolean isMultiTrade() {
        return true;
    }
}
