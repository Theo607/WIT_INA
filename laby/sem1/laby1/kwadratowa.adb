with Ada.Text_IO; use Ada.Text_IO;
with Ada.Float_Text_IO; use Ada.Float_Text_IO;
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

procedure kwadratowa is
	a,b,c,delt,odp1,odp2 :  Float;
begin
	Put("Wczytaj a: ");
	Get(a);
	Put("Wczytaj b: ");
	Get(b);
	Put("Wczytaj c: ");
	Get(c);
	if a = 0.0 then
	c := (-1.0)*c;
	c := c/b;
	Put_Line("To równanie jest liniowe z rozwiązaniem: " & c'Image);
	else
	delt := b*b;
	delt := delt-(4.0 * a * c);
	if delt < 0.0 then
		Put("Nie ma rozwiązań tego równania");
	end if;
	if delt = 0.0 then
		odp1 := -b/(2.0 * a);
		Put_Line("To równanie ma jedno rozwiązanie wynoszące: " & odp1'Image);
	end if;
	if delt > 0.0 then
	delt := Sqrt(delt);
	odp1 := -b+delt;
	odp1 := odp1/(2.0 * a);
	odp2 := -b-delt;
	odp2 := odp2/(2.0 * a);
	Put_Line("To równanie ma dwa rozwiązania, pierwsze z nich to: " & odp1'Image);
	Put_Line("A drugie to: " & odp2'Image);
	end if;
	end if;
end kwadratowa;
