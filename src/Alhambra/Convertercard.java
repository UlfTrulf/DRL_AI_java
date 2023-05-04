package Alhambra;

public class Convertercard extends Card {

    private int altColor;

    public Convertercard (int c, int a) {
        super(c);
        this.altColor = a;
    }

    public int getAltColor() {
        return altColor;
    }
}
