package telran.multithreading;

import java.util.concurrent.locks.*;
import java.util.concurrent.locks.ReentrantLock;

public class MessageBox {
	String message;
	Lock lock = new ReentrantLock();
	Condition waitingConsumer = lock.newCondition();
	Condition waitingProducer = lock.newCondition();

	public void put(String message) throws InterruptedException {
		lock.lock();
		try {
			while (this.message != null) {
				waitingProducer.await(); // if wait() - it'd be objects functionality used
			}
			this.message = message;
			waitingConsumer.signal(); // instead of notifyAll
		} finally {
			lock.unlock();
		}
	}

	public String take() throws InterruptedException {
		lock.lock();
		try {
			while (message == null) {
				waitingConsumer.await();
			}
			String res = message;
			message = null;
			waitingProducer.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}
	
	public String get() {
		lock.lock();
		try {
			String res =  message;
			message = null;
			if (res != null) {
				waitingProducer.signal();
			}
			return res;
		} finally {
			lock.unlock();
		}
	}
}