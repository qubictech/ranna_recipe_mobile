package rannaghor.recipe.tarmsbd.com.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://rannaghor.tarmsbd.com/"

object RetrofitClient {
    private var INSTANCE: Retrofit? = null

    val instance: Retrofit
        get() {
            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }

            return INSTANCE!!
        }

}