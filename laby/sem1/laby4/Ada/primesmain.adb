with PrimePackage; use PrimePackage;
with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Command_Line; use Ada.Command_Line;
procedure PrimesMain is
begin
    if Argument_Count < 2 then
         Put_Line("Złe dane!");
    elsif Argument(1) = "pn" then
        Put_Line(PrimeNumbers(Positive(Integer'Value(Argument(2))))'Image);
    elsif Argument(1) = "pr" then
        Put_Line(Prime(Positive(Integer'Value(Argument(2))))'Image);
    elsif Argument(1) = "ip" then
        Put_Line(IsPrime(Positive(Integer'Value(Argument(2))))'Image);
    else
        Put_Line("Złe dane!");
    end if;
end PrimesMain;
