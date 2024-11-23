package com.example.drugie_podejscie

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FourthActivity : AppCompatActivity() {

    private lateinit var studentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        // Inicjalizacja DAO
        studentDao = StudentDao(this)

        // Przycisk do testowania funkcji
        val btn_addSampleStudent = findViewById<Button>(R.id.addSampleStudent)
        val btn_addStudent = findViewById<Button>(R.id.addStudent)


        btn_addSampleStudent.setOnClickListener {
            addSampleStudent()
            displayAllStudents()
        }
    }

    private fun addSampleStudent() {
        val student = Student(
            name = "Rick",
            surname = "Sanchez",
            university = "MIT",
            age = 69
        )
        val id = studentDao.addStudent(student)
        Toast.makeText(this, "Dodano studenta o ID: $id", Toast.LENGTH_SHORT).show()
    }

    private fun displayAllStudents() {
        val students = studentDao.getAllStudents()
        if (students.isEmpty()) {
            Toast.makeText(this, "Brak studentów w bazie", Toast.LENGTH_SHORT).show()
        } else {
            students.forEach { student ->
                println("Student: ${student.name} ${student.surname}, Wiek: ${student.age}, Uniwersytet: ${student.university}")
            }
            Toast.makeText(this, "Wypisano ${students.size} studentów w konsoli", Toast.LENGTH_SHORT).show()
        }
    }
}
