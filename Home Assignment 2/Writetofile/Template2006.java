import java.util.*;
import java.io.*;


public class Template2006 {
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State();
    	new EventList();
        EventList.InsertEvent(G.ARRIVAL, 1);
        EventList.InsertEvent(G.MEASURE, 5);
    	while (G.time < 100000){
    		actEvent = EventList.FetchEvent();
    		G.time = actEvent.eventTime;
    		actState.TreatEvent(actEvent);
    	}
    	System.out.println(1.0*actState.accumulated/actState.noMeasurements);
    	System.out.println(actState.accumulated);
    	System.out.println(actState.noMeasurements);
    	actState.W.close();
    }
}