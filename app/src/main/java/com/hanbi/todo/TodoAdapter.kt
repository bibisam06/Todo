package com.hanbi.todo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.hanbi.todo.databinding.ItemTodoBinding

class TodoAdapter(
    private var todoList: MutableList<Todo>,
    private val onDelete : (Int) -> Unit,
    private val onUpdate : (Int) -> Unit

) : RecyclerView.Adapter<TodoAdapter.TodoHolder>(){


    inner class TodoHolder(val binding : ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        private lateinit var context: Context
        fun bind(todo: Todo, position : Int) {
            binding.task.text = todo.task
            binding.todo.text = todo.todo
            binding.btnDelete.setOnClickListener{ onDelete(position)}
            binding.btnEdit.setOnClickListener{
                onUpdate(position)
            }
        }
    }

    override fun getItemCount(): Int = todoList.size
        //항목을 구성할 때, 뷰 홀더 객체를 준비
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder = TodoHolder(ItemTodoBinding.inflate(
        LayoutInflater.from(parent.context)
    ))
    // 뷰 홀더 객체를 생성하여 반환
    override fun onBindViewHolder(holder: TodoAdapter.TodoHolder, position: Int) {
    val todo = todoList[position]
    holder.bind(todo, position)
    }

    fun updateData(newtodoList : MutableList<Todo>){
        todoList = newtodoList
        notifyDataSetChanged()
    }
    }