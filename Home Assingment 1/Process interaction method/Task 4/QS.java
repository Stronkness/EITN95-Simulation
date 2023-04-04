import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0, accumulated, noMeasurements, numberOfArrivals, specialDone, normalDone;
	public Proc sendTo;
	Random slump = new Random();
	double[] probs = {0.1, 0.2, 0.5};
	LinkedList<String> customers = new LinkedList<String>();

	public void TreatSignal(Signal x){
		switch (x.signalType){

			case ARRIVAL:{
				// int randomIndex = slump.nextInt(probs.length);
				// double prob = probs[randomIndex];
				// System.out.println(prob);
				double prob = 0.5;
				double rand = slump.nextDouble();

				if (prob > rand){
					customers.addFirst("Special");
				}
				else{
					customers.addLast("Normal");
				}

				numberOfArrivals++;
				numberInQueue++;
				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time + Math.log(slump.nextDouble())*(-(double)(4*60)));
				}
			} break;

			case READY:{
				numberInQueue--;
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time + Math.log(slump.nextDouble())*(-(double)(5*60)));
				}
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + Math.log(slump.nextDouble())*(-(double)(4*60)));
				}
			} break;

			case MEASURE:{
				String person = customers.poll();
				if (person == "Special"){
					specialDone++;
				}else{
					normalDone++;
				}

				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}
}