package lab1;

import java.util.Scanner;

public class BicycleRental {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int startTime, endTime;
        int totalCost = 0;

        System.out.print("Enter starting hour (0-23): ");
        startTime = input.nextInt();

        System.out.print("Enter ending hour (1-24): ");
        endTime = input.nextInt();

        for (int hour = startTime; hour < endTime; hour++) {

            if ((hour >= 0 && hour < 7) || (hour >= 21 && hour < 24)) {
                totalCost += 500;
            } 
            else if ((hour >= 7 && hour < 14) || (hour >= 19 && hour < 21)) {
                totalCost += 1000;
            } 
            else if (hour >= 14 && hour < 19) {
                totalCost += 1500;
            }
        }

        System.out.println("Total rental cost = " + totalCost + " RWF");
    }
}
