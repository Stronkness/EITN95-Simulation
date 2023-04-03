import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	public static final int ARRIVAL_Q2 = 4, READY_Q2 = 5;

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQ1 = 0, accumulated = 0, noMeasurements = 0, noRejectionQ1 = 0, numberInQ2 = 0, arrivalsQ1 = 0, arrivalsQ2 = 0, numberOfDone = 0;
	public LinkedList<Double> times = new LinkedList<Double>();
	public double time = 0;

	Random slump = new Random(); // This is just a random number generator
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY:
				readyQ1();
				break;
			case MEASURE:
				measure();
				break;
			case ARRIVAL_Q2:
				arrivalQ2();
				break;
			case READY_Q2:
				readyQ2();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		// Arrivals
		arrivalsQ1++;
		if (numberInQ1 == 0){
			insertEvent(READY, time + Math.log(slump.nextDouble())*(-(double)1));
		}
		insertEvent(ARRIVAL, time + Math.log(slump.nextDouble())*(-(double)1.1));
		numberInQ1++;
	}
	
	private void readyQ1(){
		numberInQ1--;
		if (numberInQ1 > 0){
			insertEvent(READY, time + Math.log(slump.nextDouble())*(-(double)1));
		}
		// Arrival Q2?
		insertEvent(ARRIVAL_Q2, time);
	}
	
	public void arrivalQ2(){
		if(numberInQ2 == 0){
			insertEvent(READY_Q2, time + Math.log(slump.nextDouble())*(-(double)1));
		}
		numberInQ2++;
	}

	public void readyQ2(){
		numberInQ2--;
		numberOfDone++;
		if (numberInQ2 > 0){
			time += Math.log(slump.nextDouble())*(-(double)1);
			insertEvent(READY_Q2, time + Math.log(slump.nextDouble())*(-(double)1));
		}
	}

	private void measure(){
		accumulated += numberInQ1 + numberInQ2;
		noMeasurements++;
		insertEvent(MEASURE, time + Math.log(slump.nextDouble())*(-(double)5));
	}

}