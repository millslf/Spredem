package za.co.jefdev.calc.cex;

import org.junit.Test;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

public class CexHelperTest {
    CexHelper cexHelper = new CexHelper();

    @Test
    public void getBtcUsd() throws Exception {
        Assert.notNull(cexHelper.getBtcUsd(),"Failed usd quote");
    }

    @Test
    public void getBtcEur() throws Exception {
        Assert.notNull(cexHelper.getBtcEur(),"Failed euro quote");
    }

}