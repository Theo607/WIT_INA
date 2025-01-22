package Perm is
  type Permutation is array (Integer range<>) of Integer;

  function NextPermutation(tab : in out Permutation; n : Integer) return Boolean;
--  function Factorial(n : Integer) return Integer;
end Perm;
