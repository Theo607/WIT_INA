= Symulacje Monte Carlo

Mateusz Smuga \
1.11.2025

== Aproksymacja całek (k = 5)

=== 1. $f(x) = root(3, x)$, #h(1%) $x in [0,8]$

#image("Graphs/cbrt_x_integral.svg")

*Wnioski:*
Rozrzut wyników maleje wraz ze wzrostem liczby punktów `n`. Średnia wartości dobrze przybliża dokładną całkę.

=== 2. $f(x) = sin(x)$, #h(1%) $[0,π]$

#image("Graphs/sin_integral.svg")

*Wnioski:*
Przy małych `n` wyniki są bardziej rozproszone, ale już dla `n > 1000` średnia jest bardzo bliska dokładnej wartości całki = 2.

=== 3. $f(x) = 4x(1-x)^3$, #h(1%) $x in [0,1]$

#image("Graphs/4x1-x3_integral.svg")

*Wnioski:*
Metoda Monte Carlo dobrze przybliża całki wielomianowe. Średnia wartości stabilizuje się przy większych `n`.

=== 4. Aproksymacja liczby $pi$ z $f(x) = sqrt(1-x^2)$, #h(1%) $x in [0,1]$

#image("Graphs/pi_approximation.svg")

*Wnioski:*
Ćwiartka koła jednostkowego pozwala przybliżyć $pi$. Rozrzut wyników maleje wraz ze wzrostem `n`, a średnia daje przybliżoną wartość $pi$ = 3.14159.

== Aproksymacja całek (k = 50)

=== 1. $f(x) = root(3, x)$, #h(1%) $x in [0,8]$

#image("Graphs/cbrt_x_integral50.svg")

*Wnioski:*
Większa liczba powtórzeń `k` znacząco zmniejsza rozrzut wyników. Średnia jest bardziej stabilna niż dla k = 5.

=== 2. $f(x) = sin(x)$, #h(1%) $x in [0,π]$

#image("Graphs/sin_integral50.svg")

*Wnioski:*
Rozrzut wyników praktycznie zanika, a średnia niemal idealnie pokrywa się z wartością dokładną całki = 2.

=== 3. $f(x) = 4x(1-x)^3$, #h(1%) $x in [0,1]$

#image("Graphs/4x1-x3_integral50.svg")

*Wnioski:*
Więcej powtórzeń `k` powoduje stabilniejszą średnią, rozrzut wyników jest minimalny.

=== 4. Aproksymacja liczby $pi$ z $f(x) = sqrt(1-x^2)$, #h(1%) $x in [0,1]$

#image("Graphs/pi_approximation50.svg")

*Wnioski:*
Dzięki większej liczbie powtórzeń `k` przybliżenie $pi$ jest bardzo dokładne już przy stosunkowo małych `n`.

== Podsumowanie

- Metoda Monte Carlo pozwala efektywnie przybliżać całki funkcji ciągłych przy użyciu losowych punktów.
- Zwiększenie liczby powtórzeń `k` zmniejsza rozrzut wyników i stabilizuje średnią.
- Dla wszystkich testowanych funkcji średnie wartości bardzo dobrze odzwierciedlają dokładną wartość całki.
- Aproksymacja π działa naturalnie poprzez całkowanie ćwiartki koła jednostkowego – im więcej punktów, tym większa dokładność.
