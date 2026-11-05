import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        String file = "tiny.txt";
        String fileTicker = "TINY";
        Scanner in = new Scanner(new File(file));

        String symbol = in.next();
        int n = in.nextInt();
        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];

        loadData(in, years, months, prices);
        printData(years, months, prices);

        System.out.println(fileTicker + " lowest: $" + prices[indexOfLowest(prices)] + " in "
            + monthName(months[indexOfLowest(prices)]) + " " + years[indexOfLowest(prices)]);

        System.out.println(fileTicker + " highest: $" + prices[indexOfHighest(prices)] + " in "
            + monthName(months[indexOfHighest(prices)]) + " " + years[indexOfHighest(prices)]);

        System.out.println("Biggest Drop on: " + years[indexOfBiggestDrop(prices)] + " "
            + months[indexOfBiggestDrop(prices)] + " at ");

        double result = simulate(years, months, prices);
        System.out.printf("Final value: $%,.2f%n", result);
    }

    public static void loadData(Scanner in, int[] years, int[] months, double[] prices) {
        for (int i = 0; i < years.length; i++) {
            years[i] = in.nextInt();
            months[i] = in.nextInt();
            prices[i] = in.nextDouble();
        }
    }

    public static String monthName(int month) {
        String[] calender = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return calender[month - 1];
    }

    public static void printData(int[] years, int[] months, double[] prices) {
        for (int i = 0; i < years.length; i++) {
            System.out.print(monthName(months[i]) + " " + years[i] + ": $");
            System.out.printf("%.2f", prices[i]);
            System.out.println();
        }
    }

    public static int indexOfLowest(double[] prices) {
        int minArrayIndex = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < prices[minArrayIndex]) {
                minArrayIndex = i;
            }
        }
        return minArrayIndex;
    }

    public static int indexOfHighest(double[] prices) {
        int maxArrayIndex = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > prices[maxArrayIndex]) {
                maxArrayIndex = i;
            }
        }
        return maxArrayIndex;
    }

    public static int indexOfBiggestDrop(double[] prices) {
        int bigDropArrayIndex = 0;
        double percent = 0.0;
        double bigPercent = 0.0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] < prices[i]) {
                percent = 100 - (prices[i + 1] / prices[i] * 100);
                bigPercent =
                    100 - (prices[bigDropArrayIndex + 1] / prices[bigDropArrayIndex] * 100);
                if (percent > 0 && percent > bigPercent) {
                    bigDropArrayIndex = i;
                }
            }
        }
        return bigDropArrayIndex;
    }

    public static final double START_CASH = 10000.0;
    public static final double FEE = 10.0;

    public static double simulate(int[] years, int[] months, double[] prices) {
        double cash = START_CASH;
        int shares = 0;
        double boughtAt = 0.0;

        for (int i = 0; i < prices.length; i++) {
            double[] history = Arrays.copyOf(prices, i + 1);
            String action = decide(history, shares > 0, boughtAt);

            if (action.equals("BUY") && shares == 0) {
                int canBuy = (int) ((cash - FEE) / prices[i]);
                if (canBuy > 0) {
                    shares = canBuy;
                    cash = cash - shares * prices[i] - FEE;
                    boughtAt = prices[i];
                    System.out.printf("%s %d: BUY %d shares at $%.2f%n", monthName(months[i]),
                        years[i], shares, prices[i]);
                }
            } else if (action.equals("SELL") && shares > 0) {
                cash = cash + shares * prices[i] - FEE;
                System.out.printf("%s %d: SELL %d shares at $%.2f%n", monthName(months[i]),
                    years[i], shares, prices[i]);
                shares = 0;
                boughtAt = 0.0;
            }
        }
        return cash + shares * prices[prices.length - 1];
    }

    public static String decide(double[] history, boolean holding, double boughtAt) {
        return dipBuyer(history, holding, boughtAt);
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static String buyAndHold(double[] history, boolean holding, double boughtAt) {
        return "BUY";
    }

    public static String dipBuyer(double[] history, boolean holding, double boughtAt) {
        if (history.length == 0) {
            return "";
        }

        double currentPrice = history[history.length - 1];

        if (holding) {
            if (currentPrice >= boughtAt * 1.10) {
                return "SELL";
            } else {
                return "";
            }
        } else {
            if (history.length >= 3) {
                double lastMonth = history[history.length - 2];
                double twoMonthsAgo = history[history.length - 3];

                if (currentPrice < lastMonth && lastMonth < twoMonthsAgo) {
                    return "BUY";
                }
            }

            return "";
        }
    }

    public static String movingAverage(double[] history, boolean holding, double boughtAt) {
        return "";
    }
}
