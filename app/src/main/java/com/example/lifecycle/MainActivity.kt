package com.example.lifecycle

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Setup Bottom Navigation Click
        setupBottomNav()

        // 2. Setup Header Logic (Profile, Notification)
        findViewById<View>(R.id.btnProfile).setOnClickListener {
            Toast.makeText(this, "Mở quản lý tài khoản Google", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.btnNotification).setOnClickListener {
            Toast.makeText(this, "Bạn có 1 thông báo mới", Toast.LENGTH_SHORT).show()
        }

        // 3. Setup Logic cho Danh sách App (Dọc)
        setupVerticalApp(findViewById(R.id.appRow1), "Mech Assemble", "Zombie Game")
        setupVerticalApp(findViewById(R.id.appRow2), "MU: Hong Hoa Dao", "Role Playing")
        setupVerticalApp(findViewById(R.id.appRow3), "War Inc: Rising", "Strategy")

        // 4. Setup Logic cho Danh sách App (Ngang)
        setupSquareApp(findViewById(R.id.appSquare1), "SUNO AI", "#FF9800")
        setupSquareApp(findViewById(R.id.appSquare2), "Claude AI", "#D67D56")
        setupSquareApp(findViewById(R.id.appSquare3), "DramaBox", "#E91E63")
    }

    // Hàm xử lý Bottom Navigation
    private fun setupBottomNav() {
        val navGames = findViewById<LinearLayout>(R.id.navGames)
        val navApps = findViewById<LinearLayout>(R.id.navApps)
        val navSearch = findViewById<LinearLayout>(R.id.navSearch)
        val navBooks = findViewById<LinearLayout>(R.id.navBooks)

        val navListener = View.OnClickListener { view ->
            // Reset logic (chỉ hiển thị thông báo)

            when (view.id) {
                R.id.navGames -> Toast.makeText(this, "Chuyển sang tab GAMES", Toast.LENGTH_SHORT).show()
                R.id.navApps -> Toast.makeText(this, "Đang ở tab APPS", Toast.LENGTH_SHORT).show()
                R.id.navSearch -> Toast.makeText(this, "Mở tìm kiếm", Toast.LENGTH_SHORT).show()
                R.id.navBooks -> Toast.makeText(this, "Chuyển sang tab BOOKS", Toast.LENGTH_SHORT).show()
            }
        }

        navGames.setOnClickListener(navListener)
        navApps.setOnClickListener(navListener)
        navSearch.setOnClickListener(navListener)
        navBooks.setOnClickListener(navListener)
    }

    // Hàm điền dữ liệu và sự kiện cho App Dọc
    private fun setupVerticalApp(view: View, name: String, category: String) {
        val tvName = view.findViewById<TextView>(R.id.tvAppName)
        // Tìm TextView thứ 2 để set category (cách đơn giản vì chưa đặt ID)
        tvName.text = name

        view.setOnClickListener {
            Toast.makeText(this, "Đang tải xuống: $name", Toast.LENGTH_SHORT).show()
        }
    }

    // Hàm điền dữ liệu cho App Ngang
    private fun setupSquareApp(view: View, name: String, colorHex: String) {
        val tvName = view.findViewById<TextView>(R.id.tvAppSquareName)
        val imgBg = view.findViewById<View>(R.id.imgAppSquare)

        tvName.text = name
        imgBg.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor(colorHex))

        view.setOnClickListener {
            Toast.makeText(this, "Xem chi tiết: $name", Toast.LENGTH_SHORT).show()
        }
    }
}