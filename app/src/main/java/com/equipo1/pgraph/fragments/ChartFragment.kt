package com.equipo1.pgraph.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.equipo1.pgraph.R
import com.equipo1.pgraph.providers.AuthProvider
import com.equipo1.pgraph.providers.RegistersProvider
import com.github.aachartmodel.aainfographics.aachartcreator.*
import kotlinx.android.synthetic.main.fragment_chart.*
import java.text.SimpleDateFormat

class ChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getData()

        super.onViewCreated(view, savedInstanceState)
    }


    @SuppressLint("SimpleDateFormat")
    private fun getData() {
        val authProvider = AuthProvider()
        val registersProvider = RegistersProvider()
        val id = authProvider.getUid()
        if (id != null) {
            rlLoadingChart.visibility = View.VISIBLE
            registersProvider.getRegistersByUser(id).orderBy("datetime").get().addOnSuccessListener {documents ->
                var systolic: Array<Any> = arrayOf()
                var diastolic: Array<Any> = arrayOf()
                var heartRate: Array<Any> = arrayOf()
                var oxygenSaturation: Array<Any> = arrayOf()
                var datetime: Array<Any> = arrayOf()

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm a")

                for (document in documents) {
                    systolic += document.getLong("pressureSystolic")!!.toInt()
                    diastolic += document.getLong("pressureDiastolic")!!.toInt()
                    heartRate += document.getLong("hearthRate")!!.toInt()
                    oxygenSaturation += document.getLong("oxygenSaturation")!!.toInt()
                    val date = document.getDate("datetime")
                    if(date != null) {
                        datetime += simpleDateFormat.format(date)
                    }
                }

                plotData(systolic, diastolic, heartRate, oxygenSaturation, datetime)

                rlLoadingChart.visibility = View.INVISIBLE

            }
                .addOnFailureListener {
                    rlLoadingChart.visibility = View.INVISIBLE
                    Log.d("ERROR", it.message.toString())
                }
        }


    }

    //TODO: Plot datetime in xAxis
    private fun plotData(systolic: Array<Any>, diastolic: Array<Any>, heartRate: Array<Any>, oxygenSaturation: Array<Any>, datetime: Array<Any>) {
        val aaChartView = this.activity?.findViewById<AAChartView>(R.id.aa_chart_view)
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Spline)
            .backgroundColor("#fff")
            .dataLabelsEnabled(false)
            .yAxisTitle("")
            .zoomType(AAChartZoomType.XY)
            .gradientColorEnable(true)
            .xAxisTickInterval(1)
            .yAxisAllowDecimals(false)

            .series(arrayOf(
                AASeriesElement()
                    .name("Sistolica")
                    .data(systolic),
                AASeriesElement()
                    .name("Diastolica")
                    .data(diastolic),
                AASeriesElement()
                    .name("Pulso cardiaco")
                    .data(heartRate),
                AASeriesElement()
                    .name("Oxigenacion")
                    .data(oxygenSaturation)
            ))

        aaChartView?.aa_drawChartWithChartModel(aaChartModel)
    }

}