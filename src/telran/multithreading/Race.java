package telran.multithreading;

import java.util.ArrayList;
import java.util.List;

public class Race {
	private int quantityOfThreads;
	int distance;

	public Race(int quantityOfThreads, int distance) {
		this.quantityOfThreads = quantityOfThreads;
		this.distance = distance;
	}

	public int run() {
		ThreadParams winner = new ThreadParams();
		System.out.println("Race's started");
		List<ThreadRunner> list = new ArrayList<>();
		for (int i = 0; i < quantityOfThreads; i++) {
			ThreadRunner thread = new ThreadRunner(distance, winner);
			list.add(thread);
			thread.start();
		}

		Runnable displ = () -> displayProcessing();
		Thread threadDispl = new Thread(displ);
		threadDispl.start();

		for (ThreadRunner runner : list) {
			try {
				runner.join();
			} catch (InterruptedException e) {
			}
		}
		threadDispl.interrupt();
		System.out.println();
		
		return winner.getWinnerIndex();
	}

	private void displayProcessing() {
		String line = "Processing" + ". ".repeat(3);
		String[] arr =  line.split(" ");
		try {
			while (true) {
				for (String c : arr) {
					System.out.print(c);
					Thread.sleep(200);
				}
				System.out.print("\r"+" ".repeat(line.length() - arr.length) + "\r");
			}
		} catch (InterruptedException e) {}
	}
}
