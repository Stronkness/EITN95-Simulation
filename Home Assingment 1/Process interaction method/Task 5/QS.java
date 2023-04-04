import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0, accumulated, noMeasurements, numberOfArrivals;
	public Proc sendTo;
	Random slump = new Random();
	LinkedList<String> customers = new LinkedList<String>();
	public static final int ARRIVAL1=0;

	public void TreatSignal(Signal x){
		switch (x.signalType){
			//Dispatch
			case ARRIVAL:{
				numberOfArrivals++;
				numberInQueue++;
				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time + Math.log(slump.nextDouble())*(-(double)(0.5)));
				}
			} break;
			case ARRIVAL1:{

				SignalList.SendSignal(READY,this, time + Math.log(slump.nextDouble())*(-(double)(0.5)));
				break;
			}

			case READY:{
				numberInQueue--;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time + 1/2); //  Uniformly distibuted arrivaltime
				}
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + Math.log(slump.nextDouble())*(-(double)(0.5)));
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}
}