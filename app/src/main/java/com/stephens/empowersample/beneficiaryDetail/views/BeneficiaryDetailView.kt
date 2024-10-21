package com.stephens.empowersample.beneficiaryDetail.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.stephens.empowersample.R
import com.stephens.empowersample.data.Beneficiary
import com.stephens.empowersample.data.BeneficiaryAddress

class BeneficiaryDetailView(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    private val nameTextView: TextView = TextView(context)
    private val beneTypeTextView: TextView = TextView(context)
    private val designationCodeTextView: TextView = TextView(context)
    private val ssnTextView: TextView = TextView(context)
    private val dobTextView: TextView = TextView(context)
    private val phoneTextView: TextView = TextView(context)
    private val addressTextView: TextView = TextView(context)

    init {
        orientation = VERTICAL
        setItemValueStyle(nameTextView)
        setItemValueStyle(beneTypeTextView)
        setItemValueStyle(designationCodeTextView)
        setItemValueStyle(ssnTextView)
        setItemValueStyle(dobTextView)
        setItemValueStyle(phoneTextView)
        setItemValueStyle(addressTextView)

        addView(beneficiaryItemFieldTextView(context, context.getString(R.string.name)))
        addView(nameTextView)
        addView(beneficiaryItemFieldTextView(context, context.getString(R.string.designation)))
        addView(designationCodeTextView)
        addView(beneficiaryItemFieldTextView(context, context.getString(R.string.type)))
        addView(beneTypeTextView)
        addView(beneficiaryItemFieldTextView(context, context.getString(R.string.ssn)))
        addView(ssnTextView)
        addView(beneficiaryItemFieldTextView(context, context.getString(R.string.date_of_birth)))
        addView(dobTextView)
        addView(beneficiaryItemFieldTextView(context, context.getString(R.string.phone)))
        addView(phoneTextView)
        addView(beneficiaryItemFieldTextView(context, context.getString(R.string.address)))
        addView(addressTextView)
    }

    /**
     * Sets the textFields with the values of the beneficiary being displayed
     */
    fun setBeneficiary(beneficiary: Beneficiary) {
        nameTextView.text = context.getString(
            R.string.name_string_format,
            beneficiary.firstName,
            beneficiary.middleName,
            beneficiary.lastName
        )
        beneTypeTextView.text = beneficiary.beneType
        designationCodeTextView.text =
            when(beneficiary.designationCode) {
                "P" -> "Primary"
                "C" -> "Contingent"
                else -> "Unknown"
            }
        ssnTextView.text = beneficiary.socialSecurityNumber
        dobTextView.text = formatDate(beneficiary.dateOfBirth)
        phoneTextView.text = formatPhoneNumber(beneficiary.phoneNumber)
        addressTextView.text = buildFormattedAddressString(beneficiary.beneficiaryAddress)
    }

    /**
     * Sets some stylistic things for the textViews that'll display the actual Values of the person
     */
    private fun setItemValueStyle(textView: TextView) {
        textView.textAlignment = TEXT_ALIGNMENT_TEXT_START
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.setPadding(12,0,12,24)
    }

    /**
     * @return Returns a textField representing the "field" being displayed (name, address, etc)
     */
    private fun beneficiaryItemFieldTextView(
        context: Context,
        fieldName: String,
    ): TextView {
        val fieldNameTextView = TextView(context)
        fieldNameTextView.text = fieldName
        fieldNameTextView.textAlignment = TEXT_ALIGNMENT_TEXT_START
        fieldNameTextView.setPadding(12,12,12,0)
        return fieldNameTextView
    }

    /**
     * Builds the address string with line 1, line 2, city/state/zip, and country
     * on separate lines
     * @return the formatted address string
     */
    private fun buildFormattedAddressString(beneficiaryAddress: BeneficiaryAddress): String{
        val addressString = StringBuilder().appendLine(beneficiaryAddress.firstLineMailing)
        if (beneficiaryAddress.scndLineMailing != "null") {
            addressString.appendLine(beneficiaryAddress.scndLineMailing)
        }
        addressString.appendLine("${beneficiaryAddress.city}, ${beneficiaryAddress.stateCode} ${beneficiaryAddress.zipCode}")
        addressString.append(beneficiaryAddress.country)
        return addressString.toString()
    }

    /**
     * Going to assume that all phone numbers are correct US phone numbers
     * if working with other countries we could add cases to handle different length
     * numbers and such
     * @return the formatted phone number string
     */
    private fun formatPhoneNumber(phone: String): String {
        if (phone.length == 10) {
            val formattedNumber = StringBuilder().append("(")
            formattedNumber.append(phone.substring(0..2))
            formattedNumber.append(") ")
            formattedNumber.append(phone.substring(3..5))
            formattedNumber.append("-")
            formattedNumber.append(phone.substring(6..phone.lastIndex))
            return formattedNumber.toString()
        }
        return phone
    }

    /**
     * Formatting the DOB string by added slashes
     * The dob string could be made into a Calendar/Date object if it's
     * going to be used for other purposes, but that's unnecessary here
     * @return the formatted date string
     */
    private fun formatDate(dob: String) : String {
        val formattedDob = StringBuilder().append(dob.substring(0..1))
        formattedDob.append(" / ")
        formattedDob.append(dob.substring(2..3))
        formattedDob.append(" / ")
        formattedDob.append(dob.substring(4..7))
        return formattedDob.toString()
    }
}