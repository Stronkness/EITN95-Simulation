import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {
		PrintWriter writer = new PrintWriter("output", "UTF-8");
		StringBuilder sb = new StringBuilder();

		Signal actSignal;
		new SignalList();

		Gen Generator = new Gen();
		Generator.lambda = 4;
		int servers = 100;
		int service_time = 10;
		int noMeasurements = 4000;

		for (int i = 0; i < servers; i++) {
			QS Q = new QS();
			Q.service_time = service_time;
			Generator.send_to.add(Q);
		}

		SignalList.SendSignal(READY, Generator, 0);
		SignalList.SendSignal(MEASURE, Generator, 0);

		int prev_no_measurements = -1;
		while (Generator.noMeasurements < noMeasurements) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);

			if (prev_no_measurements < Generator.noMeasurements) {
				prev_no_measurements = Generator.noMeasurements;
				int customers_in_queue = 0;
				for (QS q : Generator.send_to) {
					customers_in_queue += q.in_queue;
				}
				sb.append(customers_in_queue + "\n");
			}
		}
		writer.print(sb.toString());
		writer.close();

		double mean = 1.0 * Generator.noArrivals / Generator.noMeasurements;
		System.out.println("Mean: " + mean);

		double standardDeviation = 0.0;
		for (int customers : Generator.customers) {
			standardDeviation += Math.pow(customers - mean, 2);
		}

		standardDeviation = Math.sqrt(standardDeviation / Generator.customers.size());
		System.out.println("Standard deviation: " + standardDeviation);

		// * 1.96 as its a 95% confidence interval
		System.out.println("Confidence Interval length:" + "\n\t"
				+ (1.96 * standardDeviation / Math.sqrt(Generator.customers.size())));
	}
}