package com.android.cabifymarketplace.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.cabifymarketplace.model.db.ProductOrder

@Database(entities = [ProductOrder::class], version = 1, exportSchema = false)
abstract class DbDataSource : RoomDatabase() {

    abstract fun productDAO(): ProductDAO
}