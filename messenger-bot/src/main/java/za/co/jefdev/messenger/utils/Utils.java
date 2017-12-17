package za.co.jefdev.messenger.utils;

public class Utils {

    public static <T> T exec(Call1<T> call) {
        try {
            return call.call();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
