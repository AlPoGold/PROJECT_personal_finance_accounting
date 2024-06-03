package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
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
@Log
public class IncomeService {


    private IncomeRepository incomeRepository;

    public List<Income> getUserIncomes(UserAccount userAccount) {
        return incomeRepository.findAllIncomesByUserAccount(userAccount);
    }


    public List<Income> getAllIncomes(){
        return incomeRepository.findAll();
    }


    public Income addIncome(String amount, String source, Date date, UserAccount userAccount) {

        Income newIncome = new Income();
        newIncome.setAmount(BigDecimal.valueOf(Long.parseLong(amount)));
        newIncome.setSource(source);
        if(date==null){
            date = convertToDate(LocalDate.now());
        }
        newIncome.setDate(date);
        newIncome.setUserAccount(userAccount);
        return incomeRepository.save(newIncome);
    }

    public void deleteById(Long id) {
        incomeRepository.deleteById(id);
    }

    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    @Transactional
    public void updateById(Long id, Income updatedIncome, UserAccount userAccount) {
        Optional<Income> incomeOptional = incomeRepository.findById(id);
        if (incomeOptional.isPresent()) {
            Income income = incomeOptional.get();
            income.setAmount(updatedIncome.getAmount());
            income.setSource(updatedIncome.getSource());
            Date date = updatedIncome.getDate();
            if(date==null){
                updatedIncome.setDate(convertToDate(LocalDate.now()));
            }
            income.setDate(updatedIncome.getDate());
            incomeRepository.save(income);
        } else {
            throw new EntityNotFoundException("Income with id " + id + " not found");
        }
    }

    public List<Income> getAllIncomesForLast3Months(UserAccount userAccount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date threeMonthsAgo = calendar.getTime();
        Date today = new Date();
        return incomeRepository.findIncomesByDateRangeUser(threeMonthsAgo, today, userAccount);
    }

    public List<Income> getAllIncomesForLastYear(UserAccount userAccount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();
        Date today = new Date();
        return incomeRepository.findIncomesByDateRangeUser(oneYearAgo, today, userAccount);
    }

    public List<Income> getAllIncomesForLastMonth(UserAccount userAccount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();
        Date today = new Date();
        return incomeRepository.findIncomesByDateRangeUser(oneMonthAgo, today, userAccount);
    }


    public void initialRepo(UserAccount userAccount) {
        Income income = addIncome("1000", "example", convertToDate(LocalDate.now()), userAccount);
        incomeRepository.save(income);

    }

    private static Date convertToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
