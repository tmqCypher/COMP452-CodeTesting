import java.util.Random;

public class MockRandom extends Random {
    public int number;

    public MockRandom(int number) {
        this.number = number;
    }

    public int nextInt(int constraint) {
        return this.number;
    }
}
