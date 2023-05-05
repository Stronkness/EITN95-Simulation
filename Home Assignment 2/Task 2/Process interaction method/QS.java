import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0, noRejected = 0, numberOfDone = 0, accumulated, noMeasurements;
	public Proc sendTo;
	public LinkedList<Double> times = new LinkedList<Double>();
	public double accumulatedTime = 0;
	public double accumulatedFinished = 0;
	Random slump = new Random();

	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL:{
				if(time <480){    //Stoping new customers from arriving after 17:00
					numberInQueue++;
					double arrivalTime = time;
					times.add(arrivalTime);
					if (numberInQueue == 1){
						SignalList.SendSignal(READY,this, time + 10 + 10*slump.nextDouble());    //uniformly distributed between 10-20 min
					}
				}	
			} break;

			case READY:{  //FILLING
				numberInQueue--;
				numberOfDone++;
				double doneTime = time;
				double fillTime = (doneTime - times.poll());
				accumulatedTime += fillTime;
				if(time > 480 && numberInQueue == 0){					// if 17:00 or more && no queue-collect time = workday done
					double workDone = time;
					accumulatedFinished += workDone;
				}
				if (sendTo != null){
					SignalList.SendSignal(ARRIVAL, sendTo, time);
				}
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + 10 + 10*slump.nextDouble());   //uniformly distributed between 10-20 min
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