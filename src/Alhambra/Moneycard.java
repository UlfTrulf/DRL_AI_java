package Alhambra;

public class Moneycard extends Card{
    private int value;

    public Moneycard (int c, int v) {
        super(c);
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}
