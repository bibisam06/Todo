package com.hanbi.todo

import androidx.recyclerview.widget.RecyclerView
import com.hanbi.todo.databinding.ItemTodoBinding

class TodoHolder(val binding : ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(todo: Todo) {
        binding.task.text = todo.task
        binding.todo.text = todo.todo
    }
}