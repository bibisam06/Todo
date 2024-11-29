package com.hanbi.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
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
        if (result.resultCode == RESULT_OK) {
            todoList = todoStorage.loadTodos()
            adapter.updateData(todoList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        todoStorage = TodoStorage(this)
        todoList = todoStorage.loadTodos()
        setContentView(binding.root) //초기데이터불러옴.

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(todoList)
        binding.recyclerView.adapter = adapter
        //어댑터초기화
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        binding.fabAdd.setOnClickListener{
            val intent : Intent = Intent(this, AddEditActivity::class.java)
            addEditActivityResultLauncher.launch(intent)
        }
    }
}