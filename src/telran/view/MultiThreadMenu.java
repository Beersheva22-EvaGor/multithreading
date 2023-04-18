package telran.view;

import telran.multithreading.MultiThreadPrinter;

public class MultiThreadMenu {
	public static Item getMenu() {
		return new Menu("MultiThreadPrinter menu", new Item[] {
				Item.of("Start", MultiThreadMenu::start),
				Item.exit()
		});
		
	}
	
	private static void start (InputOutput io) {
		int numThreads = io.readInt("Input number of threads (2-10)", "It should be 2-10", 2, 10);
		int count = io.readInt("Input portion of the above amount (1-10)", "It should be 1-10", 1, 10);
		int sumAmount = io.readInt("Input amount of being printed numbers ("+count+"-100)", "It should be "+count+"-100", count, 100);
		MultiThreadPrinter thread = new MultiThreadPrinter(sumAmount, count, numThreads);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
	}
}
