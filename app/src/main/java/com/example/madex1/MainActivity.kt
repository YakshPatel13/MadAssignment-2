package com.example.madex1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.example.madex1.TaskModel

class MainActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var taskArrayList: ArrayList<TaskModel>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addButton: FloatingActionButton = findViewById(R.id.addbtn)
        addButton.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }

        userRecyclerView = findViewById(R.id.recylerview1)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        taskArrayList = ArrayList()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        val adapter = TaskAdapter(taskArrayList, dbRef)


        userRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : TaskAdapter.OnItemClickListener {
            override fun onCheckboxClick(position: Int, isChecked: Boolean) {
                if (isChecked) {
                    val taskId = taskArrayList[position].taskId
                    dbRef.child(taskId).removeValue()
                }
            }

        })

        getTaskData()
    }

    private fun getTaskData() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    taskArrayList.clear()
                    for (userSnapshot in snapshot.children) {
                        val task = userSnapshot.getValue(TaskModel::class.java)
                        if (task != null) {
                            taskArrayList.add(task)
                        }
                    }
                    userRecyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
