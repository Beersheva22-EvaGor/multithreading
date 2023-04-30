package telran.multithreading.carservice;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

import telran.multithreading.util.LogFormatter;
import telran.view.CarServiceChart;
import telran.view.CarServiceMenu;
import telran.view.Menu;
import telran.view.StandardInputOutput;

import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class CarServiceApplication {
	public static boolean flFinish = false;
	public static void main(String[] args) throws InterruptedException {
		Handler handler = new ConsoleHandler();
		handler.setFormatter(new LogFormatter());
		handler.setLevel(Level.ALL);

		Thread thread = new Thread(() -> {
			chartRender();
		});
		thread.start();

		Menu menu = CarServiceMenu.getCarsOperationsMenu();
		CarServiceMenu.setHandler(handler);
		menu.perform(new StandardInputOutput());
		flFinish= true;
	}

	private static void chartRender() {
		final CarServiceChart chart = new CarServiceChart("Car Service Data");
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		var rend_rejected = new StandardXYItemRenderer();
		var rend_busyWorkers = new StandardXYItemRenderer();
		while (!CarServiceApplication.flFinish) {
			Integer sent = CarSender.counter.get();
			Integer accepted = Worker.counter.get();
			Integer rejected = sent - accepted;
			if (sent > 0 && !CarServiceChart.flStop) {
				chart.series_rejected.addOrUpdate(new Millisecond(), rejected * 100.0 / sent);
				chart.plot.setDataset(0, new TimeSeriesCollection(chart.series_rejected));
				
				chart.series_busyWorkers.addOrUpdate(new Millisecond(),
						Worker.busyWorkers.get() * 100.0 / CarServiceController.getNumWorkers());
				chart.plot.setDataset(1, new TimeSeriesCollection(chart.series_busyWorkers));

				chart.plot.setRenderer(0, rend_rejected);
				chart.plot.setRenderer(1, rend_busyWorkers);
			}
		}
		chart.close();
	}

}
