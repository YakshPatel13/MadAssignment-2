package com.example.madex1
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madex1.databinding.ActivityAddTaskBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTask : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Users")

        binding.addtaskbtn.setOnClickListener {
            val tasknames = binding.AddTask.text.toString()
            val descriptions = binding.description.text.toString()
            try {

                if (tasknames.isNotEmpty()) {


                    val taskid = generateUniqueTaskID()
                    val Tasks = TaskModel(taskid,tasknames)

                    databaseReference.child(taskid).setValue(Tasks,
                        object : DatabaseReference.CompletionListener {
                            override fun onComplete(
                                databaseError: DatabaseError?,
                                databaseReference: DatabaseReference
                            ) {
                                if (databaseError == null) {
                                    Toast.makeText(this@AddTask, "Data Inserted Successfully", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this@AddTask, "Error: " + databaseError.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    )
                } else {
                    Toast.makeText(this, "Error: Task ID is empty", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_LONG).show()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun generateUniqueTaskID(): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "task_$timestamp"

    }
}
