package com.example.cattime.ui.catBreedsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cattime.R
import com.example.cattime.data.DataSource
import com.example.cattime.data.model.Cat
import com.example.cattime.domain.Repository
import com.example.cattime.ui.factory.VMFactory
import com.example.cattime.ui.sharedViewModel.SharedCatViewModel
import com.example.cattime.valueObject.Resource

class CatBreedsListFragment : Fragment(), CatBreedsListAdapter.OnCatClickListener {

    private val viewModel by viewModels<CatBreedsListViewModel> {
        VMFactory(
                Repository(
                        DataSource()
                )
        )
    }
    private val shareCatViewModel: SharedCatViewModel by activityViewModels()

    private lateinit var catListRv: RecyclerView
    private lateinit var catBreedsListAdapter: CatBreedsListAdapter
    private lateinit var progressBar: RelativeLayout
    private lateinit var spinner: Spinner

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cat_breeds_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catListRv = view.findViewById(R.id.rv_catList)
        progressBar = view.findViewById(R.id.progressBar)
        spinner = view.findViewById(R.id.spinner_country)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        catListRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        viewModel.fetchCatList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    catBreedsListAdapter = CatBreedsListAdapter(requireContext(), result.data, this)
                    catListRv.adapter = catBreedsListAdapter
                    setupSpinnerCountries(result.data)
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                            requireContext(),
                            "Something went wrong: ${result.exception}",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupSpinnerCountries(catList: List<Cat>) {
        spinner.visibility = View.VISIBLE
        val countriesSet = hashSetOf<String>()
        for (cat in catList) {
            countriesSet.add(cat.origin)
        }
        val countriesList = countriesSet.sorted().toList() as ArrayList<String>
        countriesList.add(0, "Select a country")
        spinner.visibility = View.VISIBLE
        spinner.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                countriesList
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                val country = parent?.getItemAtPosition(position).toString()
                catBreedsListAdapter.filter(country)
                catListRv.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCatClick(cat: Cat) {
        shareCatViewModel.shareCat(cat)
        findNavController().navigate(R.id.catDetailsFragment)
    }

}