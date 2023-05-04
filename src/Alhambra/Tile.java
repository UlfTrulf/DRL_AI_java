package Alhambra;

public class Tile {

    private int cost;
    private int type;
    private String borders;

    public Tile(int c, int t, String b) {
        this.cost = c;
        this.type = t;
        this.borders = b;
    }

    public int getCost() {
        return cost;
    }

    public int getType() {
        return type;
    }

    public String getBorders() {
        return borders;
    }


}
