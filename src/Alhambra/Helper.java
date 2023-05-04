package Alhambra;

import Alhambra.Convertercard;
import Alhambra.Moneycard;
import Alhambra.Tile;
import Alhambra.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class Helper {

    public static String convertto4BitString (int n) {
        String s = Integer.toBinaryString(n);
        if (s.length() > 4) {
            s = s.substring(s.length() - 4);
        } else if (s.length() < 4) {
            while (s.length() < 4) {
                s = "0" + s;
            }
        }
        return s;
    }

    protected static Tile generateTile(String input) {
        String[] data = input.split(", ");
        return new Tile(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]);
    }

    protected static Card generateCard(String input) {
        String[] data = input.split(", ");
        switch (data[0]) {
            case "c" -> {
                return new Convertercard(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            }
            case "m" -> {
                return new Moneycard(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            }
            default -> {
                System.out.println("Error, card could not be converted");
                return null;
            }
        }

    }

    public static List <Tile> generateBag (String path) {
        List <Tile> bag = new ArrayList<>();
        File f = new File(path);
        Scanner reader = null;
        try {
            reader = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Error could not load Tiles");
            System.exit(1);
        }
        while(reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line != null) {
                bag.add(generateTile(line));
            }
        }
        return bag;
    }

    public static List <Card> generateCardpile (String path) {
        List <Card> pile = new ArrayList<>();
        File f = new File(path);
        Scanner reader = null;
        try {
            reader = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Error could not load Cards");
            System.exit(1);
        }
        while(reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line != null) {
                pile.add(generateCard(line));
            }
        }
        return pile;
    }

    public static void generateVanillaTileset(int numBuildingTypes) {
        List<String> buildingTiles = new ArrayList<>();
        int fileNumber = 1;

        for (int buildingType = 1; buildingType <= numBuildingTypes; buildingType++) {
            int numBuildings = 5 + (int) Math.ceil((buildingType + 1) / 2) * 2;
            List<Integer> costs = new ArrayList<>();

            for (int cost = 1 + buildingType; cost <= 8 + buildingType; cost++) {
                costs.add(cost);
            }

            List<String> wallConfigs = generateWallConfigs();

            Random rand = new Random();
            List<Integer> secCost = new ArrayList<>(costs);
            for (int i = 0; i < numBuildings; i++) {
                if (wallConfigs.size() == 0) {
                    wallConfigs = generateWallConfigs();
                }
                Integer cost = 0;
                if (costs.size() != 0) {
                     cost = costs.remove(rand.nextInt(costs.size()));
                } else {
                     cost =  secCost.get(rand.nextInt(secCost.size()));
                }
                String wall = wallConfigs.remove(rand.nextInt(wallConfigs.size()));

                buildingTiles.add(cost + ", " + buildingType + ", " + wall);
            }
        }

        while (true) {
            File file = new File("data/VanillaTiles" + fileNumber + ".txt");
            if (!file.exists()) {
                try (FileWriter writer = new FileWriter(file)) {
                    for (String tile : buildingTiles) {
                        writer.write(tile + System.lineSeparator());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                fileNumber++;
            }
        }
    }

    public static List<String> generateWallConfigs() {
        List<String> validWallConfigs = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            String binary = String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0');
            validWallConfigs.add(binary);
            //**if (!binary.contains("0101") && !binary.contains("1010")) {
            //    validWallConfigs.add(Integer.parseInt(binary, 2));
            //}
        }

        return validWallConfigs;
    }

    public static void generateVanillaCardset() {
        int fileNumber = 1;
        List<String> cardpile = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            for (int n = 1; n < 10; n++) {
                cardpile.add("m, " + i % 4 + ", " + n);
            }
        }

        while (true) {
            File file = new File("data/VanillaCards" + fileNumber + ".txt");
            if (!file.exists()) {
                try (FileWriter writer = new FileWriter(file)) {
                    for (String card : cardpile) {
                        writer.write(card + System.lineSeparator());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                fileNumber++;
            }
        }
    }

    public static boolean canSumToTarget(List<Integer> list, int target) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : list) {
            for (int i = target; i >= num; i--) {
                dp[i] |= dp[i - num];
            }
        }

        return dp[target];
    }

    public static void createCombinations(List<List<Integer>> combinations, List<Integer> data, int start, int end, int index) {
        if (index == data.size()) {
            List<Integer> combination = new ArrayList<>(data);
            combinations.add(combination);
        } else if (start <= end) {
            data.set(index, start);
            createCombinations(combinations, data, start + 1, end, index + 1);
            createCombinations(combinations, data, start + 1, end, index);
        }
    }

    public static boolean isStrictSubset(List<Integer> subset, List<Integer> superset) {
        if (subset.size() >= superset.size()) {
            return false;
        }
        Set<Integer> supersetSet = new HashSet<>(superset);
        for (Integer value : subset) {
            if (!supersetSet.contains(value)) {
                return false;
            }
        }
        return true;
    }


}
