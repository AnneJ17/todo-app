package com.apolis.todoapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apolis.todoapp.R
import com.apolis.todoapp.helpers.d
import com.apolis.todoapp.models.Todo
import kotlinx.android.synthetic.main.row_items.view.*

class TodoItemAdapter(var context: Context) : RecyclerView.Adapter<TodoItemAdapter.ViewHolder>(){

    private var itemList: ArrayList<Todo> = ArrayList()
    private var rowListener: ItemRowListener = context as ItemRowListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    fun setData(itemList: java.util.ArrayList<Todo>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun onBind(todoItem: Todo) {
            context.d("${todoItem.itemId}, ${todoItem.itemText}")
            itemView.text_view_text.text = todoItem.itemText
            itemView.checkbox_done.isChecked = todoItem.done!!

            itemView.checkbox_done.setOnClickListener {
                rowListener.modifyItemState(todoItem.itemId!!, !todoItem.done!!)
            }
            itemView.button_delete.setOnClickListener {
                rowListener.onItemDelete(todoItem.itemId!!)
            }

        }
    }
}