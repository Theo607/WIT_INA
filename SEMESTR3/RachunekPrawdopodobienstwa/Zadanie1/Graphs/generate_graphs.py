import time
import numpy as np
import matplotlib.pyplot as plt


def random_point(max_x, min_x, max_y, min_y):
    x = np.random.rand() * (max_x - min_x) + min_x
    y = np.random.rand() * (max_y - min_y) + min_y
    return x, y


def generate_points(max_x, min_x, max_y, min_y, repetitions):
    points = []
    for _ in range(repetitions):
        points.append(random_point(max_x, min_x, max_y, min_y))
    return points


def simulate(number_of_points, max_x, min_x, max_y, function):
    points = generate_points(max_x, min_x, max_y, 0.0, number_of_points)
    count = 0
    for p in points:
        if p[1] < function(p[0]):
            count += 1
    return count / number_of_points * (max_x - min_x) * max_y


class function_data:
    def __init__(self, function, sup, min_x, max_x):
        self.function = function
        self.sup = sup
        self.min_x = min_x
        self.max_x = max_x


class point:
    def __init__(self, x, y):
        self.x = x
        self.y = y


def graph(func_data, k):
    blue_points = []
    red_points = []
    last_k_points = []
    for n in range(50, 5000, 50):
        last_k_points = []
        for i in range(k):
            simulation = simulate(
                n, func_data.max_x, func_data.min_x, func_data.sup, func_data.function
            )
            last_k_points.append(simulation)
            blue_points.append(point(n, simulation))
        red_points.append(point(n, np.mean(last_k_points)))

    return blue_points, red_points


def plot(points, red_points, expected, title):
    x = [p.x for p in points]
    y = [p.y for p in points]
    plt.scatter(x, y, s=10, color="blue")
    plt.scatter([p.x for p in red_points], [p.y for p in red_points], s=10, color="red")
    plt.plot(x, [expected] * len(x), color="green")
    plt.xlabel("n (liczba punktów)")
    plt.ylabel("aproksymacja całki")
    plt.savefig(title, format="svg")
    plt.close()


def main():
    np.random.seed(int(time.time()))
    # k = 5
    # 1. f(x) = cbrt(x) na [0,8]
    func1 = function_data(lambda x: np.cbrt(x), sup=np.cbrt(8), min_x=0.0, max_x=8.0)
    blue1, red1 = graph(func1, 5)
    expected1 = (3 / 4) * 8 ** (4 / 3)
    plot(blue1, red1, expected1, "cbrt_x_integral.svg")

    # 2. f(x) = sin(x) na [0,π]
    func2 = function_data(lambda x: np.sin(x), sup=1.0, min_x=0.0, max_x=np.pi)
    blue2, red2 = graph(func2, 5)
    expected2 = 2.0
    plot(blue2, red2, expected2, "sin_integral.svg")

    # 3. f(x) = 4x(1-x)^3 na [0,1]
    func3 = function_data(
        lambda x: 4 * x * (1 - x) ** 3, sup=27 / 64, min_x=0.0, max_x=1.0
    )
    blue3, red3 = graph(func3, 5)
    expected3 = 0.2
    plot(blue3, red3, expected3, "4x1-x3_integral.svg")

    # 4. Aproksymacja pi: f(x) = sqrt(1-x^2) na [0,1]
    func4 = function_data(lambda x: np.sqrt(1 - x**2), sup=1.0, min_x=0.0, max_x=1.0)
    blue4, red4 = graph(func4, 5)
    expected4 = np.pi / 4
    plot(blue4, red4, expected4, "pi_approximation.svg")
    # k = 50
    # 1. f(x) = cbrt(x) na [0,8]
    func1 = function_data(lambda x: np.cbrt(x), sup=np.cbrt(8), min_x=0.0, max_x=8.0)
    blue1, red1 = graph(func1, 50)
    expected1 = (3 / 4) * 8 ** (4 / 3)
    plot(blue1, red1, expected1, "cbrt_x_integral50.svg")

    # 2. f(x) = sin(x) na [0,π]
    func2 = function_data(lambda x: np.sin(x), sup=1.0, min_x=0.0, max_x=np.pi)
    blue2, red2 = graph(func2, 50)
    expected2 = 2.0
    plot(blue2, red2, expected2, "sin_integral50.svg")

    # 3. f(x) = 4x(1-x)^3 na [0,1]
    func3 = function_data(
        lambda x: 4 * x * (1 - x) ** 3, sup=27 / 64, min_x=0.0, max_x=1.0
    )
    blue3, red3 = graph(func3, 50)
    expected3 = 0.2
    plot(blue3, red3, expected3, "4x1-x3_integral50.svg")

    # 4. Aproksymacja pi: f(x) = sqrt(1-x^2) na [0,1]
    func4 = function_data(lambda x: np.sqrt(1 - x**2), sup=1.0, min_x=0.0, max_x=1.0)
    blue4, red4 = graph(func4, 50)
    expected4 = np.pi / 4
    plot(blue4, red4, expected4, "pi_approximation50.svg")


main()
