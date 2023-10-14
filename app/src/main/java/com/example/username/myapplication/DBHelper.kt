package com.example.username.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException

class DBHelper(context: Context?) :
    OrmLiteSqliteOpenHelper(context, NOMBRE_DB, null, VERSION_DB) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            TableUtils.createTable(connectionSource, Libro::class.java)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(
        database: SQLiteDatabase?, connectionSource: ConnectionSource?,
        oldVersion: Int, newVersion: Int
    ) {
    }

    companion object {
        private const val NOMBRE_DB = "DB_EXAMPLE"
        private const val VERSION_DB = 1
    }
}