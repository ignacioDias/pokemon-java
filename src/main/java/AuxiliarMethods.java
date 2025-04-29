import java.util.Random;
public class AuxiliarMethods {
    //Where probability is between 0 and 100
    public static boolean calculateProbability(int probability) {
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        return randomNum < probability;
    }
}
