package com.example.aviamirror.presentation.main.ticketscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.aviamirror.R
import com.example.aviamirror.presentation.main.mainscreen.TakeOffView
import com.example.aviamirror.repository.responses.BuyTicketResponse
import com.example.aviamirror.repository.responses.Order
import com.example.aviamirror.utils.auth.AuthSession
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.SimpleDateFormat
import java.util.*

class BuyTicketFragment(
    private val timetableId: Int,
) : BottomSheetDialogFragment() {

    private val buyTicketViewModel: BuyTicketViewModel by viewModels()

    private var moneyAmount: TextView? = null
    private var buy: Button? = null
    private var includeBaggage: SwitchMaterial? = null
    private var fromTo: TextView? = null
    private var ticket: TakeOffView? = null

    private var ticketData: BuyTicketResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_buy_ticket, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moneyAmount = view.findViewById(R.id.moneyAmount)
        buy = view.findViewById(R.id.buy)
        includeBaggage = view.findViewById(R.id.includeBaggage)
        fromTo = view.findViewById(R.id.fromTo)
        ticket = view.findViewById(R.id.ticket)

        setListeners()
        setObservers()
        buyTicketViewModel.getTicketData(timetableId)
    }

    private fun setListeners() {
        buy?.setOnClickListener {
            ticketData?.let {
                val price = if (includeBaggage?.isChecked == true)
                    it.price.toString()
                else
                    "${it.price + it.extraLuggageAmount}"

                val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance().time)

                val order = Order(
                    AuthSession.user.clientId,
                    it.timeFrom,
                    it.timeTo,
                    it.cityCodeFrom,
                    it.cityCodeTo,
                    it.seatNumberId,
                    price,
                    date,
                    it.planeFactoryId,
                    "base",
                    if (includeBaggage?.isChecked == true) 1 else 0,
                    timetableId
                )
                Log.e("BuyTicket", order.toString())
                try {
                    buyTicketViewModel.addOrderToHistory(order)
                    parentFragmentManager.beginTransaction().remove(this).commit()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        includeBaggage?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                moneyAmount?.text = "${(ticketData?.price ?: 0) + (ticketData?.extraLuggageAmount ?: 0)} RUB"
            } else {
                moneyAmount?.text = "${(ticketData?.price ?: 0)} RUB"
            }
        }
    }

    private fun setObservers() {
        buyTicketViewModel.ticketData.observe(viewLifecycleOwner) { result ->
            if (result.isError) {
                showToast(result.exception?.message.toString())
            }

            result.data?.let {
                Log.e("BuyTicket", it.toString())
                ticketData = it

                moneyAmount?.text = "${ticketData?.price} RUB"
                fromTo?.text = "${ticketData?.cityNameFrom}-${ticketData?.cityNameTo}"
                ticket?.render(
                    TakeOffView.State(
                        timetableId,
                        ticketData?.timeFrom ?: "Error",
                        ticketData?.timeTo ?: "Error",
                        "",
                        ""
                    )
                )
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}