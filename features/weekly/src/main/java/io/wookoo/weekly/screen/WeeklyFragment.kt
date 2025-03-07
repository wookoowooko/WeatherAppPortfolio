package io.wookoo.weekly.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncDifferConfig
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import io.wookoo.common.collectWithLifecycle
import io.wookoo.weekly.adapters.MainAdapter
import io.wookoo.weekly.adapters.diffCallback
import io.wookoo.weekly.databinding.FragmentWeeklyBinding
import io.wookoo.weekly.delegates.calendar.calendarAdapterDelegate
import io.wookoo.weekly.mvi.WeeklyViewModel
import io.wookoo.weekly.mvi.WeeklyViewModelContract

@AndroidEntryPoint
class WeeklyFragment : Fragment() {

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
                    onIntent(WeeklyViewModelContract.OnIntent.OnCalendarItemClick(pos))
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
//        ViewCompat.setOnApplyWindowInsetsListener(binding.ordersRecycler) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(0, 0, 0, systemBars.bottom)
//            insets
//        }
        Log.d(TAG, "onViewCreated")
        with(binding) {
            mainRecycler.adapter = mainAdapter
            calendarRecycler.adapter = calendarAdapter
        }
        collectState()
    }

    private fun collectState() {
        viewModel.state.collectWithLifecycle(viewLifecycleOwner) { uiState: WeeklyViewModelContract.WeeklyState ->
            with(binding) {
                Log.d(TAG, "collectState: $uiState")

//                // Логирование состояния
//                Log.d(TAG, "Main weather items: ${uiState.mainWeatherRecyclerItems.mainWeatherRecyclerItems.size}")
//                Log.d(TAG, "Calendar items: ${uiState.weeklyCalendar.size}")

                mainAdapter.items = uiState.mainWeatherRecyclerItems.mainWeatherRecyclerItems
                calendarAdapter.items = uiState.weeklyCalendar

//                when (uiState) {
//                    // Добавить проверку состояния, если необходимо
//                }
            }
        }
    }

    companion object {
        private const val TAG = "WeeklyFragment"
    }
}
