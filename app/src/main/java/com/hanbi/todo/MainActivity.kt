package com.hanbi.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanbi.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var todoList: MutableList<Todo> = mutableListOf()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var todoStorage: TodoStorage

    private val addEditActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (data != null) {
            val todoId = data.getIntExtra("todoId", -1)
            val updatedTodo = data.getStringExtra("todo") ?: ""
            val updatedTask = data.getStringExtra("task") ?: ""

            // RecyclerView 갱신
            adapter.updateData(todoList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoStorage = TodoStorage(this)
        todoList = todoStorage.loadTodos()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(todoList, ::onDelete, ::onUpdate)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            addEditActivityResultLauncher.launch(intent)
        }
    }

    private fun onDelete(position: Int) {
        val todo = todoList[position]
        todoStorage.deleteTodos(todo.id!!) // DATA Update
        todoList.removeAt(position)
        adapter.updateData(todoList)
    }

    private fun onUpdate(position: Int) {
        val todo: Todo = todoList[position]
        val intent = Intent(this, AddEditActivity::class.java).apply {
            putExtra("todoId", todo.id) // ID도 전달
            putExtra("todo", todo.todo) // 제목 전달
            putExtra("task", todo.task) // 상세 설명 전달
        }
        addEditActivityResultLauncher.launch(intent)
    }
}
