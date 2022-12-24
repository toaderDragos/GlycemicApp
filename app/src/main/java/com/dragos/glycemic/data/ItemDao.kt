package com.dragos.glycemic.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
//    onconflict.. if it finds the same item in the table then ignores that item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)
    @Update
    suspend fun update(item: Item)
    @Delete
    suspend fun delete(item: Item)
//    use flow for database changes in real time
    @Query("SELECT * from item WHERE id= :id")
    fun getItem(id: Int): Flow<Item>
    @Query("SELECT * from item ORDER BY yourFood ASC")
    fun getItems(): Flow<List<Item>>
}

// incarcatura glicemica este SUMA mancarurilor
// la o portie glicemica daca este mai mare de 20 este avertisment
// trebuie si un total al zilei
// daca cinevavrea sa vada toata ziua sa poata sa faca
// daca are un pranz - sa isi treaca alimentele si sa vada indicele glicemic acolo
// poate sa ramana o suma si la carbohidrati si la glicemic load