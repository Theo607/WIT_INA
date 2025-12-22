#include "simulation.hpp"
#include <iostream>

int main() {
    simulation sim;

    sim.array_sizes = range(100, 10000, 100);

    sim.repetitions = 50;

    std::cout << "Starting simulation..." << std::endl;
    sim.simulate();
    std::cout << "Simulation complete. Saving CSV..." << std::endl;

    sim.save_csv("insertion_sort_results.csv");
    std::cout << "CSV saved as insertion_sort_results.csv" << std::endl;

    return 0;
}

