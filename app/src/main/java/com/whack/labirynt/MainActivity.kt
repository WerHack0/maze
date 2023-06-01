package com.whack.labirynt

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var mazeLayout: GridLayout
    private lateinit var winTextView: TextView
    private lateinit var playerButtonUp: Button
    private lateinit var playerButtonDown: Button
    private lateinit var playerButtonLeft: Button
    private lateinit var playerButtonRight: Button

    private var playerRow: Int = 7
    private var playerCol: Int = 0
    private var mazeSize: Int = 14// rozmiar labiryntu

    private val maze: Array<BooleanArray> = arrayOf(

        booleanArrayOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false),
        booleanArrayOf(false,true,true,true,true,true,false,true,true,true,true,true,false,false),
        booleanArrayOf(false,true,false,false,false,true,false,true,false,false,false,true,false,false),
        booleanArrayOf(false,true,false,false,true,true,false,true,true,false,true,true,false,false),
        booleanArrayOf(false,true,true,false,true,false,false,false,true,false,true,false,false,false),
        booleanArrayOf(false,false,true,false,true,false,true,true,true,false,true,true,true,false),
        booleanArrayOf(false,false,true,false,true,true,true,false,false,false,false,false,true,false),
        booleanArrayOf(true,true,true,true,true,false,false,false,false,false,true,true,true,false),
        booleanArrayOf(false,false,true,false,false,false,true,true,true,true,true,false,false,false),
        booleanArrayOf(false,false,true,false,true,false,true,false,false,false,true,true,true,false),
        booleanArrayOf(false,true,true,false,true,true,true,false,true,false,false,false,true,false),
        booleanArrayOf(false,true,false,false,true,false,false,true,true,true,true,true,true,false),
        booleanArrayOf(false,true,true,true,true,false,true,true,false,false,false,false,false,false),
        booleanArrayOf(false,false,false,false,false,false,false,true,false,false,false,false,false,false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mazeLayout = findViewById(R.id.maze_layout)
        winTextView = findViewById(R.id.win_text_view)
        playerButtonDown = findViewById(R.id.down_button)
        playerButtonLeft = findViewById(R.id.left_button)
        playerButtonUp = findViewById(R.id.up_buttom)
        playerButtonRight = findViewById(R.id.right_button)

        initMaze()
        updatePlayerPosition()

        playerButtonRight.setOnClickListener {
            movePlayer(0, 1)
        }
        playerButtonUp.setOnClickListener {
            movePlayer(-1, 0)
        }
        playerButtonDown.setOnClickListener {
            movePlayer(1, 0)
        }
        playerButtonLeft.setOnClickListener {
            movePlayer(0, -1)
        }
    }

    private fun initMaze() {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val cellSize = screenWidth / mazeSize
        mazeLayout.removeAllViews()
        mazeLayout.rowCount = mazeSize
        mazeLayout.columnCount = mazeSize

        for (row in 0 until mazeSize) {
            for (col in 0 until mazeSize) {
                val cell = Button(this)
                val params = GridLayout.LayoutParams()
                params.setGravity(Gravity.FILL)
                params.width = cellSize
                params.height = cellSize
                cell.layoutParams = params
                cell.isEnabled = maze[row][col]
                cell.setBackgroundColor(if (maze[row][col]) Color.WHITE else Color.BLACK)
                mazeLayout.addView(cell)
            }
        }
    }

    private fun movePlayer(rowOffset: Int, colOffset: Int) {
        val newRow = playerRow + rowOffset
        val newCol = playerCol + colOffset
        if (newRow in 0 until mazeSize && newCol in 0 until mazeSize && maze[newRow][newCol]) {
            playerRow = newRow
            playerCol = newCol
            updatePlayerPosition()
            if (playerRow == 13 && playerCol == 7) {
                winTextView.visibility = View.VISIBLE
            }
        }
    }

    private fun updatePlayerPosition() {
        for (row in 0 until mazeSize) {
            for (col in 0 until mazeSize) {
                val cell = mazeLayout.getChildAt(row * mazeSize + col) as Button
                if (row == playerRow && col == playerCol) {

                    cell.setBackgroundColor(Color.GREEN) // Ustawia kolor tła na zielony dla gracza
                } else {
                    cell.setBackgroundColor(if (maze[row][col]) Color.WHITE else Color.BLACK)
                }
            }
        }
    }
}