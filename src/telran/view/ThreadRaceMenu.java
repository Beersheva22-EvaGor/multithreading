package telran.view;

import java.util.*;

import telran.multithreading.Race;

public class ThreadRaceMenu {
	public static Item getMenu() {
		return new Menu("Race menu", new Item[] {
				Item.of("Start", ThreadRaceMenu::start),
				Item.exit()
		});
		
	}
	
	private static void start (InputOutput io) {
		int numThreads = io.readInt("Input number of threads (2-100)", "It should be 2-100", 2, 100);
		int distance = io.readInt("Input distance (100-1000)", "It should be 100-1000", 100, 1000);
		Race race = new Race(numThreads, distance);
		String winners = race.run();
		io.writeLine("Winners are: \r\n" + winners);
		Set<String> answers = new HashSet();
		answers.add("y");
		answers.add("n");
		if (io.readStringOptions("Would you like to start again? (y|n)", "Answer y|n", answers).equals("y")) {
			start(io);
		}
	}
}
