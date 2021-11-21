package com.heydari.usingretrofitandmvvm.helper

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heydari.usingretrofitandmvvm.R
import com.heydari.usingretrofitandmvvm.model.*
import com.rishabhharit.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class CountryListAdapter(private val context: Context) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>() {

    private var countryList: List<CountryModel>? = null
    private var mContext:Context? = null

    fun setCountryList(countryList: List<CountryModel>) {
        this.countryList = countryList
        this.mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        return holder.bind(countryList!![position])
    }

    override fun getItemCount(): Int {
        if (countryList == null)
            return 0
        else
            return countryList?.size!!
    }

    inner class MyViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val imageFlage = view.findViewById<RoundedImageView>(R.id.flagImage)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvContinent = view.findViewById<TextView>(R.id.tvContinent)
        val tvPopulation = view.findViewById<TextView>(R.id.tvPopulation)

        @SuppressLint("SetTextI18n")
        fun bind(data: CountryModel) {
            tvName.text = "Country: " + data.country
            tvContinent.text = "continent: " + data.continent
            tvPopulation.text = "population: " + data.population
            Picasso.get().load(data.countryInfo.flag).into(imageFlage)

            itemView.setOnClickListener {
                mContext?.let { it1 -> alertDialog(it1,
                        "country name:  " + data.country +"\n"+
                                "continent:  " + data.continent + "\n"+
                                "population:  " + data.population + "\n"+
                                "critical:  " + data.critical + "\n"+
                                "deaths:  " + data.deaths + "\n"+
                                "oneCasePerPeople:  " + data.oneCasePerPeople + "\n"+
                                "oneDeathPerPeople:  " + data.oneDeathPerPeople + "\n"+
                                "oneTestPerPeople:  " + data.oneTestPerPeople + "\n"+
                                "recovered:  " + data.recovered + "\n"+
                                "tests:  " + data.tests + "\n"+
                                "todayCases:  " + data.todayCases + "\n"+
                                "todayDeaths:  " + data.todayDeaths + "\n"+
                                "todayRecovered:  " +data.todayRecovered) }
            }

        }

        private fun alertDialog(context: Context, message:String) {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Detailed information")
                    .setMessage(message)
                    .setCancelable(true)
                    .setPositiveButton("Ok") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
        }

    }

    fun filterList(filteredList: ArrayList<CountryModel>) {
        countryList = filteredList
        notifyDataSetChanged()
    }

}