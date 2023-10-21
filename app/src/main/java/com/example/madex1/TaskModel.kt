package com.example.madex1

data class TaskModel(
    val taskId: String = "",
    val taskName: String = "",
    val isChecked: Boolean = false
) {
    constructor() : this("", "", false)
}

