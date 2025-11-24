package com.example.lifecycle

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. XỬ LÝ NÚT ĐÓNG THÔNG BÁO (Nút X)
        setupNotificationCloseLogic()

        // 2. XỬ LÝ NÚT SOẠN THƯ (FAB)
        setupFabLogic()

        // 3. XỬ LÝ CÁC DÒNG EMAIL VÀ NGÔI SAO
        // Vì ta dùng include tĩnh, ta phải ánh xạ từng dòng một
        setupEmailRowLogic(findViewById(R.id.emailRow1), "Edurila.com")
        setupEmailRowLogic(findViewById(R.id.emailRow2), "Chris Abad")
        setupEmailRowLogic(findViewById(R.id.emailRow3), "Tuto.com")
    }

    // Logic tắt thanh thông báo
    private fun setupNotificationCloseLogic() {
        val btnClose = findViewById<ImageView>(R.id.btnCloseConfig)
        val layoutNotification = findViewById<LinearLayout>(R.id.layoutNotification)

        btnClose.setOnClickListener {
            // Ẩn layout đi (GONE sẽ giải phóng không gian, INVISIBLE chỉ tàng hình)
            layoutNotification.visibility = View.GONE

            Toast.makeText(this, "Đã đóng thông báo", Toast.LENGTH_SHORT).show()
        }
    }

    // Logic nút Soạn thư
    private fun setupFabLogic() {
        val fab = findViewById<ImageButton>(R.id.fabCompose)
        fab.setOnClickListener {
            Toast.makeText(this, "Mở màn hình soạn thư mới...", Toast.LENGTH_SHORT).show()
            // Sau này bạn có thể dùng Intent để chuyển màn hình ở đây
        }
    }

    // Hàm dùng chung để xử lý logic cho từng dòng email
    private fun setupEmailRowLogic(rowView: View, senderName: String) {
        // 1. Click vào cả dòng email -> Mở chi tiết
        rowView.setOnClickListener {
            Toast.makeText(this, "Đang đọc thư từ: $senderName", Toast.LENGTH_SHORT).show()
        }

        // 2. Click vào ngôi sao -> Đổi màu (Vàng <-> Xám)
        val imgStar = rowView.findViewById<ImageView>(R.id.imgStar)

        // Dùng tag để lưu trạng thái (đã chọn hay chưa) vì View không có biến boolean sẵn
        imgStar.tag = false // Mặc định là chưa chọn

        imgStar.setOnClickListener {
            val isSelected = imgStar.tag as Boolean

            if (!isSelected) {
                // Nếu chưa chọn -> Đổi sang sao vàng
                imgStar.setImageResource(android.R.drawable.star_on)
                imgStar.tag = true
                Toast.makeText(this, "Đã gắn dấu sao cho $senderName", Toast.LENGTH_SHORT).show()
            } else {
                // Nếu đã chọn -> Đổi về sao xám
                imgStar.setImageResource(android.R.drawable.star_off)
                imgStar.tag = false
                Toast.makeText(this, "Bỏ dấu sao", Toast.LENGTH_SHORT).show()
            }
        }
    }
}