package com.example.cattime.ui.catBreedsList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cattime.R
import com.example.cattime.data.model.Cat

class CatBreedsListAdapter(
        private val context: Context,
        private val catList: List<Cat>,
        private val itemClickListener: OnCatClickListener)
    : RecyclerView.Adapter<CatBreedsListAdapter.CatBreedsViewHolder>() {

    interface OnCatClickListener {
        fun onCatClick(cat: Cat)
    }

    private var visibleList = catList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedsViewHolder {
        return CatBreedsViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_card, parent, false))
    }

    override fun getItemCount(): Int {
        return visibleList.size
    }

    override fun onBindViewHolder(holder: CatBreedsViewHolder, position: Int) {
        val cat = visibleList[position]
        holder.bindView(cat)
    }

    fun filter(country: String) {
        visibleList.clear()
        if (country.toLowerCase().trim() != "Select a country".toLowerCase().trim()) {
            catList.forEach { cat ->
                if (cat.origin.toLowerCase().trim() == country.toLowerCase().trim()) {
                    visibleList.add(cat)
                }
            }
        } else {
            visibleList = catList.toMutableList()
        }
        notifyDataSetChanged()
    }

    inner class CatBreedsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var cardImage: ImageView
        private lateinit var cardTitle: TextView
        private lateinit var cardDescription: TextView
        fun bindView(cat: Cat) {
            cardImage = itemView.findViewById(R.id.card_imageView)
            cardTitle = itemView.findViewById(R.id.card_tv_name)
            cardDescription = itemView.findViewById(R.id.card_tv_description)

            Glide.with(context).load(cat.catImage.url).centerCrop().into(cardImage)
            cardTitle.text = cat.name
            cardDescription.text = cat.description
            itemView.setOnClickListener { itemClickListener.onCatClick(cat) }
        }
    }
}