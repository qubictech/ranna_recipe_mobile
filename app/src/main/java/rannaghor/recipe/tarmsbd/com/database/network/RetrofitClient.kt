package rannaghor.recipe.tarmsbd.com.database.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://rannaghor.tarmsbd.com/"

object RetrofitClient {
    private var mInstance: Retrofit? = null

    val INSTANCE: Retrofit
        get() {
            if (mInstance == null) {
                mInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }

            return mInstance!!
        }

}