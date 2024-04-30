package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.repository.IncomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class IncomeService {
    private IncomeRepository incomeRepository;

    public List<Income> getAllIncomes(){
        return incomeRepository.findAll();
    }
}
