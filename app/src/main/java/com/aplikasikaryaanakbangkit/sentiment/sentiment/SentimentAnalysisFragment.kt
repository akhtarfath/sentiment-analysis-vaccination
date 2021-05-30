package com.aplikasikaryaanakbangkit.sentiment.sentiment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aplikasikaryaanakbangkit.sentiment.R
import com.aplikasikaryaanakbangkit.sentiment.core.viewmodel.ViewModelFactory
import com.aplikasikaryaanakbangkit.sentiment.databinding.FragmentSentimentAnalysisBinding
import kotlinx.android.synthetic.main.fragment_home.*

class SentimentAnalysisFragment : Fragment() {

    private var _sentimentAnalysisBinding: FragmentSentimentAnalysisBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val _binding get() = _sentimentAnalysisBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _sentimentAnalysisBinding =
            FragmentSentimentAnalysisBinding.inflate(inflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        val factory = ViewModelFactory.getInstance(requireContext())
        val sentimentAnalysisViewModel =
            ViewModelProvider(this, factory)[SentimentAnalysisViewModel::class.java]

        loadTweet(sentimentAnalysisViewModel)

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

                    loadTweet(sentimentAnalysisViewModel)
                }, 750)
                onItemLoad()
            }

            fun onItemLoad() {
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun loadTweet(sentimentAnalysisViewModel: SentimentAnalysisViewModel) {
        sentimentAnalysisViewModel.getTweet().observe(viewLifecycleOwner, { tweet ->
            with(_sentimentAnalysisBinding?.layoutRvTweetsPost?.rvTweet) {
                val layoutManagerVertical =
                    LinearLayoutManager(context)
                this?.layoutManager = layoutManagerVertical
                this?.setHasFixedSize(true)

                val tweetAdapter = SentimentAnalysisAdapter()
                this?.adapter = tweetAdapter

                tweet.let { tweetAdapter.setTweet(it) }
                tweetAdapter.notifyDataSetChanged()
            }
            false.shimmerLoading()
        })

//        sentimentAnalysisViewModel.getPost().observe(viewLifecycleOwner, { post ->
//            Log.d("Post Fragment", post.status.toString())
//            Log.d("Post Fragment", post.data.toString())
//            Log.d("Post Fragment", post.message.toString())
//            false.shimmerLoading()
//        })
//
//        sentimentAnalysisViewModel.getProfile().observe(viewLifecycleOwner, { profile ->
//            Log.d("profile Fragment", profile.status.toString())
//            Log.d("profile Fragment", profile.data.toString())
//            Log.d("profile Fragment", profile.message.toString())
//            false.shimmerLoading()
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _sentimentAnalysisBinding = null
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