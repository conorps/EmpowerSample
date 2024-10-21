package com.stephens.empowersample.beneficiaryList

import android.os.Bundle
import android.widget.LinearLayout.LayoutParams
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.stephens.empowersample.beneficiaryDetail.BeneficiaryDetailActivity
import com.stephens.empowersample.beneficiaryList.views.BeneficiaryListAdapter
import com.stephens.empowersample.data.Beneficiary
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Using an Activity per screen is slightly outdated, given more time a single-activity approach
 * with fragments and/or compose screens could be better
 */
@AndroidEntryPoint
class BeneficiaryListActivity : ComponentActivity() {
    private val viewModel: BeneficiaryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = buildListView()
        setContentView(listView)

        //This is where we will collect the state (ex: items from the list)
        //and collect the effects (ex: navigation)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.stateFlow.collect {renderListView(listView, it.beneficiaries)} }
                launch { viewModel.effectFlow.collect { onEffect(it)} }
            }
        }
    }

    private fun buildListView(): ListView {
        val listView = ListView(this)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        listView.layoutParams = layoutParams
        return listView
    }

    private fun renderListView(listView: ListView, beneficiaries: List<Beneficiary>) {
        listView.adapter = BeneficiaryListAdapter(this, beneficiaries) { beneficiary ->
            viewModel.postAction(BeneficiaryListAction.TapItem(beneficiary = beneficiary))
        }
    }

    /**
     * when the effects are collected from the effect flow, they are handled here
     */
    private fun onEffect(effect: BeneficiaryListEffect) {
        when (effect) {
            is BeneficiaryListEffect.ToItemDetail -> startActivity(
                BeneficiaryDetailActivity.newIntent(
                    context = this,
                    beneficiary = effect.beneficiary
                )
            )
        }
    }
}