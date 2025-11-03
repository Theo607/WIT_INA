#ifndef FIGURES_H
#define FIGURES_H

#include <iostream>
#include <cmath>
#include <vector>

class Figure {
public:
    virtual double Surface() const = 0;
    virtual double Perimeter() const = 0;
    virtual std::string Name() const = 0;
    virtual ~Figure() = default;
};

class Circle : public Figure {
private:
    double radius;
public:
    Circle(double r);
    double Surface() const override;
    double Perimeter() const override;
    std::string Name() const override;
};

class Quadrangle : public Figure {
public:
    std::string Name() const override { return "Quadrangle"; }
};

class Square : public Quadrangle {
private:
    double sideLength;
public:
    Square(double a);
    double Surface() const override;
    double Perimeter() const override;
    std::string Name() const override;
};

class Rectangle : public Quadrangle {
private:
    double sideLengthA, sideLengthB;
public:
    Rectangle(double a, double b);
    double Surface() const override;
    double Perimeter() const override;
    std::string Name() const override;
};

class Rhomboid : public Quadrangle {
private:
    double sideLength, angleDegrees;
public:
    Rhomboid(double a, double b);
    double Surface() const override;
    double Perimeter() const override;
    std::string Name() const override;
};

class Pentagon : public Figure {
private:
    double sideLength;
public:
    Pentagon(double a);
    double Surface() const override;
    double Perimeter() const override;
    std::string Name() const override;
};

class Hexagon : public Figure {
private:
    double sideLength;
public:
    Hexagon(double a);
    double Surface() const override;
    double Perimeter() const override;
    std::string Name() const override;
};

#endif // FIGURES_H
