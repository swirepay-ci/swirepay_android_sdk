package com.swirepay.swirepay_sdk_playground

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.swirepay.android_sdk.KeyNotInitializedException
import com.swirepay.android_sdk.SwirepaySdk
import com.swirepay.android_sdk.model.CurrencyType
import com.swirepay.android_sdk.model.PaymentLink
import com.swirepay.android_sdk.ui.payment_activity.PaymentActivity
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SwirepaySdk.initSdk("sk_test_xkNDG8VLfNYEqOMVvrMho98K60NGkuyQ")
        setContentView(R.layout.activity_main)
        val button : Button = findViewById(R.id.btnPayment)
        button.setOnClickListener {
            val listOfPaymentMethods = ArrayList<String>().apply {
                add("CARD")
                add("UPI")
            }
            SwirepaySdk.doPayment(this, 10000, CurrencyType.INR, REQUEST_CODE ,
                listOfPaymentMethods
            )
        }
        val btnSubscriptionButton : Button = findViewById(R.id.btnSubscriptionButton)
        btnSubscriptionButton.setOnClickListener {
            SwirepaySdk.createSubscriptionButton(this , "test1" , 10000 , "test description" , CurrencyType.INR , "MONTH" , 1 , REQUEST_CODE_SUBSCRIPTION_BUTTON  , Calendar.getInstance().time)
        }
        val btnPaymentMethod : Button = findViewById(R.id.btnPaymentMethod)
        btnPaymentMethod.setOnClickListener {
            SwirepaySdk.createPaymentMethod(this , REQUEST_CODE_PAYMENT_METHOD)
        }
        val btnCreateAccount : Button = findViewById(R.id.btnAccount)
        btnCreateAccount.setOnClickListener {
            SwirepaySdk.createAccount(this , REQUEST_CODE_PAYMENT_METHOD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val resultText = findViewById<TextView>(R.id.tvResult)
        val responseText = findViewById<TextView>(R.id.tvResponse)
        if(requestCode == REQUEST_CODE){
            val paymentResult = SwirepaySdk.getPaymentLink(resultCode , data)
            Log.d("sdk_test", "onActivityResult: $paymentResult")
            resultText.text = paymentResult.entity.toString()
            responseText.text = paymentResult.toString()
        }else if(requestCode == REQUEST_CODE_SUBSCRIPTION_BUTTON){
            val result = SwirepaySdk.getSubscriptionButton(resultCode , data)
            Log.d("sdk_test", "onActivityResult: $result")
            resultText.text = result.toString()
            responseText.text = result.entity.toString()
        }else if(requestCode == REQUEST_CODE_PAYMENT_METHOD){
            val result = SwirepaySdk.getSetupSession(resultCode , data)
            Log.d("sdk_test", "onActivityResult: $result")
           resultText.text = result.toString()
            responseText.text = result.entity.toString()
        }else if (requestCode == REQUEST_CODE_CONNECT_ACCOUNT){
            val result = SwirepaySdk.getSetupSession(resultCode , data)
            Log.d("sdk_test", "onActivityResult: $result")
            resultText.text = result.toString()
            responseText.text = result.entity.toString()
        }
    }

    companion object{
        const val REQUEST_CODE = 1001
        const val REQUEST_CODE_SUBSCRIPTION_BUTTON = 1002
        const val REQUEST_CODE_PAYMENT_METHOD = 1003
        const val REQUEST_CODE_CONNECT_ACCOUNT = 1004
    }

}