import java.util.LinkedList;
import java.util.Random;

class Student {
    public double speed, delay;
    double[] talkedTo;
    int direction, stepsLeft, uid;
    private int[] pos;
    Random slump = new Random();
    public static final int N = 4, W = 5, E = 6, S = 7, NW = 8, NE = 9, SE = 10, SW = 11;
    public boolean talking;
    public boolean stopTalking;
    private double straightTime;
    private double diagonalTime;

    Student(double delay, double speed, int uid) {
        this.delay = delay;
        this.speed = speed;
        this.uid = uid;
        pos = new int[2];
        direction = newDirection();
        stepsLeft = stepToTake();
        talking = false;
        talkedTo = new double[20];
        stopTalking = false;
        straightTime = (1.0 / 2.0) / speed;
        diagonalTime = Math.sqrt(1.0 / 2.0) / speed;
    }

    public void setPos(int x, int y) {
        pos[0] = x;
        pos[1] = y;
    }

    public int[] getPos() {
        return pos;
    }

    public int[] startPos() {

        int startX = slump.nextInt(20);
        int startY = slump.nextInt(20);
        pos[0] = startX;
        pos[1] = startY;

        return pos;

    }

    public int newDirection() {

        return slump.nextInt(4, 11);

    }

    public void changeDirection() {
        direction = newDirection();
    }

    public boolean checkBounds(int nextX, int nextY) {
        if (nextX + pos[0] < 20 && nextY + pos[1] < 20) {
            return true;
        }
        return false;
    }

    public double timeToMove(int[] oldPos, int[] newPos) {
        LinkedList<Integer> tmp = new LinkedList<>();
        tmp.add(4);
        tmp.add(5);
        tmp.add(6);
        tmp.add(7);
        if (tmp.contains(direction)) {
            return ((newPos[0] - oldPos[0]) + (newPos[1] - oldPos[0])) / speed;
        }
        return Math.sqrt(Math.pow((newPos[0] - oldPos[0]), 2) + Math.pow((newPos[1] - oldPos[0]), 2)) / speed;

    }

    public double timeto() {
        if (direction == NE || direction == NW || direction == SW || direction == SE) {
            return diagonalTime;

        } else {

            return straightTime;
        }
    }

    public void move() {
        switch (direction) {
            case N:
                pos[1]++;
                break;
            case W:
                pos[0]--;
                break;
            case E:
                pos[0]++;
                break;
            case S:
                pos[1]--;
                break;
            case NW:
                pos[1]++;
                pos[0]--;
                break;
            case NE:
                pos[1]++;
                pos[0]++;
                break;
            case SE:
                pos[1]--;
                pos[0]++;
                break;
            case SW:
                pos[1]--;
                pos[0]--;
                break;
            default:
                System.out.println("Invalid move");
        }
        stepsLeft--;
    }

    public boolean validMove() {
        boolean tmp = false;
        if (direction == N || direction == NW || direction == NE) {
            if (pos[1] == 19) {
                tmp = true;
            }
        }
        if (direction == W || direction == NW || direction == SW) {
            if (pos[0] == 0) {
                tmp = true;
            }
        }
        if (direction == S || direction == SW || direction == SE) {
            if (pos[1] == 0) {
                tmp = true;
            }
        }
        if (direction == E || direction == SE || direction == NE) {
            if (pos[0] == 19) {
                tmp = true;
            }
        }
        return !tmp;
    }

    public int stepToTake() {
        return slump.nextInt(1, 11);
    }

    public void metStudent(int uid, double time) {
        talkedTo[uid] += time;
    }

    public boolean metAll() {
        for (int i = 0; i < 20; i++) {
            if (talkedTo[i] == 0.0 && i != uid) {
                return false;
            }
        }
        return true;
    }

    public int metAllCounter() {
        int counter = 0;
        for (int i = 0; i < 20; i++) {
            if (talkedTo[i] != 0.0 && i != uid) {
                counter++;
            }
        }
        return counter;
    }

    public String toString() {
        return "UID: " + uid + " ,X: " + pos[0] + ", Y: " + pos[1] + ", Direction: " + direction + ", StepsLeft: "
                + stepsLeft + ", Talking: " + talking;
    }

    public String getAllMettingTimes() {
        StringBuilder sb = new StringBuilder();
        sb.append(uid + " met \n\t");
        for (int i = 0; i < 20; i++) {
            sb.append("number " + i + " for amount of time " + talkedTo[i] + "\n");
        }
        return sb.toString();
    }

}
