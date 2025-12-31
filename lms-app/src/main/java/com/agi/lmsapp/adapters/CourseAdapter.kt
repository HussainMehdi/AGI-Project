package com.agi.lmsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.lmsapp.R
import com.agi.lmsapp.models.Course

class CourseAdapter(
    private val courses: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    
    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.courseName)
        val instructorText: TextView = view.findViewById(R.id.instructorName)
        val descriptionText: TextView = view.findViewById(R.id.courseDescription)
        val studentsText: TextView = view.findViewById(R.id.studentsCount)
        val assignmentsText: TextView = view.findViewById(R.id.assignmentsCount)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.nameText.text = course.name
        holder.instructorText.text = course.instructor
        holder.descriptionText.text = course.description
        holder.studentsText.text = "${course.students} students"
        holder.assignmentsText.text = "${course.assignments} assignments"
        
        holder.itemView.setOnClickListener {
            onItemClick(course)
        }
    }
    
    override fun getItemCount() = courses.size
}

