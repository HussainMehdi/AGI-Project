package com.agi.lmsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agi.lmsapp.adapters.AssignmentAdapter
import com.agi.lmsapp.data.SampleData
import com.agi.lmsapp.databinding.ActivityCourseDetailBinding
import com.agi.lmsapp.models.Course

class CourseDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseDetailBinding
    private lateinit var course: Course
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val courseId = intent.getStringExtra("course_id") ?: return finish()
        course = SampleData.courses.find { it.id == courseId } ?: return finish()
        
        setupViews()
    }
    
    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = course.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.courseName.text = course.name
        binding.instructorName.text = course.instructor
        binding.courseDescription.text = course.description
        binding.studentsCount.text = "${course.students} students"
        binding.assignmentsCount.text = "${course.assignments} assignments"
        
        val assignments = SampleData.getAssignmentsByCourse(course.id)
        val adapter = AssignmentAdapter(assignments) { assignment ->
            val intent = Intent(this, AssignmentDetailActivity::class.java)
            intent.putExtra("assignment_id", assignment.id)
            startActivity(intent)
        }
        binding.assignmentsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.assignmentsRecyclerView.adapter = adapter
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

