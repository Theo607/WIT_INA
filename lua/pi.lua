local function log2(n)
  local x = 1
  local ans = 1
  while x < n do
    x = x * 2
    ans = ans + 1
  end
  return ans
end

local function sqrt(n, digit)
  local xCurrent = n/2
  local xNext = 0.5*(xCurrent+n/xCurrent)
  local tmpVal
  digit = log2(digit)
  while digit > 0 do
    tmpVal = xNext
    xNext = 0.5*(xCurrent+n/xCurrent)
    xCurrent = tmpVal
    digit = digit - 1
  end
  return xNext
end

local function piToNThDigit(digit)
  digit = log2(digit)

  local ACurrent = 1
  local BCurrent = 1/sqrt(2, 100)
  local TCurrent = 1/4
  local PCurrent = 1

  local ANext
  local BNext
  local TNext
  local PNext

  while digit > 0 do
    ANext = (ACurrent+BCurrent)/2
    BNext = sqrt(ACurrent*BCurrent, 100)
    TNext = TCurrent -PCurrent*(ACurrent-ANext)*(ACurrent-ANext)
    PNext = 2*PCurrent

    ACurrent = ANext
    BCurrent = BNext
    TCurrent = TNext
    PCurrent = PNext

    digit = digit - 1
  end

  return (ANext+BNext)*(ANext+BNext)/(4*TNext)
end

local function main()
  print("This lua program calculates pi to the desired digit. Up to the 52nd digit.")
  io.write("Put digit accuracy: ")
  local eps = tonumber(io.read())
  local num = piToNThDigit(eps)
  io.write(string.format("%." .. eps-1 .. "f", num))
end

main()

