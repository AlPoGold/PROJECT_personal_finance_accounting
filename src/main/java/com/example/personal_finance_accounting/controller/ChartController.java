package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.service.ChartService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Controller
@Data
@AllArgsConstructor
@Log
public class ChartController {
    private final ChartService chartService;


    @GetMapping("/chart")
    public String showIncomeChart(Model model){
        // Создаем график с данными
        JFreeChart chartIncome = chartService.createIncomeChart();
        JFreeChart chartExpence = chartService.createExpenseChart();
        JFreeChart chartBalance = chartService.createBalanceChart();
        BufferedImage imageIncome = chartIncome.createBufferedImage(600, 600); // Ваш BufferedImage
        BufferedImage imageExpence = chartExpence.createBufferedImage(600, 600); // Ваш BufferedImage
        BufferedImage imageBalance = chartBalance.createBufferedImage(600, 600); // Ваш BufferedImage
        File outputFileIncome = new File("src/main/resources/static/img/income_chart.png");
        File outputFileExpence = new File("src/main/resources/static/img/expence_chart.png");
        File outputFileBalance = new File("src/main/resources/static/img/balance_chart.png");
        try {
            ImageIO.write(imageIncome, "png", outputFileIncome);
            ImageIO.write(imageExpence, "png", outputFileExpence);
            ImageIO.write(imageBalance, "png", outputFileBalance);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Добавляем график в модель
        model.addAttribute("incomeChart", chartIncome);
        model.addAttribute("expenceChart", chartExpence);
        model.addAttribute("balanceChart", chartBalance);

        return "chart";
    }

}
