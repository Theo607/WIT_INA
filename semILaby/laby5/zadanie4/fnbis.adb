with Ada.Text_IO; use Ada.Text_IO;
with Ada.Float_Text_IO; use Ada.Float_Text_IO;
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

procedure fnBis is
  type FuncType is access function (x : Float) return Float;
  function cos2 (x : Float) return Float is
  begin
    return cos(x/2.0);
  end cos2;
  a : Float;
  b : Float;
  eps : Float;
  zero : Float;
  function FindZero(f : FuncType; a : in out Float; b : in out Float; eps : Float) return Float is
  s : Float := (a+b)/2.0;
  begin
    while b-a > eps loop
      if f(s)*f(a) > 0.0 then
        a := s;
      else
        b := s;
      end if;
      s := (a+b)/2.0;
    end loop;
    return s;
  end FindZero;
begin
  Put("a: ");
  Get(a);
  Put("b: ");
  Get(b);
  Put("eps: ");
  Get(eps);
  zero := FindZero(cos2'Access, a, b, eps);
  Put(zero'Image);
end fnBis;


