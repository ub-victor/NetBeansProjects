package lab1;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class BicycleRentalConsole {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String result = "";

        System.out.println("===== Bicycle Rental System =====");

        try {
            // Input
            System.out.print("Enter Starting hour (0–23): ");
            int startTime = scanner.nextInt();

            System.out.print("Enter Ending hour (1–24): ");
            int endTime = scanner.nextInt();

            // Validation
            if (startTime < 0 || startTime > 23 || endTime < 1 || endTime > 24 || startTime >= endTime) {
                System.out.println("Invalid hours! Please try again.");
                return;
            }

            int totalCost = 0;

            // Cost calculation
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

            int timeUsed = endTime - startTime;

            // Result
            result =
                    "Start Time : " + startTime + ":00\n" +
                    "End Time   : " + endTime + ":00\n" +
                    "Time Used  : " + timeUsed + " hour(s)\n\n" +
                    "Amount to Pay : " + totalCost + " RWF";

            System.out.println("\n===== RESULT =====");
            System.out.println(result);

            // Ask to save
            System.out.print("\nDo you want to save the result? (yes/no): ");
            scanner.nextLine(); // clear buffer
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                saveResult(result);
            }

        } catch (Exception e) {
            System.out.println("Error: Please enter valid numbers.");
        }

        System.out.println("\nThanks for using Bicycle Rental System!");
        scanner.close();
    }

    // Save method
    public static void saveResult(String result) {
        try (FileWriter writer = new FileWriter("bicycle_rental_result.txt")) {
            writer.write(result);
            System.out.println("Result saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }
}