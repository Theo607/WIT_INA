import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("insertion_sort_results.csv")

avg = df.groupby('n').mean().reset_index()

plt.figure(figsize=(10,6))
for n in df['n'].unique():
    subset = df[df['n']==n]
    plt.scatter([n]*len(subset), subset['comparisons'], color='blue', alpha=0.3)
plt.plot(avg['n'], avg['comparisons'], color='red', label='Average')
plt.xlabel('n')
plt.ylabel('Comparisons')
plt.title('Insertion Sort Comparisons')
plt.legend()
plt.savefig("comparisons.png")
plt.close()

plt.figure(figsize=(10,6))
for n in df['n'].unique():
    subset = df[df['n']==n]
    plt.scatter([n]*len(subset), subset['movements'], color='green', alpha=0.3)
plt.plot(avg['n'], avg['movements'], color='red', label='Average')
plt.xlabel('n')
plt.ylabel('Movements')
plt.title('Insertion Sort Movements')
plt.legend()
plt.savefig("movements.png")
plt.close()

plt.figure(figsize=(10,6))
plt.plot(avg['n'], avg['comparisons']/avg['n'], label='Comparisons/n')
plt.plot(avg['n'], avg['comparisons']/avg['n']**2, label='Comparisons/n^2')
plt.xlabel('n')
plt.ylabel('Value')
plt.title('Comparisons Ratios')
plt.legend()
plt.savefig("comparisons_ratios.png")
plt.close()

plt.figure(figsize=(10,6))
plt.plot(avg['n'], avg['movements']/avg['n'], label='Movements/n')
plt.plot(avg['n'], avg['movements']/avg['n']**2, label='Movements/n^2')
plt.xlabel('n')
plt.ylabel('Value')
plt.title('Movements Ratios')
plt.legend()
plt.savefig("movements_ratios.png")
plt.close()

