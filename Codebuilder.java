import java.util.Random;

public class Codebuilder {
    private static final char[] KEYS = {'W', 'A', 'S', 'D'};
    private static final Random RNG = new Random();

    // Builds a random code of the given length, e.g. "WDSA"
    public static String randomCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(KEYS[RNG.nextInt(KEYS.length)]);
        }
        return code.toString();
    }
}
