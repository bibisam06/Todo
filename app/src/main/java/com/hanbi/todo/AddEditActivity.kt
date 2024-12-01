package com.hanbi.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hanbi.todo.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private lateinit var todoStorage: TodoStorage
    private lateinit var todoList: MutableList<Todo>
    //1. 디비 가져오기
    //private val db = openOrCreateDatabase("tododb", Context.MODE_PRIVATE, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        todoStorage = TodoStorage(this)
        todoList = todoStorage.loadTodos()
        setContentView(binding.root)

        //저장
        binding.btnSave.setOnClickListener{
            val todo = binding.etTitle.text.toString()
            val task = binding.etDescription.text.toString()

            if(todo.isNotBlank() && task.isNotBlank()){
                val newTodo = Todo(todo = todo, task = task)
                todoStorage.saveTodos(newTodo)

                setResult(RESULT_OK)
                finish() //화면 되돌리기
            }
        }


    }
}