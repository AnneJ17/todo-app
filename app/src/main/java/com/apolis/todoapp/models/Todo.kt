package com.apolis.todoapp.models

import java.io.Serializable

data class ToDoItem (
    var itemId: String? = null,
    var itemText: String? = null,
    var done: Boolean? = false
): Serializable {
    companion object {
        const val FIREBASE_ITEM = "todo-item"
    }
}