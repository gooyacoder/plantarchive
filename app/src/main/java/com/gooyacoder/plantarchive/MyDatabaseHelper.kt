package com.gooyacoder.plantarchive

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
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

    @Throws(SQLiteException::class)
    fun getDetails(plantName: String) : ArrayList<DetailsItem>{
        val data = this.readableDatabase
        val query = "SELECT * FROM PlantsGallery WHERE plant_name=\"$plantName\";"
        val cursor = data.rawQuery(query, null)
        val list: ArrayList<DetailsItem> = ArrayList<DetailsItem>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(1)
            val imagebyte = cursor.getBlob(2)
            val date = cursor.getString(3)
            val detail = DetailsItem(
                name,
                imagebyte,
                date
            )
            list.add(detail)
        }
        data.close()
        return list
    }

    fun removeDetail(date: String?) {
        val db = this.writableDatabase
        db.execSQL(
            "delete from PlantsGallery where" +
                    " image_date = \"" + date + "\";"
        );
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "plants_gallery_db.db"
        private const val DATABASE_VERSION = 1
    }
}