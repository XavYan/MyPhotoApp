/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.hbarquin.myphotoapp;

import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Histogram {
    private HashMap<String, XYSeries> oSeries;
    private XYSeriesCollection oDataset;
    
    private ChartPanel chartPanelHistogram;
    
    public Histogram () {
        this.oSeries = new HashMap<String, XYSeries>();
        this.oDataset = new XYSeriesCollection();
    }
    
    public void addSerie (String serie) {
        this.oSeries.put(serie, new XYSeries(serie));
    }
    
    public void addToSerie (String serie, int xValue, int yValue) {
        this.oSeries.get(serie).add(xValue, yValue);
    }
    
    public int getYValue (String serie, int xValue) {
        try {
            return this.oSeries.get(serie).getY(xValue).intValue();
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }
    
    public ChartPanel generateHistogram (String histogramName, String xName, String yName) {
        
        if (this.oSeries.isEmpty()) {
            return null;
        }
        
        this.oSeries.forEach((key, value) -> this.oDataset.addSeries(value));

        JFreeChart oChart = ChartFactory.createHistogram(histogramName, xName, yName, this.oDataset);
        
        this.chartPanelHistogram = new ChartPanel(oChart);

        return this.chartPanelHistogram;
    }
    
    public ChartPanel getChartHistogram () {
        return this.chartPanelHistogram;
    }
}
