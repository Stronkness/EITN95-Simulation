import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements, numberOfArrivals, specialDone, normalDone;
	public double normalQueueTime = 0, specialQueueTime = 0;
	public Proc sendTo;
	public double lambda;
	Random slump = new Random();
	double[] probs = { 0.1, 0.2, 0.5 };
	LinkedList<Double> normalCustomers = new LinkedList<Double>();
	LinkedList<Double> specialCustomers = new LinkedList<Double>();

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

			case ARRIVAL: {
				double rand = slump.nextDouble();

				if (probs[2] > rand) {
					specialCustomers.addLast(time);
				} else {
					normalCustomers.addLast(time);
				}

				numberOfArrivals++;
				numberInQueue++;
				if (numberInQueue == 1) {
					SignalList.SendSignal(READY, this, time + Math.log(1 - slump.nextDouble()) / (-lambda));
				}
			}
				break;

			case READY: {
				if (specialCustomers.size() > 0) {
					specialQueueTime += time - specialCustomers.pop();
					specialDone++;
				} else {
					normalQueueTime += time - normalCustomers.pop();
					normalDone++;
				}

				numberInQueue--;

				if (numberInQueue > 0) {
					SignalList.SendSignal(READY, this, time + Math.log(1 - slump.nextDouble()) / (-lambda));
				}
			}
				break;

			case MEASURE: {
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2 * slump.nextDouble());
			}
				break;
		}
	}
}