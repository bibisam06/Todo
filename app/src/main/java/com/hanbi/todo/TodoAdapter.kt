package com.hanbi.todo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hanbi.todo.databinding.ItemTodoBinding

class TodoAdapter(val todoList: MutableList<Todo>) : RecyclerView.Adapter<TodoHolder>(){

    override fun getItemCount(): Int = todoList.size
        //항목을 구성할 때, 뷰 홀더 객체를 준비
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder = TodoHolder(ItemTodoBinding.inflate(
        LayoutInflater.from(parent.context)
    ))
// 뷰 홀더 객체를 생성하여 반환
    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        Log.d("hanbi", "onBindViewHolder : $position")
    val todo = todoList[position]
    holder.binding.todo.text = todo.todo
    holder.binding.task.text = todo.task
    }
}