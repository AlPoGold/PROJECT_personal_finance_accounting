package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.service.ChartService;
import com.example.personal_finance_accounting.service.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.jfree.chart.JFreeChart;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Data
@AllArgsConstructor
@Log
public class ChartController {
    private final ChartService chartService;
    private final UserAccountService userAccountService;

    /**
     * Отображает страницу с графиками доходов, расходов и баланса.
     *
     * @param model Модель для передачи данных в шаблон.
     * @return Имя представления для отображения страницы.
     */
    @GetMapping("/charts")
    public String showIncomeChart(Model model, Authentication auth){
        UserAccount userAccount = userAccountService.findByEmail(auth.getName());
        if (userAccount == null) {
            return "redirect:/login";
        }
        // INCOMES
        JFreeChart chartIncome = chartService.createIncomeChartHystogramm(0, userAccount);
        JFreeChart chartIncomeMonth = chartService.createIncomeChartHystogramm(1, userAccount);
        JFreeChart chartIncome3Months = chartService.createIncomeChartHystogramm(3, userAccount);
        JFreeChart chartIncomeLastYear = chartService.createIncomeChartHystogramm(12, userAccount);


        model.addAttribute("incomeChart", chartIncome);
        model.addAttribute("incomes3month", chartIncome3Months );
        model.addAttribute("incomeslastmonth", chartIncomeMonth);
        model.addAttribute("incomeslastyear", chartIncomeLastYear );


        //EXPENSES

        JFreeChart chartExpense = chartService.createExpenseChartHystogramm(0, userAccount);
        JFreeChart chartExpenseLastMonth = chartService.createExpenseChartHystogramm(1, userAccount);
        JFreeChart chartExpense3Months = chartService.createExpenseChartHystogramm(3, userAccount);
        JFreeChart chartExpenseLastYear = chartService.createExpenseChartHystogramm(12, userAccount);

        model.addAttribute("expenseChart", chartExpense);
        model.addAttribute("expenses3month", chartExpense3Months );
        model.addAttribute("expenselastmonth", chartExpenseLastMonth);
        model.addAttribute("expenselastyear", chartExpenseLastYear );


        //BALANCE
        JFreeChart chartBalance = chartService.createBalanceChart(0, userAccount);
        JFreeChart chartBalanceForLastMonth = chartService.createBalanceChart(1, userAccount);
        JFreeChart chartBalanceForLast3Month = chartService.createBalanceChart(3, userAccount);
        JFreeChart chartBalanceForLastYear = chartService.createBalanceChart(12, userAccount);



        model.addAttribute("balanceChart", chartBalance);
        model.addAttribute("balanceChartForLastMonth", chartBalanceForLastMonth);
        model.addAttribute("balanceChartForLast3Month", chartBalanceForLast3Month);
        model.addAttribute("balanceChartForLastYear", chartBalanceForLastYear);
        model.addAttribute("userId", userAccount.getUserId());


        return "charts";
    }




}
