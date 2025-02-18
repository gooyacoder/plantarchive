package com.gooyacoder.plantarchive

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlantDetailsActivity : AppCompatActivity(), DetailsItemAdapter.OnItemClickListener,
    DetailsItemAdapter.OnItemLongClickListener {
    lateinit var plantName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_plant_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        plantName = findViewById(R.id.DetailsActivityPlantName)
        plantName.text = intent.getCharSequenceExtra("plant_name").toString()
        LoadDetais()
    }

    fun LoadDetais(){
        var db = MyDatabaseHelper(applicationContext)
        val plant_details = db.getDetails(intent.getCharSequenceExtra("plant_name").toString())
        val list: ArrayList<DetailsItem> = ArrayList()
        for(detail in plant_details){
            val item = DetailsItem(detail.title, detail.image, detail.startDate)
            list.add(item)
        }
        val plantList = findViewById<RecyclerView>(R.id.plantDetailsRecyclerView)
        plantList.layoutManager = LinearLayoutManager(this)
        val adapter = DetailsItemAdapter(list, this, this)
        plantList.adapter = adapter

    }

    override fun onItemClick(item: DetailsItem) {
        val big_image: ImageView = findViewById(R.id.bigImage)
        big_image.setOnClickListener{
            if(big_image.visibility == View.VISIBLE)
                big_image.visibility = View.INVISIBLE
        }
        big_image.setImageBitmap(DbBitmapUtility.getImage(item.image))
        big_image.visibility = View.VISIBLE
    }

    override fun onItemLongClick(item: DetailsItem) {}

}