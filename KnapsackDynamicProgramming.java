import java.util.Arrays;
import java.util.List;

public class KnapsackDynamicProgramming {
    public static void main(String[] args) {
        List<Item> items = Arrays.asList(
            new Item("Lenovo X1 Carbon (5th Gen)", 40, 112),
            new Item("10 pairs thongs", 39, 80),
            new Item("5 Underarmour Strappy", 38, 305),
            new Item("1 pair Uniqlo leggings", 37, 185),
            new Item("2 Lululemon Cool Racerback", 36, 174),
            new Item("Chargers and cables in Mini Bomber Travel Kit", 35, 665),
            new Item("The Roost Stand", 34, 170),
            new Item("ThinkPad Compact Bluetooth Keyboard with trackpoint", 33, 460),
            new Item("Seagate Backup PlusSlim", 32, 159),
            new Item("1 pair black denim shorts", 31, 197),
            new Item("2 pairs Nike Pro shorts", 30, 112),
            new Item("2 pairs Lululemon shorts", 29, 184),
            new Item("Isabella T-Strap Croc sandals", 28, 200),
            new Item("2 Underarmour HeatGear CoolSwitch tank tops", 27, 138),
            new Item("5 pairs black socks", 26, 95),
            new Item("2 pairs Injinji Women's Run Lightweight No-Show Toe Socks", 25, 54),
            new Item("1 fancy tank top", 24, 71),
            new Item("1 light and stretchylong-sleeve shirt (Gap Fit)", 23, 147),
            new Item("Uniqlo Ultralight Down insulating jacket", 22, 235),
            new Item("Patagonia Torrentshell", 21, 301),
            new Item("Lightweight Merino Wool Buff", 20, 50),
            new Item("1 LBD (H&M)", 19, 174),
            new Item("Field Notes Pitch Black Memo Book Dot-Graph", 18, 68),
            new Item("Innergie PocketCell USB-C 6000mAh power bank", 17, 14),
            new Item("Important papers", 16, 228),
            new Item("Deuter First Aid Kit Active", 15, 144),
            new Item("Stanley Classic Vacuum Camp Mug 16oz", 14, 454),
            new Item("JBL Reflect Mini Bluetooth Sport Headphones", 13, 14),
            new Item("Anker SoundCore nano Bluetooth Speaker", 12, 89),
            new Item("Oakley Latch Sunglasses", 11, 30),
            new Item("Ray Ban Wayfarer Classic", 10, 45),
            new Item("Zip bag of toiletries", 9, 236),
            new Item("Petzl E+LITE Emergency Headlamp", 8, 27),
            new Item("Laptop Bag", 7, 20),
            new Item("Peak Design Cuff Camera Wrist Strap", 6, 26),
            new Item("Travelon Micro Scale", 5, 125),
            new Item("BlitzWolf Bluetooth Tripod/Monopod", 4, 150),
            new Item("Humangear GoBites Duo", 3, 22),
            new Item("Touchlight", 2, 10),
            new Item("Vapur Bottle 1L", 1, 41)
        );

        int weightLimit = 4000; // Adjust weight limit as needed

        long startTime = System.nanoTime(); // Start time in nanoseconds

        int[] dynamicResult = dynamicProgrammingKnapsack(items, weightLimit);

        long endTime = System.nanoTime(); // End time in nanoseconds
        long executionTime = endTime - startTime; // Execution time in nanoseconds

        System.out.println("\nDynamic Programming Solution:");
        printKnapsackItems(dynamicResult, items, weightLimit);

        System.out.println("\nExecution time: " + executionTime + " nanoseconds");
    }

    public static int[] dynamicProgrammingKnapsack(List<Item> items, int weightLimit) {
        int n = items.size();
        int[][] dp = new int[n + 1][weightLimit + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= weightLimit; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (items.get(i - 1).getWeight() <= w) {
                    dp[i][w] = Math.max(items.get(i - 1).getWorth() + dp[i - 1][w - items.get(i - 1).getWeight()], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int[] knapsack = new int[n];
        int result = dp[n][weightLimit];
        int remainingWeight = weightLimit;

        for (int i = n; i > 0 && result > 0; i--) {
            if (result != dp[i - 1][remainingWeight]) {
                knapsack[i - 1] = 1;
                result -= items.get(i - 1).getWorth();
                remainingWeight -= items.get(i - 1).getWeight();
            }
        }

        return knapsack;
    }

    public static void printKnapsackItems(int[] knapsack, List<Item> items, int weightLimit) {
        int totalWeight = 0;
        int totalWorth = 0;

        System.out.println("Item\t\t\t\t\t\t\tWorth\tWeight");
        System.out.println("------------------------------------------------------------------");

        for (int i = 0; i < knapsack.length; i++) {
            if (knapsack[i] == 1) {
                Item item = items.get(i);
                System.out.printf("%-60s%-8d%d%n", item.getName(), item.getWorth(), item.getWeight());
                totalWorth += item.getWorth();
                totalWeight += item.getWeight();
            }
        }

        System.out.println("------------------------------------------------------------------");
        System.out.println("Total weight: " + totalWeight + " grams");
        System.out.println("Total worth: " + totalWorth);
        System.out.println("Weight limit: " + weightLimit);
    }



    static class Item {
        private String name;
        private int worth;
        private int weight;

        public Item(String name, int worth, int weight) {
            this.name = name;
            this.worth = worth;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public int getWorth() {
            return worth;
        }

        public int getWeight() {
            return weight;
        }
    }
}
