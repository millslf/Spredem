package za.co.jefdev;

import za.co.jefdev.messenger.utils.Util;
import za.co.jefdev.persistence.*;
import za.co.jefdev.utils.FileReaderWriter;
import za.co.jefdev.utils.InstantiateClasses;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExchangeSpreadCalc {

    public static void main(String[] args) {
        ExchangeSpreadCalc newCalc = new ExchangeSpreadCalc();
        try {
            newCalc.getAllRatesfromAPIs();

            CEXEntity cexEntity = (CEXEntity) FileReaderWriter.loadValues(CEXEntity.class.getName().toString());
            EXMOEntity exmoEntity = (EXMOEntity) FileReaderWriter.loadValues(EXMOEntity.class.getName().toString());
            CoinapultEntity coinapultEntity = (CoinapultEntity) FileReaderWriter.loadValues(CoinapultEntity.class.getName().toString());
            QuadrigaEntity quadrigaEntity = (QuadrigaEntity) FileReaderWriter.loadValues(QuadrigaEntity.class.getName().toString());
            BitFinexEntity bitFinexEntity = (BitFinexEntity) FileReaderWriter.loadValues(BitFinexEntity.class.getName().toString());
            BitsaneEntity bitsaneEntity = (BitsaneEntity) FileReaderWriter.loadValues(BitsaneEntity.class.getName().toString());
            XBTCEEntity xbtceEntity = (XBTCEEntity) FileReaderWriter.loadValues(XBTCEEntity.class.getName().toString());

            System.out.println("\nCEX EXMO");
            newCalc.compareExchanges(cexEntity, exmoEntity);
            System.out.println("\nCOINAPULT CEX");
            newCalc.compareExchanges(coinapultEntity, cexEntity);
            System.out.println("\nCOINAPULT EXMO");
            newCalc.compareExchanges(coinapultEntity, exmoEntity);
            System.out.println("\nCOINAPULT QUADRIGA");
            newCalc.compareExchanges(coinapultEntity, quadrigaEntity);
            System.out.println("\nQUADRIGA CEX");
            newCalc.compareExchanges(quadrigaEntity, cexEntity);
            System.out.println("\nQUADRIGA EXMO");
            newCalc.compareExchanges(quadrigaEntity, exmoEntity);
            System.out.println("\nBITFINEX CEX");
            newCalc.compareExchanges(bitFinexEntity, cexEntity);
            System.out.println("BITSANE EXMO");
            newCalc.compareExchanges(bitsaneEntity, exmoEntity);
            System.out.println("XBTC EXMO");
            newCalc.compareExchanges(xbtceEntity, exmoEntity);

//            newCalc.allExchanges(xbtceEntity, exmoEntity, bitsaneEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void allExchanges(BaseExchangeEntity... exchangeInfo) {
        Map<String, Double> highest = new HashMap<>();
        for (BaseExchangeEntity exchange : exchangeInfo) {
            exchange.getAllPairs().forEach((k1, v1) -> {
                if(!highest.containsKey(k1)) {
                    highest.put(k1, v1);
                }else {
                    if (highest.get(k1) < v1) {
                        highest.put(k1, v1);
                    }
                }
            });
        }
        highest.forEach((s, aDouble) -> {
            System.out.println(s + " " + aDouble);
        });
    }

    private void compareExchanges(BaseExchangeEntity ex1, BaseExchangeEntity ex2) {

        List<ExchangeSpreadResult> exchangeSpreadResults = new ArrayList<>();

        ex1.getAllPairs().forEach((k1, v1) ->
                ex2.getAllPairs().forEach((k2, v2) -> {
                    if (k1.equals(k2)) {
                        ExchangeSpreadResult result;
                        Double diff = v1 - v2;
                        Double spread = (v2 - v1) / v2 * 100;
                        result = new ExchangeSpreadResult(k1, v1, v2, diff, spread);
                        exchangeSpreadResults.add(result);
                    }
                }));

        Collections.sort(exchangeSpreadResults, Comparator.comparing(ExchangeSpreadResult::getPercentage));
        System.out.println(exchangeSpreadResults.stream()
                .map(Object::toString)
                .collect(Collectors.joining("")));
    }


    public ExchangeSpreadCalc() {
    }

    public void getAllRatesfromAPIs() throws IOException, ClassNotFoundException, InterruptedException {
        List<Runnable> threadlist = new ArrayList<>();
        threadlist.add(new Thread(new InstantiateClasses(CEXEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(CoinapultEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(EXMOEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(QuadrigaEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(BitFinexEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(BitsaneEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(XBTCEEntity.class)));
        Util.runThreads(threadlist);
    }
}
