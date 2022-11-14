package com.deccovers.todomvvm.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deccovers.todomvvm.data.TodoEntry
import com.deccovers.todomvvm.databinding.TodoRowLayoutBinding

class TodoListAdapter(private val clickListener: TodoClickListener): ListAdapter<TodoEntry, TodoListAdapter.ViewHolder>(TodoDiffCallback) {

    companion object TodoDiffCallback: DiffUtil.ItemCallback<TodoEntry>(){
        override fun areItemsTheSame(oldItem: TodoEntry, newItem: TodoEntry) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TodoEntry, newItem: TodoEntry) = oldItem == newItem
    }

    class ViewHolder(private val binding: TodoRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(todoEntry: TodoEntry, clickListener: TodoClickListener) {
            binding.todoEntry = todoEntry
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodoRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }
}

class TodoClickListener(val clickListener: (todoEntry: TodoEntry) -> Unit) {
    fun onClick(todoEntry: TodoEntry) = clickListener(todoEntry)
}

