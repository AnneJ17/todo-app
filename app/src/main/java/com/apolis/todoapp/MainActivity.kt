package com.apolis.todoapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.apolis.todoapp.models.Todo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        database = FirebaseDatabase.getInstance().reference

        fab_button_add.setOnClickListener {
            addNewItemDialog()
        }

    }

    private fun addNewItemDialog() {
        val alert = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        alert.setMessage("Add New Item")
        alert.setTitle("Enter To Do Item Text")
        alert.setView(itemEditText)
        alert.setPositiveButton("Submit") { dialog, positiveButton ->
            Toast.makeText(this, "Here", Toast.LENGTH_SHORT).show()
            onSubmit(itemEditText, dialog)
        }
        alert.show()
    }

    private fun onSubmit(itemEditText: EditText, dialog: DialogInterface?) {
        var todoItem = Todo()
        todoItem.itemText = itemEditText.text.toString()
        todoItem.done = false
        //We first make a push so that a new item is made with a unique ID
        val newItem = database.child(Todo.FIREBASE_ITEM).push()
        todoItem.itemId = newItem.key
        //then, we used the reference to set the value on that ID
        newItem.setValue(todoItem)
        dialog?.dismiss()
        Log.d("ABC", "Failed to store in db")
        Toast.makeText(this, "Item saved with ID ${todoItem.itemId}", Toast.LENGTH_SHORT)
    }
}