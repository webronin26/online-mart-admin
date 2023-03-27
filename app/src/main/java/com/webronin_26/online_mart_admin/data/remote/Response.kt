package com.webronin_26.online_mart_admin.data.remote

import com.google.gson.annotations.SerializedName

class Response {

    /**
     *  Login
     */
    data class LoginResponse (val count: Int, val code: Int, val data: LoginResponseData)

    data class LoginResponseData (val token: String, val id: Int, val name: String)

    /**
     *  Logout
     */
    data class LogoutResponse (val count: Int, val code: Int)

    /**
     *  Retailer list
     */
    data class RetailerListResponse (val count: Int, val code: Int, val data: Array<Retailers>)

    data class Retailers (val id: Int,
                          @SerializedName("name") val name: String,
                          @SerializedName("responsible_person") val responsiblePerson: String,
                          @SerializedName("invoice") val invoice: String,
                          @SerializedName("office_address") val address: String)

    /**
     *  Retailer
     */
    data class RetailerResponse (val count: Int, val code: Int, val data: Retailer)

    data class Retailer (val id: Int,
                         @SerializedName("name") val name: String,
                         @SerializedName("responsible_person") val responsiblePerson: String,
                         @SerializedName("invoice") val invoice: String,
                         @SerializedName("remittance_account") val remittanceAccount: String,
                         @SerializedName("office_phone") val officePhone: String,
                         @SerializedName("personal_phone") val personalPhone: String,
                         @SerializedName("office_address") val officeAddress: String,
                         @SerializedName("correspondence_address") val correspondenceAddress: String,
                         @SerializedName("delivery_fee") val deliveryFee: Float,)

    /**
     *  Add retailer
     */
    data class AddRetailerResponse (val count: Int, val code: Int)
}
