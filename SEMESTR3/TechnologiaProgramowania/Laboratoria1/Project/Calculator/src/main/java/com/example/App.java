package com.example;

public class App {
    public static void main(String[] args) {
        ConsoleManager console = new ConsoleManager();
        Calculate calculator = new Calculate();

        console.printWelcome();

        boolean running = true;
        while (running) {
            String operation = console.getOperation();
            if (operation.equalsIgnoreCase("exit")) {
                running = false;
                console.printGoodbye();
            } else {
                double[] numbers = console.getNumbers();
                double result = calculator.calculate(numbers[0], numbers[1], operation);
                console.printResult(result);
            }
        }
    }
}
