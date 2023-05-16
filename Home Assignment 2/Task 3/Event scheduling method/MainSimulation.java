import java.util.*;
import java.io.*;
import java.time.Period;


public class MainSimulation extends GlobalSimulation{
 

 public static double task3(double money) throws IOException{

 double balance=0.0;
 int months=1;
 int counter_month=1;
	writeToFile(months,money); 
	balance += money;
	while(!(balance>=2000000)){
	if(months!=48){
		balance +=  money + balance*0.025 ;
		months++;
		counter_month ++;
		writeToFile(counter_month,balance);
		
	}else{
	double val = ranodmly_selected();
	writeToFile(val);
if(val== 0.1){
	balance= market_dis(0.25,  balance);
	
} else if(val== 0.25){
	balance=market_dis(0.5,  balance);

}else if(val== 0.15){
	balance=market_dis(0.6,  balance);
}else{
	balance=market_dis(0.1,  balance);
}
months=0;
	}			
	}
	counter_month++;
	writeToFile(counter_month,balance);	
	//return balance;
	return counter_month;
 }
 /**
  * uniformly distributed the disturbances
  * @return
  */
 public static double ranodmly_selected(){
	double[] prob = {0.1, 0.25, 0.15,0.0};
	Random rand = new Random();
	int i = rand.nextInt(prob.length);
	double value = prob[i];
	return value;

 }
 /*
  * market disturbance
  */
 public static double market_dis(double drop_proc, double balance) {
	balance+= balance - balance*drop_proc;

	return balance;
}
/*
 * Store value inorder to create confidence interval
 */
 public static void writeToFile( int month , double money) throws IOException {
	FileWriter writer = new FileWriter("plots.txt",true);
	writer.write(month + "\t" + money + "\n");
	writer.close();
}
public static void writeToFile(double v) throws IOException {
	FileWriter writer = new FileWriter("plots.txt",true);
	writer.write( v+ "\n");
	writer.close();
}



    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that shoud be used
    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (time < 5000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value
    	//System.out.println(1.0*actState.accumulated/actState.noMeasurements);
		double year= task3(20000.0);
		Period time= Period.ofMonths( (int) year ).normalized();
		System.out.println("I takes "+ time.getYears() + " years " +time.getMonths()+ " months");

    }
} 
