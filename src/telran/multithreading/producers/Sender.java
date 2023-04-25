package telran.multithreading.producers;

import telran.multithreading.*;

public class Sender extends Thread {
	private MessageBox messageBox;
	private int nMessages;

	public Sender(MessageBox messageBox, int nMessages) {
		this.messageBox = messageBox;
		this.nMessages = nMessages;
	}

	@Override
	public void run() {
		for (int i = 1; i <= nMessages; i++) {
			messageBox.put("message" + i);
			try {
				sleep(10);
			} catch (InterruptedException e) {

			}
		}
	}
}