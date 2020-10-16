package com.jesusbadenas.goldenspearchallenge.artist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.databinding.ArtistFragmentBinding
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import com.jesusbadenas.goldenspearchallenge.util.showError
import com.jesusbadenas.goldenspearchallenge.util.toUIError
import com.jesusbadenas.goldenspearchallenge.viewmodel.ArtistViewModel
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
        observe()

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

    private fun setupViews(view: View) {
        // Recycler view
        layoutManager = LinearLayoutManager(context)
        artistAdapter.addLoadStateListener { loadState ->
            handleState(loadState)
        }
        view.findViewById<RecyclerView>(R.id.artists_rv).apply {
            layoutManager = this@ArtistFragment.layoutManager
            adapter = artistAdapter
        }
    }

    private fun handleState(loadState: CombinedLoadStates) {
        // Show progress bar
        val isLoading =
            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading
        viewModel.emptyTextVisible.value = !isLoading && artistAdapter.itemCount == 0
        viewModel.loadingVisible.value = isLoading
        // Show error
        when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }?.let { errorState ->
            showError(errorState.error.toUIError())
        }
    }

    private fun observe() {
        viewModel.artists.observe(viewLifecycleOwner) { pagingData ->
            artistAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
        viewModel.uiError.observe(viewLifecycleOwner) { uiError ->
            showError(uiError)
        }
    }

    private fun handleSearch() {
        arguments?.getString(Navigator.QUERY_ARG_KEY)?.let { query ->
            viewModel.searchArtists(query)
        }
    }
}
