package com.agi.lmsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agi.lmsapp.R
import com.agi.lmsapp.models.Assignment
import java.text.SimpleDateFormat
import java.util.*

class AssignmentAdapter(
    private val assignments: List<Assignment>,
    private val onItemClick: (Assignment) -> Unit
) : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {
    
    class AssignmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.assignmentTitle)
        val descriptionText: TextView = view.findViewById(R.id.assignmentDescription)
        val dueDateText: TextView = view.findViewById(R.id.dueDate)
        val statusText: TextView = view.findViewById(R.id.assignmentStatus)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_assignment, parent, false)
        return AssignmentViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val assignment = assignments[position]
        holder.titleText.text = assignment.title
        holder.descriptionText.text = assignment.description
        holder.statusText.text = assignment.status
        
        // Format date
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
            val date = inputFormat.parse(assignment.dueDate)
            holder.dueDateText.text = "Due: ${outputFormat.format(date ?: Date())}"
        } catch (e: Exception) {
            holder.dueDateText.text = "Due: ${assignment.dueDate}"
        }
        
        holder.itemView.setOnClickListener {
            onItemClick(assignment)
        }
    }
    
    override fun getItemCount() = assignments.size
}

