package Alhambra;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private Alhambra alhambra;
    private List<Tile> stash;
    private List<Card> money;
    private Boolean[] canBuy;
    private Boolean[] canBuyClean;

    public Player(){
        this.canBuy = new Boolean[4];
        this.canBuyClean = new Boolean[4];
        this.money = new ArrayList<>();
        this.stash = new ArrayList<>();
        this.alhambra = new Alhambra();
    }

    public void stashTile(Tile t) {
        this.stash.add(t);
        System.out.println("Tile added: " + t.getCost() + ", " + t.getType() + ", " + t.getBorders());
    }
    public void addMoney(List<Card> draw) {
        this.money.addAll(draw);
    }

    public void removeMoney(Card card) {
        this.money.remove(card);
    }
    public void analyzeSale(int[] cost) {
        Pair<List<Integer>[], int[]> sorted = sortCards();
        List<Integer>[] values = sorted.getValue0();
        int[] totals = sorted.getValue1();
        System.out.println("STATE:");
        for(int i = 0; i < 4; i++) {
             this.canBuy[i] = totals[i] >= cost[i];
             this.canBuyClean[i] = Helper.canSumToTarget(values[i], cost[i]);
             System.out.println("Cost: " + cost[i] + ", Total: " + totals[i] + ", Buy: " + this.canBuy[i] +
                     ", Clean: " + this.canBuyClean[i]);
        }
    }

    public List<List<Integer>>[] calcPossibleBuys(int[] cost) {
        List<List<Integer>>[] possibleBuys = new ArrayList[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        };
        Pair<List<Integer>[], int[]> sorted = sortCards();
        List<Integer>[] values = sorted.getValue0();
        //int[] totals = sorted.getValue1();
        System.out.println("COMBINATIONS:");
        for (int i = 0; i < 4; i++) {
            if (this.canBuy[i]) {
                System.out.println("Color: " + i);
                for (int r = 1; r <= values[i].size(); r++) {
                    List<List<Integer>> combinations = new ArrayList<>();
                    Helper.createCombinations(combinations, new ArrayList<>(Collections.nCopies(r, 0)), 0, values[i].size() - 1, 0);
                    for (List<Integer> combination : combinations) {
                        int sum = 0;
                        for (int index : combination) {
                            sum += values[i].get(index);
                        }
                        if (sum >= cost[i]) {
                            boolean isStrictSubsetOfExisting = false;
                            for (List<Integer> existingCombination : possibleBuys[i]) {
                                if (Helper.isStrictSubset(existingCombination, combination)) {
                                    isStrictSubsetOfExisting = true;
                                    break;
                                }
                            }
                            if (!isStrictSubsetOfExisting) {
                                List<Integer> buy = new ArrayList<>();
                                for (int index : combination) {
                                    buy.add(values[i].get(index));
                                    System.out.print(values[i].get(index) + "|");
                                }
                                if (sum == cost[i]) {
                                    possibleBuys[i].add(0, buy);
                                } else {
                                    possibleBuys[i].add(buy);
                                }
                                System.out.println();
                            }
                        }

                    }
                }

            }
        }
        return possibleBuys;
    }

    private Pair<List<Integer>[], int[]> sortCards(){
        int[] totals = new int[4];
        Boolean[][] convMatrix = new Boolean[4][4];
        List<Integer>[] values = new ArrayList[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        };
        System.out.println("CARDS:");
        for(Card c : this.money) {
            if (c instanceof Moneycard) {
                totals[c.getColor()] += ((Moneycard) c).getValue();
                values[c.getColor()].add(((Moneycard) c).getValue());
                System.out.println("Color: " + c.getColor() + ", Value: " + ((Moneycard) c).getValue());
            } else if (c instanceof Convertercard) {
                convMatrix[c.getColor()][((Convertercard) c).getAltColor()] = true;
                convMatrix[((Convertercard) c).getAltColor()][c.getColor()] = true;
            }
        }
        return new Pair<>(values, totals);
    }
}
