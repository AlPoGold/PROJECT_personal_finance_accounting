package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.repository.IncomeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@AllArgsConstructor
@Service
@Log
public class IncomeService {


    private IncomeRepository incomeRepository;

    public List<Income> getAllIncomes(){
        log.log(Level.INFO, incomeRepository.findAll().toString());
        return incomeRepository.findAll();
    }

    public Income addIncome(String amount, String source, Date date) {

        Income newIncome = new Income();
        newIncome.setAmount(BigDecimal.valueOf(Long.parseLong(amount)));
        newIncome.setSource(source);
        newIncome.setDate(date);
        return incomeRepository.save(newIncome);
    }

    public void deleteById(Long id) {
        incomeRepository.deleteById(id);
    }

    public Income findByid(Long id) {

        return incomeRepository.findById(id).orElse(null);
    }
}
