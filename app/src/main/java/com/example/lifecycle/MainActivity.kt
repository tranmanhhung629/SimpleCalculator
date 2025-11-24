package com.example.lifecycle

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    // Danh sách màu giả lập giống Gmail
    private val colors = listOf(
        Color.parseColor("#4285F4"), // Blue
        Color.parseColor("#DB4437"), // Red
        Color.parseColor("#0F9D58"), // Green
        Color.parseColor("#FF5722")  // Orange
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val now = getCurrentTime()
        studentList.add(Student(UUID.randomUUID().toString(), "Edurila.com", "WEB-001", colors[0], "10:00 AM"))
        studentList.add(Student(UUID.randomUUID().toString(), "Chris Abad", "CMP-002", colors[3], "11:00 AM"))
        studentList.add(Student(UUID.randomUUID().toString(), "Tuto.com", "SEO-003", colors[2], "12:00 AM"))
        studentList.add(Student(UUID.randomUUID().toString(), "Support", "OVH-004", Color.GRAY, "13:00 AM"))

        recyclerView = findViewById(R.id.recyclerView)
        val fabAdd: FloatingActionButton = findViewById(R.id.fabAdd)

        // Khởi tạo Adapter
        adapter = StudentAdapter(studentList,
            onItemClick = { student -> showUpdateDialog(student) },
            onDeleteClick = { student -> deleteStudent(student) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Sự kiện nút Add (FAB)
        fabAdd.setOnClickListener {
            showAddDialog()
        }
    }

    // Hàm xóa sinh viên
    private fun deleteStudent(student: Student) {
        val position = studentList.indexOf(student)
        if (position != -1) {
            studentList.removeAt(position)
            adapter.notifyItemRemoved(position)
            Toast.makeText(this, "Đã xóa ${student.name}", Toast.LENGTH_SHORT).show()
        }
    }

    // Hộp thoại Thêm mới
    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_student, null)
        val etName: EditText = dialogView.findViewById(R.id.etName)
        val etMssv: EditText = dialogView.findViewById(R.id.etMssv)
        val btnSave: Button = dialogView.findViewById(R.id.btnSave)
        val tvTitle: TextView = dialogView.findViewById(R.id.tvDialogTitle)

        tvTitle.text = "Thêm Sinh Viên Mới"
        btnSave.text = "Add"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val mssv = etMssv.text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                val randomColor = colors[Random().nextInt(colors.size)]
                val timeNow = getCurrentTime()
                val newStudent = Student(UUID.randomUUID().toString(), name, mssv, randomColor, timeNow )
                studentList.add(0, newStudent) // Thêm lên đầu
                adapter.notifyItemInserted(0)
                recyclerView.scrollToPosition(0)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
    //lấy thời gian thực
    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date())
    }
    // Hộp thoại Cập nhật
    private fun showUpdateDialog(student: Student) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_student, null)
        val etName: EditText = dialogView.findViewById(R.id.etName)
        val etMssv: EditText = dialogView.findViewById(R.id.etMssv)
        val btnSave: Button = dialogView.findViewById(R.id.btnSave)
        val tvTitle: TextView = dialogView.findViewById(R.id.tvDialogTitle)

        tvTitle.text = "Cập Nhật Sinh Viên"
        btnSave.text = "Update"

        // Điền dữ liệu cũ
        etName.setText(student.name)
        etMssv.setText(student.mssv)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val mssv = etMssv.text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                student.name = name
                student.mssv = mssv
                val position = studentList.indexOf(student)
                adapter.notifyItemChanged(position)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
}