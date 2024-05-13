package com.example.personal_finance_accounting.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class ViewCharts extends JFrame {

    public ViewCharts(String title) {
        super(title);

        // Создаем набор данных
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Series 1", "Category 1");
        dataset.addValue(4.0, "Series 1", "Category 2");
        dataset.addValue(3.0, "Series 1", "Category 3");

        // Создаем график
        JFreeChart chart = ChartFactory.createBarChart(
                "Sample Chart",  // заголовок
                "Category",       // ось X
                "Value",          // ось Y
                dataset           // набор данных
        );

        // Создаем панель для графика
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Добавляем панель с графиком на окно
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewCharts example = new ViewCharts("Simple Chart Example");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}

