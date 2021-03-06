package com.aplikasikaryaanakbangkit.sentiment.news

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aplikasikaryaanakbangkit.sentiment.R
import com.aplikasikaryaanakbangkit.sentiment.core.viewmodel.ViewModelFactory
import com.aplikasikaryaanakbangkit.sentiment.core.vo.Status
import com.aplikasikaryaanakbangkit.sentiment.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.fragment_home.*

class NewsFragment : Fragment() {

    private var _fragmentNewsBinding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val _binding get() = _fragmentNewsBinding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _fragmentNewsBinding = FragmentNewsBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

            loadCovidNews(newsViewModel)
            loadVaccineNews(newsViewModel)

            val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
            /*event ketika widget dijalankan*/
            swipeRefreshLayout.setOnRefreshListener(object :
                    SwipeRefreshLayout.OnRefreshListener {
                override fun onRefresh() {
                    refreshItem()
                }

                fun refreshItem() {
                    true.shimmerLoading()
                    Handler(requireActivity().mainLooper).postDelayed({
                        swipeRefreshLayout.isRefreshing = false

                        loadCovidNews(newsViewModel)
                        loadVaccineNews(newsViewModel)
                    }, 500)
                    onItemLoad()
                }

                fun onItemLoad() {
                    swipeRefreshLayout.isRefreshing = false
                }
            })
        }
    }

    private fun loadVaccineNews(newsViewModel: NewsViewModel) {
        newsViewModel.newsVaccine.observe(viewLifecycleOwner, { newsVaccine ->
            if (newsVaccine != null) {
                when (newsVaccine.status) {
                    Status.LOADING -> true.shimmerLoading()
                    Status.SUCCESS -> {
                        _fragmentNewsBinding?.covidNews?.newsActivityVertical?.let {
                            with(it.rvVertical) {

                                val layoutManagerVertical = GridLayoutManager(context, 2)
                                this.layoutManager = layoutManagerVertical
                                this.setHasFixedSize(true)

                                val newsVaccineAdapter = NewsVaccineAdapter()
                                this.adapter = newsVaccineAdapter
                                newsVaccineAdapter.submitList(newsVaccine.data)
                            }
                        }
                        false.shimmerLoading()
                    }
                    Status.ERROR -> {
                        false.shimmerLoading()
                        _binding.viewError.viewError.visibility = View.VISIBLE
                        Toast.makeText(
                                activity?.applicationContext,
                                getString(R.string.error_msg),
                                Toast.LENGTH_SHORT
                        )
                                .show()
                    }
                }
            }
        })
    }

    private fun loadCovidNews(newsViewModel: NewsViewModel) {
        newsViewModel.newsCovidHeadline.observe(viewLifecycleOwner, { newsCovid ->
            if (newsCovid != null) {
                when (newsCovid.status) {
                    Status.LOADING -> true.shimmerLoading()
                    Status.SUCCESS -> {
                        _fragmentNewsBinding?.covidNews?.newsActivityHorizontal?.let {
                            with(it.rvHorizontal) {
                                val layoutManagerHorizontal =
                                        LinearLayoutManager(
                                                context,
                                                LinearLayoutManager.HORIZONTAL,
                                                false
                                        )
                                this.layoutManager = layoutManagerHorizontal
                                this.setHasFixedSize(true)

                                val newsCovidAdapter = NewsCovidAdapter()
                                this.adapter = newsCovidAdapter
                                newsCovidAdapter.submitList(newsCovid.data)
                            }
                        }
                        false.shimmerLoading()
                    }
                    Status.ERROR -> {
                        false.shimmerLoading()
                        _binding.viewError.viewError.visibility = View.VISIBLE
                        Toast.makeText(
                                activity?.applicationContext,
                                getString(R.string.error_msg),
                                Toast.LENGTH_SHORT
                        )
                                .show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentNewsBinding = null
    }

    private fun Boolean.shimmerLoading() {
        if (this) {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            scrollViewLayout.visibility = View.GONE
        } else {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            scrollViewLayout.visibility = View.VISIBLE
            Thread.sleep(750)
        }
    }
}