package za.co.jefdev.utils;

import java.util.function.Function;

public class Utils {

    public static <T> T exec(Call1<T> call) {
        try {
            return call.call();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
