package com.bit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.dto.Expense;
import com.bit.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public double getTotalExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public Map<String, Double> getTotalExpenseByMember() {
        // Calculate total expenses grouped by member, handling null or empty members
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .collect(Collectors.groupingBy(expense -> {
                    String member = expense.getMember();
                    return (member == null || member.trim().isEmpty()) ? "Unknown" : member;
                }, Collectors.summingDouble(Expense::getAmount)));
    }
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.orElse(null);  // Return null if not found
    }

    // Delete expense by ID
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
    
}
