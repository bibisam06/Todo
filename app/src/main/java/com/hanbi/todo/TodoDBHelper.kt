package com.hanbi.todo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDBHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //db객체 생성
    companion object {
        private const val DATABASE_NAME = "tododb"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "todos"
        var COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESC = "description"
    }


    override fun onCreate(db: SQLiteDatabase?) { //테이블 생성하는 코드, 앱설치 후 한번 실행.
        db!!.execSQL("""create table $TABLE_NAME (
        $COLUMN_ID integer primary key autoincrement,
        $COLUMN_TITLE text not null,
        $COLUMN_DESC text not null)
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


}