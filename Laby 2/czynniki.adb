with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
procedure czynniki is
procedure czyn (n : in Integer)  is
    i, k, x, czy, q : Integer;
begin
    x := n;
    czy := 0;
    i := 3;
    if x mod 2 = 0 then
        q := 0;
        while x mod 2 = 0 loop
            x := x/2;
            q := q+1;
        end loop;
        Put("2");
        if q > 1 then
            Put("^");
            Put(q'Image);
        end if;
        czy := 1;
    end if;
    while i*i<=x loop
    if x mod i = 0 then
        if czy = 1 then
            Put("*");
        else
            czy := 1;
        end if;
        k:=0;
        while x mod i = 0 loop
            x := x/i;
            k := k+1;
        end loop;
        Put_Line(i'Image);
        if k>1 then
            Put("^");
            Put_Line(k'Image);
        end if;
     end if;
     i := i+2;
     end loop;
     if x /= 1 then
         Put("*");
         Put(x'Image);
     end if;
end czyn;

    n : Integer;
begin
    Put_Line("Wczytaj liczbę: ");
    Get(n);
    czyn( n );
end czynniki;
