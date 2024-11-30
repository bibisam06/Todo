package com.hanbi.todo

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TodoStorage (context : Context) {
    private val dbHelper = TodoDBHelper(context)
    private val prefs: SharedPreferences =
        context.getSharedPreferences("todo_pref",
            Context.MODE_PRIVATE)
    private val gson = Gson()

    fun loadTodos(): MutableList<Todo> {
        val json = prefs.getString("todo_list", "")
        val type = object : TypeToken<ArrayList<Todo>>() {}.type
        return if (json.isNullOrEmpty()) ArrayList() else
            gson.fromJson(json, type)
    }

    fun saveTodos(todo : Todo) {
        val db : SQLiteDatabase = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoDBHelper.COLUMN_TITLE, todo.todo)
            put(TodoDBHelper.COLUMN_DESC, todo.task)
        }
        db.insert(TodoDBHelper.TABLE_NAME, null, values )
        db.close()
    }


}