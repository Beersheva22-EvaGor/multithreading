package telran.multithreading;

import java.time.Instant;
import java.util.Random;

public class ThreadRunner extends Thread {
	int index = 0;
	int distance;
	Random rand = new Random(); 
	int min = 20;
	int max = 50;
	ThreadParams winner;
	
	public ThreadRunner(int distance, ThreadParams winner) {
		this.index = winner.getIndex();
		this.distance = distance;
		this.winner = winner;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < distance; i++) {
			try {
				sleep(rand.nextInt(min, max));
			} catch (InterruptedException e) {}
		}
		winner.addWinner(index, Instant.now());
	}
	
}
