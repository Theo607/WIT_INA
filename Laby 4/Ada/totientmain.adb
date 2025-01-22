with TotientPackage; use TotientPackage;
with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Command_Line; use Ada.Command_Line;
procedure TotientMain is
begin
    if Argument_Count > 1 then
        for i in 1..Argument_Count loop
            Put("totient(");
            Put(Argument(i));
            Put(") ="); 
            Put_Line(Totient(Positive(Integer'Value(Argument(i))))'Image);
        end loop;
    else
        Put_Line("Złe dane!");
    end if;
end TotientMain;
