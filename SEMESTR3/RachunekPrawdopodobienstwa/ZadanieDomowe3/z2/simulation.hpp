#ifndef SIMULATION_HPP
#define SIMULATION_HPP

#include <algorithm>
#include <fstream>
#include <numeric> // for std::iota
#include <random>
#include <string>
#include <tuple>
#include <utility>
#include <vector>
#include <iostream>

inline std::pair<int, int> insert_sort(std::vector<int> &array) {
  int comparisons = 0;
  int movements = 0;

  for (size_t i = 1; i < array.size(); i++) {
    int key = array[i];
    int j = i - 1;
    while (j >= 0) {
      comparisons++;
      if (array[j] > key) {
        array[j + 1] = array[j];
        movements++;
        j--;
      } else {
        break;
      }
    }
    array[j + 1] = key;
    movements++;
  }
  return {comparisons, movements};
}

struct simulation {
  std::vector<int> array_sizes;
  int repetitions;
  std::mt19937_64 rng{std::random_device{}()};
  std::vector<std::tuple<int, int, int>> results;

  void simulate() {
    for (int n : array_sizes) {
      std::vector<int> array(n); 
      std::cout << n << "\n";
      for (int rep = 0; rep < repetitions; rep++) {
        std::iota(array.begin(), array.end(), 1);      
        std::shuffle(array.begin(), array.end(), rng);

        auto stats = insert_sort(array);
        results.push_back({n, stats.first, stats.second});
      }
    }
  }

  void save_csv(const std::string &filename) {
    std::ofstream file(filename);
    file << "n,comparisons,movements\n";
    for (auto &r : results) {
      int n, comps, moves;
      std::tie(n, comps, moves) = r;
      file << n << "," << comps << "," << moves << "\n";
    }
  }
};

inline std::vector<int> range(int start, int end, int step) {
  std::vector<int> vec;
  for (int i = start; i <= end; i += step)
    vec.push_back(i);
  return vec;
}

#endif // SIMULATION_HPP
