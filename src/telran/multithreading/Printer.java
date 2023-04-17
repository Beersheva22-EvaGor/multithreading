package telran.multithreading;

public class Printer extends Thread {
	private String str;
	private int nRuns;
	
	public Printer(String str, int nRuns) {
		this.str = str;
		this.nRuns = nRuns;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < nRuns; i++) {
			System.out.println(str);
			try {
				sleep(10);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
}
