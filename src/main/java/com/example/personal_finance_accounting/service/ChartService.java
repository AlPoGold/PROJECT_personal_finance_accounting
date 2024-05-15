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
                "Income Over Time",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        return chart;
    }

    public JFreeChart createExpenseChart() {
        // Получаем данные о доходах из репозитория
        List<Expense> expences = expenseService.getAllExpenses();

        // Создаем набор данных для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Expense expense:expences) {
            dataset.addValue(expense.getAmount(), "Expence", expense.getDate().toString());
        }

        // Создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "Expence Over Time",  // заголовок
                "Date",              // ось X
                "Amount",            // ось Y
                dataset             // набор данных
        );

        return chart;
    }

    public JFreeChart createBalanceChart(){
        Balance balance = balanceService.calculateBalance();
        DefaultPieDataset dataset = new DefaultPieDataset();
        // Добавляем значения в набор данных
        dataset.setValue("Доходы", balance.getTotalIncome());
        dataset.setValue("Расходы", balance.getTotalExpense());
        JFreeChart chart = ChartFactory.createPieChart(
                "Доходы и расходы", // Заголовок диаграммы
                dataset,            // Набор данных
                true,               // Легенда
                true,               // Подписи
                false               // Ссылки
        );
        return chart;

    }



}
