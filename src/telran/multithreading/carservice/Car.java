package telran.multithreading.carservice;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/*! class Car */
public class Car {

	private int servingTime;
	private static AtomicInteger _carId = new AtomicInteger(0);
	private AtomicInteger carId = new AtomicInteger(0);
	private static int MINTIME = 30;
	private static int MAXTIME = 8 * 60;

	public Car() {
		servingTime = new Random().nextInt(MINTIME, MAXTIME + 1);
		_carId.incrementAndGet();
		carId.set(_carId.get());;
	}

	public int getCarId() {
		return carId.get();
	}
	
	public int servingTime() {
		return servingTime;
	}

	@Override
		public String toString() {
			return String.format("CarId = %d, serving time = %d min", carId.get(), servingTime);
		}
}
