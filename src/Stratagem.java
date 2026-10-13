import java.util.HashMap;
import java.util.Map;

public class Stratagem {
    private final String stratagemOne;
    private final String stratagemTwo;
    private final String stratagemThree;
    private final String stratagemFour;

    public Stratagem(String one, String two, String three, String four) {
        stratagemOne = one;
        stratagemTwo = two;
        stratagemThree = three;
        stratagemFour = four;
    }

    public String getStratagemOne() {
        return stratagemOne;
    }

    public String getStratagemTwo() {
        return stratagemTwo;
    }

    public String getStratagemThree() {
        return stratagemThree;
    }

    public String getStratagemFour() {
        return stratagemFour;
    }

    private final Map<String, Integer> cooldowns = new HashMap<>(); // turns left, by stratagem name

    // How many turns each stratagem is locked after use (pick your own numbers)
    private static int baseCooldown(String name) {
        switch (name.toUpperCase()) {
            case "PODS":    return 1;
            case "ESTRIKE": return 2;
            case "OBURST":  return 3;
            case "500K":    return 2;
            case "ORAIL":   return 4;
            case "OLASER":  return 3;
            default:        return 0;
        }
    }

    public int getCooldown(String name) {
        return cooldowns.getOrDefault(name.toUpperCase(), 0);
    }

    public void startCooldown(String name) {
        cooldowns.put(name.toUpperCase(), baseCooldown(name));
    }

    // Call once per turn: every stratagem gets 1 turn closer to ready
    public void tickCooldowns() {
        cooldowns.replaceAll((name, turns) -> Math.max(turns - 1, 0));
    }
}
