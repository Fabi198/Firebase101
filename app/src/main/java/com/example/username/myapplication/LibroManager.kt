package com.example.username.myapplication

import android.content.Context
import com.j256.ormlite.android.apptools.OpenHelperManager.*
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import java.sql.SQLException

class LibroManager private constructor(context: Context) {
    private var dao: Dao<Libro, Int>? = null

    init {
        val helper: OrmLiteSqliteOpenHelper =
            getHelper(context, DBHelper::class.java)
        try {
            dao = helper.getDao(Libro::class.java)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    fun agregarLibro(libro: Libro) {
        dao?.create(libro)
    }

    @Throws(SQLException::class)
    fun getLibros(): List<Libro> {
        return dao?.queryForAll() as List<Libro>
    }

    @Throws(SQLException::class)
    fun getLibro(id: Int): Libro {
        return dao?.queryForId(id) as Libro
    }

    companion object {
        private var instance: LibroManager? = null
        fun getInstance(context: Context): LibroManager {
            if (instance == null) {
                instance = LibroManager(context)
            }
            return instance as LibroManager
        }
    }
}