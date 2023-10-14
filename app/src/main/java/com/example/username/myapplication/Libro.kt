package com.example.username.myapplication

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable(tableName = "Libros")
class Libro : Serializable {
    @DatabaseField(id = true)
    var id: Int? = null

    @DatabaseField
    var nombre: String? = null

    @DatabaseField
    var autor: String? = null

    constructor()
    constructor(id: Int?, nombre: String?, autor: String?) {
        this.id = id
        this.nombre = nombre
        this.autor = autor
    }
}
