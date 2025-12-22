#include "simulation.hpp"
#include <iostream>

void print_sample_results(simulation& sim, const std::string& name) {
    const auto& n_values = sim.get_n_values();
    int k_value = sim.get_k_value();

    std::cout << "\nSample results for " << name << " (first repetition of each n):\n";
    std::cout << "n\tvalue\n";
    for (size_t i = 0; i < n_values.size(); ++i) {
        std::cout << n_values[i] << "\t" << sim.result_at(i, 0) << "\n";
    }
}

int main() {
    int n_start = 5, n_end = 20, n_step = 5;
    int k_value = 10;

    simulation_Bn simBn(n_start, n_end, n_step, k_value);
    simulation_Un simUn(n_start, n_end, n_step, k_value);
    simulation_Cn simCn(n_start, n_end, n_step, k_value);
    simulation_Dn simDn(n_start, n_end, n_step, k_value);
    simulation_Dn_minus_Cn simDnMinusCn(n_start, n_end, n_step, k_value);

    std::cout << "Running simulations...\n";

    simBn.simulate();
    simUn.simulate();
    simCn.simulate();
    simDn.simulate();
    simDnMinusCn.simulate();

    print_sample_results(simBn, "Bn");
    print_sample_results(simUn, "Un");
    print_sample_results(simCn, "Cn");
    print_sample_results(simDn, "Dn");
    print_sample_results(simDnMinusCn, "Dn-Cn");

    std::cout << "\nAll simulations completed.\n";
    return 0;
}

