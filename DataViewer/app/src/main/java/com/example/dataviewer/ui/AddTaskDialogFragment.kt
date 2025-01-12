package com.example.dataviewer.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.dataviewer.data.Task
import com.example.dataviewer.R

class AddTaskDialogFragment(private val onTaskAdded: (Task) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_add_task, null)

        val editTextTitle: EditText = view.findViewById(R.id.editTextTitle)
        val editTextDescription: EditText = view.findViewById(R.id.editTextDescription)
        val spinnerPriority: Spinner = view.findViewById(R.id.spinnerPriority)

        return AlertDialog.Builder(requireContext())
            .setTitle("Přidat úkol")
            .setView(view)
            .setPositiveButton("Přidat") { _, _ ->
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                val priority = spinnerPriority.selectedItem.toString().toInt()

                Log.d("AddTaskDialog", "Title: $title, Description: $description, Priority: $priority")

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    onTaskAdded(Task(title = title, description = description, priority = priority))
                } else {
                    Log.e("AddTaskDialog", "Title or Description is empty!")
                }

                onTaskAdded(Task(title = title, description = description, priority = priority))
            }
            .setNegativeButton("Zrušit", null)
            .create()
    }
}
