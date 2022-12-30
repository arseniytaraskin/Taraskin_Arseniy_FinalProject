package countries;

import java.awt.*;
import java.util.Map;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends JFrame {
    public Graph(Map<String, Float> value) {
        DrawGraph(value);
    }

    private JFreeChart createGraph(CategoryDataset dataset) {

        String title = "График процентного соотношения пользователей в интернете от всего населения по субрегионам.";
        String catAxixLabel = "Субрегионы";
        String value = "Количество пользователей зарегистрированных в интернете, от общего населения в процентах %";



        return ChartFactory.createBarChart(title, catAxixLabel, value, dataset, PlotOrientation.HORIZONTAL, true, true, true);
    }

    private void DrawGraph(Map<String, Float> average) {
        CategoryDataset dataset = createDS(average);

        JFreeChart chart = createGraph(dataset);

        //chart.setBackgroundPaint(Color.blue);

        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.black);

        ChartPanel chartPanel = new ChartPanel(chart);

        add(chartPanel);

        pack();
    }

    private CategoryDataset createDS(Map<String, Float> capitals) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();

        capitals.forEach((Subregion, Average) ->
                ds.setValue(Average, "Subregion", Subregion));

        return ds;
    }


}