package com.equipo1.pgraph.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo1.pgraph.R
import com.equipo1.pgraph.adapter.HistoryAdapter
import com.equipo1.pgraph.adapter.HistoryListener
import com.equipo1.pgraph.models.Register
import com.equipo1.pgraph.models.RegisterSerializable
import com.equipo1.pgraph.viewmodel.HistoryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment(), HistoryListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        container?.removeAllViews()

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel = ViewModelProvider(this).get(HistoryViewModel()::class.java)
        this.viewModel.refresh()

        historyAdapter = HistoryAdapter(this)

        rvHistory.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        this.viewModel.listHistory.observe(viewLifecycleOwner, Observer<List<RegisterSerializable>> {register ->
            historyAdapter.updateData(register)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            if(it != null) {
                when(it) {
                    true -> rlLoadingHistory.visibility = View.VISIBLE
                    false -> rlLoadingHistory.visibility = View.INVISIBLE
                }
            } else {
                rlLoadingHistory.visibility = View.INVISIBLE
            }
        })
    }


    override fun onHistoryNotesClicked(register: RegisterSerializable, position: Int) {

        val notes = register.notes

        val message = when(notes.isNotEmpty()) {
            true -> notes
            false -> "No se registraron notas en este reporte."
        }

        this.activity?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle("Notas")
                .setMessage(message)
                .setPositiveButton("Okey") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .setBackground(
                    ResourcesCompat.getDrawable(resources,
                        R.drawable.alert_dialog_bg, null))
                .setCancelable(false)
                .show()
        }
    }
}