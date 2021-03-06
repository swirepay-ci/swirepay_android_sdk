package com.swirepay.android_sdk.ui.subscription_button

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swirepay.android_sdk.SwirepaySdk
import com.swirepay.android_sdk.model.PaymentRequest
import com.swirepay.android_sdk.retrofit.ApiClient
import com.swirepay.android_sdk.retrofit.ApiInterface
import com.swirepay.android_sdk.ui.subscription_button.model.PlanRequest
import com.swirepay.android_sdk.ui.subscription_button.model.SubscriptionButton
import com.swirepay.android_sdk.ui.subscription_button.model.SubscriptionButtonRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ViewModelSubscriptionButton(val name : String , val amount : Int ,
                                  val description : String , val currencyCode : String , val billingFrequency : String,
                                  val billingPeriod : Int , val planStartDate : String) : ViewModel() {
    val liveErrorMessages : MutableLiveData<String> = MutableLiveData()
    val liveSubscriptionButton : MutableLiveData<SubscriptionButton> = MutableLiveData()
    val liveResult : MutableLiveData<SubscriptionButton> = MutableLiveData()
    init {
        fetchPlan()
    }

    private fun fetchPlan()  = viewModelScope.launch(Dispatchers.IO){
        val apiClient = ApiClient.retrofit.create(ApiInterface::class.java)
        val planRequest = PlanRequest(name , amount , description , currencyCode , billingFrequency , billingPeriod )
        val response = apiClient.createPlan(planRequest , SwirepaySdk.apiKey!!).execute()
        if(response.isSuccessful && response.body() != null){
            val plan = response.body()!!.entity
            plan.let {
                val subscriptionButtonRequest = SubscriptionButtonRequest(it.currency.name , it.description , plan.amount , planBillingFrequency = it.billingFrequency , planBillingPeriod = it.billingPeriod ,planGid = it.gid,
                      planQuantity = 1 , planTotalPayments = "12" , redirectUri ="https://www.google.com" , planStartDate = planStartDate
                    )
                val subResponse = apiClient.createSubscriptionButton(subscriptionButtonRequest , SwirepaySdk.apiKey!!).execute()
                if(subResponse.isSuccessful && subResponse.body() != null){
                    val subscriptionButton = subResponse.body()!!.entity
                    liveSubscriptionButton.postValue(subscriptionButton)
                }else{
                    liveErrorMessages.postValue("error code : ${response.code()}")
                    Log.d("sdk_test", "fetchPaymentLink: ${response.code()}")
                }
            }
        }else{
            val error = if (response.errorBody() == null) "Unknown" else response.message()
            liveErrorMessages.postValue(error)
            Log.d("sdk_test", "fetchPaymentLink: $error")
        }
    }

    fun fetchSubscriptionButton(subscriptionButtonGid : String) = viewModelScope.launch(Dispatchers.IO){
        val apiClient = ApiClient.retrofit.create(ApiInterface::class.java)
        val response = apiClient.getSubscriptionButton(subscriptionButtonGid , SwirepaySdk.apiKey!!).execute()
        if(response.isSuccessful && response.body() != null){
            liveResult.postValue(response.body()!!.entity)
        }else{
            val code = response.code()
            liveErrorMessages.postValue("error code : $code")
            Log.d("sdk_test", "fetchPaymentLink: ${response.code()}")
        }
    }

}