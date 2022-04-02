package com.multi.trivia.ui.quiz.exit

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

class ExitDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder
                .setTitle("Confirm")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Proceed") { dialog, _ ->
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        "selectedOption",
                        "Proceed"
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")

    }

}