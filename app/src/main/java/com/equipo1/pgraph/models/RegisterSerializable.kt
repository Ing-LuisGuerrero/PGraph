package com.equipo1.pgraph.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.io.Serializable
import java.util.*

class RegisterSerializable : Serializable {
    lateinit var uid: String
    lateinit var pressureSystolic: Integer
    lateinit var pressureDiastolic: Integer
    lateinit var hearthRate: Integer
    lateinit var oxygenSaturation: Integer
    lateinit var notes: String
    lateinit var datetime: Date
}