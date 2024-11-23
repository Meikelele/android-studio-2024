package com.example.drugie_podejscie

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "students.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tworzenie tabeli studentów
        db.execSQL(StudentTable.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Usunięcie starej tabeli, jeśli istnieje, i stworzenie nowej
        db.execSQL(StudentTable.SQL_DELETE_TABLE)
        onCreate(db)
    }
}
