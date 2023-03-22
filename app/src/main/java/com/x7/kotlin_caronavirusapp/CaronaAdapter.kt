package com.x7.kotlin_caronavirusapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.x7.kotlin_caronavirusapp.databinding.RecyclerviewItemBinding
import com.x7.kotlin_caronavirusapp.model.Carona

class CaronaAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<Carona>
):RecyclerView.Adapter<CaronaAdapter.CaronaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaronaViewHolder {
        val view=RecyclerviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CaronaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaronaViewHolder, position: Int) {
        holder.binding.apply {
            textviewcountryname.text=arrayList.get(position).country
            textviewcountrystats.text="Cases :${arrayList.get(position).cases}\n Recovered :${arrayList.get(position).recovered}\n  Deaths :${arrayList.get(position).deaths} "
            Glide.with(context).load(arrayList.get(position).countryInfo.flag).into(imageviewcountryflag)

        }
    }
    override fun getItemCount(): Int =arrayList.size

    class CaronaViewHolder(val binding: RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root)
}