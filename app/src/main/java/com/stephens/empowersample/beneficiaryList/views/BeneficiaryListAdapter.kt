package com.stephens.empowersample.beneficiaryList.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.stephens.empowersample.data.Beneficiary

/**
 * Custom Adapter for the listView that will display the list of beneficiaries
 * @param onClick the lambda passed in that will be called when an item is clicked
 */
class BeneficiaryListAdapter(
    private val context: Context,
    private val items: List<Beneficiary>,
    private val onClick: (beneficiary: Beneficiary) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView: BeneficiaryListItemView = if (convertView == null) {
            BeneficiaryListItemView(context)
        } else {
            convertView as BeneficiaryListItemView
        }

        itemView.setBeneficiary(items[position])
        itemView.setOnClickListener {
            onClick(items[position])
        }

        return itemView
    }
}
