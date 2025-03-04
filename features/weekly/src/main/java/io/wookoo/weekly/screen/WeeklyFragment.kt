package io.wookoo.weekly.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.vbpd.viewBinding
import io.wookoo.common.collectWithLifecycle
import io.wookoo.weekly.adapters.MainAdapter
import io.wookoo.weekly.databinding.FragmentWeeklyBinding
import io.wookoo.weekly.mvi.WeeklyViewModel
import io.wookoo.weekly.uimodels.CalendarContainer
import io.wookoo.weekly.uimodels.PropertiesContainer
import io.wookoo.weekly.uimodels.UIPropModel
import io.wookoo.weekly.uimodels.UiCalendarDayModel
import io.wookoo.weekly.uimodels.UiCardInfoModel

@AndroidEntryPoint
class WeeklyFragment : Fragment() {

    private var onBackIconClickListener: (() -> Unit)? = null

    private val binding by viewBinding(FragmentWeeklyBinding::bind)
    private val viewModel by viewModels<WeeklyViewModel>()
    private val onIntent by lazy {
        viewModel::onIntent
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
        collectState()
        with(binding) {
        }
    }

    private fun collectState() {
        with(binding) {
            viewModel.state.collectWithLifecycle(viewLifecycleOwner) { uiState ->
                val mainAdapter = MainAdapter()

                mainRecycler.adapter = mainAdapter

                mainAdapter.items = listOf(
                    CalendarContainer(
                        listOf(
                            UiCalendarDayModel(dayName = "12"),
                            UiCalendarDayModel(isToday = true),
                            UiCalendarDayModel(),
                            UiCalendarDayModel(),
                            UiCalendarDayModel(),
                            UiCalendarDayModel(),
                            UiCalendarDayModel(),
                            UiCalendarDayModel(),
                        )
                    ),
                    UiCardInfoModel(
                        tempMax = "23°C", tempMin = "-10°C", feelsLikeMin = "-5°C",
                        feelsLikeMax = "28°C", weatherCondition = "Sunny"
                    ),
                    PropertiesContainer(
                        listOf(
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel(),
                            UIPropModel()
                        )
                    )
                )

                when (uiState) {
                }
            }
        }
    }

    fun setOnBackIconClickListener(listener: () -> Unit) {
        onBackIconClickListener = listener
    }

    companion object {
        private const val TAG = "WeeklyFragment"
    }
}
