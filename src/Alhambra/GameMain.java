package Alhambra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMain {

    private ArrayList<Player> players;
    private Shop shop;

    public GameMain(int playercount, String tiles, String cards){
        List<Tile> t = Helper.generateBag(tiles);
        Collections.shuffle(t);
        List <Card> c = Helper.generateCardpile(cards);
        Collections.shuffle(c);
        this.players = new ArrayList<Player>();
        for (int i = 0; i < playercount; i++) {
            Player p = new Player();
            while (p.getTotalValue() < 20) {
                if (c.get(0) instanceof Moneycard) {
                    p.addMoney(c.remove(0));
                } else {
                    c.add(c.remove(0));
                }
            }
            this.players.add(p);
        }
        sortPlayerOrder();
        this.shop = new Shop(t, c);
    }

    private void sortPlayerOrder(){
        for (int i = 0; i < this.players.size() - 1; i++) {
            for (int j = this.players.size() - 1; j > i; j--) {
                int valueComp = this.players.get(j - 1).getTotalValue() - this.players.get(j).getTotalValue();
                boolean amountComp = this.players.get(j - 1).getAmountOfCards() > this.players.get(j).getAmountOfCards();
                if (valueComp > 0 || (valueComp == 0 && amountComp)) {
                    Player tmp = this.players.get(j - 1);
                    this.players.set(j - 1, this.players.get(j));
                    this.players.set(j, tmp);
                }
            }
        }
    }
}
