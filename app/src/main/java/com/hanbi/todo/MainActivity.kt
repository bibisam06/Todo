package com.hanbi.todo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanbi.todo.databinding.ItemTodoBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding  = ItemTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val todoList = mutableListOf<Todo>()
        for (i in 1..10) {
            todoList.add(Todo("TASK $i", "TODO $i"))
        } //초기 todo 값넣어둠
        binding.itemTodo.layoutManager = LinearLayoutManager(this)
        binding.itemTodo.adapter = TodoAdapter(todoList)
        binding.itemTodo.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

    }
}