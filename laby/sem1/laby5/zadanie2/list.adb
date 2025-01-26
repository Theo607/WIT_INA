with Ada.Text_IO; use Ada.Text_IO;

package body list is
   function isEmpty (l : ListT) return Boolean is
   begin
      return l.first = null;
   end isEmpty;

   function Pop (l : in out ListT) return Integer is
      n : NodePtr := l.first;
      e : Integer := n.elem;
   begin
      l.first := n.next;
      if l.first = null then -- last element
         l.last := null;
      end if;
      Free (n);
      return e;
   end Pop;

   procedure Push (l : in out ListT; e : Integer) is
      n : NodePtr := new Node;
   begin
      n.elem := e;
      n.next := l.first;
      l.first := n;
      if l.last = null then -- first element
         l.last := n;
      end if;
   end Push;

   procedure Append (l : in out ListT; e : Integer) is
      n : NodePtr := new Node;
   begin
      n.elem := e;
      if l.first = null then -- first element
         l.first := n;
      else
         l.last.next := n;
      end if;
      l.last := n;
   end Append;

   procedure Print (l : ListT) is
      n : NodePtr := l.first;
   begin
      while n /= null loop
         Put (n.elem'Image);
         n := n.next;
      end loop;
      Put_Line (" (" & Length (l)'Image & " )");
   end Print;

   function Length (l : ListT) return Integer is
      i : Integer := 0;
      n : NodePtr := l.first;
   begin
      while n /= null loop
         i := i + 1;
         n := n.next;
      end loop;
      return i;
   end Length;

   function Gett(l : ListT; i : Integer) return Integer is 
     n : NodePtr := l.first;
     j : Integer := i;
   begin
     while j > 1 loop
       n := n.next;
       j := j - 1;
     end loop;

     return n.elem;
   end Gett;

   procedure Putt(l : in out ListT; i : Integer; e : Integer) is 
     n : NodePtr := l.first;
     index : Integer := 1;
   begin
     while index /= i loop
       n := n.next;
       index := index + 1;
     end loop;

     n.elem := e;
   end Putt;

   procedure Insert(l : in out ListT; i : Integer; e : Integer) is 
     newNode : NodePtr := new Node;
     n : NodePtr := l.first;
     j : Integer := i;
   begin
     if i = 1 then
       Push(l, e);
     elsif i = Length(l)+1 then
       Append(l, e);
     else
       newNode.elem := e;
       n := l.first;
       while j - 1 > 1 loop
         n := n.next;
         j := j - 1;
       end loop;
       newNode.next := n.next;
       n.next := newNode;
     end if;
   end Insert;

   procedure Delete(l : in out ListT; i : Integer) is 
     n : NodePtr := l.first;
     index : Integer := 1;
     frN : NodePtr;
     tmp : Integer;
   begin
     if i = 1 then
       tmp := Pop(l);
     else
       while index /= i - 1 loop
         n := n.next;
         index := index + 1;
       end loop;
       frN := n.next;
       n.next := n.next.next;
       Free(frN);
       if i = Length(l)-1 then
         l.last := n;
       end if;
     end if;
    end Delete;

   procedure Clean (l : in out ListT) is
     tmp : Integer;
   begin
     while isEmpty(l) = False loop
       tmp := Pop(l);
     end loop;
   end Clean;
end list;
