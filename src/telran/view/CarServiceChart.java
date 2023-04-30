package telran.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class CarServiceChart extends ApplicationFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public TimeSeries series_rejected;
	public TimeSeries series_busyWorkers;
	public final TimeSeriesCollection dataset;
	public static boolean flStop = false;
	public XYPlot plot;
	 
	public CarServiceChart(String title) {
		super(title);
		this.series_rejected = new TimeSeries("Rejected cars, %", Millisecond.class);
		this.series_busyWorkers = new TimeSeries("Workers serving cars, %", Millisecond.class);
		dataset = new TimeSeriesCollection(this.series_rejected);
		dataset.addSeries(series_busyWorkers);
        final JFreeChart chart =  ChartFactory.createTimeSeriesChart(
                "Car Service Data", "Time", "%", dataset, true, true, false
                );        
        chart.setBackgroundPaint(Color.white);
        this.plot = chart.getXYPlot();
        this.plot.setBackgroundPaint(Color.lightGray);
        this.plot.setDomainGridlinePaint(Color.white);
        this.plot.setRangeGridlinePaint(Color.white);
        
        final ChartPanel chartPanel = new ChartPanel(chart);
        final JButton button = new JButton("Stop/Resume");
        button.setActionCommand("STOPRESUME_DATA");
        button.addActionListener(this);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        content.add(button, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);
        
        this.setAlwaysOnTop(true);
	}

	public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals("STOPRESUME_DATA")) {
        	flStop = !flStop;
        }
	}
	public void close() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
