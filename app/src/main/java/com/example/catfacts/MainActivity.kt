package com.example.catfacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com/")
            .addConverterFactory()
            .build()
    }
}
