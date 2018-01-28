package za.co.jefdev.persistence;

import java.io.Serializable;

public class BaseCurrencyEntity implements Serializable{
    public static Double FNB_FOREX_FEE = new Double("1.035");
    Double zar;
    Double fnbZar;

    public Double getZar() {
        return zar;
    }

    public void setZar(Double zar) {
        this.zar = zar;
        setFnbZar(zar*FNB_FOREX_FEE);
    }

    public Double getFnbZar() {
        return fnbZar;
    }

    public void setFnbZar(Double fnbZar) {
        this.fnbZar = fnbZar;
    }
}
