#ifndef SIMULATION_HPP
#define SIMULATION_HPP

#include "bins.hpp"
#include <algorithm>
#include <stdexcept>
#include <vector>

class simulation {
protected:
  std::vector<int> n_values;
  int k_value = 50;
  int **result = nullptr;

public:
  simulation(int n_start = 1, int n_end = 10, int n_step = 1, int k_val = 50)
      : k_value(k_val) {
    if (n_start <= 0 || n_end < n_start || n_step <= 0 || k_val <= 0)
      throw std::invalid_argument("Invalid n or k range");

    for (int i = n_start; i <= n_end; i += n_step)
      n_values.push_back(i);

    result = new int *[n_values.size()];
    for (size_t i = 0; i < n_values.size(); ++i)
      result[i] = new int[k_value]();
  }

  virtual ~simulation() {
    if (result) {
      for (size_t i = 0; i < n_values.size(); ++i)
        delete[] result[i];
      delete[] result;
    }
  }

  virtual void simulate() = 0;

  int &result_at(size_t n_index, size_t k_index) {
    if (n_index >= n_values.size() || k_index >= k_value)
      throw std::out_of_range("Index out of bounds in simulation::result_at");
    return result[n_index][k_index];
  }

  const std::vector<int> &get_n_values() const { return n_values; }
  int get_k_value() const { return k_value; }
};

class simulation_Ln : public simulation {
  int d; // number of choices

public:
  simulation_Ln(int d_choices, int n_start, int n_end, int n_step, int k_val)
      : simulation(n_start, n_end, n_step, k_val), d(d_choices) {
    if (d != 1 && d != 2)
      throw std::invalid_argument("d must be 1 or 2");
  }

  void simulate() override {
    for (size_t ni = 0; ni < n_values.size(); ++ni) {
      int n = n_values[ni];
      bins urns(n);

      for (int k = 0; k < k_value; ++k) {
        urns.clear();

        for (int ball = 0; ball < n; ++ball) {
          if (d == 1) {
            std::size_t idx = urns.dist(urns.rng);
            urns.arr[idx]++;
          } else {
            std::size_t i1 = urns.dist(urns.rng);
            std::size_t i2 = urns.dist(urns.rng);
            std::size_t idx = (urns.arr[i1] <= urns.arr[i2]) ? i1 : i2;
            urns.arr[idx]++;
          }
        }

        std::size_t max_load = 0;
        for (auto v : urns.arr)
          max_load = std::max(max_load, v);

        result_at(ni, k) = static_cast<int>(max_load);
      }
    }
  }
};

class simulation_Bn : public simulation {
public:
  using simulation::simulation;

  void simulate() override {
    for (size_t ni = 0; ni < n_values.size(); ++ni) {
      bins urns(n_values[ni]);
      for (int k = 0; k < k_value; ++k) {
        urns.clear();
        int count = 0;
        while (!urns.first_collision) {
          count++;
          urns.add_ball();
        }
        result_at(ni, k) = count;
      }
    }
  }
};

class simulation_Un : public simulation {
public:
  using simulation::simulation;

  void simulate() override {
    for (size_t ni = 0; ni < n_values.size(); ++ni) {
      bins urns(n_values[ni]);
      for (int k = 0; k < k_value; ++k) {
        urns.clear();
        urns.add_balls(n_values[ni]);
        result_at(ni, k) = static_cast<int>(urns.get_empty_bins());
      }
    }
  }
};

class simulation_Cn : public simulation {
public:
  using simulation::simulation;

  void simulate() override {
    for (size_t ni = 0; ni < n_values.size(); ++ni) {
      bins urns(n_values[ni]);
      for (int k = 0; k < k_value; ++k) {
        urns.clear();
        int count = 0;
        while (urns.get_empty_bins() > 0) {
          count++;
          urns.add_ball();
        }
        result_at(ni, k) = count;
      }
    }
  }
};
class simulation_Dn : public simulation {
public:
  using simulation::simulation;

  void simulate() override {
    for (size_t ni = 0; ni < n_values.size(); ++ni) {
      int n = n_values[ni];
      bins urns(n);
      for (int k = 0; k < k_value; ++k) {
        urns.clear();
        int count = 0;

        std::size_t bins_lt2 = n;

        while (bins_lt2 > 0) {
          count++;
          std::size_t idx = urns.dist(urns.rng);
          if (urns.arr[idx] == 1)
            bins_lt2--;
          urns.arr[idx]++;
        }

        result_at(ni, k) = count;
      }
    }
  }
};
class simulation_Dn_minus_Cn : public simulation {
public:
  using simulation::simulation;

  void simulate() override {
    for (size_t ni = 0; ni < n_values.size(); ++ni) {
      int n = n_values[ni];
      bins urns(n);
      for (int k = 0; k < k_value; ++k) {
        urns.clear();
        int count = 0;
        int C = 0;

        std::size_t empty_bins = n;

        while (empty_bins > 0) {
          count++;
          std::size_t idx = urns.dist(urns.rng);
          if (urns.arr[idx] == 0)
            empty_bins--;
          urns.arr[idx]++;
        }
        C = count;

        std::size_t bins_lt2 = 0;
        for (auto balls : urns.arr)
          if (balls < 2)
            bins_lt2 = bins_lt2 + 1;

        while (bins_lt2 > 0) {
          count++;
          std::size_t idx = urns.dist(urns.rng);
          if (urns.arr[idx] == 1)
            bins_lt2--;
          urns.arr[idx]++;
        }

        result_at(ni, k) = count - C;
      }
    }
  }
};

#endif // SIMULATION_HPP
