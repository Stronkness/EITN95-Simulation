import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {

		Event actEvent;
		State actState = new State(); // The state that shoud be used
		double total = 0;

		// 1000 runs of the system
		for (int i = 0; i < 1000; i++) {
			// Some events must be put in the event list at the beginning
			insertEvent(ARRIVAL, 0);

			// The main simulation loop
			// This is the main loop that fetches the next event, updates the state and inserts new events in the event list.
			// Also fills the aliveQS list with null values
			actState.fillEvent();
			while (actState.isAlive()) {
				actEvent = eventList.fetchEvent();
				globalTime = actEvent.eventTime;
				actState.treatEvent(actEvent);
			}

			// Add the time to the total from GlobalSimulation
			total += GlobalSimulation.globalTime;
			// Reset the global time
			GlobalSimulation.globalTime = 0;
			// Reset the event list
			GlobalSimulation.eventList = new EventListClass();
			// Reset the actEvent
			actEvent = null;
			// Reset the actState
			actState = new State();
		}

		// Some events must be put in the event list at the beginning
		System.out.println(total / 1000);

	}
}