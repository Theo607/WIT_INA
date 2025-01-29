with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
with Ada.Strings.Unbounded.Text_IO; use Ada.Strings.Unbounded.Text_IO;
with Ada.Command_Line; use Ada.Command_Line;
procedure Change is
    type Coins is array (Integer range<>) of Integer;
    
    procedure CalculateRest(n : Integer; m : Integer; InFile : File_Type) is

    function Max(c : Coins; n : Integer) return Integer is
        ans : Integer := -1;
        i : Integer := 1;
    begin
        while i<=n loop
            if c(i) > ans then
                ans := c(i);
            end if;
            i := i+1;
        end loop;
        return ans;
    end Max;

    procedure Rest(c : Coins; s : Coins; n : Integer; m : Integer) is

        procedure BackTrack(c : Coins; CoinIndex : Coins; n : Integer; x : Integer; ammount : Integer) is
            k,i : Integer;
            HowMany : Coins (0..n);
        begin
            i := 0;
            while i <= n loop
                HowMany(i) := 0;
                i := i+1;
            end loop;
            Put(x'Image);
            Put(" ==>");
            Put_Line(ammount'Image);
            k := x;
            while k > 0 loop
                HowMany(CoinIndex(k)) := HowMany(CoinIndex(k))+1;
                k := k-c(CoinIndex(k));
            end loop;
            i := 1;
            while i <= n loop
                if HowMany(i) /= 0 then
                    Put("    ");
                    Put(HowMany(i)'Image);
                    Put(" x");
                    Put_Line(c(i)'Image);
                end if;
                i := i+1;
            end loop;
        end BackTrack;

        i, j, reach : Integer;
        Ammount : Coins (0..Max(s,m));
        CoinIndex : Coins (0..Max(s,m));
    begin
        i := 0;
        reach := Max(s,m);
        while i <= reach loop
            CoinIndex(i) := -1;
            Ammount(i) := -1;
            i := i+1;
        end loop;
        Ammount(0) := 0;
        i := 1;
       -- while i<=n loop
         --   Put(c(i)'Image);
           -- i := i+1;
       -- end loop;
       -- Put_Line("");
       -- i := 1;
        while i <= n loop
            j := 0;
            while j <= reach loop
                 if Ammount(j) /= -1 then
                    -- Put(j'Image);
                    -- Put(i'Image);
                    -- Put_Line(c(i)'Image);
                     if j+c(i) <= reach then
                         if Ammount(j+c(i))>=Ammount(j)+1 or Ammount(j+c(i)) = -1 then
                             Ammount(j+c(i)) := Ammount(j)+1;
                             CoinIndex(j+c(i)) := i;
                         end if;
                     end if;
                 end if;
                 j := j+1;
            end loop;
            i := i+1;
        end loop;

        i := 1;
        while i <= m loop
            if Ammount(s(i)) /= -1 then
                BackTrack(c,CoinIndex,n,s(i),Ammount(s(i)));
            else
                Put(s(i)'Image);
                Put(" ==> ");
                Put_Line("No solution!");
            end if;
            i := i+1;
        end loop;
      --  i := 0;
      --  while i <= reach loop
       --     Put(Ammount(i)'Image);
        --    i := i+1;
       -- end loop;
    end Rest;

    i : Integer := 1;
    coin : Coins (1..n);
    spares : Coins (1..m);
    s : Unbounded_String;
    begin
         while i <= n loop
             Get_Line(InFile,s);
             --   Get(InFile, coin(i));
              coin(i) := Integer'Value(To_String(s));
              i := i+1;
         end loop;
         i := 2;
         while i<=m+1 loop
             spares(i-1) := Integer'Value(Argument(i));
             i := i+1;
         end loop;
        -- i := 1;
        -- while i<=m loop
          --   Put(spares(i)'Image);
            -- i := i+1;
        -- end loop;
        -- Put_Line("");
         Rest(coin,spares,n,m);
    end CalculateRest;
        
    n,m : Integer;
    InFile : File_Type;
    s : Unbounded_String;
begin
   -- Get(n);
   -- Get(m);
    Open(InFile, In_File, Argument(1));
    Get_Line(InFile, s);
   -- Put_Line("test");
      n := Integer'Value(To_String(s));
    --  Get(InFile, n);
 --   n := 3;
 --   Put_Line(n'Image);
    m := Argument_Count-1;
    CalculateRest(n,m,InFile);
end Change;
