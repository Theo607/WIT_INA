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

  procedure set(i : Integer) is
    j : Integer := 1;
    k : Integer;
  begin
    while j <= n loop
      if not(colRow(j) or colDiag1(i+j) or colDiag2(i-j)) then
        position(i) := j;
        colRow(j) := True;
        colDiag1(j) := True;
        colDiag2(j) := True;
        if i < n then
          set(i+1);
        else
          k := 1;
          while k <= n loop
            Put(position(k)'Image & " ");
            k := k+1;
          end loop;
          Put("\n");
        end if;
        position(i) := 0;
        colRow(j) := False;
        colDiag1(i+j) := False;
        colDiag2(i-j) := False;
      end if;
    end loop;
  end Set;
begin
  set(1);
end Hetman;


