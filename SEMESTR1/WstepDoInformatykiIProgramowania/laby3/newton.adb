with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
procedure newton is
	function newton (n: Integer; k: Integer) return Integer is
	type Pascal is array (0..n) of Integer;

	procedure preparePascal (s : in out Pascal) is
	begin
		for i in s'Range loop
			s(i) := 0;
		end loop;
		s(0):=1;
	end preparePascal;
	procedure ComputePascal (s : in out Pascal) is
		i,j,a,b : Integer;
	begin
		i:=1;
		while i<=n loop
			a:=1;
			j:=1;
			while j<=i loop
				b:=s(j);
				s(j):=s(j)+a;
				a:=b;
				j:=j+1;
			end loop;
			i:=i+1;
		end loop;
	end ComputePascal;

	s : Pascal;
	a,b : Integer;

	begin
		a:=n;
		b:=k;
		preparePascal(s);
		ComputePascal(s);
		return s(b);
	end newton;

	n,k,ans : Integer;
begin
	Put("Podaj liczby dwumianu: ");
	Get(n);
	Get(k);
	ans := newton(n,k);
	Put(ans'Image);
end newton;
