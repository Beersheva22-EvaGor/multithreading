package telran.multithreading.consumers;

import telran.multithreading.MessageBox;

public class Receiver extends Thread {
 private MessageBox messageBox;

public Receiver(MessageBox mesageBox) {
	this.messageBox = mesageBox;
	setDaemon(true);
}
 @Override
 public void run() {
	 while(true) {
		 try {
			String message = messageBox.take();
			 System.out.printf("thread: %s; received message: %s\n", getName(), message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 }
 }
}