package com.example.drugie_podejscie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onDeleteStudent: (Student) -> Unit, // Funkcja do usuwania
    private val onEditStudent: (Student) -> Unit    // Funkcja do edycji
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentNameSurname: TextView = itemView.findViewById(R.id.textViewStudentNameSurname)
        val studentAge: TextView = itemView.findViewById(R.id.textViewAge)
        val studentUniversity: TextView = itemView.findViewById(R.id.textViewStudentUniversity)
        val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        // Ustawianie danych w widokach
        holder.studentNameSurname.text = "${student.name} ${student.surname}"
        holder.studentAge.text = "(${student.age} yo)"
        holder.studentUniversity.text = student.university

        // Obsługa długiego naciśnięcia do usunięcia studenta
        holder.itemView.setOnLongClickListener {
            onDeleteStudent(student)
            true
        }

        // Obsługa kliknięcia przycisku EDIT
        holder.buttonEdit.setOnClickListener {
            onEditStudent(student)
        }
    }

    override fun getItemCount(): Int = students.size
}
