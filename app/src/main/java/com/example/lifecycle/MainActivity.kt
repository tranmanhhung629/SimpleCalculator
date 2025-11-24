package com.example.lifecycle

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etMssv: EditText
    private lateinit var etName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    private val studentList = mutableListOf<Student>()
    private var selectedStudent: Student? = null // Biến lưu sinh viên đang được chọn để sửa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ View
        etMssv = findViewById(R.id.etMssv)
        etName = findViewById(R.id.etName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        // Dữ liệu mẫu giống ảnh
        studentList.add(Student("1", "Nguyễn Văn A", "20200001"))
        studentList.add(Student("2", "Trần Thị B", "20200002"))
        studentList.add(Student("3", "Lê Văn C", "20200003"))

        // Cấu hình Adapter
        adapter = StudentAdapter(studentList,
            onItemClick = { student ->
                // Khi click vào item: Đưa dữ liệu lên ô nhập
                etMssv.setText(student.mssv)
                etName.setText(student.name)
                selectedStudent = student // Lưu lại để biết đang sửa ai
            },
            onDeleteClick = { student ->
                deleteStudent(student)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Xử lý nút ADD
        btnAdd.setOnClickListener {
            val mssv = etMssv.text.toString()
            val name = etName.text.toString()

            if (mssv.isNotEmpty() && name.isNotEmpty()) {
                // Kiểm tra trùng MSSV (tùy chọn)
                if (studentList.any { it.mssv == mssv }) {
                    Toast.makeText(this, "MSSV đã tồn tại!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val newStudent = Student(System.currentTimeMillis().toString(), name, mssv)
                studentList.add(newStudent)
                adapter.notifyDataSetChanged() // Cập nhật toàn bộ list

                clearInput() // Xóa trắng ô nhập
                Toast.makeText(this, "Đã thêm!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút UPDATE
        btnUpdate.setOnClickListener {
            val mssv = etMssv.text.toString()
            val name = etName.text.toString()

            if (selectedStudent != null) {
                if (mssv.isNotEmpty() && name.isNotEmpty()) {
                    // Cập nhật thông tin
                    selectedStudent?.name = name
                    selectedStudent?.mssv = mssv

                    adapter.notifyDataSetChanged()

                    clearInput()
                    selectedStudent = null // Reset trạng thái chọn
                    Toast.makeText(this, "Đã cập nhật!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên trong danh sách để sửa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hàm xóa sinh viên
    private fun deleteStudent(student: Student) {
        studentList.remove(student)
        adapter.notifyDataSetChanged()

        // Nếu xóa đúng người đang chọn để sửa thì reset ô nhập
        if (selectedStudent == student) {
            clearInput()
            selectedStudent = null
        }
        Toast.makeText(this, "Đã xóa!", Toast.LENGTH_SHORT).show()
    }

    // Hàm xóa trắng ô nhập
    private fun clearInput() {
        etMssv.text.clear()
        etName.text.clear()
        etMssv.requestFocus()
    }
}