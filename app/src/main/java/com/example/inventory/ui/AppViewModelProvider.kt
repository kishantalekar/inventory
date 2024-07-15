package com.example.inventory.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                itemsRepository = inventoryApplication().container.itemRepository
            )
        }

        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemRepository)
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                itemsRepository = inventoryApplication().container.itemRepository
            )
        }
        initializer {
            HomeViewModel(inventoryApplication().container.itemRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)