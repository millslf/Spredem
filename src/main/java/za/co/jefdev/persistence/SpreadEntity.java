package persistence;

import java.io.Serializable;

public class SpreadEntity implements Serializable{
    Double btcBuyUsdSellZarSpread;
    Double btcBuyEurSellZarSpread;
    Double btcBuyZarSellUsdSpread;
    Double btcBuyEthUSDSellZarSpread;
    Double btcBuyEthEURSellZarSpread;
    Double lastEmailspreadBuyUsdSellZar;
    Double lastEmailspreadBuyEurSellZar;
    Double lastEmailspreadBuyZarSellUsd;
    Double lastEmailspreadBuyEthUSDSellZar;
    Double lastEmailspreadBuyEthEURSellZar;

    public Double getBtcBuyUsdSellZarSpread() {
        return btcBuyUsdSellZarSpread;
    }

    public void setBtcBuyUsdSellZarSpread(Double btcBuyUsdSellZarSpread) {
        this.btcBuyUsdSellZarSpread = btcBuyUsdSellZarSpread;
    }

    public Double getBtcBuyZarSellUsdSpread() {
        return btcBuyZarSellUsdSpread;
    }

    public void setBtcBuyZarSellUsdSpread(Double btcBuyZarSellUsdSpread) {
        this.btcBuyZarSellUsdSpread = btcBuyZarSellUsdSpread;
    }


    public Double getLastEmailspreadBuyUsdSellZar() {
        return lastEmailspreadBuyUsdSellZar;
    }

    public void setLastEmailspreadBuyUsdSellZar(Double lastEmailspreadBuyUsdSellZar) {
        this.lastEmailspreadBuyUsdSellZar = lastEmailspreadBuyUsdSellZar;
    }

    public Double getLastEmailspreadBuyZarSellUsd() {
        return lastEmailspreadBuyZarSellUsd;
    }

    public void setLastEmailspreadBuyZarSellUsd(Double lastEmailspreadBuyZarSellUsd) {
        this.lastEmailspreadBuyZarSellUsd = lastEmailspreadBuyZarSellUsd;
    }


    public Double getBtcBuyEurSellZarSpread() {
        return btcBuyEurSellZarSpread;
    }

    public void setBtcBuyEurSellZarSpread(Double btcBuyEurSellZarSpread) {
        this.btcBuyEurSellZarSpread = btcBuyEurSellZarSpread;
    }

    public Double getLastEmailspreadBuyEurSellZar() {
        return lastEmailspreadBuyEurSellZar;
    }

    public void setLastEmailspreadBuyEurSellZar(Double lastEmailspreadBuyEurSellZar) {
        this.lastEmailspreadBuyEurSellZar = lastEmailspreadBuyEurSellZar;
    }

    public Double getBtcBuyEthUSDSellZarSpread() {
        return btcBuyEthUSDSellZarSpread;
    }

    public void setBtcBuyEthUSDSellZarSpread(Double btcBuyEthUSDSellZarSpread) {
        this.btcBuyEthUSDSellZarSpread = btcBuyEthUSDSellZarSpread;
    }

    public Double getBtcBuyEthEURSellZarSpread() {
        return btcBuyEthEURSellZarSpread;
    }

    public void setBtcBuyEthEURSellZarSpread(Double btcBuyEthEURSellZarSpread) {
        this.btcBuyEthEURSellZarSpread = btcBuyEthEURSellZarSpread;
    }

    public Double getLastEmailspreadBuyEthUSDSellZar() {
        return lastEmailspreadBuyEthUSDSellZar;
    }

    public void setLastEmailspreadBuyEthUSDSellZar(Double lastEmailspreadBuyEthUSDSellZar) {
        this.lastEmailspreadBuyEthUSDSellZar = lastEmailspreadBuyEthUSDSellZar;
    }

    public Double getLastEmailspreadBuyEthEURSellZar() {
        return lastEmailspreadBuyEthEURSellZar;
    }

    public void setLastEmailspreadBuyEthEURSellZar(Double lastEmailspreadBuyEthEURSellZar) {
        this.lastEmailspreadBuyEthEURSellZar = lastEmailspreadBuyEthEURSellZar;
    }
}
