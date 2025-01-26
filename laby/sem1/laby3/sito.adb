with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
procedure sito is
	function sito (n : Integer) return Integer is
	type Sieve is array (2..n) of Boolean;

	procedure DoSieve (s : in out Sieve) is
		j, k : Integer;
	begin
		for i in s'Range loop
			s(i) := True;
		end loop;
		k := 2;
		while k*k<=n loop
			if s(k) then
				j:= k*k;
				while j<=n loop
					s(j) := False;
					j := j+k;
				end loop;
			end if;
		k := k+1;
		end loop;
	end DoSieve;
	function CountInSieve (s : Sieve) return Integer is
		o : Integer := 0;
	begin
		for i in s'Range loop
			if s(i) then
				o := o+1;
			end if;
		end loop;
		return o;
	end CountInSieve;
	
	s : Sieve;
	begin
		DoSieve(s);
		return CountInSieve(s);
	end sito;
		
	n, ans : Integer;
begin
	Put("Podaj liczbę: ");
	Get(n);
	if n<2 then
		Put("Złe dane!");
	else
		ans := sito(n);
		Put("Liczba liczb pierwszych mniejszych bądź równych ");
		Put(n'Image);
		Put(" to: " & ans'Image);
	end if;
end sito;
