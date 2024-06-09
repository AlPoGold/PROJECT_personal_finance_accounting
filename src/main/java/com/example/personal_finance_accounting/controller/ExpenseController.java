package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.service.ExpenseService;
import com.example.personal_finance_accounting.service.FileLogger;
import com.example.personal_finance_accounting.service.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Controller
@RequestMapping("/expenses")
@AllArgsConstructor
@Log
public class ExpenseController {

    private ExpenseService expenseService;
    private UserAccountService userAccountService;

    /**
     * Отображает страницу с расходами.
     *
     * @param model Модель для передачи данных в шаблон.
     * @return Имя представления для отображения страницы.
     */
    @GetMapping
    public String getExpenses(Model model, Authentication authentication) {
        UserAccount userAccount = userAccountService.findByEmail(authentication.getName());
        Long userId = userAccount.getUserId();
        List<Expense> expenses = expenseService.getUserExpenses(userAccount);
        if(expenses.size()>=2){
            expenses.sort(Comparator.comparing(Expense::getDate));

        }

        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("userId", userId);
        return "expenses";
    }

    /**
     * Добавляет новый расход.
     *
     * @param amount сумма расхода.
     * @param category категория расхода.
     * @param date дата, когда произведена оплата.
     * @param authentication сессия пользователя.
     * @return Перенаправление на страницу с расходами.
     */
    @PostMapping
    public String addExpense(@RequestParam("amount") String amount,
                            @RequestParam("category") String category,
                            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                             Authentication authentication){
        UserAccount user = userAccountService.findByEmail(authentication.getName());
        expenseService.addExpense(amount, category, date, user);
        log.log(Level.INFO, "expense was added!");
        FileLogger.log(user, "expense was added!" + "|" + "-" +amount);


        return "redirect:/expenses";
    }


    /**
     * Удаляет расход по идентификатору.
     *
     * @param id Идентификатор расхода.
     * @return Перенаправление на страницу с расходами.
     */
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteById(id);
        return "redirect:/expenses";
    }

    @GetMapping("/update/{id}")
    public String updateIncome(@PathVariable("id") Long id, Model model){
        expenseService.findById(id).ifPresent(expense -> model.addAttribute("expense", expense));
        return "redirect:/expense/update/{id}";
    }


    /**
     * Обновляет существующий расход.
     *
     * @param id Идентификатор расхода.
     * @param updatedExpense Обновленный расход.
     * @return Перенаправление на страницу с расходами.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExpense(@PathVariable("id") Long id, @RequestBody Expense updatedExpense) {
        Optional<Expense> existingExpenseOptional = expenseService.findById(id);
        if (existingExpenseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        expenseService.updateById(id, updatedExpense);
        return ResponseEntity.ok("Expense updated successfully");
    }


}
