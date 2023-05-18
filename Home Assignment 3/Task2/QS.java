import java.util.*;

class QS extends Proc {
	public Proc sendTo;
	Random slump = new Random();
	public List<Student> allStudents;

	public void TreatSignal(Signal x) {
		Student currentStudent = x.student;
		switch (x.signalType) {

			case MOVE: {
				int tmp = sameSquare(allStudents, currentStudent);
				if (tmp == -1 || currentStudent.stopTalking) {
					currentStudent.stopTalking = false;
					if (currentStudent.stepsLeft > 0) {

						if (currentStudent.validMove()) {
							currentStudent.move();

							SignalList.SendSignal(MOVE, this, time + currentStudent.timeto(),
									currentStudent);

						} else {
							while (!currentStudent.validMove()) {
								currentStudent.changeDirection();
							}
							currentStudent.move();
							SignalList.SendSignal(MOVE, this, time + currentStudent.timeto(),
									currentStudent);

						}

					} else {
						currentStudent.stepsLeft = currentStudent.stepToTake();
						currentStudent.changeDirection();
						while (!currentStudent.validMove()) {
							currentStudent.changeDirection();
						}
						currentStudent.move();
						SignalList.SendSignal(MOVE, this, time + currentStudent.timeto(),
								currentStudent);

					}
				} else {
					currentStudent.talking = true;
					allStudents.get(tmp).talking = true;

					currentStudent.talkedTo[tmp] += currentStudent.delay;

					SignalList.SendSignal(TALK, this, time + currentStudent.delay, currentStudent);
				}
			}
				break;

			case TALK: {
				if (currentStudent.talking) {
					currentStudent.talking = false;
					currentStudent.stopTalking = true;
				}
				SignalList.SendSignal(MOVE, this, time + currentStudent.timeto(),
						currentStudent);
			}
				break;

		}
	}

	private int sameSquare(List<Student> studentsList, Student currentStudent) {
		int counter = 0;
		int temp = 0;
		for (Student s : studentsList) {
			if (s.uid != currentStudent.uid && s.getPos()[0] == currentStudent.getPos()[0]
					&& s.getPos()[1] == currentStudent.getPos()[1]) {
				counter++;
				temp = s.uid;
			}
		}

		if (counter == 1) {
			return temp;
		}

		return -1;
	}

	public String met_student() {
		StringBuilder sb = new StringBuilder();
		for (Student s : allStudents) {
			sb.append("UID " + s.uid + " have met total of " + s.metAllCounter() + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}
}