package com.example.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private var firstNum: Int? = null
    private var operator: String? = null
    private var isNew: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvResult)
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9
        )
        // Bấm số
        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                val value = (it as Button).text.toString()
                if (isNew || tvResult.text == "0") {
                    tvResult.text = value
                    isNew = false
                } else {
                    tvResult.append(value)
                }
            }
        }
        // Các phép toán
        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { setOperator("/") }
        // Nút =
        findViewById<Button>(R.id.btnEq).setOnClickListener { calculate() }
        // Nút C: xóa tất cả
        findViewById<Button>(R.id.btnC).setOnClickListener {
            tvResult.text = "0"
            firstNum = null
            operator = null
            isNew = true
        }
        // Nút CE: xóa toán hạng hiện tại
        findViewById<Button>(R.id.btnCE).setOnClickListener {
            tvResult.text = "0"
        }
        // Nút BS: xóa 1 ký tự cuối
        findViewById<Button>(R.id.btnBS).setOnClickListener {
            val txt = tvResult.text.toString()
            if (txt.length > 1) tvResult.text = txt.dropLast(1)
            else tvResult.text = "0"
        }
        // Nút +/-
        findViewById<Button>(R.id.btnNeg).setOnClickListener {
            val value = tvResult.text.toString()
            if (value != "0") {
                if (value.startsWith("-"))
                    tvResult.text = value.substring(1)
                else
                    tvResult.text = "-$value"
            }
        }
    }
    private fun setOperator(op: String) {
        firstNum = tvResult.text.toString().toInt()
        operator = op
        isNew = true
    }
    private fun calculate() {
        val secondNum = tvResult.text.toString().toInt()
        val result = when (operator) {
            "+" -> firstNum!! + secondNum
            "-" -> firstNum!! - secondNum
            "*" -> firstNum!! * secondNum
            "/" -> {
                if (secondNum == 0) {
                    Toast.makeText(this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show()
                    return
                } else firstNum!! / secondNum
            }
            else -> secondNum
        }
        tvResult.text = result.toString()
        firstNum = result
        isNew = true
    }
}
