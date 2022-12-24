package com.dragos.glycemic.data
// schita tabelului cu titlu, nume de coloane
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity(tableName = "item")
data class Item (
    @PrimaryKey(autoGenerate = true)   // auto generates unique IDs
    val id: Int = 0,
    @ColumnInfo(name = "yourFood")
    val yourFood: String,
    @ColumnInfo(name = "foodWeight")
    val foodWeight: Int,
    @ColumnInfo(name = "carbsPer100Grams")
    val carbsPer100Grams: Int,
    @ColumnInfo(name = "carbs")
    val carbs: Int,
    @ColumnInfo(name = "glycemicIndex")
    val glycemicIndex: Int,
    @ColumnInfo(name = "glycemicLoad")
    val glycemicLoad: Int
)

