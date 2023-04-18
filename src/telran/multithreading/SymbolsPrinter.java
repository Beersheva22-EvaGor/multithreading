package telran.multithreading;

public class SymbolsPrinter extends Thread {
private String[] symbols;
	public SymbolsPrinter(String symbols) {
		this.symbols = symbols.split("");
		setDaemon(true);
	}
	@Override
		public void run() {
		int i = 0;
		while(true) {
			System.out.print(symbols[i]);
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				if (++i == symbols.length) {
					i = 0;
				}
				interrupted();
			}
		}
		}
}
