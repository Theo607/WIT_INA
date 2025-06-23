import java.util.Scanner;

public class FiguryZadanie {
    
    interface FigureInterface {
        double Surface();
        double Perimeter();
        String Name();
    }

    abstract static class Figure implements FigureInterface {
    }

    static class Circle extends Figure {
        double radius;
        Circle(double r) {
            radius = r;
        }
        @Override
        public double Surface() {
            return Math.PI * radius * radius;
        }
        @Override
        public double Perimeter() {
            return 2.0 * Math.PI * radius;
        }
        @Override
        public  String Name() {
            return "Circle";
        }
    }

    abstract static class Quadrangle extends Figure {
        @Override
        public  String Name() {
            return "Quadrangle";
        } 
    }

    static class Square extends Quadrangle {
        double sideLenght;
        Square(double a) {
            sideLenght = a;
        }
        @Override
        public double Surface() {
            return sideLenght * sideLenght;
        }
        @Override
        public double Perimeter() {
            return 4.0 * sideLenght;
        }
        @Override
        public String Name() {
            return "Square";
        }
    }

    static class Rectangle extends Quadrangle {
        double sideLenghtA;
        double sideLenghtB;

        Rectangle(double a, double b) {
            sideLenghtA = a;
            sideLenghtB = b;
        }

        @Override
        public double Surface() {
            return sideLenghtA * sideLenghtB;
        }

        @Override
        public double Perimeter() {
            return 2.0 * sideLenghtA + 2.0 * sideLenghtB;
        }
        @Override
        public  String Name() {
            return "Rectangle";
        }
    }

    static class Rhomboid extends Quadrangle {
        double sideLenght;
        double angleDegrees;
        Rhomboid(double a, double b) {
            sideLenght = a;
            angleDegrees = b;
        }

        @Override
        public double Surface() {
            return sideLenght * sideLenght * Math.sin(Math.toRadians(angleDegrees));
        }
        @Override
        public double Perimeter() {
            return 4.0 * sideLenght;
        }
        @Override
        public String Name() {
            return "Rhomboid";
        }
    }

    static class Pentagon extends Figure {
        double sideLenght;

        Pentagon(double a) {
            sideLenght = a;
        }

        @Override
        public double Surface() {
            return 0.25 * Math.sqrt(5.0 * (5.0 + 2.0 * Math.sqrt(5.0)))* sideLenght * sideLenght;
        }
        @Override
        public double Perimeter() {
            return 5.0 * sideLenght;
        }
        @Override
        public String Name() {
            return "Pentagon";
        }
    }

    static class Hexagon extends Figure {
        double sideLenght;

        Hexagon(double a) {
            sideLenght = a;
        }

        @Override
        public double Surface() {
            return 1.5 * Math.sqrt(3.0) * sideLenght * sideLenght;
        }

        @Override
        public double Perimeter() {
            return 6.0 * sideLenght;
        }

        @Override
        public String Name() {
            return "Hexagon";
        }
    }
    public static void main(String[] args) {
        Figure[] figures = new Figure[100];
        int i = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter figure details. Type 'show' to see results.");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("show")) {
                System.out.println("Showing the answers.");
                break;
            }

            String[] parts = input.split(" ");
            String shapeType = parts[0];

            try {
                switch (shapeType) {
                    case "o":
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Invalid input for circle. Expected format: o <radius>");
                        }
                        double radius = Double.parseDouble(parts[1]);
                        figures[i] = new Circle(radius);
                        i = i +  1;
                        break;

                    case "c":
                        if (parts.length == 3) {
                            double side = Double.parseDouble(parts[1]);
                            double angle = Double.parseDouble(parts[2]);
                            if (angle == 90) {
                                figures[i] = new Square(side);
                                i = i + 1;
                            } else {
                                figures[i] = new Rhomboid(side, angle);
                                i = i + 1;
                            }
                        } else if (parts.length == 4 && parts[3].equals("90")) {
                            double a = Double.parseDouble(parts[1]);
                            double b = Double.parseDouble(parts[2]);
                            figures[i] = new Rectangle(a, b);
                            i = i + 1;
                        } else {
                            throw new IllegalArgumentException("Invalid input for quadrangle. Expected format: c <side> <angle> for square or rhombus, or c <a> <b> 90 for rectangle.");
                        }
                        break;

                    case "p":
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Invalid input for pentagon. Expected format: p <side>");
                        }
                        double a = Double.parseDouble(parts[1]);
                        figures[i] = new Pentagon(a);
                        i = i + 1;
                        break;

                    case "s":
                        if (parts.length != 2) {
                            throw new IllegalArgumentException("Invalid input for hexagon. Expected format: s <side>");
                        }
                        double ac = Double.parseDouble(parts[1]);
                        figures[i] = new Hexagon(ac);
                        i = i + 1;
                        break;

                    default:
                        System.out.println("Unknown shape type!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter valid numbers.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();

        int j = 0;
        while (j < i) {
            System.out.println(figures[j].Name() + " --- Surface:  " + figures[j].Surface() + " ___ Perimeter:  " + figures[j].Perimeter());
            j = j + 1;
        }
    }
}

    