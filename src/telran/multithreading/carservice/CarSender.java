package telran.multithreading.carservice;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import telran.multithreading.util.MyLogger;

public class CarSender extends Thread {
	private CarQueue carQueue;
	private MyLogger logger;
	private static final int TIMESCALE = 1;
	private static final int PROBABILITY = 15;	// 15% probability that new car is coming
	private static Long timeLapse;
	/** counter - number of cars are served for the whole time by all workers*/
	public static AtomicInteger counter = new AtomicInteger(0);

	public CarSender(CarQueue carQueue, Long timeLapse) {
		this.carQueue = carQueue;
		this.timeLapse = timeLapse;
	}

	public void setLogger(MyLogger logger) {
		this.logger = logger;
	}
	
	@Override
	public void run() {
		if (logger != null) logger.log(Level.FINE, "Start sending cars");
		long time = 0;
		while (time++ < timeLapse) {
			if ( Math.random()*100 < PROBABILITY + 1) {
				carQueue.addCar(new Car());
				counter.incrementAndGet();				
			}
			try {
				sleep(TIMESCALE);
			} catch (InterruptedException e) {
				if (logger != null) logger.log(Level.SEVERE, e.getMessage());
			}
		}
		if (logger != null) logger.log(Level.FINE, "Time's expired. No more cars are sent");
	}
}
