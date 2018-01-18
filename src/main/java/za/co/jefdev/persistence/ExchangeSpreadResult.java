package za.co.jefdev.persistence;

public class ExchangeSpreadResult {
    String pair;
    Double askExchange, bidExchange, diff, percentage;

    public ExchangeSpreadResult(String pair, Double askExchange, Double bidExchange, Double diff, Double percentage) {
        this.pair = pair;
        this.askExchange = askExchange;
        this.bidExchange = bidExchange;
        this.diff = diff;
        this.percentage = percentage;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Double getAskExchange() {
        return askExchange;
    }

    public void setAskExchange(Double askExchange) {
        this.askExchange = askExchange;
    }

    public Double getBidExchange() {
        return bidExchange;
    }

    public void setBidExchange(Double bidExchange) {
        this.bidExchange = bidExchange;
    }

    public Double getDiff() {
        return diff;
    }

    public void setDiff(Double diff) {
        this.diff = diff;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return String.format("%-10.10s  %-10.10s  %-10.10s  %-10.10s  %-10.10s%n", pair, askExchange, bidExchange, diff, percentage + "%");
    }
}
