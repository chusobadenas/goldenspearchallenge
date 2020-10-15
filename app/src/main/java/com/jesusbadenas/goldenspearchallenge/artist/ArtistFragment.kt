package com.jesusbadenas.goldenspearchallenge.artist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.databinding.ArtistFragmentBinding
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import com.jesusbadenas.goldenspearchallenge.viewmodel.ArtistViewModel
import com.jesusbadenas.goldenspearchallenge.viewmodel.ArtistViewModel.Companion.MAX_SIZE
import org.koin.android.ext.android.inject

class ArtistFragment : Fragment() {

    private val artistAdapter: ArtistAdapter by inject()
    private val viewModel: ArtistViewModel by inject()

    private lateinit var binding: ArtistFragmentBinding
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.artist_fragment, container, false)
        binding.lifecycleOwner = this

        // View model
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        setupViews(binding.root)
        subscribe()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchableInfo = searchManager.getSearchableInfo(activity?.componentName)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchableInfo)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleSearch()
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
            val totalItemCount: Int = layoutManager.itemCount
            val visibleItemCount: Int = layoutManager.childCount
            val isLastPage = totalItemCount >= MAX_SIZE

            if (!viewModel.isLoading
                && !isLastPage
                && firstVisibleItemPosition > 0
                && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
            ) {
                loadMoreArtists()
            }
        }
    }

    private fun setupViews(view: View) {
        // Recycler view
        layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.artists_rv).apply {
            layoutManager = this@ArtistFragment.layoutManager
            adapter = artistAdapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun subscribe() {
        viewModel.artists.observe(viewLifecycleOwner) { list ->
            artistAdapter.submitList(list)
        }
    }

    private fun handleSearch() {
        arguments?.getString(Navigator.QUERY_ARG_KEY)?.let { query ->
            viewModel.loadArtists(query)
        }
    }

    private fun loadMoreArtists() {
        arguments?.getString(Navigator.QUERY_ARG_KEY)?.let { query ->
            viewModel.loadMoreArtists(query)
        }
    }
}
