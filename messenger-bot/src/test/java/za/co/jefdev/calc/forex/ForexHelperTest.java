package za.co.jefdev.calc.forex;

import org.junit.Test;
import org.springframework.util.Assert;
import za.co.jefdev.messenger.calc.forex.ForexHelper;

public class ForexHelperTest {
    ForexHelper forexHelper = new ForexHelper();

    @Test
    public void get() throws Exception {
        Assert.notNull(forexHelper.get(),"Forex quote failed");
    }

}