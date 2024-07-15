package com.example.inventory.data

import android.content.Context

interface AppContainer {
    val itemRepository: ItemsRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val itemRepository: ItemsRepository by lazy {
        OfflineItemsRepository(
            InventoryDatabase.getDatabase(
                context
            ).itemDao()
        )
    }
}