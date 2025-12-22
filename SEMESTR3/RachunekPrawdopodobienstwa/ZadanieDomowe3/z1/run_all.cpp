#include "simulation.hpp"
#include <filesystem>
#include <fstream>
#include <iostream>
#include <string>
namespace fs = std::filesystem;

void save_results_csv(const std::string &filename, simulation &sim) {
  std::ofstream file(filename);
  if (!file.is_open()) {
    std::cerr << "Error opening file " << filename << "\n";
    return;
  }

  const auto &n_values = sim.get_n_values();
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

  int n_start = 10000;
  int n_end = 1000000;
  int n_step = 10000;
  int k_value = 50;

  simulation_Ln simL1(1, n_start, n_end, n_step, k_value); // d = 1
  simulation_Ln simL2(2, n_start, n_end, n_step, k_value); // d = 2

  std::cout << "Running L_n (d=1) simulation...\n";
  simL1.simulate();

  std::cout << "Running L_n (d=2) simulation...\n";
  simL2.simulate();

  save_results_csv(output_dir + "/Ln_d1.csv", simL1);
  save_results_csv(output_dir + "/Ln_d2.csv", simL2);

  std::cout << "All simulations finished.\n";
  return 0;
}
