import java.util.*;
import java.io.*;

class State extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	private LinkedList<Boolean> aliveQS = new LinkedList<Boolean>();

	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each globalTime a new event has
	// been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {
		switch (x.eventType) {
			case ARRIVAL:
				arrival();
				break;
			case READY_Q1:
				ready_Q1();
				break;
			case READY_Q2:
				ready_Q2();
				break;
			case READY_Q3:
				ready_Q3();
				break;
			case READY_Q4:
				ready_Q4();
				break;
			case READY_Q5:
				ready_Q5();
				break;
		}
	}

	// The following methods defines what should be done when an event takes place.
	// This could
	// have been placed in the case in treatEvent, but often it is simpler to write
	// a method if
	// things are getting more complicated than this.

	public void fillEvent() {
		for (int i = 0; i < 5; i++)
			aliveQS.add(i, null);
	}

	// When READY_Qx is begin called that queue dies by jumping to the ready method as its lifespan is over
	private void arrival() {
		insertEvent(READY_Q1, globalTime + 4.0*slump.nextDouble() + 1); // Plus 1 as it is in the interval 1-5 and slump is 0-1
		insertEvent(READY_Q2, globalTime + 4.0*slump.nextDouble() + 1);
		insertEvent(READY_Q3, globalTime + 4.0*slump.nextDouble() + 1);
		insertEvent(READY_Q4, globalTime + 4.0*slump.nextDouble() + 1);
		insertEvent(READY_Q5, globalTime + 4.0*slump.nextDouble() + 1);
	}

	// Kill the queue and the connections to it
	private void ready_Q1() {
		aliveQS.set(0, true);
		insertEvent(READY_Q2, globalTime);
		insertEvent(READY_Q5, globalTime);
	}

	private void ready_Q2() {
		aliveQS.set(1, true);
	}

	// Kill the queue and the connections to it
	private void ready_Q3() {
		aliveQS.set(2, true);
		insertEvent(READY_Q4, globalTime);
	}

	private void ready_Q4() {
		aliveQS.set(3, true);
	}

	private void ready_Q5() {
		aliveQS.set(4, true);
	}

	public boolean isAlive() {
		for(int i = 0; i < 5; i++) {
			if(aliveQS.get(i) == null)
				return true;
		}
		return false;
	}

}