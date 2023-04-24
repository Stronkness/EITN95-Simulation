import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	public static final int READY_Q2 = 4;

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQ1 = 0, accumulatedQ1 = 0, noMeasurements = 0, noRejectionQ1 = 0, numberInQ2 = 0, arrivalsQ1 = 0,
			accumulatedQ2 = 0;

	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event has
	// been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
			case ARRIVAL:
				arrival();
				break;
			case READY:
				readyQ1();
				break;
			case MEASURE:
				measure();
				break;
			case READY_Q2:
				readyQ2();
				break;
		}
	}

	// The following methods defines what should be done when an event takes place.
	// This could
	// have been placed in the case in treatEvent, but often it is simpler to write
	// a method if
	// things are getting more complicated than this.

	private void arrival() {
		// Arrivals Q1
		arrivalsQ1++;
		if (numberInQ1 == 0) {
			insertEvent(READY, time + (Math.log(1 - slump.nextDouble())) / (1 / -2.1));
		}
		numberInQ1++;
		if (numberInQ1 == 11) {
			noRejectionQ1++;
			numberInQ1--; // no rejected in Q1
			// System.out.println("hääär");
		}
		// if not first and not rejected: arrives to Q1
		insertEvent(ARRIVAL, time + 1); // Constant interarrival time Q1, tasks try: 1 sec, 2 sec and 5 sec
	}

	private void readyQ1() {
		numberInQ1--;
		if (numberInQ1 > 0) {
			insertEvent(READY, time + (Math.log(1 - slump.nextDouble())) / (1 / -2.1));
		}
		// Arrival Q2
		if (numberInQ2 == 0) {
			insertEvent(READY_Q2, time + 2); // Constant Q2 service time 2 seconds
		}
		numberInQ2++;
	}

	public void readyQ2() {
		numberInQ2--;
		if (numberInQ2 > 0) {
			insertEvent(READY_Q2, time + 2); // Constant Q2 service time 2 seconds
		}
	}

	private void measure() {
		accumulatedQ1 = accumulatedQ1 + numberInQ1;
		accumulatedQ2 = accumulatedQ2 + numberInQ2;
		noMeasurements++;
		insertEvent(MEASURE, time + (Math.log(1 - slump.nextDouble())) / (1 / -5.0)); // Times between measurements exp.
																						// distributed with mean 5
																						// seconds
	}

}