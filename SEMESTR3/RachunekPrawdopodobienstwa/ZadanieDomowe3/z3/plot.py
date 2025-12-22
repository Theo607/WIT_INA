import pandas as pd
import matplotlib.pyplot as plt

for p in [0.5, 0.1]:
    filename = f"Tn_p{int(p*10)}.csv"
    df = pd.read_csv(filename)

    mean_rounds = df.groupby("n")["rounds"].mean().reset_index()

    plt.figure(figsize=(10,6))
    for n in mean_rounds["n"]:
        subset = df[df["n"] == n]
        plt.scatter([n]*len(subset), subset["rounds"], color='lightblue', alpha=0.5, s=10)
    plt.plot(mean_rounds["n"], mean_rounds["rounds"], color='red', label="Średnia")
    plt.xlabel("Liczba węzłów n")
    plt.ylabel("Liczba rund Tn")
    plt.title(f"Rozkład liczby rund Tn dla p={p}")
    plt.legend()
    plt.grid(True)
    plt.savefig(f"Tn_p{int(p*10)}_plot.png")
    plt.close()

    print(f"Wykres zapisany do Tn_p{int(p*10)}_plot.png")

