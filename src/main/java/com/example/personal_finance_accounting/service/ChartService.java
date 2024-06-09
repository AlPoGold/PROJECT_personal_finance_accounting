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




    /**
     * Получает статистику расходов.
     * @param period целое число, отображающее за сколько месяцев необходимо получить статистику,
     *               доступные значения 0(за все время), 1(за 1 месяц), 3(за 3 месяца), 12(за год)
     *
     * @return объект ChartData, представляющих данные о расходах в виде гистограммы.
     */
    public JFreeChart createExpenseChartHystogramm(int period, UserAccount user) {
        List<Expense> expences = null;
        switch (period){
            case 0:
                expences = expenseService.findByUserAccount(user);
                break;
            case 1:
                expences = expenseService.getAllExpensesForLastMonth(user);
                break;
            case 3:
                expences = expenseService.getAllExpensesForLast3Months(user);
                break;
            case 12:
                expences = expenseService.getAllExpensesForLastYear(user);
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

    /**
     * Получает статистику доходов.
     * @param period целое число, отображающее за сколько месяцев необходимо получить статистику,
     *               доступные значения 0(за все время), 1(за 1 месяц), 3(за 3 месяца), 12(за год)
     *
     * @return объект ChartData, представляющих данные о доходах в виде гистограммы.
     */
    public JFreeChart createIncomeChartHystogramm(int period, UserAccount user){
        List<Income> incomes = null;
        switch (period){
            case 0:
                incomes = incomeService.getUserIncomes(user);
                break;
            case 1:
                incomes = incomeService.getAllIncomesForLastMonth(user);
                break;
            case 3:
                incomes = incomeService.getAllIncomesForLast3Months(user);
                break;
            case 12:
                incomes = incomeService.getAllIncomesForLastYear(user);
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


    /**
     * Получает статистику по соотношению доходов и расходов.
     * @param period целое число, отображающее за сколько месяцев необходимо получить статистику,
     *               доступные значения 0(за все время), 1(за 1 месяц), 3(за 3 месяца), 12(за год)
     *
     * @return  объект ChartData, представляющий данные о соотношении в виде круговой диаграммы.
     */
    public JFreeChart createBalanceChart(int period, UserAccount user){
        Balance balance = null;
        switch (period){
            case 0:
                balance = balanceService.calculateBalance(user);
                break;
            case 1:
                balance = balanceService.calculateBalanceForLastMonth(user);
                break;
            case 3:
                balance = balanceService.calculateBalanceForLast3Months(user);
                break;
            case 12:
                balance = balanceService.calculateBalanceForLastYear(user);
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


    /**
     * Получает изображение графика и сохраняет в папке src/main/resources/static/img/.
     * @param chart объект ChartData,который генерируется за счет библиотеки JFreeChart
     * @param period целое число, отображающее за сколько месяцев необходимо получить статистику,
     *               доступные значения 0(за все время), 1(за 1 месяц), 3(за 3 месяца), 12(за год)
     *
     * @return  запись изображения в папку.
     */
    public void createImage(JFreeChart chart, int period){
        BufferedImage imageIncome = chart.createBufferedImage(800, 800);
        File outputFileIncome = new File("src/main/resources/static/img/" + chart.getTitle().getText() + period + ".png");
        try {
            ImageIO.write(imageIncome, "png", outputFileIncome);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public JFreeChart createExpenseChart(UserAccount userAccount, int period) {
        List<Expense> expences = null;
        switch (period){
            case 0:
                expences = expenseService.findByUserAccount(userAccount);
                break;
            case 1:
                expences = expenseService.getAllExpensesForLastMonth(userAccount);
                break;
            case 3:
                expences = expenseService.getAllExpensesForLast3Months(userAccount);
                break;
            case 12:
                expences = expenseService.getAllExpensesForLastYear(userAccount);
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
    @Deprecated
    public JFreeChart createIncomeChart(int period, UserAccount user) {
        List<Income> incomes = null;
        switch (period){
            case 0:
                incomes = incomeService.getUserIncomes(user);
                break;
            case 1:
                incomes = incomeService.getAllIncomesForLastMonth(user);
                break;
            case 3:
                incomes = incomeService.getAllIncomesForLast3Months(user);
                break;
            case 12:
                incomes = incomeService.getAllIncomesForLastYear(user);
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








}
