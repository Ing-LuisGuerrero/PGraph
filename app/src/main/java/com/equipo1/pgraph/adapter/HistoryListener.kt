package com.equipo1.pgraph.adapter

import com.equipo1.pgraph.models.RegisterSerializable

interface HistoryListener {
    fun onHistoryNotesClicked(notes: RegisterSerializable, position: Int)
}