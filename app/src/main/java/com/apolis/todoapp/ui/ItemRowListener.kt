package com.apolis.todoapp.ui

interface ItemRowListener {
    fun modifyItemState(itemId: String, isDone: Boolean)
    fun onItemDelete(itemId: String)
}