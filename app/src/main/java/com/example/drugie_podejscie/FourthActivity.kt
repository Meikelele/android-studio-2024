package com.example.drugie_podejscie

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FourthActivity : AppCompatActivity() {

    private lateinit var studentDao: StudentDao
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var recyclerView: RecyclerView
    private val studentsList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        // Inicjalizacja DAO
        studentDao = StudentDao(this)

        // Inicjalizacja RecyclerView i adaptera
        recyclerView = findViewById(R.id.recyclerViewStudents)
        studentAdapter = StudentAdapter(
            studentsList,
            onDeleteStudent = { student -> deleteStudent(student) },
            onEditStudent = { student -> editStudent(student) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        // Przycisk do dodawania przykładowego studenta
        val btn_addSampleStudent = findViewById<Button>(R.id.addSampleStudent)
        btn_addSampleStudent.setOnClickListener {
            addSampleStudent()
        }

        // Przycisk do dodawania studenta przez użytkownika
        val btn_addStudent = findViewById<Button>(R.id.addStudent)
        btn_addStudent.setOnClickListener {
            showAddStudentDialog()
        }

        // Załaduj studentów z bazy danych przy starcie aktywności
        loadAllStudents()
    }

    private fun addSampleStudent() {
        val student = Student(
            name = "Rick",
            surname = "Sanchez",
            university = "MIT",
            age = 69
        )
        val id = studentDao.addStudent(student)
        if (id != -1L) {
            student.id = id.toInt()
            studentsList.add(student)
            studentAdapter.notifyItemInserted(studentsList.size - 1)
            Toast.makeText(this, "Dodano studenta o ID: $id", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Błąd podczas dodawania studenta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddStudentDialog() {
        // Inflate layout dialogu
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_student, null)
        val editName = dialogView.findViewById<EditText>(R.id.editStudentName)
        val editSurname = dialogView.findViewById<EditText>(R.id.editStudentSurname)
        val editUniversity = dialogView.findViewById<EditText>(R.id.editStudentUniversity)
        val editAge = dialogView.findViewById<EditText>(R.id.editStudentAge)

        AlertDialog.Builder(this)
            .setTitle("Dodaj nowego studenta")
            .setView(dialogView)
            .setPositiveButton("Dodaj") { _, _ ->
                val name = editName.text.toString()
                val surname = editSurname.text.toString()
                val university = editUniversity.text.toString()
                val age = editAge.text.toString().toIntOrNull()

                if (name.isNotBlank() && surname.isNotBlank() && university.isNotBlank() && age != null) {
                    val student = Student(
                        name = name,
                        surname = surname,
                        university = university,
                        age = age
                    )
                    val id = studentDao.addStudent(student)
                    if (id != -1L) {
                        student.id = id.toInt()
                        studentsList.add(student)
                        studentAdapter.notifyItemInserted(studentsList.size - 1)
                        Toast.makeText(this, "Dodano studenta o ID: $id", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Błąd podczas dodawania studenta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Wszystkie pola muszą być wypełnione", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Anuluj", null)
            .show()
    }

    private fun editStudent(student: Student) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_student, null)
        val editName = dialogView.findViewById<EditText>(R.id.editStudentName)
        val editSurname = dialogView.findViewById<EditText>(R.id.editStudentSurname)
        val editUniversity = dialogView.findViewById<EditText>(R.id.editStudentUniversity)
        val editAge = dialogView.findViewById<EditText>(R.id.editStudentAge)

        editName.setText(student.name)
        editSurname.setText(student.surname)
        editUniversity.setText(student.university)
        editAge.setText(student.age.toString())

        AlertDialog.Builder(this)
            .setTitle("Edytuj studenta")
            .setView(dialogView)
            .setPositiveButton("Zapisz") { _, _ ->
                student.name = editName.text.toString()
                student.surname = editSurname.text.toString()
                student.university = editUniversity.text.toString()
                student.age = editAge.text.toString().toInt()

                val rowsUpdated = studentDao.updateStudent(student)
                if (rowsUpdated > 0) {
                    studentAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Zaktualizowano dane studenta", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Nie udało się zaktualizować danych studenta", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Anuluj", null)
            .show()
    }

    private fun deleteStudent(student: Student) {
        val rowsDeleted = studentDao.deleteStudentById(student.id)
        studentsList.remove(student)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Usunięto studenta", Toast.LENGTH_SHORT).show()
    }

    private fun loadAllStudents() {
        val students = studentDao.getAllStudents()
        studentsList.clear()
        studentsList.addAll(students)
        studentAdapter.notifyDataSetChanged()
    }
}
