package com.stephens.empowersample.beneficiaryDetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout.LayoutParams
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.stephens.empowersample.beneficiaryDetail.views.BeneficiaryDetailView
import com.stephens.empowersample.data.Beneficiary
import kotlinx.coroutines.launch

class BeneficiaryDetailActivity : ComponentActivity() {
    private val viewModel: BeneficiaryDetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        intent.getParcelableExtra(BENEFICIARY_CODE, Beneficiary::class.java)
            ?.let {
                viewModel.selectBeneficiary(it)
            }
    }

    /**
     * Creates the view and collects from the state flow for the view
     */
    private fun initView() {
        val scrollView = ScrollView(this)
        scrollView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        val beneficiaryDetailView = BeneficiaryDetailView(this)
        beneficiaryDetailView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        scrollView.addView(beneficiaryDetailView)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    state.beneficiary?.let { beneficiaryDetailView.setBeneficiary(it) }
                }
            }
        }
        setContentView(scrollView)
    }

    companion object {
        private const val BENEFICIARY_CODE = "beneficiary"

        fun newIntent(context: Context, beneficiary: Beneficiary): Intent {
            return Intent(context, BeneficiaryDetailActivity::class.java).apply {
                putExtra(BENEFICIARY_CODE, beneficiary)
            }
        }
    }
}