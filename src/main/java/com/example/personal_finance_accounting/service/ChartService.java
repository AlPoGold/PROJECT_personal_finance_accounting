package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.model.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ChartService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final BalanceService balanceService;

    public JFreeChart createIncomeChart() {
        // Получаем данные о доходах из репозитория
        List<Income> incomes = incomeService.getAllIncomes();

        // Создаем набор данных для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Income income : incomes) {
            dataset.addValue(income.getAmount(), "Income", income.getDate().toString());
        }

        // Создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "IncomeTime",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        return chart;
    }

    public JFreeChart createExpenseChart(int period) {
        List<Expense> expences = null;
        switch (period){
            case 0:
                expences = expenseService.getAllExpenses();
                break;
            case 1:
                expences = expenseService.getAllExpensesForLastMonth();
                break;
            case 3:
                expences = expenseService.getAllExpensesForLast3Months();
                break;
            case 12:
                expences = expenseService.getAllExpensesForLastYear();
                break;


        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        assert expences != null;
        for (Expense expense:expences) {
            dataset.addValue(expense.getAmount(), "Expense", expense.getDate().toString());
        }

        // Создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "ExpenseTime",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        createImage(chart, period);

        return chart;
    }

    public JFreeChart createBalanceChart(int period){
        Balance balance = null;
        switch (period){
            case 0:
                balance = balanceService.calculateBalance();
                break;
            case 1:
                balance = balanceService.calculateBalanceForLastMonth();
                break;
            case 3:
                balance = balanceService.calculateBalanceForLast3Months();
                break;
            case 12:
                balance = balanceService.calculateBalanceForLastYear();
                break;


        }


        DefaultPieDataset dataset = new DefaultPieDataset();
        // Добавляем значения в набор данных
        dataset.setValue("Доходы", balance.getTotalIncome());
        dataset.setValue("Расходы", balance.getTotalExpense());
        JFreeChart chart = ChartFactory.createPieChart(
                "Balance", // Заголовок диаграммы
                dataset,            // Набор данных
                true,               // Легенда
                true,               // Подписи
                false               // Ссылки
        );
        createImage(chart, period);
        return chart;

    }

    public JFreeChart createIncomeChart3Months() {
        // Получаем данные о доходах из репозитория
        List<Income> incomes = incomeService.getAllIncomesForLast3Months();

        // Создаем набор данных для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Income income : incomes) {
            dataset.addValue(income.getAmount(), "Income", income.getDate().toString());
        }

        // Создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "IncomeTime",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        return chart;
    }

    public JFreeChart createIncomeChartLastMonth() {
        // Получаем данные о доходах из репозитория
        List<Income> incomes = incomeService.getAllIncomesForLastMonth();

        // Создаем набор данных для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Income income : incomes) {
            dataset.addValue(income.getAmount(), "Income", income.getDate().toString());
        }

        // Создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "IncomeTime",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        return chart;
    }

    public JFreeChart createIncomeChartLastYear() {
        // Получаем данные о доходах из репозитория
        List<Income> incomes = incomeService.getAllIncomesForLastYear();

        // Создаем набор данных для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Income income : incomes) {
            dataset.addValue(income.getAmount(), "Income", income.getDate().toString());
        }

        // Создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "IncomeTime",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        return chart;
    }

    public void createImage(JFreeChart chart, int period){
        BufferedImage imageIncome = chart.createBufferedImage(400, 600);
        File outputFileIncome = new File("src/main/resources/static/img/" + chart.getTitle().getText() + period + ".png");
        try {
            ImageIO.write(imageIncome, "png", outputFileIncome);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }








}
