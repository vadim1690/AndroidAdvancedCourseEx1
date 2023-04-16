package com.vadim.dev.mobile.common

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.google.gson.reflect.TypeToken
import com.vadim.dev.mobile.common.utils.AppUtils
import com.vadim.dev.mobile.common.utils.MSP

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalBudgetTV: MaterialTextView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var add_expense_btn: Button
    private lateinit var add_budget_button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        initValues()

    }

    private fun initValues() {

        totalBudgetTV.text = AppUtils.formatDoubleWithDecimal(MSP.getInstance().getString("TOTAL_BUDGET", "0")) + " $"
        setAdapter()
    }

    private fun setAdapter() {
        adapter = ExpenseAdapter(getExpenseList())
        adapter.setOnItemClickListener { expenseSelected(it) }
        recyclerView.adapter = adapter
    }

    private fun findViews() {
        totalBudgetTV = findViewById(R.id.total_budget_TV)
        recyclerView = findViewById(R.id.recyclerView)
        add_expense_btn = findViewById(R.id.add_expense_btn)
        add_budget_button = findViewById(R.id.add_budget_button)
        add_expense_btn.setOnClickListener { addNewExpense() }
        add_budget_button.setOnClickListener { addBudget() }
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun addBudget() {
        var budgetDouble = MSP.getInstance().getString("TOTAL_BUDGET", "0").toDouble()
        budgetDouble += 2500.0
        Toast.makeText(this, "2500 $ added", Toast.LENGTH_SHORT).show()
        MSP.getInstance().putString("TOTAL_BUDGET", budgetDouble.toString())

        totalBudgetTV.text = AppUtils.formatDoubleWithDecimal(MSP.getInstance().getString("TOTAL_BUDGET", budgetDouble.toString())) + " $"
    }

    private fun addNewExpense() {
        val dialog = NewExpenseDialog()
        dialog.setOnConfirmListener {
            val list = getExpenseList()
            list.add(it)
            putList(list)
            setAdapter()
        }
        dialog.show(supportFragmentManager, dialog.tag)

    }

    private fun expenseSelected(expense: Expense) {
        val price = expense.price
        var budgetText = totalBudgetTV.text.toString()
        budgetText = budgetText.removeSuffix("$")
        if (budgetText.equals("0")) {
            Toast.makeText(this, "No budget left", Toast.LENGTH_SHORT).show()
            return
        }
        budgetText = (budgetText.toDouble() - price).toString()
        MSP.getInstance().putString("TOTAL_BUDGET", budgetText)
        totalBudgetTV.text = AppUtils.formatDoubleWithDecimal(budgetText) + " $"

    }

    private fun getExpenseList(): ArrayList<Expense> {
        val expenseType = object : TypeToken<List<Expense>>() {}
        var list: ArrayList<Expense>? = MSP.getInstance().getArray("EXPENSES_LIST", expenseType)
        if (list == null) list = ArrayList()
        return list
    }

    private fun putList(list: ArrayList<Expense>) =
        MSP.getInstance().putArray("EXPENSES_LIST", list)
}