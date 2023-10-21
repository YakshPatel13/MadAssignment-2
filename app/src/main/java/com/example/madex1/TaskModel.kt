package com.example.madex1

data class TaskModel(
    val taskId: String = "", // Unique identifier for the task
    val taskName: String = "", // Name of the task
    val isChecked: Boolean = false // Whether the task is checked or not
) {
    // Default (no-argument) constructor
    constructor() : this("", "", false)
}

