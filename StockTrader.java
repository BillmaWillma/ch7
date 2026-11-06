// Part 2 Q1
// For msft_2000_2010.txt, the highest price comes before the lowest price. What does that
// mean for
//  someone who bought Microsoft stock in 2000?

// Part 2 A1
// It is very possible for someone to purchase a share of Microsoft in 2000 and lose money
// because the same share of Microft depreciated in value over time.

// Part 2 Q2.
// Look up what happened in the world around the lowest month for sbux_2007_2016.txt and
// sp500_1925_1945.txt. Does the timing make sense?

// Part 2 A2.
// For sbux_2007_2016.txt, Starbucks was affected by the 2008 financial crisis and this event
// aligns
//  with when Starbucks had its lowest price of $4.09 in November 2008. For sp500_1925_1945.txt, the
//  stock market crash in 1929 (Beginning of the Great Depression) affected S&P 500. This aligns
//  with S&P 500's lowest price of $31.30 in the month of September 1929.

// Step D Explain your own strategy
//  Before 12 months of data: do nothing. The strategy judges the trend by comparing today’s price
//  to the average of the last 12 months. With less history than that, the average isn’t meaningful,
//  so it waits. When I don’t own shares, buy only if two things are true:
// 1, Today’s price is above the 12-month average. That suggests the stock is in an uptrend rather
// than a slump. 2, Today’s price is higher than last month’s. That suggests the stock is still
// moving up right now, not just sitting above the average while falling back toward it. When I own
// shares, sell if any of these happens: 1, The price falls to 8% below what I paid. It caps how
// much a bad purchase can hurt me. The price rises to 25% above what I paid. This locks in a big
// gain before it can evaporate. The price drops below the 12-month average. The uptrend that
// justified buying has ended. Prices often keep moving in the direction they’ve been going, at
// least for a while. So buying only during an uptrend and leaving when the trend breaks tries to
// catch the middle of a rise. The check (rule 2 for buying) is there because the
// movingAverage strategy buys the moment the price crosses the average, which can be a brief blip
// that reverses right away. The stop-loss exists because the average is slow to react, and a sharp
// one-month crash could cost a lot before the price falls below it.

// Step E Q1.
//

// Step E Q2.
//

// Step E Q3.
//

// Step E Q4.
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Input file name that you wish to analyze and simulate trading on.");
        System.out.println("Input \"end\" when you wish to end simulations.");
        String strat = "myStrategy";
        System.out.println("Current strategy: " + strat);
        for (;;) {
            Scanner s = new Scanner(System.in);
            String file = s.nextLine();
            if (file.equals("end")) {
                break;
            }
            Scanner in = new Scanner(new File(file + ".txt"));
            String fileTicker = "RISE";
            String symbol = in.next();
            int n = in.nextInt();
            int[] years = new int[n];
            int[] months = new int[n];
            double[] prices = new double[n];

            loadData(in, years, months, prices);
            printData(years, months, prices);

            System.out.println();
            System.out.println("//==========DATA==========\\\\");
            System.out.println(fileTicker + " lowest: $" + prices[indexOfLowest(prices)] + " in "
                + monthName(months[indexOfLowest(prices)]) + " " + years[indexOfLowest(prices)]);

            System.out.println(fileTicker + " highest: $" + prices[indexOfHighest(prices)] + " in "
                + monthName(months[indexOfHighest(prices)]) + " " + years[indexOfHighest(prices)]);

            System.out.println("Biggest Drop on: " + years[indexOfBiggestDrop(prices)] + " "
                + months[indexOfBiggestDrop(prices)] + " at ");
            System.out.println();
            System.out.println();

            System.out.println("//==========TRADE==========\\\\");
            double result = simulate(years, months, prices);
            System.out.println();
            System.out.println("Strategy employed: " + strat);
            System.out.printf("Final value: $%,.2f%n", result);
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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
        return myStrategy(history, holding, boughtAt);
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
        if (history.length < 12) {
            return "";
        }
        double sum = 0.0;
        for (int i = history.length - 12; i < history.length; i++) {
            sum += history[i];
        }
        double average = sum / 12.0;
        double currentPrice = history[history.length - 1];
        if (!holding && currentPrice > average) {
            return "BUY";
        }
        if (holding && currentPrice < average) {
            return "SELL";
        }
        return "";
    }

    public static String myStrategy(double[] history, boolean holding, double boughtAt) {
        if (history.length < 12) {
            return "";
        }
        double sum = 0;
        for (int i = history.length - 12; i < history.length; i++) {
            sum += history[i];
        }
        double average = sum / 12;
        double today = history[history.length - 1];
        double lastMonth = history[history.length - 2];
        if (holding) {
            if (today <= boughtAt * 0.92) {
                return "SELL";
            }
            if (today >= boughtAt * 1.25) {
                return "SELL";
            }
            if (today < average) {
                return "SELL";
            }
            return "";
        }

        if (today > average && today > lastMonth) {
            return "BUY";
        }
        return "";
    }
}
