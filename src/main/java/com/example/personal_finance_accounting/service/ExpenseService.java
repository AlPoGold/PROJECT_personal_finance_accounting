package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    /**
     * получаем список всех расходов
     *
     * @param userAccount учетная запись пользователя, который прошел авторизации.
     */
    public List<Expense> getUserExpenses(UserAccount userAccount) {
        return expenseRepository.findByUserAccount(userAccount);
    }


    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense addExpense(String amount, String category, Date date, UserAccount userAccount) {
        if(date==null){
            date = convertToDate(LocalDate.now());
        }
        Expense newExpense = new Expense();
        try{
            Double amountDouble = Double.parseDouble(amount);
            newExpense.setAmount(BigDecimal.valueOf(Double.parseDouble(amount)));
        } catch (Exception e){
            newExpense.setAmount(BigDecimal.ZERO);
        }
        newExpense.setAmount(BigDecimal.valueOf(Double.parseDouble(amount)));
        newExpense.setCategory(category);
        newExpense.setDate(date);
        newExpense.setUserAccount(userAccount);
        return expenseRepository.save(newExpense);


    }

    /**
     * Удаляет расход по идентификатору.
     *
     * @param id Идентификатор расхода.
     */
    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Transactional
    public void updateById(Long id, Expense updatedExpense) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        if (expenseOptional.isPresent()) {
            if(updatedExpense.getDate()==null){
                updatedExpense.setDate(convertToDate(LocalDate.now()));
            }
            Expense expense = expenseOptional.get();
            expense.setAmount(updatedExpense.getAmount());
            expense.setCategory(updatedExpense.getCategory());
            expense.setDate(updatedExpense.getDate());
            expenseRepository.save(expense);
        } else {
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
    }

    public List<Expense> getAllExpensesForLast3Months(UserAccount user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date threeMonthsAgo = calendar.getTime();
        Date today = new Date();
        return expenseRepository.findExpenseByDateRangeUser(threeMonthsAgo, today, user);
    }

    public List<Expense> getAllExpensesForLastYear(UserAccount user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();
        Date today = new Date();
        return expenseRepository.findExpenseByDateRangeUser(oneYearAgo, today, user);
    }

    public List<Expense> getAllExpensesForLastMonth(UserAccount user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();
        Date today = new Date();
        return expenseRepository.findExpenseByDateRangeUser(oneMonthAgo, today, user);
    }

    public void initialRepo(UserAccount userAccount) {
        Expense expense = addExpense("1000", "example", convertToDate(LocalDate.now()), userAccount);

    }


    private static Date convertToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public List<Expense> findByUserAccount(UserAccount userAccount) {
        return expenseRepository.findByUserAccount(userAccount);
    }
}
