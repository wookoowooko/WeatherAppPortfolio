package io.wookoo.weekly.screen

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.common.ext.collectWithLifecycle
import io.wookoo.designsystem.ui.Crossfade
import io.wookoo.models.ui.UiCardInfoModel
import io.wookoo.weekly.adapters.MainAdapter
import io.wookoo.weekly.adapters.diffCallback
import io.wookoo.weekly.databinding.FragmentWeeklyBinding
import io.wookoo.weekly.delegates.calendar.calendarAdapterDelegate
import io.wookoo.weekly.mvi.OnCalendarItemClick
import io.wookoo.weekly.mvi.WeeklyEffect
import io.wookoo.weekly.mvi.WeeklyState
import io.wookoo.weekly.mvi.WeeklyViewModel

@AndroidEntryPoint
class WeeklyFragment : Fragment() {

    var onShowSnackBar: ((String) -> Unit)? = null

    private val binding by viewBinding(FragmentWeeklyBinding::bind)
    private val viewModel by viewModels<WeeklyViewModel>()
    private val onIntent by lazy {
        viewModel::onIntent
    }

    private val mainAdapter by lazy { MainAdapter() }
    private val calendarAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            AsyncDifferConfig.Builder(diffCallback).build(),
            calendarAdapterDelegate(
                onItemClick = { pos ->
                    onIntent(OnCalendarItemClick(pos))
                }
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentWeeklyBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        collectState()
        collectEffects()
    }

    private fun setupRecyclerViews() {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        binding.mainRecycler.layoutManager = if (isLandscape) {
            GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (mainAdapter.items[position]) {
                            is UiCardInfoModel -> 2
                            else -> 1
                        }
                    }
                }
            }
        } else {
            LinearLayoutManager(requireContext())
        }

        binding.mainRecycler.adapter = mainAdapter
        binding.calendarRecycler.adapter = calendarAdapter
    }

    private fun collectEffects() {
        viewModel.sideEffect.collectWithLifecycle(viewLifecycleOwner) { effect ->
            when (effect) {
                is WeeklyEffect.OnShowSnackBar -> {
                    onShowSnackBar?.invoke(
                        effect.error.asLocalizedString(
                            requireContext()
                        )
                    )
                }

                null -> Unit
            }
        }
    }

    private fun collectState() {
        viewModel.state.collectWithLifecycle(viewLifecycleOwner) { uiState: WeeklyState ->
            with(binding) {
                val crossFade = when {
                    uiState.isLoading -> Crossfade.LOADING
                    else -> Crossfade.CONTENT
                }
                when (crossFade) {
                    Crossfade.LOADING -> manageLoading()
                    Crossfade.CONTENT -> manageContent(uiState)
                }
            }
        }
    }

    private fun FragmentWeeklyBinding.manageContent(state: WeeklyState) {
        mainAdapter.items = state.mainWeatherRecyclerItems.mainWeatherRecyclerItems
        calendarAdapter.items = state.weeklyCalendar
        updateVisibility(
            ViewVisibilityState(
                mainRecycler = View.VISIBLE,
                calendarRecycler = View.VISIBLE
            )
        )
    }

    private fun FragmentWeeklyBinding.manageLoading() {
        updateVisibility(
            ViewVisibilityState(
                loadingIndicator = View.VISIBLE
            )
        )
    }

    private fun FragmentWeeklyBinding.updateVisibility(state: ViewVisibilityState) {
        lottieLoader.visibility = state.loadingIndicator
        calendarRecycler.visibility = state.calendarRecycler
        mainRecycler.visibility = state.mainRecycler
    }
}
