with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

package body Perm is
  function NextPermutation(tab : in out Permutation; n : Integer) return Boolean is
    procedure Swap (tab : in out Permutation; a : Integer; b : Integer) is
      c : Integer;
    begin
      c := tab(a);
      tab(a) := tab(b);
      tab(b) := c;
    end Swap;
    function FindIndex(tab : in out Permutation; x : Integer; a : Integer; b : Integer) return Integer is
      i,j,ans,ind : Integer;
    begin
      i := a;
      ind := a;
      ans := tab(a);
      while i<=b loop
        if tab(i) > x then
          if tab(i) < ans then
            ans := tab(i);
            ind := i;
          end if;
        end if;
        i := i+1;
      end loop;
      return ind;
    end FindIndex;
    procedure Rev(tab : in out Permutation; a : Integer; b : Integer) is
      i, j : Integer;
    begin
      i := a;
      j := b;
      while i < j loop
         Swap(tab, i, j);
         i := i+1;
         j := j-1;
      end loop;
    end Rev;

    l, i : Integer;
    begin
      l := n-1;
      --Put_Line(l'Image);
      while l>0 and tab(l)<tab(l-1) loop
        l := l-1;
      end loop;
      if l=0 then
        return False;
      end if;
      i := FindIndex(tab, tab(l-1), l, n-1);
      Swap(tab, l-1, i);
      Rev(tab, l, n-1);
      return True;
end NextPermutation;

--function Factorial(n : Integer) return Integer is
  -- i : Integer := 1;
  -- ans : Integer :=1;
--begin
  -- while i<=n loop
    -- ans := ans*i;
     --i := i+1;
   --end loop;
   --return ans;
--end Factorial;
end Perm;
