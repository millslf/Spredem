package za.co.jefdev;

import za.co.jefdev.persistence.*;
import za.co.jefdev.utils.FileReaderWriter;
import za.co.jefdev.utils.InstantiateClasses;
import za.co.jefdev.utils.Util;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ExchangeSpreadCalc {

    public static void main(String[] args) throws Exception {
        ExchangeSpreadCalc newCalc = new ExchangeSpreadCalc();
        newCalc.getAllRatesfromAPIs();
        CEXEntity cexEntity = (CEXEntity) FileReaderWriter.loadValues(CEXEntity.class.getName().toString());
        EXMOEntity exmoEntity = (EXMOEntity) FileReaderWriter.loadValues(EXMOEntity.class.getName().toString());
        CoinapultEntity coinapultEntity = (CoinapultEntity) FileReaderWriter.loadValues(CoinapultEntity.class.getName().toString());

        System.out.println("\nCEX EXMO");
        newCalc.compareExchanges(cexEntity, exmoEntity);
        System.out.println("\nCOINAPULT CEX");
        newCalc.compareExchanges(coinapultEntity, cexEntity);
        System.out.println("\nCOINAPULT EXMO");
        newCalc.compareExchanges(coinapultEntity, exmoEntity);
    }

    private void compareExchanges(BaseExchangeEntity ex1, BaseExchangeEntity ex2) {
        List<ExchangeSpreadResult> exchangeSpreadResults = new ArrayList<>();
        for (Map.Entry<String, Double> exchange1 : ex1.getAllPairs().entrySet()) {
            for (Map.Entry<String, Double> exchange2 : ex2.getAllPairs().entrySet()) {
                if (exchange1.getKey().equals(exchange2.getKey())) {
                    ExchangeSpreadResult result;
                    Double diff = exchange1.getValue() - exchange2.getValue();
                    Double spread = (exchange2.getValue() - exchange1.getValue()) / exchange2.getValue() * 100;
                    result = new ExchangeSpreadResult(exchange1.getKey(), exchange1.getValue(), exchange2.getValue(), diff, spread);
                    exchangeSpreadResults.add(result);
                }
            }
        }
        Collections.sort(exchangeSpreadResults, Comparator.comparing(ExchangeSpreadResult::getPercentage));
        System.out.println(exchangeSpreadResults.stream()
                .map(Object::toString)
                .collect(Collectors.joining("")));
    }

    public ExchangeSpreadCalc() {
    }

    public void getAllRatesfromAPIs() throws IOException, ClassNotFoundException {
        //Free version only allows two currencies at a time and only 60 calls per hour
        List<Thread> threadlist = new ArrayList<>();
        threadlist.add(new Thread(new InstantiateClasses(CEXEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(CoinapultEntity.class)));
        threadlist.add(new Thread(new InstantiateClasses(EXMOEntity.class)));
        Util.runThreads(threadlist);
    }
}
