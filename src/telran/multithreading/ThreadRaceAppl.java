package telran.multithreading;
import telran.view.InputOutput;
import telran.view.StandardInputOutput;
import telran.view.ThreadRaceMenu;

public class ThreadRaceAppl {

	static final InputOutput IO = new StandardInputOutput();
	public static void main(String[] args) {
		ThreadRaceMenu.getMenu().perform(IO);
		

	}

}
