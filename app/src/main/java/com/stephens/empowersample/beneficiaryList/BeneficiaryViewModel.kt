package com.stephens.empowersample.beneficiaryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stephens.empowersample.data.BeneficiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeneficiaryViewModel @Inject constructor(
    private val repository: BeneficiaryRepository
): ViewModel() {
    private var state = BeneficiaryListState()
        private set (value) {
            field = value
            _stateFlow.tryEmit(value)
        }
    private val _stateFlow = MutableStateFlow(state)
    val stateFlow = _stateFlow.asStateFlow()

    private val _effectFlow = MutableSharedFlow<BeneficiaryListEffect>(extraBufferCapacity = 1)
    val effectFlow = _effectFlow.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getBeneficiaries()
            if (result.isSuccess) {
                val beneficiaries = result.getOrNull()
                beneficiaries?.let {
                    state = state.copy(beneficiaries = it)
                }
            }
        }
    }

    fun postAction(action: BeneficiaryListAction) {
        when(action) {
            is BeneficiaryListAction.TapItem ->
                _effectFlow.tryEmit(BeneficiaryListEffect.ToItemDetail(action.beneficiary))
        }
    }
}