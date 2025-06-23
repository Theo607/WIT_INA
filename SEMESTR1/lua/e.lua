local function eApprox(terms)
  local denomin = 1
  local eApp = 1
  for i = 1, terms do
    denomin = denomin*i
    eApp = eApp + 1/denomin
  end
  return eApp
end

local function main()
  print("This program calculates Euler's number to nth digit.")
  print("Put digit accuracy: ")
  local acc = tonumber(io.read())
  local eNum = eApprox(10)
  io.write(string.format("%." .. acc-1 .. "f", eNum))
end

main()
