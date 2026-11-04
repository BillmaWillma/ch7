// P2Q1. For msft_2000_2010.txt, the highest price comes before the lowest price. What does that
// mean for
//  someone who bought Microsoft stock in 2000?

// P2A1. It is very possible for someone to purchase a share of Microsoft in 2000 and lose money
// because
//  the same share of Microft depreciated in value over time.

// P2Q2. Look up what happened in the world around the lowest month for sbux_2007_2016.txt and
//  sp500_1925_1945.txt. Does the timing make sense?

// P2A2. For sbux_2007_2016.txt, Starbucks was affected by the 2008 financial crisis and this event
// aligns
//  with when Starbucks had its lowest price of $4.09 in November 2008. For sp500_1925_1945.txt, the
//  stock market crash in 1929 (Beginning of the Great Depression) affected S&P 500. This aligns
//  with S&P 500's lowest price of $31.30 in the month of September 1929.

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
        System.out.println(years[indexOfBiggestDrop(prices)]+" "+months[indexOfBiggestDrop(prices)]);
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
    
    public static int  indexOfBiggestDrop(double[] prices){
		int bigDropArrayIndex = 0;
		double percent = 0.0;
		double bigPercent = 0.0;
		for (int i=0; i<prices.length-1; i++){
			if(prices[i+1]<prices[i]){
			percent = 100-(prices[i+1]/prices[i] * 100);
			bigPercent = 100-(prices[bigDropArrayIndex+1]/prices[bigDropArrayIndex] * 100);
			if(percent>0 && percent>bigPercent){
				bigDropArrayIndex = i;
			}
		}
		}
		return bigDropArrayIndex;
	}
	
}
