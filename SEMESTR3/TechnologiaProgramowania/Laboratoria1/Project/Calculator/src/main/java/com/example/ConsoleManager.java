package com.example;

import java.util.Scanner;

public class ConsoleManager {
    private final Scanner scanner = new Scanner(System.in);

    public void printWelcome() {
        System.out.println("Welcome to Simple Terminal Calculator!");
        System.out.println("Available operations: +, -, *, /");
        System.out.println("Type 'exit' to quit.");
    }

    public String getOperation() {
        System.out.print("Enter operation: ");
        return scanner.nextLine();
    }

    public double[] getNumbers() {
        double[] nums = new double[2];
        System.out.print("Enter first number: ");
        nums[0] = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter second number: ");
        nums[1] = Double.parseDouble(scanner.nextLine());
        return nums;
    }

    public void printResult(double result) {
        System.out.println("Result: " + result);
    }

    public void printGoodbye() {
        System.out.println("Goodbye!");
    }
}
