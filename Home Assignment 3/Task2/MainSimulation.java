import java.util.*;
import java.io.*;

public class MainSimulation extends Global {
	public static void main(String[] args) throws IOException {
		Random random = new Random();
		// FileWriter writer = new FileWriter("resultU.txt", true);
		// FileWriter writer2 = new FileWriter("studentMeetingsU.txt");

		Signal actSignal;
		new SignalList();

		double delay = 60.0;
		double speed = random.nextInt(7);

		LinkedList<Double> times = new LinkedList<Double>();
		Map<Double, Integer> freqMap = new HashMap<Double, Integer>();

		for (int j = 0; j < 250; j++) {
			QS Q1 = new QS();
			Q1.sendTo = null;

			LinkedList<Student> students = new LinkedList<Student>();

			for (int i = 0; i < 20; i++) {
				Student tmpStudent = new Student(delay, speed, i);
				tmpStudent.startPos();
				while (occupied(students, tmpStudent)) {
					tmpStudent.startPos();
				}
				students.add(tmpStudent);
				SignalList.SendSignal(MOVE, Q1, time, tmpStudent);
			}
			Q1.allStudents = students;

			boolean running = true;
			while (running) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);

				int cc = 0;
				for (Student s : students) {
					int count = s.metAllCounter();
					if (count == 19) {
						cc++;
					}
				}

				if (cc == 20) {
					running = false;
				}

			}

			// for(Map.Entry<Double, Integer> val: freqMap.entrySet()){
			// writer2.write(val.getKey()/60 + " : " + val.getValue() + "\n");
			// }

			times.add(time / 60);
			// writer.write(Double.toString(time / 60) + "\n");
			time = 0;
			List<Student> all_times = Q1.allStudents;
			// System.out.println(times.size());
			for (Student s : all_times) {
				for (double t : s.talkedTo) {
					if (!freqMap.containsKey(t)) {
						freqMap.put(t, 1);
					} else {
						freqMap.put(t, freqMap.get(t) + 1);
					}
				}
			}

		}

		// writer.close();
		// writer2.close();
	}

	public static boolean occupied(LinkedList<Student> studentlist, Student current) {
		for (Student stud : studentlist) {
			if (stud.getPos() == current.getPos()) {
				return true;
			}
		}
		return false;
	}
}