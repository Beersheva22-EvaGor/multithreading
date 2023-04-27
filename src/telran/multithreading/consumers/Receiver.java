package telran.multithreading.consumers;

import java.util.concurrent.atomic.AtomicInteger;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;
	public static AtomicInteger counter = new AtomicInteger(0);

	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	private void display(String message) {
		System.out.printf("thread: %s; received message: %s\n", getName(), message);
		counter.incrementAndGet();
	}
	@Override
	public void run() {
		while (true) {
			String message;
			try {
				message = messageBox.take();
				display(message);
			} catch (InterruptedException e) {
				do {
					message = messageBox.get();
					if (message !=null) {
						display(message);
					}
				} while (message != null);
				break;
			}

		}
	}
}