import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import os

data_dir = "simulation_results"

ln_vars = ["Ln_d1", "Ln_d2"]
ln_data = {}

for var in ln_vars:
    file_path = os.path.join(data_dir, f"{var}.csv")
    if not os.path.exists(file_path):
        print(f"File {file_path} not found!")
        continue
    df = pd.read_csv(file_path)
    ln_data[var] = df

def compute_and_plot_ratio(var_name, mean_values, n_values, ratios_dict):
    plt.figure(figsize=(10,6))
    for label, ratio in ratios_dict.items():
        plt.plot(n_values, ratio, label=label)
    plt.xlabel("n")
    plt.ylabel(f"{var_name} ratios")
    plt.title(f"{var_name} normalized ratios vs n")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(os.path.join(data_dir, f"{var_name}_ratios.png"))
    plt.close() 

for var, df in ln_data.items():
    n_values = df['n'].values
    repetitions = df.drop(columns='n').values
    mean_values = repetitions.mean(axis=1)

    plt.figure(figsize=(10,6))
    for k in range(repetitions.shape[1]):
        plt.plot(n_values, repetitions[:, k], color='blue', alpha=0.3)
    plt.plot(n_values, mean_values, color='red', linewidth=2, label='Mean')
    plt.xlabel("n")
    plt.ylabel(var)
    plt.title(f"{var} vs n (individual runs and mean)")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(os.path.join(data_dir, f"{var}_plot.png"))
    plt.close()

if "Ln_d1" in ln_data:
    df = ln_data["Ln_d1"]
    n_values = df['n'].values
    mean_values = df.drop(columns='n').mean(axis=1).values
    f1 = np.log(n_values) / np.log(np.log(n_values))
    compute_and_plot_ratio(
        "Ln_d1",
        mean_values,
        n_values,
        {
            "Ln_d1 / (ln n / ln ln n)": mean_values / f1
        }
    )

if "Ln_d2" in ln_data:
    df = ln_data["Ln_d2"]
    n_values = df['n'].values
    mean_values = df.drop(columns='n').mean(axis=1).values
    f2 = np.log(np.log(n_values)) / np.log(2)
    compute_and_plot_ratio(
        "Ln_d2",
        mean_values,
        n_values,
        {
            "Ln_d2 / (ln ln n / ln 2)": mean_values / f2
        }
    )

print("All plots generated in:", data_dir)

