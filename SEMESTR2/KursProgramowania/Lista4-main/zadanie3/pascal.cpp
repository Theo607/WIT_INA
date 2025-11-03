#include <iostream>
#include <string>

using namespace std;

bool isdigit(char c) {
    return c >= '0' && c <= '9';
}

unsigned long long binomialCoeff(int n, int k) {
    if (k < 0 || k > n) return 0;
    unsigned long long res = 1;
    for (int i = 1; i <= k; ++i) {
        res *= n--;
        res /= i;
    }
    return res;
}

bool isValidInteger(std::string s) {
    if (s.length() == 0) return false;

    int i = 0;
    if (s[0] == '-' || s[0] == '+') {
        i = 1;
        if (s.length() == 1) return false; // sign alone isn't valid
    }

    while (i < s.length()) {
        if (!isdigit(s[i])) return false;
        i++;
    }

    return true;
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        cerr << "Usage: pascal <row> <index1> <index2> ..." << endl;
        return 1;
    }

    string rowStr = argv[1];
    if (!isValidInteger(rowStr)) {
        cerr << "Invalid row: " << rowStr << endl;
        return 1;
    }

    int row = stoi(rowStr);
    if (row < 0) {
        cerr << "Row must be non-negative." << endl;
        return 1;
    }

    for (int i = 2; i < argc; ++i) {
        string arg = argv[i];
        cout << arg << " - ";
        if (!isValidInteger(arg)) {
            cout << "niepoprawne dane";
        } else {
            int index = stoi(arg);
            if (index < 0 || index > row) {
                cout << "poza zakresem";
            } else {
                cout << binomialCoeff(row, index);
            }
        }
        cout << endl;
    }

    return 0;
}
