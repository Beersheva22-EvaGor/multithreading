package telran.multithreading.producers;

import java.util.concurrent.BlockingQueue;

public class SenderBlockingQueue extends Thread {
private BlockingQueue<String> messageBox;
private int nMessages;
public SenderBlockingQueue(BlockingQueue<String> messageBox, int nMessages) {
	this.messageBox = messageBox;
	this.nMessages = nMessages;
}
@Override
public void run() {
	for(int i = 1; i <= nMessages; i++) {
		try {
			messageBox.put("message" + i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
}