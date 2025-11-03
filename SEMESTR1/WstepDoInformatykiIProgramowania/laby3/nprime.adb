with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
procedure nprime is
    function nprime (n: Integer) return Integer is
        type Primes is array (0..n-1) of Integer;
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
    end nprime;

    n, ans : Integer;
begin
    Put("Podaj n: ");
    Get(n);
    ans := nprime(n);
    Put(ans'Image);
end nprime;
