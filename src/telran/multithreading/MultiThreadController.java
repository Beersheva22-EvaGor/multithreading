package telran.multithreading;

import telran.view.MultiThreadMenu;
import telran.view.*;

public class MultiThreadController {

	public static void main(String[] args) {
		InputOutput io = new StandardInputOutput();
		Item menu = MultiThreadMenu.getMenu();
		menu.perform(io);
	}

}
