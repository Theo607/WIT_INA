#include <bits/stdc++.h>
using namespace std;

int simulate(int n, double p, mt19937 &rng) {
    vector<bool> informed(n, false);
    int rounds = 0;
    uniform_real_distribution<double> dist(0.0, 1.0);

    while (true) {
        rounds++;
        bool all_informed = true;
        for (int i = 0; i < n; i++) {
            if (!informed[i]) {
                if (dist(rng) < p) {
                    informed[i] = true;
                }
            }
            if (!informed[i]) all_informed = false;
        }
        if (all_informed) break;
    }

    return rounds;
}

int main() {
    mt19937 rng(random_device{}());
    vector<int> n_values;
    for (int n = 10; n <= 1000; n += 10) n_values.push_back(n);

    vector<double> p_values = {0.5, 0.1};
    int k = 50;
    for (double p : p_values) {
        string filename = "Tn_p" + to_string(int(p*10)) + ".csv";
        ofstream fout(filename);
        fout << "n,rep,rounds\n";

        for (int n : n_values) {
            for (int rep = 1; rep <= k; rep++) {
                int rounds = simulate(n, p, rng);
                fout << n << "," << rep << "," << rounds << "\n";
            }
        }
        fout.close();
        cout << "Wyniki zapisane do " << filename << endl;
    }

    return 0;
}

