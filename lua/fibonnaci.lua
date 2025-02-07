local function Fibonnaci(term)
  local FibArr = {}
  FibArr[1] = 1
  FibArr[2] = 1
  print(FibArr[1])
  print(FibArr[2])
  local tmp
  for i = 3, term do
    tmp = FibArr[2]
    FibArr[2] = FibArr[2] + FibArr[1]
    FibArr[1] = tmp
    print(FibArr[2])
  end
end

local function main()
  print("This program prints n first terms of Fibonnaci sequence.")
  print("Put number of terms: ")
  local numTerms = tonumber(io.read())
  Fibonnaci(numTerms)
end

main()
