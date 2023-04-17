package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PrinterController {

	public static void main(String[] args) throws InterruptedException {
		Printer printer1 = new Printer("#", 100);
		Printer printer2 = new Printer("*", 100);
		printer2.join();
		Instant start = Instant.now();
		printer1.start();
		printer2.start();
		printer1.join();
//		Thread.sleep(1000);
		System.out.printf("Running time is %d, ms \r\n",ChronoUnit.MILLIS.between(start, Instant.now()));
	}

}
