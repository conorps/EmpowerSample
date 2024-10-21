package com.stephens.empowersample.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class BeneficiaryRepository(private val context: Context) {

    /**
     * Retrieves the list of beneficiaries from a local source
     * if the local source isn't found, returns an empty list
     */
    fun getBeneficiaries(appContext: Context = context): Result<List<Beneficiary>> {
        try {
            val fileInString =
                appContext.assets.open("Beneficiaries.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(fileInString)
            val result: MutableList<Beneficiary> = mutableListOf()
            for (i in 0..<jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                result.add(jsonObjectToBeneficiary(jsonObject))
            }
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    /**
     * Unpacks a json object into the beneficiary Data Class without using a third-party library
     * Could typically be handled by something like gson
     */
    private fun jsonObjectToBeneficiary(jsonObject: JSONObject): Beneficiary {
        val beneficiaryAddressJson = jsonObject.getJSONObject("beneficiaryAddress")
        val beneficiaryAddress = BeneficiaryAddress(
            firstLineMailing = beneficiaryAddressJson.getString("firstLineMailing"),
            scndLineMailing = beneficiaryAddressJson.getString("scndLineMailing"),
            city = beneficiaryAddressJson.getString("city"),
            zipCode = beneficiaryAddressJson.getString("zipCode"),
            stateCode = beneficiaryAddressJson.getString("stateCode"),
            country = beneficiaryAddressJson.getString("country")
        )
        val beneficiary = Beneficiary(
            lastName = jsonObject.getString("lastName"),
            firstName = jsonObject.getString("firstName"),
            designationCode = jsonObject.getString("designationCode"),
            beneType = jsonObject.getString("beneType"),
            socialSecurityNumber = jsonObject.getString("socialSecurityNumber"),
            dateOfBirth = jsonObject.getString("dateOfBirth"),
            middleName = jsonObject.getString("middleName"),
            phoneNumber = jsonObject.getString("phoneNumber"),
            beneficiaryAddress = beneficiaryAddress
        )
        return beneficiary
    }
}