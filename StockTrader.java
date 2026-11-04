//For msft_2000_2010.txt, the highest price comes before the lowest price. What does that mean for someone who bought Microsoft stock in 2000?




//Look up what happened in the world around the lowest month for sbux_2007_2016.txt and sp500_1925_1945.txt. Does the timing make sense?




import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("tiny.txt"));
        String symbol = in.next();
        int n = in.nextInt();
        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];

        loadData(in, years, months, prices);
        printData(years, months, prices);
        System.out.println("Lowest: $"+prices[indexOfLowest(prices)]+" in "+monthName(months[indexOfLowest(prices)])+" "+years[indexOfLowest(prices)]);
        System.out.println("Highest: $"+prices[indexOfHighest(prices)]+" in "+monthName(months[indexOfHighest(prices)])+" "+years[indexOfHighest(prices)]);
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
        double a = 0;
        double b = 999999999;
        int c = 0;
        double d = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            a = prices[i];
            d = prices[i+1];
            if(Math.min(a, d)<Math.min(b, a)){
				b = Math.min(a, d);
				if(a<d){
					c = i;
				} else if (d<a){
					c = i+1;
				}
			}
        }
        return c;
    }

    public static int indexOfHighest(double[] prices) {
        double a = 0;
        double b = 0;
        int c = 0;
        double d = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            a = prices[i];
            d = prices[i+1];
            if(Math.max(a, prices[i+1])>Math.max(b, a)){
				b = Math.max(a, prices[i+1]);
				if (a>d){
					c=i;
				} else if (d>a){
					c=i+1;
				}
			}
        }
        return c;
    }
    
    
}
