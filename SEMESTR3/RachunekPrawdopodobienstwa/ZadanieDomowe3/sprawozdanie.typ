#set text(
  font: "New Computer Modern",
  size: 16pt
)
= Zadanie 1


== Cel eksperymentu

Celem eksperymentu było zbadanie maksymalnego zapełnienia pojedynczej urny po wrzuceniu $n$ kul do $n$ urn w dwóch scenariuszach:

1. *$d = 1$*: każda kula jest wrzucana do jednej losowo wybranej urny.
2. *$d = 2$*: każda kula jest wrzucana do dwóch losowo wybranych urn, a następnie trafia do urny z mniejszą liczbą kul (w przypadku remisu wybór jest dowolny).

Eksperyment miał na celu sprawdzenie, jak zastosowanie strategii *Power of Two Choices* wpływa na maksymalną liczbę kul w jednej urnie, oraz weryfikację teoretycznych przewidywań asymptotycznych.

== Wykresy

#image("z1/simulation_results/Ln_d1_plot.png")
#image("z1/simulation_results/Ln_d1_ratios.png")
#image("z1/simulation_results/Ln_d2_plot.png")
#image("z1/simulation_results/Ln_d2_ratios.png")


== Metoda

Dla każdego $n$ w zakresie od 10 000 do 1 000 000 wykonano $k = 50$ niezależnych powtórzeń eksperymentu dla obu scenariuszy.
W każdym powtórzeniu zliczano liczbę kul w każdej urnie po wrzuceniu wszystkich $n$ kul i zapisywano maksymalne zapełnienie oznaczone jako $L_n^(d)$.
Wyniki zapisano do plików CSV (`Ln_d1.csv` i `Ln_d2.csv`), a następnie przetworzono w Pythonie w celu uzyskania:
  - wykresów wszystkich powtórzeń z naniesioną średnią,
  - wykresów ilorazów średniego zapełnienia do funkcji teoretycznych:
    $f_1(n) = (ln n) / (ln ln n)$
    $f_2(n) = (ln ln n) / (ln 2)$

== Wyniki

=== Surowe wyniki

Na wykresach dla $d = 1$ i $d = 2$ widać, że:

Dla *$d = 1$* maksymalne zapełnienie rośnie wraz z $n$ w sposób zbliżony do $(ln n) / (ln ln n)$.
Dla *$d = 2$* maksymalne zapełnienie jest znacznie mniejsze i rośnie wolniej, zgodnie z teoretycznym przewidywaniem $(ln ln n) / (ln 2)$.
W przypadku $d = 2$ wyniki są silnie skoncentrowane wokół wartości średniej, co pokazuje skuteczny efekt *równoważenia* urn przy wyborze dwóch opcji.

=== Wykresy ilorazów

Iloraz $L_n^(1) / ((ln n) / (ln ln n))$ jest prawie stały dla dużych $n$, co potwierdza asymptotyczną poprawność teorii dla $d = 1$.
Iloraz $L_n^(2) / ((ln ln n )/ (ln 2))$ również utrzymuje się blisko stałej wartości dla dużych $n$, co jednoznacznie wskazuje, że strategia *Power of Two Choices* skutecznie zmniejsza maksymalne zapełnienie.

== Wnioski

1. Zastosowanie strategii dwóch losowych wyborów znacząco zmniejsza maksymalną liczbę kul w pojedynczej urnie.
2. Wyniki eksperymentalne są zgodne z przewidywaniami teoretycznymi:
   - $L_n^(1)$ rośnie proporcjonalnie do $(ln n) / (ln ln n)$,
   - $L_n^(2)$ rośnie proporcjonalnie do $(ln ln n) / (ln 2)$.
3. Dla $d = 2$ obserwuje się mniejszą wariancję wyników — strategia równoważy obciążenie urn w sposób wyraźnie skuteczny.

= Zadanie 2

== Cel eksperymentu

Celem eksperymentu było zbadanie zachowania algorytmu sortowania przez wstawianie (*Insertion Sort*) dla różnych rozmiarów danych. 
Eksperyment miał na celu:
+ Pomiar liczby porównań elementów tablicy podczas sortowania.
+ Pomiar liczby przestawień elementów (ruchów) podczas sortowania.
+ Analizę zależności liczby porównań i przestawień od rozmiaru tablicy oraz porównanie ich z teoretycznymi funkcjami rosnącymi liniowo i kwadratowo.

== Metoda

