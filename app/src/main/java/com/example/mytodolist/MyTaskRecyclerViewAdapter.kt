package com.example.mytodolist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mytodolist.Data.IMPORTANCE

import com.example.mytodolist.Data.Tasks.TaskItem

/**
 * [RecyclerView.Adapter] that can display a [TaskItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyTaskRecyclerViewAdapter(
    private val values: List<TaskItem>,
    private val eventListener: ToDoListListner
) : RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val resource = when(item.importatnce){
            IMPORTANCE.LOW -> R.drawable.circle_drawable_green
            IMPORTANCE.NORMAL -> R.drawable.circle_drawable_orange
            IMPORTANCE.HIGH -> R.drawable.circle_drawable_red
        }
        holder.imageView.setImageResource(resource)
        holder.contentView.text = item.title
        holder.itemContainer.setOnClickListener {
            eventListener.onItemClick(position)
        }
        holder.itemContainer.setOnLongClickListener{
            eventListener.onItemLongClick(position)
            return@setOnLongClickListener true

        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image)
        val contentView: TextView = view.findViewById(R.id.content)
        val itemContainer: View = view.rootView

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}