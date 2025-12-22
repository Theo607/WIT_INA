#include "simulation.hpp"
#include <iostream>
#include <fstream>
#include <string>
#include <filesystem> 
namespace fs = std::filesystem;

void save_results_csv(const std::string& filename, simulation& sim) {
    std::ofstream file(filename);
    if (!file.is_open()) {
        std::cerr << "Error opening file " << filename << "\n";
        return;
    }

    const auto& n_values = sim.get_n_values();
    int k_value = sim.get_k_value();

    file << "n";
    for (int k = 0; k < k_value; ++k)
        file << ",run" << (k + 1);
    file << "\n";

    for (size_t i = 0; i < n_values.size(); ++i) {
        file << n_values[i];
        for (int k = 0; k < k_value; ++k) {
            file << "," << sim.result_at(i, k);
        }
        file << "\n";
    }

    file.close();
    std::cout << "Saved " << filename << "\n";
}

int main() {
    std::string output_dir = "simulation_results";
    if (!fs::exists(output_dir)) {
        fs::create_directory(output_dir);
        std::cout << "Created directory: " << output_dir << "\n";
    }

    int n_start = 1000;
    int n_end = 100000;
    int n_step = 1000;
    int k_value = 50;

    simulation_Bn simBn(n_start, n_end, n_step, k_value);
    simulation_Un simUn(n_start, n_end, n_step, k_value);
    simulation_Cn simCn(n_start, n_end, n_step, k_value);
    simulation_Dn simDn(n_start, n_end, n_step, k_value);
    simulation_Dn_minus_Cn simDnMinusCn(n_start, n_end, n_step, k_value);

    std::cout << "Running Bn simulation...\n";
    simBn.simulate();
    std::cout << "Running Un simulation...\n";
    simUn.simulate();
    std::cout << "Running Cn simulation...\n";
    simCn.simulate();
    std::cout << "Running Dn simulation...\n";
    simDn.simulate();
    std::cout << "Running Dn-Cn simulation...\n";
    simDnMinusCn.simulate();

    save_results_csv(output_dir + "/Bn.csv", simBn);
    save_results_csv(output_dir + "/Un.csv", simUn);
    save_results_csv(output_dir + "/Cn.csv", simCn);
    save_results_csv(output_dir + "/Dn.csv", simDn);
    save_results_csv(output_dir + "/Dn_minus_Cn.csv", simDnMinusCn);

    std::cout << "All simulations finished.\n";
    return 0;
}

