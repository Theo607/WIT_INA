#include "decimalRoman.h"
#include <iostream>

int main(int argc, char *argv[]) {
  if (argc < 2) {
    std::cout
        << "Please provide a Roman numeral or Decimal number as an argument."
        << std::endl;
    return 1;
  }

  try {
    for (int i = 1; i < argc; ++i) {
      std::string input = argv[i];

      try {
        int decimal = std::stoi(input);
        std::string roman = DecimalRoman::decimalToRoman(decimal);
        std::cout << "Decimal: " << decimal << " -> Roman numeral: " << roman
                  << std::endl;
      } catch (const std::invalid_argument &) {
        try {
          int decimal = DecimalRoman::romanToDecimal(input);
          std::cout << "Roman numeral: " << input << " -> Decimal: " << decimal
                    << std::endl;
        } catch (const DecimalRomanException &e) {
          std::cout << e.what() << std::endl;
        }
      } catch (const std::out_of_range &) {
        std::cout << "Out of range decimal number: " << input << std::endl;
      }
    }

  } catch (const std::exception &e) {
    std::cout << "An unexpected error occurred: " << e.what() << std::endl;
  }

  return 0;
}
