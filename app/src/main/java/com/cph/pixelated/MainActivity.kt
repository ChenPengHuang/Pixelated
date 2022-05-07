package com.cph.pixelated

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import com.cph.pixelated.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.image.setOnClickListener {
            val input = assets.open("keli.webp")
            val bytes = input.readBytes()
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            val width = bitmap.width
            val height = bitmap.height
            val factor = 20
            val bitmapResult = Bitmap.createBitmap(width, height, bitmap.config)
            for (i in 0..width - factor step factor) {
                for (j in 0..height - factor step factor) {
                    val colors = mutableListOf<Int>()
                    for (k in 0 until factor) {
                        for (l in 0 until factor) {
                            val pixel = bitmap.getPixel(k + i, l + j)
                            colors.add(pixel)
                        }
                    }
                    val remix = colors.reduce { acc, color ->
                        ColorUtils.blendARGB(acc, color, 0.5f)
                    }
                    for (k in 0 until factor) {
                        for (l in 0 until factor) {
                            bitmapResult.setPixel(k + i, l + j, remix)
                        }
                    }
                }
            }
            (it as ImageView).setImageBitmap(bitmapResult)
        }
    }
}