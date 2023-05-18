import numpy as np
import matplotlib.pyplot as plt
import random

files = ['studentMeetings2ms.txt',
         'studentMeetings4ms.txt', 'studentMeetingsU.txt']

for txt in files:
    file = open(txt)

    list = file.readlines()

    values = []

    for l in list:
        x, y = l.split(" : ")
        values.append((float(x), float(y)))

    values.sort()

    minutes, student = [], []

    for v in values:
        minutes.append(v[0])
        student.append(v[1])

    minutes = minutes[1:]
    student = student[1:]
    fig = plt.figure(figsize=(10, 5))

    r = random.random()
    b = random.random()
    g = random.random()

    color = (r, g, b)

    # creating the bar plot
    plt.bar(minutes, student, color=color, width=0.4)

    plt.xlabel("Times")
    plt.ylabel("Amount of students")
    plt.title("{}".format(txt))
    plt.show()
