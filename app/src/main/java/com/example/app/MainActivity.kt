package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val client = OkHttpClient()
    val FORM = "application/x-www-form-urlencoded".toMediaType()

    fun httpPost(url: String, body: RequestBody, success: (response: Response) -> Unit, failure: () -> Unit){
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                failure()
                Log.v("INFO", "Failed")
            }

            override fun onResponse(call: Call, response: Response) {
                success(response)
                Log.v("INFO", "Success")
            }
        })
    }

fun login(login: String, password: String) {
    Toast.makeText(this, "Logowanie ("+ login + ":" + password +")", Toast.LENGTH_SHORT).show()
    val url = "https://google.com"
    val body = ("session[index]=" + login + "Session[passowrd]=" + password ).toRequestBody(FORM)
    httpPost(url,body,
    fun(response: Response){
        Log.v("INFO", "Success")
        val response_string = response.body?.string()
        Log.v("INFO", response_string.toString())
    },
    fun(){
        Log.v("INFO", "Failed")
    }
    )
}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (login_field.requestFocus())
        {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
        login_button.setOnClickListener {
            val login =login_field.text.toString()
            val password = password_field.text.toString()
                login(login, password)


        }

    }



}