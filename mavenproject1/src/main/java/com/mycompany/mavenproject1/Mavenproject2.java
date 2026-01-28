package com.mycompany.mavenproject1;

import java.util.Scanner;

public class Mavenproject2 {

    Scanner input = new Scanner(System.in);

    public void startCalculator() {

        System.out.print("Enter first number: ");
        double num1 = input.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = input.nextDouble();

        input.nextLine();

        System.out.print("Enter operation (+, -, *, /): ");
        String op = input.nextLine();

        switch (op) {
            case "+" -> System.out.println("Result: " + (num1 + num2));
            case "-" -> System.out.println("Result: " + (num1 - num2));
            case "*" -> System.out.println("Result: " + (num1 * num2));
            case "/" -> {
                if (num2 != 0)
                    System.out.println("Result: " + (num1 / num2));
                else
                    System.out.println("Cannot divide by zero!");
            }
            default -> System.out.println("Invalid operation!");
        }
    }
}
