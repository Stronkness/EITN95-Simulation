import java.util.*;
import java.io.*;

class State{
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;
	

	Random slump = new Random();
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);
	
	public void TreatEvent(Event x){
		switch (x.eventType){
			case G.ARRIVAL:{
				numberInQueue++;
				EventList.InsertEvent(G.ARRIVAL, G.time - (1/0.9)*Math.log(slump.nextDouble()));
				if (numberInQueue == 1){
					EventList.InsertEvent(G.READY, G.time + 1);
				}
			} break;
			case G.READY:{
				numberInQueue--;
				if (numberInQueue > 0){
					EventList.InsertEvent(G.READY, G.time + 1);
				}
			} break;
			case G.MEASURE:{
				accumulated = accumulated + numberInQueue;
				noMeasurements++;
				EventList.InsertEvent(G.MEASURE, G.time + 5);
				W.println(String.valueOf(numberInQueue));
			} break;
		}
	}
}