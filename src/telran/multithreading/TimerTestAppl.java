package telran.multithreading;

public class TimerTestAppl {
public static void main(String[] args) throws InterruptedException {
	Timer timer = new Timer();
	timer.start();
	Thread.sleep(5000);
	timer.interrupt();
	Thread.sleep(5000);
}
}
