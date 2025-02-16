package com.gooyacoder.plantarchive

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import java.io.FileNotFoundException

class AddDetailsActivity : AppCompatActivity() {
    lateinit var plant: Plant
    lateinit var imageBitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val extras = intent.extras
        val plantName = extras?.getCharSequence("plant_name").toString()
        //Toast.makeText(applicationContext, plantName, Toast.LENGTH_SHORT).show()
        val db = DatabaseHelper(applicationContext)
        plant = db.getPlant(plantName)
        val plantNameTextView = findViewById<TextView>(R.id.plantNameTextView)
        plantNameTextView.text = plant.plant_name
        val plantImageView: ImageView = findViewById(R.id.plantImageView)
        plantImageView.setImageBitmap(DbBitmapUtility.getImage(plant.image))
        db.close()

        val addImageButton: MaterialButton = findViewById(R.id.addImageButton)
        addImageButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(intent, 2)
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.plantImageView)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageBitmap = data?.extras?.get("data") as Bitmap
            //val plant_preview: ImageView = findViewById(R.id.plant_preview)
            imageView.setImageBitmap(imageBitmap)
        }
        else if (requestCode == 2 && resultCode == RESULT_OK){
            var selectedImage: Uri? = null
            if (data != null) {
                selectedImage = data?.data
                try {
                    val input = contentResolver.openInputStream(selectedImage!!)
                    val selectedImg = BitmapFactory.decodeStream(input)
                    imageBitmap = selectedImg
                    //val plant_preview: ImageView = findViewById(R.id.plant_preview)
                    imageView.setImageBitmap(imageBitmap)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(this, "An error occurred!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}