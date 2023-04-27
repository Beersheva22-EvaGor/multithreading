package telran.multithreading;

import java.util.*;

import telran.multithreading.consumers.Receiver;
import telran.multithreading.producers.Sender;

public class SenderReceiversAppl {

	private static final int N_MESSAGES = 100_000;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) throws InterruptedException {
		MessageBox messageBox = new MessageBox();
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		List<Receiver> receivers = new ArrayList<>();
		for (int i = 0; i < N_RECEIVERS; i++) { 
			Receiver receiver =new Receiver(messageBox);
			receivers.add(receiver);
			receiver.start();
		}
		sender.join();
		receivers.forEach(r -> r.interrupt());
		receivers.forEach(r -> {
			try {
				r.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println(Receiver.counter);
	}

}