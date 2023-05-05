import java.util.*;

class Gen extends Proc {
	Random slump = new Random();
	public ArrayList<QS> send_to = new ArrayList<>();
	public ArrayList<Integer> customers = new ArrayList<>();
	public int noMeasurements = 0;
	public int noArrivals = 0;
	public Proc sendTo;
	public double lambda;

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
			case READY: {
				QS temp = null;
				for (QS q : send_to) {
					if (!q.is_used) {
						temp = q;
						break;
					}
				}

				if (temp != null) {
					SignalList.SendSignal(ARRIVAL, temp, time);
				}
				SignalList.SendSignal(READY, this, time + Math.log(1 - slump.nextDouble()) / (-1 / (1 / lambda)));
			}

				break;
			case MEASURE: {
				noMeasurements++;
				int queue = 0;
				for (QS q : send_to) {
					if (q.is_used) {
						noArrivals++;
						queue++;
					}
				}

				customers.add(queue);
				SignalList.SendSignal(MEASURE, this, time + 4);

			}
				break;
		}
	}
}