package com.apolis.todoapp.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.apolis.todoapp.R
import com.apolis.todoapp.helpers.d
import com.apolis.todoapp.models.Todo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var itemAdapter: TodoItemAdapter
    private var itemList: ArrayList<Todo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        setUp()
        onClick()
    }

    private fun setUp() {
        database = FirebaseDatabase.getInstance().reference
        itemAdapter = TodoItemAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = itemAdapter

        updateList()
    }

    private fun updateList() {
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get post object and use the values to update the UI
                itemList.clear()
                addDataToList(dataSnapshot)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                d(databaseError.toException().toString())
            }
        })
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.children.iterator()
        // Check id database contains any collection
        if (items.hasNext()) {
            val toDoListIndex = items.next()
            val itemsIterator = toDoListIndex.children.iterator()

            //check if the collection has any to do items or not
            while (itemsIterator.hasNext()) {
                //get current item
                val currentItem = itemsIterator.next()
                val todoItem = Todo()
                //get current data in a map
                val map = currentItem.value as HashMap<String, Any>
                //key will return Firebase ID
                todoItem.itemId = currentItem.key
                todoItem.done = map.get("done") as Boolean?
                todoItem.itemText = map.get("itemText") as String?
                itemList!!.add(todoItem);
            }
        }
        itemAdapter.setData(itemList)
        d(itemList.size.toString())

    }

    private fun onClick() {
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
        Log.d("ABC", "Inside onSubmit()")
        Toast.makeText(this, "Item saved with ID ${todoItem.itemId}", Toast.LENGTH_SHORT)
    }
}