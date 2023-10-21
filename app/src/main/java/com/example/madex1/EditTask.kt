package com.example.madex1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditTask : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        val taskId = intent.getStringExtra("taskId")
        val taskName = intent.getStringExtra("taskName")
        val view = findViewById<TextView>(R.id.Taskname)
        view.text = taskName
        val saveButton = findViewById<Button>(R.id.updatetask)
        val editTaskNameEditText = findViewById<EditText>(R.id.Taskname)
        saveButton.setOnClickListener {
            val updatedTaskName = editTaskNameEditText.text.toString()

            if (updatedTaskName.isNotEmpty()) {

                if (taskId != null) {
                    dbRef.child(taskId).child("taskName").setValue(updatedTaskName)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Log.d("Firebase", "Data updated successfully")
                                finish()
                            } else {

                                Log.e("Firebase", "Data update failed")
                            }
                        }
                }
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }
        }

    }
}