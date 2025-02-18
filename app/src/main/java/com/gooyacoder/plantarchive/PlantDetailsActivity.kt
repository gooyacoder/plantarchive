package com.gooyacoder.plantarchive

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
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
        // Show plant details
//        val builder = AlertDialog.Builder(this)
//        builder.setMessage("Choose an option")
//            .setCancelable(false)
//            .setPositiveButton("Show Details") { dialog, id ->
//                // show PlantDetailsActivity
//                val intent = Intent(applicationContext, PlantDetailsActivity::class.java)
//                intent.putExtra("plant_name", item.title)
//                startActivity(intent)
//                dialog.dismiss()
//
//            }
//            .setNegativeButton("Add Details") { dialog, id ->
//                // show AddDetailsActivity
//                val intent = Intent(applicationContext, AddDetailsActivity::class.java)
//                intent.putExtra("plant_name", item.title)
//                startActivity(intent)
//                dialog.dismiss()
//            }
//        val alert = builder.create()
//        alert.show()
    }

    override fun onItemLongClick(item: DetailsItem) {
//        // Handle the long-press event
//        val builder = AlertDialog.Builder(this)
//        val plant_name = item.title
//        builder.setMessage("Are you sure you want to Delete $plant_name?")
//            .setCancelable(false)
//            .setPositiveButton("Yes") { dialog, id ->
//                val db = DatabaseHelper(this)
//                db.removePlant(plant_name)
//                db.close()
//                this.recreate()
//
//            }
//            .setNegativeButton("No") { dialog, id ->
//                // Dismiss the dialog
//                dialog.dismiss()
//            }
//        val alert = builder.create()
//        alert.show()
    }

}