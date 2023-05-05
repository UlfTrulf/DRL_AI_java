package Alhambra;


import org.javatuples.Pair;

import javax.print.attribute.IntegerSyntax;
import java.sql.Array;
import java.util.*;

public class Shop {
    private List<Tile> sale;
    private List<Tile> bag;
    private List<Card> cards;
    private List<Card> cardPile;

    public Shop(List<Tile> bag, List<Card> cards) {
        this.bag = bag;
        this.cardPile = cards;
        this.sale = new ArrayList<>();
        this.cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.sale.add(null);
        }
        this.fillUp();
    }

    public Boolean fillUp(){
        while (this.cards.size() < 4) {
            Card c = this.cardPile.remove(0);
            if (c instanceof Ratingcard) {
              return false;
            } else {
                this.cards.add(c);
            }
        }
        for (int i = 0; i < 4; i++) {
            if (this.sale.get(i) == null) {
                this.sale.set(i, this.bag.remove(0));
            }
        }
        return true;
    }

    public Pair <Boolean, Tile> buy(int money, int position) {
        Tile t = this.sale.get(position);
        if (t.getCost() > money) {
            return new Pair<>(null, null);
        } else if (t.getCost() == money) {
            this.sale.set(position, null);
            return new Pair<>(true, t);
        } else {
            this.sale.set(position, null);
            return new Pair<>(false, t);
        }
    }

    public List<Card> draw(int[] positions) {
        List<Card> c = new ArrayList<>();
        switch (positions.length) {
            case 1 -> {
                int p = positions[0];
                c.add(this.cards.remove(p));
                return c;
            }
            case 2 -> {
                Card card1 = this.cards.get(positions[0]);
                Card card2 = this.cards.get(positions[1]);
                boolean mCheck = card1 instanceof Moneycard && card2 instanceof Moneycard;
                if (mCheck && ((Moneycard) card1).getValue() + ((Moneycard) card2).getValue() < 6) {
                    c.add(this.cards.remove(positions[0]));
                    c.add(this.cards.remove(positions[1]));
                }
                return c;
            }
            default -> {
                return c;
            }
        }
    }

    public int[] getCosts() {
        int[] cost = new int[4];
        for (Tile t : this.sale) {
            cost[this.sale.indexOf(t)] = (t.getCost());
        }
        return cost;
    }

    public List<Card> getCards(){
        return this.cards;
    }
}

