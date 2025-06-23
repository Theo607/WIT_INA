with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
procedure pierw is
    function czy_pierw(x : in Integer) return Integer is
        n, i : Integer;
    begin
        n := x;
        if n = 2 then
            return 1;
        end if;
        if n mod 2 = 0 then
            return 0;
	end if;
        i := 3;
        while i*i <= n loop
            if n mod i = 0 then
                return 0;
            end if;
            i := i+2;
        end loop;
        return 1;
end czy_pierw;
n, a : Integer;
begin
Put("Podaj liczbę: ");
Get(n);
a := czy_pierw(n);
if a = 1 then
Put("Ta liczba jest pierwsza");
else
Put("Ta liczba nie jest pierwsza");
end if;
end pierw;
