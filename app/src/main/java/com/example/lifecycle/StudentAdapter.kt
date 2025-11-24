package com.example.lifecycle

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: MutableList<Student>,
    private val onItemClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvMssv: TextView = itemView.findViewById(R.id.tvMssv)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime) // Ánh xạ view
        fun bind(student: Student) {
            tvName.text = student.name
            tvMssv.text = "MSSV: ${student.mssv}"
            tvTime.text = student.time //gán thời gian thực vào
            // Set ký tự đầu tiên cho Avatar
            if (student.name.isNotEmpty()) {
                tvAvatar.text = student.name.substring(0, 1).uppercase()
            }

            // Set màu nền cho Avatar
            val background = tvAvatar.background as GradientDrawable
            background.setColor(student.color)

            // Sự kiện click vào item để sửa
            itemView.setOnClickListener { onItemClick(student) }

            // Sự kiện click nút xóa (ngôi sao)
            btnDelete.setOnClickListener { onDeleteClick(student) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    override fun getItemCount(): Int = studentList.size
}