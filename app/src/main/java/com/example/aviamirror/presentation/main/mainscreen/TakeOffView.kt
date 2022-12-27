package com.example.aviamirror.presentation.main.mainscreen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.example.aviamirror.R
import com.example.aviamirror.databinding.ViewTakeoffBinding

class TakeOffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding = ViewTakeoffBinding.inflate(LayoutInflater.from(context), this)
    private var currentState: State = State(0, "no time", "no time", "no city", "no city")
        private set
    private var onClick: OnClickListener? = null

    init {
        isClickable = true
        isFocusable = true

        setOnClickListener { onClick?.onClick(it) }
        setStyleView()
    }

    private fun setStyleView() {
        binding.root.background = ContextCompat.getDrawable(context, R.drawable.takeoff_view_background)
        binding.root.setPadding(5)
    }

    fun render(state: State) {
        val datetimeFrom = splitTime(state.timeFrom)
        val datetimeTo = splitTime(state.timeTo)
        binding.cityTo.text = state.cityTo
        binding.cityFrom.text = state.cityFrom
        binding.timeTo.text = datetimeTo.second
        binding.timeFrom.text = datetimeFrom.second
        binding.dateTo.text = datetimeTo.first
        binding.dateFrom.text = datetimeFrom.first

        onClick = state.onClickListener

        currentState = state
    }

    private fun splitTime(datetime: String): Pair<String, String> {
        val splitted = datetime.split(" ")
        return Pair(splitted[0], splitted[1].substring(0..(splitted[1].length-4)))
    }

    data class State(
        val timetableId: Int,
        val timeFrom: String,
        val timeTo: String,
        val cityFrom: String,
        val cityTo: String,
        val onClickListener: OnClickListener? = null
    )
}