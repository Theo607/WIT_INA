#include "figures.h"

Circle::Circle(double r) : radius(r) {}
double Circle::Surface() const { return M_PI * radius * radius; }
double Circle::Perimeter() const { return 2.0 * M_PI * radius; }
std::string Circle::Name() const { return "Circle"; }

Square::Square(double a) : sideLength(a) {}
double Square::Surface() const { return sideLength * sideLength; }
double Square::Perimeter() const { return 4.0 * sideLength; }
std::string Square::Name() const { return "Square"; }

Rectangle::Rectangle(double a, double b) : sideLengthA(a), sideLengthB(b) {}
double Rectangle::Surface() const { return sideLengthA * sideLengthB; }
double Rectangle::Perimeter() const { return 2.0 * (sideLengthA + sideLengthB); }
std::string Rectangle::Name() const { return "Rectangle"; }

Rhomboid::Rhomboid(double a, double b) : sideLength(a), angleDegrees(b) {}
double Rhomboid::Surface() const { return sideLength * sideLength * sin(angleDegrees * M_PI / 180.0); }
double Rhomboid::Perimeter() const { return 4.0 * sideLength; }
std::string Rhomboid::Name() const { return "Rhomboid"; }

Pentagon::Pentagon(double a) : sideLength(a) {}
double Pentagon::Surface() const { return 0.25 * sqrt(5.0 * (5.0 + 2.0 * sqrt(5.0))) * sideLength * sideLength; }
double Pentagon::Perimeter() const { return 5.0 * sideLength; }
std::string Pentagon::Name() const { return "Pentagon"; }

Hexagon::Hexagon(double a) : sideLength(a) {}
double Hexagon::Surface() const { return 1.5 * sqrt(3.0) * sideLength * sideLength; }
double Hexagon::Perimeter() const { return 6.0 * sideLength; }
std::string Hexagon::Name() const { return "Hexagon"; }
