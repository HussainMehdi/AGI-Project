package com.agi.lmsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agi.lmsapp.data.SampleData
import com.agi.lmsapp.databinding.ActivityAssignmentDetailBinding
import com.agi.lmsapp.models.Assignment
import java.text.SimpleDateFormat
import java.util.*

class AssignmentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssignmentDetailBinding
    private lateinit var assignment: Assignment
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val assignmentId = intent.getStringExtra("assignment_id") ?: return finish()
        assignment = SampleData.assignments.find { it.id == assignmentId } ?: return finish()
        
        setupViews()
    }
    
    private fun setupViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Assignment"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.assignmentTitle.text = assignment.title
        binding.assignmentDescription.text = assignment.description
        binding.assignmentStatus.text = assignment.status
        
        // Format date
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(assignment.dueDate)
            binding.dueDate.text = "Due: ${outputFormat.format(date ?: Date())}"
        } catch (e: Exception) {
            binding.dueDate.text = "Due: ${assignment.dueDate}"
        }
        
        if (assignment.status == "Not Started" || assignment.status == "In Progress") {
            binding.submitButton.visibility = android.view.View.VISIBLE
            binding.submitButton.setOnClickListener {
                Toast.makeText(this, "Assignment submitted!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

