package com.hanbi.todo


import android.database.Cursor
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import com.google.gson.Gson

class TodoStorage (context : Context) {
    private val dbHelper = TodoDBHelper(context)
    private val prefs: SharedPreferences =
        context.getSharedPreferences("todo_pref",
            Context.MODE_PRIVATE)
    private val gson = Gson()

    //Cursor으로 행 데이터 가져오기, R
    fun loadTodos(): MutableList<Todo> {
        val todoList = mutableListOf<Todo>() //list초기화
        val db : SQLiteDatabase = dbHelper.readableDatabase
        val cursor : Cursor = db.query(TodoDBHelper.TABLE_NAME, arrayOf(TodoDBHelper.COLUMN_ID, TodoDBHelper.COLUMN_TITLE, TodoDBHelper.COLUMN_DESC),
            null,
            null,
            null,
            null,
            null)
        cursor.use{
            if (cursor.moveToFirst()) { // 커서를 첫 번째 행으로 이동
                do {
                    // 각 컬럼에서 데이터 읽기
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDBHelper.COLUMN_ID))
                    val todo = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.COLUMN_TITLE))
                    val task = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.COLUMN_DESC))

                    // Todo 객체 생성 및 리스트에 추가
                    todoList.add(Todo(id, todo, task))
                } while (cursor.moveToNext()) // 다음 행으로 이동
            }
        }

        db.close()
        return todoList
    }

    fun saveTodos(todo : Todo) : Long{
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TodoDBHelper.COLUMN_TITLE, todo.todo)
            put(TodoDBHelper.COLUMN_DESC, todo.task)
        }
        val id = db.insert(TodoDBHelper.TABLE_NAME,
            null,
            values)
        db.close()
        return id
    }

    fun deleteTodos(id : Int) : Int{ //id값 -> int 값 받아와서 삭제 -> id값 반환
        val db : SQLiteDatabase = dbHelper.writableDatabase
        val rowDeleted = db.delete(TodoDBHelper.TABLE_NAME,  "${TodoDBHelper.COLUMN_ID} = ?",  arrayOf(id.toString())) //배열형식으로 전달해야함.
        db.close()
        return rowDeleted
    }

    fun updateTodos(todo : Todo){
        val db : SQLiteDatabase = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(TodoDBHelper.COLUMN_ID, todo.id)
            put(TodoDBHelper.COLUMN_TITLE, todo.todo)
            put(TodoDBHelper.COLUMN_DESC, todo.task)
        }
        db.update(TodoDBHelper.TABLE_NAME, contentValues, "${TodoDBHelper.COLUMN_ID} = ?",
            arrayOf(todo.id.toString())
        )
    }


}