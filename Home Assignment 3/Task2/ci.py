import numpy as np

files = ['result2ms.txt', 'result4ms.txt', 'resultU.txt']
for file in files:
    f = open(file)
    lines = f.readlines()
    f.close()
    measurements = []
    for line in lines:
        measurements.append(float(line.split()[0]))
    mean = np.mean(measurements)
    std = np.std(measurements)
    print(file)
    print('Mean: ' + str(mean))
    print('Std: ' + str(std))
    # Confidence interval
    print('Confidence interval: ' + str(mean - 1.96 * std / np.sqrt(len(measurements))) + ' - ' + str(mean + 1.96 * std / np.sqrt(len(measurements))))
    print('\n')
