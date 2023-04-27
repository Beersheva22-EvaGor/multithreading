package telran.multithreading;

public class MessageBox{
	String message;

	 synchronized public void put(String message) throws InterruptedException {
		 while (this.message != null) {
			 wait();
		 }
		this.message = message;
		notifyAll();
	}
	 synchronized public String take() throws InterruptedException {
		while(message == null) {
			wait();
		}
		String res = message;
		message = null;
		notifyAll();
		return res;
	}
}