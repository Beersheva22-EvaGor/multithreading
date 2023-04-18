package telran.multithreading;

public class MultiThreadPrinter extends Thread {
	int number = 1;
	int count;
	int sumAmount;
	int numThreads;

	private MultiThreadPrinter(int number, int sumAmount, int count, int numThreads) {
		this(sumAmount, count, numThreads);
		this.number = number;
	}

	public MultiThreadPrinter(int sumAmount, int count, int numThreads) {
		this.count = count;
		this.sumAmount = sumAmount;
		this.numThreads = numThreads;
	}

	@Override
	public void run() {
		int diff = sumAmount - count;
		int remainder = diff > count ? count : diff;

		printout(remainder);

		if (numThreads > 1) {
			MultiThreadPrinter newThread = new MultiThreadPrinter(number + 1, sumAmount, count, numThreads - 1);
			newThread.start();
			try {
				newThread.join();
			} catch (InterruptedException e) {
			}
		}
		if (number == 1) {
			sumAmount = diff;
			if (sumAmount > 0) {
				MultiThreadPrinter newThread = new MultiThreadPrinter(sumAmount, count, numThreads);
				newThread.start();
				try {
					newThread.join();
				} catch (InterruptedException e) {
				}
			} else {
				return;
			}
		}
	}

	private void printout(int remainder) {
		System.out.print(Integer.toString(number).repeat(remainder));
		System.out.println();
	}
}
