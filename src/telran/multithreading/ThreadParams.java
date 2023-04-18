package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ThreadParams {
	List<Map.Entry<Integer,Long>> listWinners = new ArrayList<>();
	int index = 0;
	Instant start;
	private Object mutex = new Object();

	public ThreadParams() {
		start = Instant.now();
	}

	public int getIndex() {
		return ++index;
	}

	public void addWinner(int indexWinner) {
		Instant finishInstant = Instant.now();
		Map.Entry<Integer, Long> entry = new AbstractMap.SimpleEntry<>(indexWinner, ChronoUnit.MILLIS.between(start, finishInstant));
		synchronized (mutex) {
			listWinners.add(entry);			
		}
	}

	public String displayResult() {
		String[] columns = new String[] {"Place", "Thread #", "Running time, ms"};
		displayRow cons = (x, y, z) -> String.format("%"+columns[0].length()+"s %"+columns[1].length()+"s %10s\r\n", x,y,z);
		
		String res = cons.getString(columns[0], columns[1], columns[2]);
		for (int i = 0; i < listWinners.size(); i++) {
			var el = listWinners.get(i);
			res += cons.getString(Integer.toString(i+1), Integer.toString(el.getKey()), Long.toString(el.getValue())); // place - thread number - running time
		}

		return res;
	}
	
	@FunctionalInterface
	interface displayRow {
		String getString(String x, String y, String z);
	}
}
