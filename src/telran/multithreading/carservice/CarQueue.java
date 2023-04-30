package telran.multithreading.carservice;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import telran.multithreading.util.MyLinkedBlockingQueue;
import telran.multithreading.util.MyLogger;

public class CarQueue {
	MyLinkedBlockingQueue<Car> queue;
	MyLogger logger;
	Lock lock = new ReentrantLock();

	public CarQueue(int limit) {
		queue = new MyLinkedBlockingQueue<>(limit);
	}

	public void setLogger(MyLogger logger) {
		this.logger = logger;
	}

	public boolean addCar(Car car) {
		boolean res = queue.offer(car);
		if (logger != null && !res) {
			logger.log(Level.INFO,
					String.format("Rejected. Car %d cannot be served due to the queue is full",
							car.getCarId(), queue.remainingCapacity()));
		}
		return res;

	}

	public Car getLastCar() throws InterruptedException {
		Car car = queue.poll();
		return car;
	}

	public Car waitForCar() throws InterruptedException {
		Car car = queue.take();
		return car;
	}

}
