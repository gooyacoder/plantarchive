package com.gooyacoder.plantarchive

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() ,ItemAdapter.OnItemClickListener, ItemAdapter.OnItemLongClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addButton: FloatingActionButton = findViewById(R.id.addPlantButton)
        addButton.setOnClickListener(View.OnClickListener {
            // Show addPlantActivity
            val addActivity = Intent(this, AddActivity::class.java)
            startActivity(addActivity)
        })

        LoadPlants()
    }
    fun LoadPlants(){
        var db = DatabaseHelper(applicationContext)
        val plants_in_database = db.getPlants()
        val list: ArrayList<Item> = ArrayList()
        for(plant in plants_in_database){
            val item = Item(plant.plant_name, plant.image, plant.description, plant.startDate)
            list.add(item)
        }
        val plantList = findViewById<RecyclerView>(R.id.plantsView)
        plantList.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(list, this, this)
        plantList.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        LoadPlants()
        //this.recreate()
    }
    override fun onItemClick(item: Item) {
    // Show plant details

    }

    override fun onItemLongClick(item: Item) {
        // Handle the long-press event
        val builder = AlertDialog.Builder(this)
        val plant_name = item.title
        builder.setMessage("Are you sure you want to Delete $plant_name?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                val db = DatabaseHelper(this)
                db.removePlant(plant_name)
                db.close()
                this.recreate()

            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}