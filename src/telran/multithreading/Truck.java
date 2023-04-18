package telran.multithreading;

import java.util.concurrent.atomic.AtomicLong;

public class Truck extends Thread {
	private int load;
	private static AtomicLong elevator1 = new AtomicLong();
	private static AtomicLong elevator2 = new AtomicLong();
	private final static Object mutex = new Object();
	private int nRuns;
	
	public Truck(int load, int nRuns) {
		this.load = load;
		this.nRuns = nRuns;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			loadElevator1(load);
			loadElevator2(load);
		}
	}
	static private void loadElevator1(int load) {
			elevator1.getAndAdd(load);		
	}
	static private void loadElevator2(int load) {
		elevator2.getAndAdd(load);	
		
	}
	
	public static long getElevator1() {
		return elevator1.get();
	}
	public static long getElevator2() {
		return elevator2.get();
	}
}
