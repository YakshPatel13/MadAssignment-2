package com.example.madex1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madex1.TaskModel
import com.google.firebase.database.DatabaseReference

class TaskAdapter(
    private val taskList: List<TaskModel>,
    private val dbRef: DatabaseReference
) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onCheckboxClick(position: Int, isChecked: Boolean)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tasklayout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.bind(currentTask)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskCheckBox: CheckBox = itemView.findViewById(R.id.todoCheckBox)

        fun bind(task: TaskModel) {
            taskCheckBox.text = task.taskName
            taskCheckBox.isChecked = task.isChecked

            taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
                listener?.onCheckboxClick(adapterPosition, isChecked)
            }
        }
    }
}
