#include <iostream>
#include <vector>
#include <sstream>
#include "figures.h"

int main() {
    std::vector<Figure*> figures;
    std::string input;

    std::cout << "Enter figure details. Type 'show' to see results.\n";

    while (true) {
        std::getline(std::cin, input);
        if (input == "show") {
            std::cout << "Showing the results:\n";
            break;
        }

        std::istringstream inputString(input);
        std::string shapeType;
        inputString >> shapeType;

        try {
            if (shapeType == "o") {
                double radius;
                if (!(inputString >> radius)) throw std::invalid_argument("Invalid input for circle.");
                figures.push_back(new Circle(radius));
            } 
            else if (shapeType == "c") {
                double a, b, angle;
                if (!(inputString >> a >> b)) throw std::invalid_argument("Invalid input for quadrangle.");
                
                if (inputString >> angle) {
                    if (angle == 90) {
                        figures.push_back(new Rectangle(a, b));
                    }
                }
                else {
                    if (b == 90) {
                        figures.push_back(new Square(a));
                    } else {
                        figures.push_back(new Rhomboid(a, b));
                    }
                }
            } 
            else if (shapeType == "p") {
                double a;
                if (!(inputString >> a)) throw std::invalid_argument("Invalid input for pentagon.");
                figures.push_back(new Pentagon(a));
            } 
            else if (shapeType == "s") {
                double a;
                if (!(inputString >> a)) throw std::invalid_argument("Invalid input for hexagon.");
                figures.push_back(new Hexagon(a));
            } 
            else {
                std::cout << "Unknown shape type!\n";
            }
        } 
        catch (const std::exception &e) {
            std::cout << "Error: " << e.what() << "\n";
        }
    }

    for (const Figure* fig : figures) {
        std::cout << fig->Name() << " --- Surface: " << fig->Surface() << " ___ Perimeter: " << fig->Perimeter() << "\n";
        delete fig;
    }

    return 0;
}
