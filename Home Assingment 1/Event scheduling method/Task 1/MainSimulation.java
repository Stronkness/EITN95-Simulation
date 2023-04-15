import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (actState.noMeasurements < 1000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	System.out.println("mean nbr cust Q1:" + " " + 1.0*actState.accumulatedQ1/actState.noMeasurements);
		System.out.println("mean nbr cust Q2:" + " " + 1.0*actState.accumulatedQ2/actState.noMeasurements);
		System.out.println("Rejected:" + " " + 1.0*actState.noRejectionQ1);
		System.out.println("arrivals Q1:" + " " + 1.0*actState.arrivalsQ1);
		System.out.println("accumulated Q2:" + " " + 1.0*actState.accumulatedQ2);
		System.out.println("mean nbr rejected Q1:" + " " +1.0*actState.noRejectionQ1/actState.arrivalsQ1);

    }
}