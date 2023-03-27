package com.webronin_26.online_mart_admin.data.remote

import retrofit2.http.*

interface RequestInterface {

    @Headers(
        "Cache-Control: max-age=0",
        "Upgrade-Insecure-Requests: 1",
        "user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Encoding: gzip, deflate, br",
        "Accept-Language: zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,zh-CN;q=0.5,lb;q=0.4")

    @POST("/login/admin")
    suspend fun loginRequest(@Body loginRequestBody: LoginRequestBody): Response.LoginResponse

    data class LoginRequestBody (var account: String, val password: String)

    @DELETE("/admin/logout")
    suspend fun logoutRequest(@Header("Authorization") token: String): Response.LogoutResponse

    @GET("/admin/company")
    suspend fun retailerListRequest(@Header("Authorization") token: String): Response.RetailerListResponse

    @GET("/admin/company/{company_id}")
    suspend fun retailerRequest(@Header("Authorization") token: String,
                                @Path("company_id") companyId: Int): Response.RetailerResponse

    @POST("/admin/company")
    suspend fun addRetailerRequest(@Header("Authorization") token: String,
                                  @Body addRetailerRequestBody: AddRetailerRequestBody): Response.AddRetailerResponse

    data class AddRetailerRequestBody (var company_name: String,
                                      val responsible_person: String,
                                      var invoice_number: String,
                                      val remittance_account: String,
                                      var office_phone_number: String,
                                      val personal_phone_number: String,
                                      var office_address: String,
                                      val correspondence_address: String,
                                      var delivery_fee: Float)
}