with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Command_Line; use Ada.Command_Line;

procedure Hetman is
  n : Integer := Integer'Value(Argument(1));
  
  type positionT is array (1 .. n) of Integer;
  type colRowT is array (1 .. n) of Boolean;
  type colDiag1T is array (2 .. 2*n) of Boolean;
  type colDiag2T is array (-n+1 .. n-1) of Boolean;

  position : positionT;
  colRow : colRowT;
  colDiag1 : colDiag1T;
  colDiag2 :colDiag2T;

  temp : Integer := 1;
  ans : Integer := 0;

  procedure set(i : Integer) is
    j : Integer := 1;
    k : Integer;
    
  begin
       while j <= n loop
      if not(colRow(j) or colDiag1(i+j) or colDiag2(i-j)) then
        position(i) := j;
        colRow(j) := True;
        colDiag1(i+j) := True;
        colDiag2(i-j) := True;
        if i < n then
          set(i+1);
        else
          k := 1;
          ans := ans + 1;
          while k <= n loop
            Put(position(k)'Image & " ");
            k := k+1;
          end loop;
          Put_Line("");
        end if;
        position(i) := 0;
        colRow(j) := False;
        colDiag1(i+j) := False;
        colDiag2(i-j) := False;
      end if;
      j := j+1;
    end loop;
  end Set;
begin
  while temp <= n loop
    position(temp) := 0;
    colRow(temp) := False;
    temp := temp+1;
  end loop;

  temp := 2;
  while temp <= 2*n loop
    colDiag1(temp) := False;
    temp := temp+1;
  end loop;

  temp := -n+1;
  while temp <= n-1 loop
    colDiag2(temp) := False;
    temp := temp+1;
  end loop;

  set(1);
  Put_Line("#Solutions: " & ans'Image);
end Hetman;


