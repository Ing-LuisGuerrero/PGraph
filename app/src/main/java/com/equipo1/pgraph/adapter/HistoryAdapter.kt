package com.equipo1.pgraph.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.equipo1.pgraph.R
import com.equipo1.pgraph.models.RegisterSerializable
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class HistoryAdapter(val historyListener: HistoryListener): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    var listTour = ArrayList<RegisterSerializable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_history, parent, false))

    override fun getItemCount() = listTour.size

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val register = listTour[position] as RegisterSerializable

        holder.tvItemPressure.text = "Presión:  ${register.pressureSystolic}/${register.pressureDiastolic}"
        holder.tvItemHeartRate.text = "Ritmo cardiaco: ${register.hearthRate} bpm"
        holder.tvItemOxygen.text = "Saturacion de oxigeno: ${register.oxygenSaturation}%"

        val simpleDateFormat = SimpleDateFormat("dd/MM")
        val simpleYearFormat = SimpleDateFormat("yyyy")
        val simpleHourFormat = SimpleDateFormat("HH:mm a")

        holder.tvItemYear.text = simpleYearFormat.format(register.datetime)
        holder.tvItemDate.text = simpleDateFormat.format(register.datetime)
        holder.tvItemHour.text = simpleHourFormat.format(register.datetime)

        holder.tvItemNotes.setOnClickListener {
            historyListener.onHistoryNotesClicked(register, position)
        }

    }

    fun updateData(data: List<RegisterSerializable>) {
        listTour.clear()
        listTour.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemHeartRate = itemView.findViewById<TextView>(R.id.tvItemHeartRate)
        val tvItemDate = itemView.findViewById<TextView>(R.id.tvItemDate)
        val tvItemYear = itemView.findViewById<TextView>(R.id.tvItemYear)
        val tvItemHour = itemView.findViewById<TextView>(R.id.tvItemHour)
        val tvItemPressure = itemView.findViewById<TextView>(R.id.tvItemPressure)
        val tvItemOxygen = itemView.findViewById<TextView>(R.id.tvItemOxygen)
        val tvItemNotes = itemView.findViewById<TextView>(R.id.tvItemNotes)
    }

}