import Alhambra.*;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.List;

public class Main {

        public static void main(String[] args) {
            List <Tile> t = Helper.generateBag("data/VanillaTiles1.txt");
            List <Card> c = Helper.generateCardpile("data/VanillaCards1.txt");
            Collections.shuffle(c);
            Shop s = new Shop(t, c);
            Player p = new Player();
            for (int i = 0; i <= 10; i++) {
                p.addMoney(s.draw(new int[]{0}));
                s.fillUp();
            }
            p.analyzeSale(s.getCosts());
            List<List<Integer>>[] buys = p.calcPossibleBuys(s.getCosts());
            int randomColor = 2;
            int randomCombo = 0;
            List<Integer> randomBuy = buys[randomColor].get(randomCombo);
            int totalSpend = 0;
            while(randomBuy.size() > 0) {
                int value = randomBuy.remove(0);
                totalSpend += value;
                p.removeMoney(new Moneycard(randomColor, value));
            }
            Pair<Boolean, Tile> bought = s.buy(totalSpend, randomColor);
            if (bought.getValue1() != null) {
                if (bought.getValue0()) {
                    System.out.println("keep going");
                } else {
                    System.out.println("Turn end");
                }
                p.stashTile(bought.getValue1());
            } else {
                System.out.println("not enough Money");
            }

    }
}