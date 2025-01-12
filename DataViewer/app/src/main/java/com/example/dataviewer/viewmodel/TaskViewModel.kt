package com.example.dataviewer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.dataviewer.data.Task
import com.example.dataviewer.data.TaskDatabase
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    fun addTask(task: Task) {
        Log.d("TaskViewModel", "Adding task: $task")
        viewModelScope.launch {
            taskDao.insert(task)
            Log.d("TaskViewModel", "Task added")
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }

    // Filtrace podle dokončených úkolů
    fun getCompletedTasks(): LiveData<List<Task>> {
        return taskDao.getTasksByCompletion(true)
    }

    // Filtrace podle ne-dokončených úkolů
    fun getIncompleteTasks(): LiveData<List<Task>> {
        return taskDao.getTasksByCompletion(false)
    }
}
