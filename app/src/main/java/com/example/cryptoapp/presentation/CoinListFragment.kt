package com.example.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.CryptoApplication
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentCoinListBinding
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinListFragment : Fragment() {
    private val binding: FragmentCoinListBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinListBinding is null")
    private var _binding: FragmentCoinListBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: CoinInfoAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CryptoApplication).component
            .activityComponentFactory()
            .create(requireActivity())
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = setAdapter()
        binding.rvCoinPriceList.itemAnimator = null
        setObservers(adapter)
    }

    private fun setObservers(adapter: CoinInfoAdapter) {
        viewModel.getCoinInfoList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setAdapter(): CoinInfoAdapter {
        //val adapter = CoinInfoAdapter(requireActivity())
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfoEntity) {

                parentFragmentManager
                    .beginTransaction()
                    .replace(
                        if (Utils.isLandscape(requireActivity()))
                            R.id.fragment_container_land else
                            R.id.fragment_container,
                        CoinDetailFragment.newInstance(coinInfo.fromSymbol),
                        "details"
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        return adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}