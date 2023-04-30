package telran.view;

import java.util.logging.Handler;

import telran.multithreading.carservice.CarServiceController;

public class CarServiceMenu {
	static CarServiceController service;
	static Handler handler; 
	public static void setHandler(Handler _handler) {
		handler = _handler;
	}

	public static Menu getCarsOperationsMenu() {
		return new Menu("Car Service Menu", new Item[] { 
				Item.of("Start with input parameters", CarServiceMenu::inputParameters),
				Item.of("Start with default parameters (20 workers, 30 cars in a queue, 6 months / 30 days)", CarServiceMenu::start), 
				Item.exit() });

	}

	static void inputParameters(InputOutput io) {
		int limit = io.readInt("Input queue max length (>0)", "Wrong number", 0, Integer.MAX_VALUE);
		int numWorkers = io.readInt("Input number of workers (>1)", "Wrong number", 1, Integer.MAX_VALUE);
		int months = io.readInt("Input number of months to evaluate (1 - 24)", "Wrong number", 1, 24);
		int daysPerMonth = io.readInt("Input average days per month", "Should be a number between 1 to 30", 1, 30);
		int lapse = months * daysPerMonth * 8 * 60;
		service = new CarServiceController(limit, numWorkers, lapse, new Handler[] { handler });
		try {
			service.startService();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static void start(InputOutput io) {
		service = new CarServiceController(new Handler[] { handler });
		try {
			service.startService();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
