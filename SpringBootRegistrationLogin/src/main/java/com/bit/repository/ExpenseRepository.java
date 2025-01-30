package com.bit.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bit.dto.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}

