package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ItemEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    suspend fun updateItem() {
        if (validateInput()) {
            itemsRepository.updateItem(itemUiState.itemDetails.toItem())
        }
    }

    init {
        viewModelScope.launch() {
            itemUiState =
                itemsRepository.getItemStream(itemId).filterNotNull().first().toItemUiState(true)
        }
    }
}