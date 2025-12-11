import java.util.concurrent.ThreadLocalRandom;

public class Dados {
    public static int rolar() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }
}
