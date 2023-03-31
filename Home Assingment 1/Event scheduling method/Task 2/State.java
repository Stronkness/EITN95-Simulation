import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, numberofA = 0, numberofB = 0;
	public static final int ARRIVAL_A = 1, READY_A = 2, MEASURE = 3; // The events, add or remove if needed!
	public static final int ARRIVAL_B = 4, READY_B = 5; // The events, add or remove if needed!
	Random slump = new Random(); // This is just a random number generator
	LinkedList<String> customers = new LinkedList<String>(); // Keep track of A and B
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL_A:
				arrival_A();
				break;
			case READY_A:
				ready_A();
				break;
			case MEASURE:
				measure();
				break;
			case ARRIVAL_B:
				arrival_B();
				break;
			// case READY_B:
			// 	ready_B();
			// 	break;
				
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival_A(){
		if (numberInQueue() == 0){
			insertEvent(READY_A, time + 0.002);
		}
			
		customers.addLast("B");
		insertEvent(ARRIVAL_A, time + Math.log(slump.nextDouble())*(-(double)1/150)); // Kanske ARRIVAL_B??
	}
	
	private void arrival_B(){
		if (numberInQueue() == 0) insertEvent(READY_A, time + 0.004);
		customers.addFirst("A");
	}
	
	/* The idea here is to first check if the head in the customers list
	 * is "A", it thats the case we send it to ARRIVAL_B to get ready to be
	 * converted to a "B" and go through the queue one more time.
	 * Then we must check if the customers list contains something, if it does
	 * we check the first element again but does peek() this time as we only
	 * want to send a READY statement with the correct service time of the 
	 * first element in the queue
	 */
	private void ready_A(){
		String temp = customers.poll();
		if(temp != null){
			if(temp.equals("B")) insertEvent(READY_A, time + 0.004); // 1 is d, delay
			else insertEvent(ARRIVAL_B, time + 1);
		}

		if(!customers.isEmpty()){
			if(customers.peek().equals("B")) insertEvent(READY_A, time + 0.004);
			else insertEvent(READY_A, time + 0.002);
		}
	}
	
	private void ready_B(){
		numberofB--;
		// insertEvent?
		//TODO: Check priority
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue();
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1);
	}

	private int numberInQueue(){
		return customers.size();
	}


}