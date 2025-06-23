#ifndef DECIMAL_ROMAN_H
#define DECIMAL_ROMAN_H

#include <iostream>
#include <string>
#include <stdexcept>
#include <regex>

class DecimalRomanException : public std::exception {
private:
    std::string message;
public:
    explicit DecimalRomanException(const std::string& msg) : message(msg) {}
    const char* what() const noexcept override { return message.c_str(); }
};

class DecimalRoman {
public:
    static int romanToDecimal(const std::string& roman);
    static std::string decimalToRoman(int decimal);
    static bool isValidRomanNumber(const std::string& input);

private:
    static int romanCharToDecimal(char ch);
    static int getDecimalValueFromRoman(const std::string& roman);
};

#endif