package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.service.ChartService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Data
@AllArgsConstructor
@Log
public class ChartController {
    private final ChartService chartService;


    @GetMapping("/charts")
    public String showIncomeChart(Model model){
        // INCOMES
        JFreeChart chartIncome = chartService.createIncomeChartHystogramm(0);
        JFreeChart chartIncomeMonth = chartService.createIncomeChartHystogramm(1);
        JFreeChart chartIncome3Months = chartService.createIncomeChartHystogramm(3);
        JFreeChart chartIncomeLastYear = chartService.createIncomeChartHystogramm(12);


        model.addAttribute("incomeChart", chartIncome);
        model.addAttribute("incomes3month", chartIncome3Months );
        model.addAttribute("incomeslastmonth", chartIncomeMonth);
        model.addAttribute("incomeslastyear", chartIncomeLastYear );


        //EXPENSES

        JFreeChart chartExpense = chartService.createExpenseChartHystogramm(0);
        JFreeChart chartExpenseLastMonth = chartService.createExpenseChartHystogramm(1);
        JFreeChart chartExpense3Months = chartService.createExpenseChartHystogramm(3);
        JFreeChart chartExpenseLastYear = chartService.createExpenseChartHystogramm(12);

        model.addAttribute("expenseChart", chartExpense);
        model.addAttribute("expenses3month", chartExpense3Months );
        model.addAttribute("expenselastmonth", chartExpenseLastMonth);
        model.addAttribute("expenselastyear", chartExpenseLastYear );



        //BALANCE
        JFreeChart chartBalance = chartService.createBalanceChart(0);
        JFreeChart chartBalanceForLastMonth = chartService.createBalanceChart(1);
        JFreeChart chartBalanceForLast3Month = chartService.createBalanceChart(3);
        JFreeChart chartBalanceForLastYear = chartService.createBalanceChart(12);



        model.addAttribute("balanceChart", chartBalance);
        model.addAttribute("balanceChartForLastMonth", chartBalanceForLastMonth);
        model.addAttribute("balanceChartForLast3Month", chartBalanceForLast3Month);
        model.addAttribute("balanceChartForLastYear", chartBalanceForLastYear);


        return "charts";
    }




}
