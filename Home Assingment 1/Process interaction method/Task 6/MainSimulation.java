import java.util.*;
import java.io.*;

//Denna klass  rver Global s  att man kan anv nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{
	private static Random slump = new Random();
	public static QS random(QS[] q){
		int randomIndex = slump.nextInt(q.length);
		QS chosen_queue = q[randomIndex];
		return chosen_queue;
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
		
		LinkedList<QS> queue = new LinkedList<QS>();
		queue.add(Q1);
		queue.add(Q2);
		queue.add(Q3);
		queue.add(Q4);
		queue.add(Q5);
		//QS[] q = {Q1,Q2,Q3,Q4,Q5};
		QS[] receivers = {Q1,Q3};
		
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

		double uniform_distribution = 0.25; // 1/(5-1)

    	// Detta  r simuleringsloopen:
    	// This is the main loop
		int runs = 0;
		double total_time = 0;
    	while (runs < 1000){
			
			Generator.sendTo = random(receivers);

			LinkedList<QS> temp = (LinkedList)queue.clone();
			for (QS qs : queue) {
				double x = slump.nextDouble();
				if (x <= uniform_distribution){
					System.out.println(qs.time);
					total_time += qs.time;
					temp.remove(qs);
				}
			}
			queue = temp;

			// Cases
			int idx_of_Q1 = queue.indexOf(Q1);
			if (idx_of_Q1 == -1){
				boolean check = queue.remove(Q2);
				if (check){
					total_time += Q2.time;
				}

				check = queue.remove(Q5);
				if (check){
					total_time += Q5.time;
				}
			}

			int idx_of_Q3 = queue.indexOf(Q3);
			if (idx_of_Q3 == -1){
				boolean check = queue.remove(Q4);
				if (check){
					total_time += Q4.time;
				}
			}

			if(queue.isEmpty()){
				break;
			}

    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
			runs++;
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:
		System.out.println(total_time);
    	System.out.println("Mean time before breaking down the system: " + 1.0*total_time/5);

    }
}