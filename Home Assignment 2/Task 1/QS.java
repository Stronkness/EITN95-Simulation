import java.util.*;

class QS extends Proc {
	public int in_queue = 0, accumulated, noMeasurements;
	public Proc sendTo;
	public int service_time = 0;
	Random slump = new Random();
	boolean is_used = false;

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

			case ARRIVAL: {
				in_queue++;
				is_used = true;
				SignalList.SendSignal(READY, this, time + service_time);
			}
				break;

			case READY: {
				in_queue--;
				is_used = false;
			}
				break;
		}
	}
}