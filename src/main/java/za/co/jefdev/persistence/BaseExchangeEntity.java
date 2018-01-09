package za.co.jefdev.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseExchangeEntity implements Serializable{

    public String allPairsDelimited = "";

    public Double getPair(String pair){
        for(String pr:allPairsDelimited.split(",")){
            if(pr.contains(pair)){
                return new Double(pr.replace(pair, ""));
            }
        }
        return null;
    }

    public Map<String, Double> getAllPairs(){
        Map<String, Double> map = new HashMap<>();
        for(String pr:allPairsDelimited.split(",")){
            try {
                map.put(pr.substring(0,6), new Double(pr.substring(6, pr.length())));
            }catch (NumberFormatException ex){
                map.put(pr.substring(0,7), new Double(pr.substring(7, pr.length())));
            }
        }
        return map;
    }
}
