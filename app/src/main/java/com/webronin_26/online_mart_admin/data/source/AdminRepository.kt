package com.webronin_26.online_mart_admin.data.source

import com.webronin_26.online_mart_admin.data.remote.RequestInterface
import com.webronin_26.online_mart_admin.data.remote.Response
import com.webronin_26.online_mart_admin.data.remote.Result
import com.webronin_26.online_mart_admin.data.remote.STATUS_SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.2.2:1323"

class AdminRepository {

    private fun getRetrofitInstance(): RequestInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestInterface::class.java)
    }

    suspend fun login(account: String, password: String): Result<Response.LoginResponse> = withContext(Dispatchers.IO) {
            try {
                val loginRequestBody = RequestInterface.LoginRequestBody(account, password)
                val response = getRetrofitInstance().loginRequest(loginRequestBody)

                if (response.code == STATUS_SUCCESS) {
                    return@withContext Result.Success(response)
                } else {
                    return@withContext Result.Error(Exception("request error"))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(Exception(e.toString()))
            }
        }

    suspend fun logout(token: String): Result<Response.LogoutResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().logoutRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun retailerList(token: String): Result<Response.RetailerListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().retailerListRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun retailerQuery(token: String, retailerId: Int): Result<Response.RetailerResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().retailerRequest(token, retailerId)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun addRetailer(token: String, name: String, responsiblePerson: String, invoice: String,
                            remittanceAccount: String, officePhone: String, personalPhone: String,
                            officeAddress: String, correspondenceAddress: String, deliveryFee: Float) :
            Result<Response.AddRetailerResponse> = withContext(Dispatchers.IO) {
        try {
            val addProductRequestBody = RequestInterface.AddRetailerRequestBody(
                name, responsiblePerson, invoice, remittanceAccount, officePhone, personalPhone,
                officeAddress, correspondenceAddress, deliveryFee)

            val response = getRetrofitInstance().addRetailerRequest(token, addProductRequestBody)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }
}