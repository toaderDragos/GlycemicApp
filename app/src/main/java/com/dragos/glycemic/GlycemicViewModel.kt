package com.dragos.glycemic

import androidx.lifecycle.*
import com.dragos.glycemic.data.Item
import com.dragos.glycemic.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao:ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    private fun insertItem(item: Item) {
//        The ViewModelScope is an extension property to the ViewModel class that
//        automatically cancels its child coroutines when the ViewModel is destroyed.
        viewModelScope.launch { itemDao.insert(item) }
    }
    private fun updateItem(item: Item) {
        viewModelScope.launch { itemDao.update(item) }
    }

    private fun getNewItemEntry(
        yourFood: String,
        foodWeight: Int,
        carbs100: Int,
        glyIndex: Int
    ): Item {
        return Item(
            yourFood = yourFood,
            foodWeight = foodWeight,
            carbsPer100Grams = carbs100,
            carbs = (foodWeight * carbs100 / 100),
            glycemicIndex = glyIndex,
            glycemicLoad = (foodWeight * carbs100 / 100) * glyIndex
        )
    }
// sunt doar 4 parametri pentru ca id e automat iar celelalte se calculeaza singure
    fun addNewItem(
        yourFood: String,
        foodWeight: Int,
        carbs100: Int,
        glyIndex: Int
    ) {
        val newItem = getNewItemEntry(yourFood, foodWeight, carbs100, glyIndex)
        insertItem(newItem)
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    // when we insert a new item we need to test if it is ok
    fun isEntryValid(
        yourFood: String,
        carbs100: Int,
        glyIndex: Int
    ): Boolean {
        if (yourFood.isBlank() || carbs100.toString().isBlank() || glyIndex.toString().isBlank()) {
            println("dra intrarea este INvalida")
            return false
        }
        println("dra intrarea este valida")
        return true
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

//    not that clear how we update values from the database to the UI
    private fun getUpdatedItemEntry(
        itemId: Int,
        yourFood: String,
        foodWeight: Int,
        carbs100: Int,
        carbs: Int,
        glyIndex: Int,
        glyLoad: Int
    ): Item {
    // item's column name = parameter from function getUpdatedItemEntry
        return Item (
            id = itemId,
            yourFood = yourFood,
            foodWeight = foodWeight,
            carbsPer100Grams = carbs100,
            carbs = carbs,
            glycemicIndex = glyIndex,
            glycemicLoad = glyLoad
        )
    }

    fun updateItem(
        itemId: Int,
        yourFood: String,
        foodWeight: Int,
        carbs100: Int,
        carbs: Int,
        glyIndex: Int,
        glyLoad: Int
    ) { val updatedItem = getUpdatedItemEntry(itemId, yourFood, foodWeight, carbs100, carbs, glyIndex, glyLoad)
        updateItem(updatedItem)
    }

}
class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
//    T means override any class
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
        @Suppress("UNCHECKED_CAST")
        return InventoryViewModel(itemDao) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}