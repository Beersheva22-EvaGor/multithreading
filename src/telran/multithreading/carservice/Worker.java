package telran.multithreading.carservice;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;
import java.util.logging.Level;

import telran.multithreading.util.MyLogger;


public class Worker extends Thread {
	private CarQueue carQueue;
	public static AtomicInteger counter = new AtomicInteger(0);
	Lock lock = new ReentrantLock();
	MyLogger logger;
	public static AtomicInteger busyWorkers = new AtomicInteger(0);
	public static AtomicInteger sumWaitingTime = new AtomicInteger(0);

	public Worker(CarQueue carQueue) {
		this.carQueue = carQueue;
		String threadName = getName();
		setName("WORKER-"+threadName.substring(threadName.lastIndexOf("-") + 1));
	}
	
	public void setLogger(MyLogger logger) {
		this.logger = logger;
	}

	@Override
	public void run() {
		while (true) {
			Car car;
			try {		
				Instant start = Instant.now();
				car = carQueue.waitForCar();
				sumWaitingTime.getAndAdd((int)ChronoUnit.MILLIS.between(start, Instant.now()));
				serve(car);
			} catch (InterruptedException e) {
				do {
					try {
						car = carQueue.getLastCar();
					
					if (car != null) {
							serve(car);
					}
					} catch (InterruptedException e1) {
						wrapLockLogger(Level.WARNING,"Interruption after finishing sending: " + e1.getMessage());
						car = null;
					}
				} while (car != null);
				break;
			}			
		}
	}
	
	private <F> void wrapLockLogger(Level level, String mes) {
		lock.lock();
		try {
			if (logger != null) logger.log(level, mes);
		} finally {
			lock.unlock();
		}
	}

	private void serve(Car car) throws InterruptedException {
		busyWorkers.incrementAndGet();
		wrapLockLogger(Level.INFO,String.format("%s: Serving is started for the car: %s", getName(), car.toString()));	
		Instant start = Instant.now();
		boolean flFinished = false;
		try {
		sleep(car.servingTime());
		} catch (InterruptedException e) {
			try {
				flFinished = true;
				long restTime = car.servingTime()- ChronoUnit.MILLIS.between(start, Instant.now());
				if (restTime > 0) sleep(restTime);
			} catch (InterruptedException e1) {
				wrapLockLogger(Level.SEVERE, "After 1st interruption serve was interrupted again");
			}
		}
		wrapLockLogger(Level.INFO,String.format("%s: Serving for the car %d is finished", getName(), car.getCarId()));
		counter.incrementAndGet();
		busyWorkers.decrementAndGet();
		if (flFinished) throw new InterruptedException();
	}
}
