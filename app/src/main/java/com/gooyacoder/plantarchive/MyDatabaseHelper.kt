package com.gooyacoder.plantarchive

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = """
            CREATE TABLE PlantsGallery (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                plant_name TEXT,
                image BLOB,
                image_date TEXT
            )
        """
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS PlantsGallery")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "plants_gallery_db.db"
        private const val DATABASE_VERSION = 1
    }
}