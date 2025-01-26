with Perm; use Perm;
with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Command_Line; use Ada.Command_Line;

procedure Hetman is
    function CheckDiagonals (tab : in out Permutation; i : Integer; n : Integer) return Boolean is
    k,j,x,y : Integer;
    begin
    k := i+1;
    j := i-1;
    x := tab(i)-1;
    y := tab(i)+1;
    while j>=0 or k<n loop
      if j>=0 then
        if tab(j) = x or tab(j) = y then
          return False;
        end if;
        j := j-1;
      end if;
      if k<n then
        if tab(k) = x or tab(k) = y then
          return False;
        end if;
        k := k+1;
      end if;
      x := x-1;
      y := y+1;
    end loop;
    return true;
    end CheckDiagonals;
    procedure PreparePermutation(tab : in out Permutation; n : Integer) is
      i : Integer := 0;
    begin
      while i<n loop
        tab(i) := i+1;
        i := i+1;
      end loop;
    end PreparePermutation;
    function CheckPermutation(tab : in out Permutation; n : Integer) return Boolean is
      i : Integer := 0;
    begin
      while i<n loop
        if CheckDiagonals(tab, i, n) = False then
          return False;
        end if;
        i := i+1;
      end loop;
      return True;
    end CheckPermutation;
    procedure WritePermutation(tab : in out Permutation; n : Integer) is
      i : Integer := 0;
    begin
      while i<n loop
        Put(tab(i)'Image);
        Put(" ");
        i := i+1;
      end loop;
      Put_Line("");
    end WritePermutation;
    procedure FindSolutions(n : Integer) is
       ans : Integer := 0;
       tab : Permutation (-1..n-1);
    begin
       PreparePermutation(tab, n);
      -- WritePermutation(tab, n);
       if CheckPermutation(tab, n) then
         ans := ans + 1;
         WritePermutation(tab, n);
       end if;
       while NextPermutation(tab, n) loop
         if CheckPermutation(tab, n) then
           ans := ans + 1;
           WritePermutation(tab, n);
         end if;
       end loop;
        Put("Number of solutions: ");
        Put_Line(ans'Image);
    end FindSolutions;
          
--    n : Integer;

    begin
      if Argument_Count < 1 then
         Put_Line("Złe dane!");
      else
        FindSolutions(Integer'Value(Argument(1)));
      end if;
   -- Put("Wczytaj rozmiar planszy: ");
   -- Get(n);
   -- Put(n'Image);
   -- FindSolutions(n);

end Hetman;
