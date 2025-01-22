with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

package body TotientPackage is
    function Totient (n : Positive) return Positive  is
    i  : Integer;
    x, totient : Positive;
begin
    x := n;
    i := 3;
    totient := 1;
    if x mod 2 = 0 then
        while x mod 2 = 0 loop
            x := x/2;
            totient := totient*2;
        end loop;
        totient := totient/2;
    end if;
    while i*i<=x loop
    if x mod i = 0 then
        totient := totient*(i-1);
        while x mod i = 0 loop
            x := x/i;
            totient := totient*i;
        end loop;
        totient := totient/i;
     end if;
     i := i+2;
     end loop;
     if x /= 1 then
         totient := totient*(x-1);
     end if;
     return totient;
end Totient;
end TotientPackage;
