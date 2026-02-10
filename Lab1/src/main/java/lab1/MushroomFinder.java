package lab1;

import java.util.Scanner;

public class MushroomFinder {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String gills, forest, ring, convex;

        System.out.print("Does your mushroom have gills? (yes/no): ");
        gills = input.nextLine();

        System.out.print("Does your mushroom grow in a forest? (yes/no): ");
        forest = input.nextLine();

        System.out.print("Does your mushroom have a ring? (yes/no): ");
        ring = input.nextLine();

        System.out.print("Does your mushroom have a convex cup? (yes/no): ");
        convex = input.nextLine();

        if (gills.equalsIgnoreCase("no")) {
            System.out.println("Your mushroom is: Cepe de bordeau");
        }
        else if (forest.equalsIgnoreCase("no") && ring.equalsIgnoreCase("yes")) {
            System.out.println("Your mushroom is: Agaric jaunissant");
        }
        else if (forest.equalsIgnoreCase("no") && ring.equalsIgnoreCase("yes") && convex.equalsIgnoreCase("no")) {
            System.out.println("Your mushroom is: Coprin chevelu");
        }
        else if (convex.equalsIgnoreCase("yes") && ring.equalsIgnoreCase("yes")) {
            System.out.println("Your mushroom is: Amanite tue-mouche");
        }
        else if (convex.equalsIgnoreCase("yes") && ring.equalsIgnoreCase("no")) {
            System.out.println("Your mushroom is: Pied bleu");
        }
        else {
            System.out.println("Your mushroom is: Girolle");
        }
    }
}
