package com.swirepay.android_sdk.retrofit

import com.swirepay.android_sdk.model.PaymentLink
import com.swirepay.android_sdk.model.PaymentRequest
import com.swirepay.android_sdk.model.SuccessResponse
import com.swirepay.android_sdk.ui.payment_method.SetupSession
import com.swirepay.android_sdk.ui.subscription_button.model.Plan
import com.swirepay.android_sdk.ui.subscription_button.model.PlanRequest
import com.swirepay.android_sdk.ui.subscription_button.model.SubscriptionButton
import com.swirepay.android_sdk.ui.subscription_button.model.SubscriptionButtonRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("v1/payment-link")
    fun fetchPaymentLink(@Body body: PaymentRequest, @Header("x-api-key") api_key: String): Call<SuccessResponse<PaymentLink>>

    @GET("v1/payment-link/{paymentLinkGid}")
    fun checkStatus(@Path("paymentLinkGid") paymentLinkGid: String) : Call<SuccessResponse<PaymentLink>>

    @POST("v1/plan")
    fun createPlan(@Body planRequest : PlanRequest , @Header("x-api-key") api_key: String) : Call<SuccessResponse<Plan>>

    @POST("v1/subscription-button")
    fun createSubscriptionButton(@Body subscriptionButtonRequest: SubscriptionButtonRequest ,  @Header("x-api-key") api_key: String) : Call<SuccessResponse<SubscriptionButton>>

    @GET("v1/subscription-button/{subscriptionButtonId}")
    fun getSubscriptionButton(@Path("subscriptionButtonId") subscriptionButtonId: String,  @Header("x-api-key") api_key: String) : Call<SuccessResponse<SubscriptionButton>>

    @GET("v1/setup-session/{setupSessionId}")
    fun getSetupSession(@Path("setupSessionId") setupSessionId: String,  @Header("x-api-key") api_key: String) : Call<SuccessResponse<SetupSession>>


}