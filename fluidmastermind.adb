with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Command_Line; use Ada.Command_Line;
procedure FluidMasterMind is
    function Fin(l : Integer; base : Integer) return Integer is
        i,o,fin,w : Integer;
    begin
        o := base-1;
        fin := o;
        w := base;
        i := 1;
        while i<l loop
            fin := fin+(w*o);
            w := w*base;
            i := i+1;
        end loop;
        return fin;
    end Fin;
    procedure Win(l : Integer; base : Integer; fin : Integer) is
        type Occur is array (1..base) of Integer;
	type Variation is array(1..l) of Integer;
        type Codes is array (0..fin) of Variation;
	type Indicator is array(0..fin+1) of Boolean;
    procedure Rev(v : in out Variation; l : Integer) is
        i,c : Integer;
        p : Variation;
    begin
        i := 1;
        while i <= l loop
            p(l-i+1) := v(i)+1;
            i := i+1;
        end loop;
        v := p;
    end Rev;
    procedure PrintVariation(v : Variation; l : Integer) is
        i : Integer := 1;
    begin
        while i <= l loop
            Put(v(i)'Image);
            --Put(" ");
            i := i+1;
        end loop;
        Put_Line("");
    end PrintVariation;
    procedure AnswerGuess(a : in out Integer; b : in out Integer) is
        m,n : Integer;
    begin
        Put("Ile zgodnych?: ");
        Get(n);
        Put("Ile na złym miejscu?: ");
        Get(m);
        a := n;
        b := m;
    end AnswerGuess;
    function Min(a : Integer; b : Integer) return Integer is
    begin
        if a < b then
            return a;
        end if;
        return b;
    end Min;
    function PermutationCheck(p : Variation; g : Variation; l : Integer; base : Integer; a : Integer; b : Integer) return Boolean is
        perm, guess : Occur;
        i, positions, correct : Integer;
    begin
        i := 1;
        while i <= base loop
            perm(i) := 0;
            guess(i) := 0;
            i := i+1;
        end loop;
        positions := 0;
        i := 1;
       -- Put(a'Image);
       -- Put_Line(b'Image);
      --  PrintVariation(p,l);
       -- PrintVariation(g,l);
        while i <= l loop
           -- Put(i'Image);
           -- Put(p(i)'Image);
           -- Put_Line(g(i)'Image);
            if p(i)/=g(i) then
                perm(p(i)) := perm(p(i))+1;
                guess(g(i)) := guess(g(i))+1;
            else
                positions := positions+1;
            end if;
            i := i+1;
        end loop;
       -- Put_Line(positions'Image);
        correct := 0;
        i := 1;
        while i <= base loop
          --  Put(i'Image);
          --  Put(": ");
           -- Put(perm(i)'Image);
           -- Put_Line(guess(i)'Image);
            correct := correct + Min(perm(i),guess(i));
            i := i+1;
        end loop;
       -- Put(positions'Image);
       -- Put_Line(correct'Image);
       -- Put(a'Image);
       -- Put_Line(b'Image);
        if positions = a and correct = b then
            return True;
        end if;
        return False;
    end PermutationCheck;
    
        n,t,val,k,i,first,count,q,a,b,id,attempts,limit : Integer;
        arr : Codes;
        Ind : Indicator;
        v : Variation;
        WinCondition : Integer := 0;
    begin
       -- Put_Line(fin'Image);
        n := 0;
        first := 1;
        val := base;
        i := 1;
        while i < l loop
            first := first+val;
            val := val*base;
            i := i+1;
        end loop;
       -- Put_Line(first'Image);
        count := 0;
        while n <= fin loop
            i := 1;
            while i <= l loop
                v(i) := 0;
                i := i+1;
            end loop;
            k := n;
            q := 0;
           -- Put_Line(n'Image);
            t := n/base;
           -- Put_Line(t'Image);
            while k > 0 loop
               -- Put_Line(k'Image);
                v(q+1) := k mod base;
               -- Put_Line(k'Image);
                k := k / base;
               -- Put_Line(k'Image);
                q := q+1;
            end loop;
          --  Put_Line(q'Image);
          --  PrintVariation(v,l);
            if q = l or n<first then
               -- PrintVariation(v,l);
                Rev(v,l);
                arr(count) := v;
              --  PrintVariation(arr(count),l);
                count := count+1;
            end if;
            n := n+1;
        end loop;
        i := 0;
        while i <= fin loop
            Ind(i) := True;
            i := i+1;
        end loop;

        id := 0;
        attempts := 0;
        limit := base + l/2;
        a := 0;
        b := 0;
        while attempts < limit loop
            PrintVariation(arr(id),l);
            AnswerGuess(a,b);
           -- Put(a'Image);
           -- Put(b'Image);
            if (a<0 or a>l) or (b<0 or b>l) or a+b>l then
                Put_Line("Niepoprawne liczby");
            elsif a = l then
                attempts := limit;
                WinCondition := 1;
            else
                i := 0;
                while i <= fin loop
                    if Ind(i) then
                        Ind(i) := PermutationCheck(arr(i),arr(id),l,base,a,b);
                       -- if PermutationCheck(arr(i),arr(id),l,base,a,b) then
                         --   Put_Line(i'Image);
                      --  end if;
                    end if;
                    i := i+1;
                end loop;
                id := 0;
                while Ind(id) = False and id <= fin loop
                   -- if Ind(i) then
                   --     Put_Line(id'Image);
                   -- end if;
                    id := id+1;
                end loop;
                if id > fin then
                    attempts := limit;
                    WinCondition := 2;
                end if;
            end if;
            attempts := attempts+1;
        end loop;
        if WinCondition = 0 then
            Put_Line("Przegrałem");
        elsif WinCondition = 1 then
            Put_Line("Wygrałem!");
        elsif WinCondition = 2 then
            Put_Line("Skłamałeś!");
        end if;
    end Win;

    l,b,n : Integer;
begin
   -- Get(l);
   -- Get(b);
    if Argument_Count < 2 then
        Put_Line("Złe dane!");
    elsif Integer'Value(Argument(1)) < 1 or Integer'Value(Argument(2)) < 1 or Integer'Value(Argument(2)) > 10 then
        Put_Line("Złe dane!");
    end if;
    l := Integer'Value(Argument(1));
    b := Integer'Value(Argument(2));
    n := Fin(l,b);
    Win(l,b,n);
end FluidMasterMind;
