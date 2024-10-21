package com.stephens.empowersample.beneficiaryList

import com.stephens.empowersample.data.Beneficiary

data class BeneficiaryListState (
    val beneficiaries: List<Beneficiary> = emptyList()
)

sealed class BeneficiaryListAction {
    data class TapItem(val beneficiary: Beneficiary): BeneficiaryListAction()
}

sealed class BeneficiaryListEffect {
    data class ToItemDetail(val beneficiary: Beneficiary): BeneficiaryListEffect()
}