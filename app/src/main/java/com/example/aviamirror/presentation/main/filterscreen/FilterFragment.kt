package com.example.aviamirror.presentation.main.filterscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aviamirror.R
import com.example.aviamirror.presentation.main.MainActivity
import com.example.aviamirror.presentation.main.mainscreen.TakeOffView
import com.example.aviamirror.presentation.main.ticketscreen.BuyTicketFragment
import com.example.aviamirror.repository.responses.TakeoffResponse

class FilterFragment(
    private var flights: List<TakeoffResponse>
) : Fragment() {

    private var container: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        container = view.findViewById(R.id.container)
        setData()
    }

    private fun setData() {
        container?.removeAllViews()
        flights.forEach {
            val view = TakeOffView(requireContext())
            view.layoutParams?.apply {
                (this as ViewGroup.MarginLayoutParams).setMargins(0, 0, 0, 20)
            } ?: run {
                view.layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 0, 0, 20) }
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
            container?.addView(view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        container?.removeAllViews()
        container = null
    }
}