import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated_A = 0, accumulated_B = 0, noMeasurements = 0;
	public int total = 0;
	public int customersBuffer_A = 0, customersBuffer_B = 0;
	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event has
	// been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
			case ARRIVAL_A:
				arrival_A();
				break;
			case READY:
				ready_A();
				break;
			case MEASURE:
				measure();
				break;
			case ARRIVAL_B:
				arrival_B();
				break;
			// case READY_B:
			// ready_B();
			// break;

		}
	}

	// The following methods defines what should be done when an event takes place.
	// This could
	// have been placed in the case in treatEvent, but often it is simpler to write
	// a method if
	// things are getting more complicated than this.

	/* Add to customer buffer and accummulated */
	private void arrival_A() {
		customersBuffer_A++;
		accumulated_A++;

		insertEvent(ARRIVAL_A, time + Math.abs((double) 1 / 150 * Math.log(1 - slump.nextDouble()))); // Kanske
																										// ARRIVAL_B??
	}

	/*
	 * Add to customer buffer and accummulated, same as A but no Event as its the
	 * final destination
	 */
	private void arrival_B() {
		customersBuffer_B++;
		accumulated_B++;
	}

	/*
	 * Determines the priority of the system. If the buffer for B is bigger than 0
	 * that means that we must choose B as the next customer to be served as it has
	 * higher priority.
	 * If the buffer for B is empty we check the buffer for A.
	 */
	private void ready_A() {
		double chosen = 0.002;

		// This is when A has higher priority than B in the Task
		if (customersBuffer_A > 0) {
			customersBuffer_A--;
			insertEvent(ARRIVAL_B, time + 1);
		} else if (customersBuffer_B > 0) {
			customersBuffer_B--;
			chosen = 0.004;
		}

		insertEvent(READY, time + chosen);
	}

	/* Add to total and noMeasurements */
	private void measure() {
		total = total + numberInQueue();
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1);
	}

	/* Get number of customers in queue */
	private int numberInQueue() {
		return customersBuffer_A + customersBuffer_B;
	}

}