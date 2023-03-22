package com.x7.kotlin_caronavirusapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.x7.kotlin_caronavirusapp.databinding.ActivityMainBinding
import com.x7.kotlin_caronavirusapp.model.Carona
import com.x7.kotlin_caronavirusapp.network.RetrofitInstance.api
import ir.mahozad.android.PieChart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var caronaAdapter: CaronaAdapter
    var arrayList=ArrayList<Carona>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // getAllCountry()

        binding.apply {
            buttongorequest.setOnClickListener {
              getCountryData(edittextcountryname.text.toString())
            }
        }


    }

    fun getCountryData(name:String){
        val call:Call<Carona> = api.getCountryData(name)
        call.enqueue(object :Callback<Carona>{
            override fun onResponse(call: Call<Carona>, response: Response<Carona>){
              if (response.isSuccessful){
              val carona=response.body()
                  binding.apply {
                      textviewcountryname1.text=carona?.country
                      textviewcountrystats1.text="Cases :${carona?.cases}\n Recovered :${carona?.recovered}\n  Deaths :${carona?.deaths} "
                      Glide.with(imageviewcountryflag1).load(carona?.countryInfo?.flag).into(imageviewcountryflag1)

                      //Pie Chart
                      val all=carona?.recovered?.toFloat()!!+carona?.cases?.toFloat()!!+carona?.deaths?.toFloat()!!
                      var recovered=(carona?.recovered?.toFloat()!!/all)
                      var cases=(carona?.cases?.toFloat()!!/all)
                      var deaths=(carona?.deaths?.toFloat()!!/all)
                      binding.pieChart.slices= listOf(
                          PieChart.Slice(recovered, Color.GREEN),
                          PieChart.Slice(cases, Color.BLUE),
                          PieChart.Slice(deaths, Color.RED),

                      )
                      textviewcountryname1.text="${carona?.recovered*100/all} %  ${carona?.cases*100/all} % ${carona?.deaths*100/all} "

                  }
              }
            }

            override fun onFailure(call: Call<Carona>, t: Throwable) {

            }

        })
    }

    fun getAllCountry(){
        val call:Call<ArrayList<Carona>> = api.getAllCountries()
        call.enqueue(object :Callback<ArrayList<Carona>>{
            override fun onResponse(
                call: Call<ArrayList<Carona>>,
                response: Response<ArrayList<Carona>>
            ) {
                if (response.isSuccessful){
                       val arrayList=response.body()!!

                    binding.apply {
                        recyclerview1.layoutManager=GridLayoutManager(this@MainActivity,2)
                        caronaAdapter=CaronaAdapter(this@MainActivity,arrayList)
                        recyclerview1.adapter=caronaAdapter
                    }
                }

            }

            override fun onFailure(call: Call<ArrayList<Carona>>, t: Throwable) {

            }

        })
    }
}