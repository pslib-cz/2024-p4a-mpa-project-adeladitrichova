package com.example.dataviewer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dataviewer.R
import com.example.dataviewer.data.Task

class TaskAdapter(
    var tasks: List<Task>,
    private val onTaskChecked: (Task) -> Unit,
    private val onDeleteTask: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.taskTitle)
        val description: TextView = itemView.findViewById(R.id.taskDescription)
        val priority: TextView = itemView.findViewById(R.id.taskPriority)
        val completed: CheckBox = itemView.findViewById(R.id.taskCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.description.text = task.description
        holder.priority.text = "Priority: ${task.priority}"
        holder.completed.isChecked = task.isCompleted

        holder.completed.setOnCheckedChangeListener { _, isChecked ->
            onTaskChecked(task.copy(isCompleted = isChecked))
        }

        holder.itemView.setOnLongClickListener {
            onDeleteTask(task)
            true
        }
    }

    override fun getItemCount(): Int = tasks.size
}
