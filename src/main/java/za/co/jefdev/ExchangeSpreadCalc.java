package za.co.jefdev;

import za.co.jefdev.persistence.BaseExchangeEntity;
import za.co.jefdev.persistence.CEXEntity;
import za.co.jefdev.persistence.CoinapultEntity;
import za.co.jefdev.persistence.EXMOEntity;
import za.co.jefdev.utils.FileReaderWriter;
import za.co.jefdev.utils.InstantiateClasses;
import za.co.jefdev.utils.Util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExchangeSpreadCalc {

    public static NumberFormat formatter = new DecimalFormat("###,###0.000000");

    public static void main(String[] args) throws Exception {
        ExchangeSpreadCalc newCalc = new ExchangeSpreadCalc();
        newCalc.getAllRatesfromAPIs();
        CEXEntity cexEntity = (CEXEntity) FileReaderWriter.loadValues(CEXEntity.class.getName().toString());
//        QuadrigaEntity quadEntity = (QuadrigaEntity) FileReaderWriter.loadValues(QuadrigaEntity.class.getName().toString());
//        BitFinexEntity bitFinexEntity = (BitFinexEntity) FileReaderWriter.loadValues(BitFinexEntity.class.getName().toString());
        EXMOEntity exmoEntity = (EXMOEntity) FileReaderWriter.loadValues(EXMOEntity.class.getName().toString());
        CoinapultEntity coinapultEntity = (CoinapultEntity) FileReaderWriter.loadValues(CoinapultEntity.class.getName().toString());

//        System.out.println("QUADRIGA CEX");
//        newCalc.compareExchanges(quadEntity, cexEntity);
//        System.out.println("\nBITFINEX CEX");
//        newCalc.compareExchanges(bitFinexEntity, cexEntity);
//        System.out.println("\nQUADRIGA BITFINEX");
//        newCalc.compareExchanges(quadEntity, bitFinexEntity);
        System.out.println("\nCEX EXMO");
        newCalc.compareExchanges(cexEntity, exmoEntity);
//        System.out.println("\nBITFINEX EXMO");
//        newCalc.compareExchanges(bitFinexEntity, exmoEntity);
        System.out.println("\nCOINAPULT CEX");
        newCalc.compareExchanges(coinapultEntity, cexEntity);
        System.out.println("\nCOINAPULT EXMO");
        newCalc.compareExchanges(coinapultEntity, exmoEntity);


    }

    private void compareExchanges(BaseExchangeEntity ex1, BaseExchangeEntity ex2) {
        for (Map.Entry<String, Double> exchange1 : ex1.getAllPairs().entrySet()) {
            for (Map.Entry<String, Double> exchange2 : ex2.getAllPairs().entrySet()) {
                if (exchange1.getKey().equals(exchange2.getKey())) {
                    Double diff = exchange1.getValue() - exchange2.getValue();
                    Double spread = (exchange2.getValue() - exchange1.getValue()) / exchange2.getValue() * 100;
                    System.out.println(exchange1.getKey() + "\t" + exchange1.getValue().toString()
                            + "\t" + exchange2.getValue().toString() + "\t" + formatter.format(diff) + "\t" + formatter.format(spread) + "%");
                }
            }
        }
    }

    public ExchangeSpreadCalc() {
    }

    public void getAllRatesfromAPIs() throws IOException, ClassNotFoundException {
        //Free version only allows two currencies at a time and only 60 calls per hour
        List<Thread> threadlist = new ArrayList<>();
        threadlist.add(new Thread(new InstantiateClasses(CEXEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(CoinapultEntity.class)));
//        threadlist.add(new Thread(new InstantiateClasses(BitFinexEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(EXMOEntity.class)));
        Util.runThreads(threadlist);
    }
}
