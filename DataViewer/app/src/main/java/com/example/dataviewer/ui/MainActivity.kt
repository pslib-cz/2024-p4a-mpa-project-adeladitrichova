package com.example.dataviewer.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dataviewer.adapters.TaskAdapter
import com.example.dataviewer.viewmodel.TaskViewModel
import com.example.dataviewer.R
import com.example.dataviewer.data.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val fabAddTask = findViewById<FloatingActionButton>(R.id.fabAddTask)

        val adapter = TaskAdapter(
            tasks = emptyList(),
            onTaskChecked = { task ->
                Log.d("MainActivity", "Updating task: ${task.title}")
                taskViewModel.updateTask(task)
            },
            onDeleteTask = { task ->
                Log.d("MainActivity", "Deleting task: ${task.title}")
                taskViewModel.deleteTask(task)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe tasks and update adapter
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            tasks?.let {
                Log.d("MainActivity", "Tasks updated: ${it.size} tasks")
                adapter.tasks = it
                adapter.notifyDataSetChanged()
            }
        })

        // FloatingActionButton click listener
        fabAddTask.setOnClickListener {
            Log.d("MainActivity", "Add task button clicked")
            showAddTaskDialog()
        }

        Log.d("MainActivity", "onCreate called")
    }

    private fun showAddTaskDialog() {
        val dialog = AddTaskDialogFragment { task ->
            Log.d("MainActivity", "Adding new task: ${task.title}")
            taskViewModel.addTask(task)
        }

        // Prevent dialog from showing if activity is finishing or destroyed
        if (!isFinishing && !isDestroyed) {
            dialog.show(supportFragmentManager, "AddTaskDialog")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop called")
    }

    override fun onDestroy() {
        // Cleanup RecyclerView adapter
        findViewById<RecyclerView>(R.id.recyclerView)?.adapter = null

        // Cleanup FloatingActionButton listener
        findViewById<FloatingActionButton>(R.id.fabAddTask)?.setOnClickListener(null)

        Log.d("MainActivity", "onDestroy called")
        super.onDestroy()
    }
}
