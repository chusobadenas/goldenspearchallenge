package com.jesusbadenas.goldenspearchallenge.artist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesusbadenas.goldenspearchallenge.R
import com.jesusbadenas.goldenspearchallenge.databinding.ArtistFragmentBinding
import com.jesusbadenas.goldenspearchallenge.navigation.Navigator
import com.jesusbadenas.goldenspearchallenge.viewmodel.ArtistViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

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
        view.findViewById<RecyclerView>(R.id.artists_rv).apply {
            layoutManager = this@ArtistFragment.layoutManager
            adapter = artistAdapter
        }
    }

    private fun handleSearch() {
        arguments?.getString(Navigator.QUERY_ARG_KEY)?.let { query ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.searchArtists(query)
                    .catch { exception ->
                        Timber.e(exception)
                    }
                    .collectLatest { pagingData ->
                        artistAdapter.submitData(pagingData)
                    }
            }
        }
    }
}
