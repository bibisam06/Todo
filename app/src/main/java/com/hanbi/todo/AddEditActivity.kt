package com.hanbi.todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hanbi.todo.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private lateinit var todoStorage: TodoStorage
    private lateinit var todoList: MutableList<Todo>
    private var todoId: Int? = null
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        todoStorage = TodoStorage(this)
        todoList = todoStorage.loadTodos()
        setContentView(binding.root)

        intent?.let {
            todoId = it.getIntExtra("todoId", -1)

            binding.etTitle.setText(it.getStringExtra("todo"))
            binding.etDescription.setText(it.getStringExtra("task"))
        }
        //저장
        binding.btnSave.setOnClickListener{
            val todo = binding.etTitle.text.toString()
            val task = binding.etDescription.text.toString()

            if(todo.isNotBlank() && task.isNotBlank()) {
                if (todoId == -1) {
                    val newTodo = Todo(todo = todo, task = task)
                    todoStorage.saveTodos(newTodo)
                    setResult(RESULT_OK)
                    finish()

                } else {
                    val resultIntent = Intent().apply {
                        putExtra("todoId", todoId) // 기존 ID 반환
                        putExtra("todo", todo)    // 수정된 제목
                        putExtra("task", task)    // 수정된 내용
                    }
                    val newTodo = Todo(id = todoId, todo = todo, task = task)
                    setResult(RESULT_OK, resultIntent)
                    todoStorage.updateTodos(newTodo)
                    todoList.add(newTodo)
                    adapter.updateData(todoList)// 결과 전달
                    finish() // AddEditActivity 종료

                }
            }
            }
        }


}