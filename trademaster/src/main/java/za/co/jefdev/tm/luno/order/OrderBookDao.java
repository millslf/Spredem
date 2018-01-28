package za.co.jefdev.tm.luno.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import za.co.jefdev.tm.util.DoubleDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderBookDao {

    private Timestamp timestamp;
    private List<Bid> bids = new ArrayList<>();
    private List<Bid> asks = new ArrayList<>();

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class Bid {
        @JsonDeserialize(using = DoubleDeserializer.class)
        private double volume, price;

        @Override
        public String toString() {
            return String.format("Volume: %.2f Price: %.2f",volume,price);
        }
    }

    @JsonIgnore
    public List<double[]> getBidsAsDouble() {
        return bids.stream().map(f -> new double[]{f.getPrice(),f.getVolume()}).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<double[]> getAsksAsDouble() {
        return asks.stream().map(f -> new double[]{f.getPrice(),f.getVolume()}).collect(Collectors.toList());
    }

}
