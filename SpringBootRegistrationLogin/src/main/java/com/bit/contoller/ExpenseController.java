package com.bit.contoller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bit.dto.Expense;
import com.bit.service.ExpenseService;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/add-expense")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "addExpense";
    }

    @PostMapping("/add-expense")
    public String addExpense(@ModelAttribute Expense expense) {
        if (expense.getMember() == null || expense.getMember().trim().isEmpty()) {
            expense.setMember("Unknown");  // Default to "Unknown" if member is not provided
        }
        expenseService.saveExpense(expense);
        return "redirect:/view-expenses";
    }

    @GetMapping("/view-expenses")
    public String viewExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        model.addAttribute("totalExpense", expenseService.getTotalExpenses());

        // Get the total expense by each member
        Map<String, Double> totalExpenseByMember = expenseService.getTotalExpenseByMember();
        model.addAttribute("totalExpenseByMember", totalExpenseByMember);

        return "viewExpenses";
    }
    @GetMapping("/edit-expense/{id}")
    public String editExpense(@PathVariable("id") Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense != null) {
            model.addAttribute("expense", expense);
            return "editExpense";  // The template to edit an expense
        } else {
            return "redirect:/view-expenses";  // If the expense doesn't exist, redirect to the list
        }
    }

    // Method to update an existing expense
    @PostMapping("/update-expense")
    public String updateExpense(@ModelAttribute Expense expense) {
        expenseService.saveExpense(expense);  // Reuse the save method to update
        return "redirect:/view-expenses";  // Redirect to the list after updating
    }

    // Method to delete an expense
    @GetMapping("/delete-expense/{id}")
    public String deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/view-expenses";  // Redirect to the list after deleting
    }
}
