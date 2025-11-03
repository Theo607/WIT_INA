with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use  Ada.Strings.Unbounded;
with Ada.Strings.Unbounded.Text_IO; use Ada.Strings.Unbounded.Text_IO;

with list; use list;

procedure listTest is
   l : ListT;
   r : Integer;
   i : Integer;
   command : Unbounded_String;
   continue : Boolean := True;
begin
   while continue loop
      Put ("Command: ");
      Get_Line (command);
      if command = "Pop" then
         if not isEmpty (l) then
            r := Pop (l);
            Put_Line ("Result: " & r'Image);
         else
            Put_Line ("Error - stack is empty!");
         end if;
      elsif command = "Push" then
         Put ("Value: ");
         Get (r);
         Skip_Line;
         Push (l, r);
         Put_Line ("Result: OK");
      elsif command = "Append" then
         Put ("Value: ");
         Get (r);
         Skip_Line;
         Append (l, r);
         Put_Line ("Result: OK");
      elsif command = "Print" then
         Put ("Result: ");
         Print (l);
      elsif command = "Length" then
         r := Length (l);
         Put_Line ("Result: " & r'Image);
      elsif command = "Exit" then
         continue := False;
      elsif command = "Get" then
        Put("Index: ");
        Get(i);
        if i > Length(l) or i < 1 then
          Put_Line("Error - bad index!");
        else
          r := Gett(l, i);
          Put_Line("Result: " & r'Image);
        end if;
      elsif command = "Put" then
        Put("Index: ");
        Get(i);
        if i > Length(l) or i < 1 then
          Put_Line("Error - bad index!");
        else
          Put("Value: ");
          Get(r);
          Putt(l, i, r);
          Put_Line("Result: OK");
        end if;
      elsif command = "Insert" then
        Put("Index: ");
        Get(i);
        if i > Length(l) + 1 or i < 1 then
          Put_Line("Error - bad index!");
        else
          Put("Value: ");
          Get(r);
          Insert(l, i, r);
          Put_Line("Result: OK");
        end if;
      elsif command = "Delete" then
        Put("Index: ");
        Get(i);
        if i > Length(l) or i < 1 then
          Put_Line("Error - bad index!");
        else
          Delete(l, i);
          Put_Line("Result: OK");
        end if;
      elsif command = "Clean" then
        Clean(l);
        Put_Line("Result: OK");
      else
         Put_Line ("Unknown command!");
      end if;
   end loop;
   --  clean list
   while not isEmpty (l) loop
      r := Pop (l);
   end loop;
end listTest;

