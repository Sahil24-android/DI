package com.event.eventmanagement.views.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.event.eventmanagement.R
import com.event.eventmanagement.apis.Result
import com.event.eventmanagement.databinding.FragmentExpensesFrgmentBinding
import com.event.eventmanagement.views.fragment.datasource.EventBody
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExpensesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpensesFragment : Fragment() {
    private lateinit var binding: FragmentExpensesFrgmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpensesFrgmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.addExpenses.setOnClickListener {
            showCustomDialog()
        }
    }

    private fun showCustomDialog(
    ) {
        val dialogView =
            LayoutInflater.from(requireContext())
                .inflate(R.layout.custome_expense_dialog, null)

        val expenseName = dialogView.findViewById<TextInputEditText>(R.id.expenseName)
        val expenseAmount = dialogView.findViewById<TextInputEditText>(R.id.expenseAmount)
        val expenseDate = dialogView.findViewById<MaterialTextView>(R.id.dateAndTime)
        val expenseToWhome = dialogView.findViewById<TextInputEditText>(R.id.expenseToWhome)
        val save = dialogView.findViewById<MaterialButton>(R.id.save)
        val cancel = dialogView.findViewById<MaterialButton>(R.id.cancel)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .setTitle("Event Master")
            .create()


        save.setOnClickListener {

        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    companion object {

    }
}