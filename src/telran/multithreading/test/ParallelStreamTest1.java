package telran.multithreading.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class ParallelStreamTest1 {
	Integer[] array = { 100, -10, 20, -90, 50, 40, 60, 10, -10 };
	int[] bigArray = ThreadLocalRandom.current().ints(N_NUMBERS).toArray();
	static final int N_NUMBERS = 10_000_000;
	@Test
	void test() {
		HashSet<String> threadNames = new HashSet<>();
		Arrays.stream(bigArray).parallel().boxed().sorted((a, b) -> {
			threadNames.add(Thread.currentThread().getName());
			return Integer.compare(a, b);
		}).toArray();
		System.out.println(threadNames);
	}

}
