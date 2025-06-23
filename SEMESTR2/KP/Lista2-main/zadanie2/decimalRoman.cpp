#include "decimalRoman.h"

const std::pair<std::string, int> numerals[] = {
    {"M", 1000}, {"CM", 900}, {"D", 500}, {"CD", 400},
    {"C", 100}, {"XC", 90}, {"L", 50}, {"XL", 40},
    {"X", 10}, {"IX", 9}, {"V", 5}, {"IV", 4}, {"I", 1}
};

int DecimalRoman::romanToDecimal(const std::string& roman) {
    if (!isValidRomanNumber(roman)) {
        throw DecimalRomanException("Invalid Roman number: " + roman);
    }
    int decimal = 0, lastValue = 0;
    for (int i = roman.length() - 1; i >= 0; --i) {
        int value = romanCharToDecimal(roman[i]);
        if (value < lastValue)
            decimal -= value;
        else
            decimal += value;
        lastValue = value;
    }
    return decimal;
}

std::string DecimalRoman::decimalToRoman(int decimal) {
    if (decimal < 1 || decimal > 3999) {
        throw DecimalRomanException("Number out of range");
    }
    std::string roman;
    for (const auto& pair : numerals) {
        while (decimal >= pair.second) {
            roman += pair.first;
            decimal -= pair.second;
        }
    }
    return roman;
}

bool DecimalRoman::isValidRomanNumber(const std::string& input) {
    if (input.empty()) return false;
    
    for (char ch : input) {
        if (std::string("IVXLCDM").find(ch) == std::string::npos) {
            return false;
        }
    }
    
    if (input.find("IIII") != std::string::npos || input.find("VV") != std::string::npos ||
        input.find("XXXX") != std::string::npos || input.find("LL") != std::string::npos ||
        input.find("CCCC") != std::string::npos || input.find("DD") != std::string::npos ||
        input.find("MMMM") != std::string::npos) {
        return false;
    }
    
    if (input.find("IL") != std::string::npos || input.find("IC") != std::string::npos ||
        input.find("ID") != std::string::npos || input.find("IM") != std::string::npos ||
        input.find("VX") != std::string::npos || input.find("VL") != std::string::npos ||
        input.find("VC") != std::string::npos || input.find("VD") != std::string::npos ||
        input.find("VM") != std::string::npos || input.find("XD") != std::string::npos ||
        input.find("XM") != std::string::npos || input.find("LC") != std::string::npos ||
        input.find("LD") != std::string::npos || input.find("LM") != std::string::npos ||
        input.find("DM") != std::string::npos || input.find("IXI") != std::string::npos ||
        input.find("IVI") != std::string::npos) {
        return false;
    }
    
    std::regex romanRegex("^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");
    return std::regex_match(input, romanRegex);
}

int DecimalRoman::romanCharToDecimal(char ch) {
    switch (ch) {
        case 'I': return 1;
        case 'V': return 5;
        case 'X': return 10;
        case 'L': return 50;
        case 'C': return 100;
        case 'D': return 500;
        case 'M': return 1000;
        default: throw DecimalRomanException("Invalid character in Roman numeral");
    }
}
