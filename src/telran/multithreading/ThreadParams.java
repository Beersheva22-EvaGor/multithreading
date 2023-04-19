package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class ThreadParams {
	Queue<Map.Entry<Integer, Long>> listWinners = new ConcurrentLinkedQueue<>();
	int index = 0;
	Instant start;

	public ThreadParams() {
		start = Instant.now();
		System.out.println("Time starting is "+ start.toString());
	}

	public int getIndex() {
		return ++index;
	}

	public void addWinner(int indexWinner, Instant finish) {
		Map.Entry<Integer, Long> entry = new AbstractMap.SimpleEntry<>(indexWinner,
				ChronoUnit.MILLIS.between(start, finish));
		listWinners.add(entry);
	}

	public String displayResult() {
		var sortedList = listWinners.stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).collect(Collectors.toList());
		String[] columns = new String[] { "Place", "Thread #", "Running time, ms" };
		displayRow cons = (x, y, z) -> String
				.format("%" + columns[0].length() + "s  %" + columns[1].length() + "s  %"+ columns[2].length() + "s\r\n", x, y, z);

		String res = cons.getString(columns[0], columns[1], columns[2]);
		var it = sortedList.iterator();
		for (int i = 0; i < sortedList.size(); i++) {
			var el = it.next();
			res += cons.getString(Integer.toString(i + 1), Integer.toString(el.getKey()),
					Long.toString(el.getValue()));
		}

		return res;
	}

	@FunctionalInterface
	interface displayRow {
		String getString(String x, String y, String z);
	}
}