Dla każdego $n in {100, 200, ..., 10000}$ wykonano k = 50 niezależnych powtórzeń eksperymentu:
+ Generowano tablicę A[1..n] będącą losową permutacją liczb {1, ..., n} (każda permutacja była jednakowo prawdopodobna).
+ Sortowano tablicę algorytmem *Insertion Sort*, zliczając:
   - liczbę porównań elementów tablicy,
   - liczbę przestawień elementów (ruchów kluczy).
+ Wyniki zapisywano do plików CSV (`insertion_sort_results.csv`) w celu dalszej analizy.

== Wykresy

=== Liczba porównań

#image("z2/comparisons.png")

Na wykresie widać rosnącą liczbę porównań wraz ze wzrostem n. Średnia liczba porównań dla każdego rozmiaru n, oznaczona jako cmp(n), rośnie w przybliżeniu kwadratowo, zgodnie z teoretyczną złożonością O(n²) algorytmu *Insertion Sort*.

=== Liczba przestawień

#image("z2/movements.png")

Analogicznie, liczba przestawień s(n) również rośnie wraz z n i wykazuje charakterystyki zbliżone do kwadratowej zależności.

=== Ilorazy porównań i przestawień

#image("z2/comparisons_ratios.png")
#image("z2/movements_ratios.png")

Na wykresach przedstawiono:
- $c m p(n)/n$ oraz $c m p(n)/n^2$ jako funkcje n,
- $s(n)/n$ oraz $s(n)/n^2$ jako funkcje n.

Widać, że:
- iloraz $c m p(n)/n$ rośnie wraz z n, ale $c m p(n)/n^2$ zbliża się do stałej wartości dla dużych n, co potwierdza kwadratową złożoność czasową,
- analogicznie dla przestawień s(n).

== Wnioski

+ Algorytm *Insertion Sort* wykazuje rosnącą liczbę porównań i przestawień w miarę zwiększania rozmiaru danych.
+ Średnia liczba porównań i przestawień rośnie w przybliżeniu jak $O(n^2)$, co jest zgodne z teoretycznymi oczekiwaniami.
+ Eksperyment potwierdza, że *Insertion Sort* jest efektywny dla małych tablic, ale jego wydajność szybko spada przy dużych danych.

= Zadanie 3

== Cel eksperymentu

Celem eksperymentu było zbadanie minimalnej liczby rund $T_n$, potrzebnej do rozesłania informacji z centralnej stacji (węzeł 0) do wszystkich pozostałych węzłów w sieci o topologii gwiazdy, przy uwzględnieniu zakłóceń.  
Eksperyment pozwalał oszacować, jak prawdopodobieństwo prawidłowego odbioru wiadomości przez pojedynczy węzeł wpływa na czas rozprzestrzeniania informacji.

== Metoda

Dla każdego $n in {10, 20, …, 1000}$ wykonano k = 50 niezależnych powtórzeń eksperymentu dla dwóch wartości prawdopodobieństwa odbioru p = 0.5 oraz p = 0.1:
+ Węzły 1..n początkowo nie posiadają informacji.  
+ W każdej rundzie stacja 0 wysyła wiadomość do wszystkich węzłów. Każdy węzeł otrzymuje wiadomość niezależnie z prawdopodobieństwem p.  
+ Rundy powtarzano, aż wszystkie węzły otrzymały informację. Liczbę rund zapisano jako Tn.  
+ Wyniki zapisano do plików CSV i wykorzystano w Pythonie do wygenerowania wykresów.

== Wykresy

=== p = 0.5

#image("z3/Tn_p5_plot.png")

Na wykresie widać rozrzut liczby rund w poszczególnych powtórzeniach (kolor niebieski) oraz średnią t(n) (kolor czerwony).  
Dla p = 0.5 średnia liczba rund rośnie powoli wraz z n, co oznacza, że komunikacja jest stosunkowo efektywna.

=== p = 0.1

#image("z3/Tn_p1_plot.png")

Dla p = 0.1 rozrzut liczby rund jest znacznie większy, a średnia t(n) rośnie szybciej wraz z n. Oznacza to, że przy niskim prawdopodobieństwie odbioru zakłócenia znacznie wydłużają czas rozprzestrzeniania informacji.

== Wnioski

+ Minimalna liczba rund Tn rośnie wraz ze zwiększaniem się liczby węzłów n.  
+ Wyższe prawdopodobieństwo odbioru (p = 0.5) prowadzi do mniejszej liczby rund i mniejszego rozrzutu wyników.  
+ Przy niskim prawdopodobieństwie odbioru (p = 0.1) liczba rund jest większa i bardziej zróżnicowana, co pokazuje znaczenie zakłóceń w procesie komunikacji.
