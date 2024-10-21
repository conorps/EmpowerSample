package com.stephens.empowersample.beneficiaryDetail

import androidx.lifecycle.ViewModel
import com.stephens.empowersample.data.Beneficiary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BeneficiaryDetailViewModel: ViewModel() {
    private var state = BeneficiaryDetailState()
        private set (value) {
            field = value
            _stateFlow.tryEmit(value)
        }
    private val _stateFlow = MutableStateFlow(state)
    val stateFlow = _stateFlow.asStateFlow()

    fun selectBeneficiary(beneficiary: Beneficiary) {
        state = state.copy(beneficiary = beneficiary)
    }
}