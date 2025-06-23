#include <stdio.h>
#include <stdbool.h>

int suma (int n)
{
    int sum = n;
    while (n > 0)
    {
        sum = sum + 2*(n % 10);
        n = n / 10;
    }
    return sum;
}

void tablica (bool s[])
{
    for (int i = 0; i < 2025; i++)
    {
        s[i] = false;
    }
    s[2025] = true;
    int j;
    for (int i = 2025; i > 0; i = i - 2)
    {
        j = suma(i);
        if (j <= 2025 && s[j])
        {
            s[i] = true;
        }
    }
}

int ile (bool s[])
{
    int c = 0;
    for (int i = 1; i < 2025; i = i + 2)
    {
        if (s[i])
        {
            c++;
            printf ("%d\n", i);
        }
    }
    return c;
}

int main()
{
    bool s[2026];
    tablica (s);
    printf ("%d\n", ile(s));
    return 0;
}
