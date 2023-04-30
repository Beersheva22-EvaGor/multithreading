package telran.multithreading.carservice;

import java.util.*;
import java.util.logging.Handler;
import java.util.logging.Level;

import telran.multithreading.util.MyLogger;

public class CarServiceController {
	private static final int DEFAULT_LIMIT = 30;
	private static final int DEFAULT_NUMBERWORKERS = 20;
	private static final int DEFAULT_TIMELAPSE = 6*30*8*60;
	private static int numberWorkers;
	long timeLapse;
	CarQueue queue;
	CarSender sender;
	public static Object obj = new Object();
	List<Worker> workers = new ArrayList<>();
	List<Handler> handlers = new ArrayList<>();
	private MyLogger logger = new MyLogger();
	
	public CarServiceController(Handler... handlers) {
		this(DEFAULT_LIMIT, DEFAULT_NUMBERWORKERS, DEFAULT_TIMELAPSE, handlers);
	}
	/**
	 * @param limitQueue
	 * @param numberWorkers
	 * @param timeLapse
	 * @param handlers
	 */
	public CarServiceController(int limitQueue, int numberWorkers, long timeLapse, Handler... handlers) {
		this.numberWorkers = numberWorkers;
		this.timeLapse = timeLapse;
		queue = new CarQueue(limitQueue);
		sender  = new CarSender(queue, timeLapse);
		if (handlers != null) {
			Arrays.stream(handlers).forEach(logger::setHandler);
		}
		queue.setLogger(logger);
		sender.setLogger(logger);
	}
	
	static public int getNumWorkers() {
		return numberWorkers;
	}
	
	public void startService() throws InterruptedException {
		sender.start();
		for (int i = 0; i < numberWorkers; i++) {
			Worker worker = new Worker(queue);
			worker.setLogger(logger);
			workers.add(worker);
			worker.start();
		}
		sender.join();
		workers.forEach(w -> w.interrupt());
		for (Worker worker : workers) {
			worker.join();
		}
		synchronized(obj) {
		logger.log(Level.FINE, "Finished");
		logger.log(Level.CONFIG, getStatistics());
		}
	}
	
	private String getStatistics() {
		int sent = CarSender.counter.get();
		int accepted = Worker.counter.get();
		return String.format("Sum cars was sent: %d\n"
				+ "Sum cars were served: %d\n"
				+ "Sum cars were rejected: %d (%.2f %%)\n"
				+ "Average time workers waiting to serve: %.2f\n",
				sent, 
				accepted,
				sent - accepted,
				((sent - accepted) * 100.0 / sent),
				Worker.sumWaitingTime.get() *1.0 / accepted);
	}
}
