import java.util.*;
import java.io.*;

//Denna klass  rver Global s  att man kan anv nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{
	private static Random slump = new Random();
	private static int queue_number = 0;
	
	public static QS random(QS[] q){
		int randomIndex = slump.nextInt(q.length);
		QS chosen_queue = q[randomIndex];
		return chosen_queue;
	}
	
	public static QS round_robin(QS[] q){
		if(queue_number > 4){
			queue_number = 0;
		}
		QS chosen = q[queue_number];
		queue_number++;
		return chosen;
	}
	
	public static QS option_3(QS[] q){
		int lowest = Integer.MAX_VALUE;
		QS temp = null;
		LinkedList<QS> temp_list = new LinkedList<QS>();
		for (QS qs : q){
			if(qs.numberInQueue < lowest){
				lowest = qs.numberInQueue;
				temp = qs;
				temp_list.clear();
			}

			if(qs.numberInQueue == lowest){
				temp_list.add(qs);
			}
		}

		if(temp_list.size() > 1){
			int randomIndex = slump.nextInt(temp_list.size());
			QS chosen_queue = temp_list.get(randomIndex);
			return chosen_queue;
		}

		return temp;
	}
	
    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal  r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	//H r nedan skapas de processinstanser som beh vs och parametrar i dem ges v rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
		QS Q2 = new QS();
		QS Q3 = new QS();
		QS Q4 = new QS();
		QS Q5 = new QS();
    	Q1.sendTo = null;
		Q2.sendTo = null;
		Q3.sendTo = null;
		Q4.sendTo = null;
		Q5.sendTo = null;
		
		QS[] q = {Q1,Q2,Q3,Q4,Q5};
		
    	Gen Generator = new Gen();
    	Generator.lambda = 9; //Generator ska generera nio kunder per sekund  //Generator shall generate 9 customers per second
    	Generator.sendTo = null; //De genererade kunderna ska skickas till k systemet QS  // The generated customers shall be sent to Q1

    	//H r nedan skickas de f rsta signalerna f r att simuleringen ska komma ig ng.
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);
		SignalList.SendSignal(MEASURE, Q2, time);
		SignalList.SendSignal(MEASURE, Q3, time);
		SignalList.SendSignal(MEASURE, Q4, time);
		SignalList.SendSignal(MEASURE, Q5, time);

    	// Detta  r simuleringsloopen:
    	// This is the main loop

    	while (time < 100000){
			Generator.sendTo = random(q);

    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

		int counter = 1;
		for (QS queue : q){
			System.out.println("Mean number of jobs in Q" + counter + ": "  + (1.0*queue.accumulated/queue.noMeasurements));
			counter++;
		}
    }
}