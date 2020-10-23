package com.equipo1.pgraph.models

import com.google.firebase.Timestamp

data class Register(val uId: String, val pressureSystolic: Int, val pressureDiastolic: Int, val hearthRate: Int, val oxygenSaturation: Int, val notes: String, val datetime: Timestamp)

