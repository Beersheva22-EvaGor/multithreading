package telran.multithreading;

import java.util.Scanner;

public class SymbolsPrinterController {

	public static void main(String[] args) throws InterruptedException {
		SymbolsPrinter printer = new SymbolsPrinter("Hello");
		printer.start();
		while(true) {
			String s = new Scanner(System.in).nextLine();
			if (s.toLowerCase().equals("")) {
				printer.interrupt();
			} else if (s.toLowerCase().equals("q")){
				break;
			}
			
		}
	}

}
