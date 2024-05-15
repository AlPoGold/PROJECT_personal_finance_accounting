package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public Optional<Income> findById(Long id) {
        return incomeRepository.findById(id);
    }

    @Transactional
    public void updateById(Long id, Income updatedIncome) {
        Optional<Income> incomeOptional = incomeRepository.findById(id);
        if (incomeOptional.isPresent()) {
            Income income = incomeOptional.get();
            income.setAmount(updatedIncome.getAmount());
            income.setSource(updatedIncome.getSource());
            income.setDate(updatedIncome.getDate());
            incomeRepository.save(income);
        } else {
            throw new EntityNotFoundException("Income with id " + id + " not found");
        }
    }
}
