package telran.multithreading.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger{
	private static Logger LOG = Logger.getLogger("Car service logger");
	Lock lock = new ReentrantLock(true);
	
	public MyLogger() {
		LOG.setLevel(Level.ALL);
		LOG.setUseParentHandlers(false);
	}
	
	public void log(Level level, String mes) {
			LOG.log(level, mes);		
	}

	public void setHandler(Handler handler) {
		lock.lock();
		try {
			LOG.addHandler(handler);			
		} finally {
			lock.unlock();
		}
	}
}
