package com.mycompany.mavenproject1;

import java.util.Scanner;

public class Mavenproject1 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // User info (once)
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.print("Enter your age: ");
        int age = input.nextInt();
        input.nextLine();

        System.out.print("Enter your email: ");
        String email = input.nextLine();

        System.out.println("\nWelcome " + name + " (" + age + ")");
        System.out.println("Email: " + email);

        // Objects
        Mavenproject2 calculator = new Mavenproject2();
        Circle circle = new Circle();
        Cylinder cylinder = new Cylinder();

        boolean running = true;

        while (running) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Calculator"); 
            System.out.println("2. Area of Circle");
            System.out.println("3. Volume of Cylinder");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> calculator.startCalculator();

                case 2 -> {
                    System.out.print("Enter radius of circle: ");
                    double r = input.nextDouble();
                    System.out.println("Area of circle: " + circle.area(r));
                }

                case 3 -> {
                    System.out.print("Enter radius of cylinder: ");
                    double cr = input.nextDouble();

                    System.out.print("Enter height of cylinder: ");
                    double h = input.nextDouble();

                    System.out.println("Volume of cylinder: " +
                            cylinder.volume(cr, h));
                }

                case 4 -> {
                    running = false;
                    System.out.println("Goodbye " + name + " 👋");
                }

                default -> System.out.println("Invalid choice! Try again.");
            }
        }

        input.close();
    }
}
