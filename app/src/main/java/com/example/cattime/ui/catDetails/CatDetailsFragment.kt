package com.example.cattime.ui.catDetails

import android.os.Bundle
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.cattime.R
import com.example.cattime.data.model.Cat
import com.example.cattime.ui.sharedViewModel.SharedCatViewModel

class CatDetailsFragment : Fragment() {
    private val sharedCatViewModel: SharedCatViewModel by activityViewModels()
    private lateinit var catImageView: ImageView
    private lateinit var breedTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var countryCodeTextView: TextView
    private lateinit var wikipediaLinkTextView: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cat_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catImageView = view.findViewById(R.id.imageView_cat_details)
        breedTextView = view.findViewById(R.id.textView_breed)
        descriptionTextView = view.findViewById(R.id.textView_description)
        countryCodeTextView = view.findViewById(R.id.textView_country)
        wikipediaLinkTextView = view.findViewById(R.id.textView_wikipedia)

        sharedCatViewModel.cat.observe(viewLifecycleOwner, Observer<Cat> { selectedCat ->
            Glide.with(requireContext()).load(selectedCat.catImage.url).fitCenter().into(catImageView)
            breedTextView.text = selectedCat.name
            descriptionTextView.text = selectedCat.description
            countryCodeTextView.text = selectedCat.country_code
            wikipediaLinkTextView.text = selectedCat.wikipedia_url
            Linkify.addLinks(wikipediaLinkTextView, Linkify.WEB_URLS)
        })
    }

}