package com.example.aviamirror.presentation.main.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aviamirror.R
import com.example.aviamirror.presentation.main.MainActivity
import com.example.aviamirror.presentation.main.filterscreen.FilterFragment
import com.example.aviamirror.presentation.main.ticketscreen.BuyTicketFragment
import com.example.aviamirror.repository.responses.TakeoffResponse
import com.example.aviamirror.utils.Result
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.android.synthetic.main.view_takeoff.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {
    private var calendarFrom: CalendarView? = null
    private var calendarTo: CalendarView? = null
    private var spinnerCityFrom: Spinner? = null
    private var spinnerCityTo: Spinner? = null
    private var loading: CircularProgressIndicator? = null
    private var popularTakeoffsContainer: LinearLayout? = null
    private var filter: Button? = null

    private val mainViewModel: MainViewModel by viewModels()

    private var dateFrom: String = ""
    private var dateTo: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarFrom = view.findViewById(R.id.calendar_from)
        calendarTo = view.findViewById(R.id.calendar_to)
        spinnerCityFrom = view.findViewById(R.id.spinner_city_from)
        spinnerCityTo = view.findViewById(R.id.spinner_city_to)
        loading = view.findViewById(R.id.loading)
        popularTakeoffsContainer = view.findViewById(R.id.popularTakeoffsContainer)
        filter = view.findViewById(R.id.filter)

        val dateF = Date(calendarFrom?.date ?: 0)
        val dateT = Date(calendarTo?.date ?: 0)

        dateFrom = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateF)
        dateTo = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateT)

        setObservers()
        setListeners()

        mainViewModel.retrieveMostPopularTakeoffs()
        mainViewModel.retrieveCitiesList()
    }

    private fun setListeners() {
        calendarFrom?.setOnDateChangeListener { calendarView, i, i2, i3 ->
            val month = if (i2 + 1 < 10) "0${i2 + 1}" else "${i2 + 1}"
            val day = if (i3 < 10) "0$i3" else "$i3"
            dateFrom = "${i}-$month-$day 00:00:00"
        }

        calendarTo?.setOnDateChangeListener { calendarView, i, i2, i3 ->
            val month = if (i2 + 1 < 10) "0${i2 + 1}" else "${i2 + 1}"
            val day = if (i3 < 10) "0$i3" else "$i3"
            dateTo = "${i}-$month-$day 23:59:59"
        }


        filter?.setOnClickListener {
            val request = TakeoffResponse(
                0,
                spinnerCityFrom?.selectedItem.toString(),
                spinnerCityTo?.selectedItem.toString(),
                dateFrom,
                dateTo
            )



            mainViewModel.retrieveFlightsByFilter(request)
        }
    }

    private fun setObservers() {
        mainViewModel.popularTakeoffs.observe(viewLifecycleOwner) { result ->

            if (result.isError) {
                showToast(result.exception?.message.toString())
            }

            result.data?.let { data ->
                popularTakeoffsContainer?.removeAllViews()
                data.forEach {
                    val view = TakeOffView(requireContext())
                    view.layoutParams?.apply {
                        (this as ViewGroup.MarginLayoutParams).setMargins(0, 0, 0, 12)
                    } ?: run {
                        view.layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { setMargins(0, 0, 0, 12) }
                    }
                    view.render(
                        TakeOffView.State(
                            timetableId = it.timetableId,
                            timeFrom = it.timeFrom,
                            timeTo = it.timeTo,
                            cityFrom = it.cityFrom,
                            cityTo = it.cityTo,
                            onClickListener = { view ->
                                BuyTicketFragment(it.timetableId).show(
                                    parentFragmentManager,
                                    "BuyTicketFragment"
                                )
                            }
                        )
                    )
                    popularTakeoffsContainer?.addView(view)
                }
            }
        }

        mainViewModel.cities.observe(viewLifecycleOwner) { result ->
            if (result.isError) {
                showToast(result.exception?.message.toString())
            }

            result.data?.let {
                val adapter: ArrayAdapter<String> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    it
                );

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                val adapter1: ArrayAdapter<String> = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    it
                );

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCityFrom?.adapter = adapter
                spinnerCityTo?.adapter = adapter1
            }
        }

        mainViewModel.filterQueryResponse.observe(viewLifecycleOwner) { result ->
            if (result.isError) {
                showToast(result.exception?.message.toString())
            }

            result.data?.let { data ->
                (requireActivity() as MainActivity).navigate(FilterFragment(data))
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.popularTakeoffs.value = Result()
        mainViewModel.popularTakeoffs.removeObservers(viewLifecycleOwner)
        mainViewModel.cities.value = Result()
        mainViewModel.cities.removeObservers(viewLifecycleOwner)
        mainViewModel.filterQueryResponse.value = Result()
        mainViewModel.filterQueryResponse.removeObservers(viewLifecycleOwner)
        calendarFrom = null
        calendarTo = null
        spinnerCityFrom = null
        spinnerCityTo = null
        loading = null
        filter = null
        popularTakeoffsContainer = null
    }
}