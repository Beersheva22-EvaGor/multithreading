package telran.multithreading;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import telran.multithreading.consumers.*;
import telran.multithreading.producers.SenderBlockingQueue;

public class SenderReceiversApplBlockingQueue {

	private static final int N_MESSAGES = 100_000;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> messageBox = new LinkedBlockingQueue(1);
		SenderBlockingQueue sender = new SenderBlockingQueue(messageBox, N_MESSAGES);
		sender.start();
		List<ReceiverBlockingQueue> receivers = new ArrayList<>();
		for (int i = 0; i < N_RECEIVERS; i++) { 
			ReceiverBlockingQueue receiver = new ReceiverBlockingQueue(messageBox);
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
		System.out.println(ReceiverBlockingQueue.counter);
	}

}