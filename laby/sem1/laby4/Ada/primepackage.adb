with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

package body PrimePackage is
    function PrimeNumbers (n : Positive) return Positive is
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
	function CountInSieve (s : Sieve) return Positive is
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
    end PrimeNumbers;

    function Prime (n: Positive) return Positive is
        type Primes is array (0..n-1) of Positive;
    procedure ComputePrimes (s : in out Primes) is
        i,j,k : Integer;
        czy : Boolean;
    begin
        s(0) := 2;
        j := 1;
        i := 3;
        k := 0;
        czy := True;
        while j<=n-1 loop
            k := 0;
            czy := True;
            while k<j and k*k<=i loop
                if i mod s(k) = 0 then
                    czy := False;
                    k := j;
                end if;
                k := k+1;
            end loop;
            if czy then
                s(j):=i;
                j:=j+1;
            end if;
            i := i+2;
        end loop;
    end ComputePrimes;
    
    s : Primes;
    begin
        ComputePrimes(s);
        return s(n-1);
    end Prime;

    function IsPrime(n : in Positive) return Boolean is
        x, i : Positive;
    begin
        x := n;
        if x = 2 then
            return True;
        end if;
        if x mod 2 = 0 then
            return False;
	end if;
        i := 3;
        while i*i <= x loop
            if x mod i = 0 then
                return False;
            end if;
            i := i+2;
        end loop;
        return True;
    end IsPrime;
end PrimePackage;
