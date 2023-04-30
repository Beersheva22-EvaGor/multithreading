package telran.multithreading.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class ParallelStreamTest {

	Integer[] array = { 100, -10, 20, -90, 50, 40, 60, 10, -10 };
	int[] bigArray = ThreadLocalRandom.current().ints(N_NUMBERS).toArray();
	static final int N_NUMBERS = 10_000_000;

	@Test
	void test() {
		Integer[] expected = { -90, -10, -10, 20, 40, 50, 60, 10, 100 };
		HashSet<String> threadNames = new HashSet<>();
		Arrays.stream(bigArray).parallel().boxed().sorted((a, b) -> {
			return a - b;
		}).toArray();

		System.out.println(threadNames);
//		assertArrayEquals(expected, actual);
	}
}
