package com.vadim.dev.mobile.common

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment


class NewExpenseDialog : DialogFragment(R.layout.dialog_new_expense) {

    private var onConfirmListener: ((Expense) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val price_edt = view.findViewById<EditText>(R.id.price_edt);
        val name_edt = view.findViewById<EditText>(R.id.name_edt);
        val confirm_button = view.findViewById<Button>(R.id.confirm_button);

        confirm_button.setOnClickListener {
            if (name_edt.text.isEmpty()) {
                name_edt.error = "Must enter a name"
                return@setOnClickListener
            }
            if (price_edt.text.isEmpty()) {
                price_edt.error = "Must enter a price"
                return@setOnClickListener
            }
            onConfirmListener?.let {
                it(
                    Expense(

                        name_edt.text.toString(),
                        price_edt.text.toString().toDouble()
                    )
                )
            }
            dismiss()
        }
    }

    fun setOnConfirmListener(onConfirmListener: ((Expense) -> Unit)) {
        this.onConfirmListener = onConfirmListener
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = (dialog!!.window!!.attributes.width * 0.8).toInt()
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

}