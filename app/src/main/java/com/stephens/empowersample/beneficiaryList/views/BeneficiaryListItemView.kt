package com.stephens.empowersample.beneficiaryList.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.stephens.empowersample.R
import com.stephens.empowersample.data.Beneficiary

class BeneficiaryListItemView(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val firstNameTextView: TextView = TextView(context)
    private val lastNameTextView: TextView = TextView(context)
    private val beneTypeTextView: TextView = TextView(context)
    private val designationCodeTextView: TextView = TextView(context)

    init {
        orientation = HORIZONTAL
        setItemValueStyle(firstNameTextView)
        setItemValueStyle(lastNameTextView)
        setItemValueStyle(beneTypeTextView)
        setItemValueStyle(designationCodeTextView)

        addView(beneficiaryItemField(context,
            context.getString(R.string.designation), designationCodeTextView))
        addView(beneficiaryItemField(context,
            context.getString(R.string.first_name), firstNameTextView))
        addView(beneficiaryItemField(context,
            context.getString(R.string.last_name), lastNameTextView))
        addView(beneficiaryItemField(context,
            context.getString(R.string.type), beneTypeTextView))

    }

    fun setBeneficiary(beneficiary: Beneficiary) {
        firstNameTextView.text = beneficiary.firstName
        lastNameTextView.text = beneficiary.lastName
        beneTypeTextView.text = beneficiary.beneType
        designationCodeTextView.text = beneficiary.designationCode
    }

    private fun setItemValueStyle(textView: TextView) {
        textView.textAlignment = TEXT_ALIGNMENT_CENTER
        textView.typeface = Typeface.DEFAULT_BOLD
    }

    /**
     * Here the Title of the field being displayed and the actual value are
     * combined into a linear layout and returned
     */
    private fun beneficiaryItemField(
        context: Context,
        fieldName: String,
        valueTextView: TextView
    ): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = VERTICAL
        val fieldNameTextView = TextView(context)
        fieldNameTextView.text = fieldName
        fieldNameTextView.textAlignment = TEXT_ALIGNMENT_CENTER
        linearLayout.addView(fieldNameTextView)
        linearLayout.addView(valueTextView)
        linearLayout.setPadding(12)
        linearLayout.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f)
        return linearLayout
    }
}