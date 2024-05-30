package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
@Service
@Data
@AllArgsConstructor
public class ChartService {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;
    private final BalanceService balanceService;
    private final UserAccountService userService;



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
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (Expense expense:expences) {
            String formattedDate = formatter.format(expense.getDate());
            dataset.addValue(expense.getAmount(), "Expense", formattedDate );
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "ExpenseTime",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        createImage(chart, period);

        return chart;
    }
    public JFreeChart createExpenseChartHystogramm(int period) {
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (Expense expense:expences) {
            String formattedDate = formatter.format(expense.getDate());
            dataset.addValue(expense.getAmount(), "Expense", formattedDate);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "ExpenseTime",
                "Date",                   // x-axis label
                "expense",                // y-axis label
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        createImage(chart, period);

        return chart;
    }

    public JFreeChart createIncomeChartHystogramm(int period){
        List<Income> incomes = null;
        switch (period){
            case 0:
                incomes = incomeService.getAllIncomes();
                break;
            case 1:
                incomes = incomeService.getAllIncomesForLastMonth();
                break;
            case 3:
                incomes = incomeService.getAllIncomesForLast3Months();
                break;
            case 12:
                incomes = incomeService.getAllIncomesForLastYear();
                break;


        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        for (Income income: incomes) {
            String formattedDate = formatter.format(income.getDate());
            dataset.addValue(income.getAmount(), "Income", formattedDate);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "IncomeTime",
                "Date",                   // x-axis label
                "income",                // y-axis label
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        createImage(chart, period);
        return chart;


    }
    public JFreeChart createIncomeChart(int period) {
        List<Income> incomes = null;
        switch (period){
            case 0:
                incomes = incomeService.getAllIncomes();
                break;
            case 1:
                incomes = incomeService.getAllIncomesForLastMonth();
                break;
            case 3:
                incomes = incomeService.getAllIncomesForLast3Months();
                break;
            case 12:
                incomes = incomeService.getAllIncomesForLastYear();
                break;


        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        assert incomes != null;
        for (Income income: incomes) {
            dataset.addValue(income.getAmount(), "Income", income.getDate().toString());
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "IncomeTime",  // заголовок
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
        dataset.setValue("Incomes", balance.getTotalIncome());
        dataset.setValue("Expenses", balance.getTotalExpense());
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


    public void createImage(JFreeChart chart, int period){
        BufferedImage imageIncome = chart.createBufferedImage(800, 800);
        File outputFileIncome = new File("src/main/resources/static/img/" + chart.getTitle().getText() + period + ".png");
        try {
            ImageIO.write(imageIncome, "png", outputFileIncome);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }








}
