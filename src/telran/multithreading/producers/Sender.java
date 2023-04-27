package telran.multithreading.producers;

import telran.multithreading.MessageBox;

public class Sender extends Thread {
private MessageBox messageBox;
private int nMessages;
public Sender(MessageBox messageBox, int nMessages) {
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