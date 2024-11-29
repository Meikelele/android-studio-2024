package com.example.drugie_podejscie

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

// wykonywanie operacji na tabeli w bazie danych CRUD
class StudentDao(context: Context) {

    private val dbHelper = DBHelper(context)

    fun addStudent(student: Student): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(StudentTable.COLUMN_NAME, student.name)
            put(StudentTable.COLUMN_SURNAME, student.surname)
            put(StudentTable.COLUMN_UNIVERSITY, student.university)
            put(StudentTable.COLUMN_AGE, student.age)
        }
        return db.insert(StudentTable.TABLE_NAME, null, values)
    }

    fun getAllStudents(): List<Student> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            StudentTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val students = mutableListOf<Student>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(StudentTable.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(StudentTable.COLUMN_NAME))
                val surname = getString(getColumnIndexOrThrow(StudentTable.COLUMN_SURNAME))
                val university = getString(getColumnIndexOrThrow(StudentTable.COLUMN_UNIVERSITY))
                val age = getInt(getColumnIndexOrThrow(StudentTable.COLUMN_AGE))
                students.add(Student(id, name, surname, university, age))
            }
            close()
        }
        return students
    }

    fun deleteStudentById(studentId: Int): Int {
        val db = dbHelper.writableDatabase
        val selection = "${StudentTable.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(studentId.toString())
        return db.delete(StudentTable.TABLE_NAME, selection, selectionArgs)
    }

    fun updateStudent(student: Student): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(StudentTable.COLUMN_NAME, student.name)
            put(StudentTable.COLUMN_SURNAME, student.surname)
            put(StudentTable.COLUMN_UNIVERSITY, student.university)
            put(StudentTable.COLUMN_AGE, student.age)
        }
        val selection = "${StudentTable.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(student.id.toString())
        return db.update(StudentTable.TABLE_NAME, values, selection, selectionArgs)
    }
}
