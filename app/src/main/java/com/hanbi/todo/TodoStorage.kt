package com.hanbi.todo

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TodoStorage (context : Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("todo_pref",
            Context.MODE_PRIVATE)
    private val gson = Gson()

    fun loadTodos(): ArrayList<Todo> {
        val json = prefs.getString("todo_list", "")
        val type = object : TypeToken<ArrayList<Todo>>() {}.type
        return if (json.isNullOrEmpty()) ArrayList() else
            gson.fromJson(json, type)
    }

    fun saveTodos(todoList: ArrayList<Todo>) {
        val json = gson.toJson(todoList)
        prefs.edit().putString("todo_list", json).apply()
    }

}