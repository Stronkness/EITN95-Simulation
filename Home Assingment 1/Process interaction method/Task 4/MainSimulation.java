import java.util.*;
import java.io.*;

//Denna klass  rver Global s  att man kan anv nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {
		int[] iterations = { 20, 50, 100, 1000 };
		// Signallistan startas och actSignal deklareras. actSignal r den senast
		// utplockade signalen i huvudloopen nedan.
		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.
		for (int iteration : iterations) {
			Signal actSignal;
			new SignalList();

			// H r nedan skapas de processinstanser som beh vs och parametrar i dem ges v
			// rden.
			// Here process instances are created (two queues and one generator) and their
			// parameters are given values.

			QS Q1 = new QS();
			Q1.sendTo = null;

			Q1.lambda = 1.0 / 4;

			Gen Generator = new Gen();
			Generator.lambda = 1.0 / 5; // Generator ska generera nio kunder per sekund //Generator shall generate 9
										// customers per second
			Generator.sendTo = Q1; // De genererade kunderna ska skickas till k systemet QS // The generated
									// customers shall be sent to Q1

			// H r nedan skickas de f rsta signalerna f r att simuleringen ska komma ig ng.
			// To start the simulation the first signals are put in the signal list

			SignalList.SendSignal(READY, Generator, time);
			SignalList.SendSignal(MEASURE, Q1, time);

			// Detta r simuleringsloopen:
			// This is the main loop

			while (Q1.numberOfArrivals < iteration) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
			}

			// Slutligen skrivs resultatet av simuleringen ut nedan:
			// Finally the result of the simulation is printed below:
			System.out.println(iteration);
			System.out.println("average queuing time for Special: " + Q1.specialQueueTime / Q1.specialDone);
			System.out.println("average queuing time for Normal: " + Q1.normalQueueTime / Q1.normalDone + "\n");
		}
	}
}