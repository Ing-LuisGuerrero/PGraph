package com.equipo1.pgraph.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import com.equipo1.pgraph.R
import com.equipo1.pgraph.models.Register
import com.equipo1.pgraph.providers.AuthProvider
import com.equipo1.pgraph.providers.RegistersProvider
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.synthetic.main.fragment_register.*
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    val registersProvider = RegistersProvider()
    val authProvider = AuthProvider()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnRegister.setOnClickListener { submit() }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun submit() {

        val heartRateString = etHeartRate.text.toString().trim()
        val pressureSystolicString = etPressSystolic.text.toString().trim()
        val pressureDiastolicString = etPressDiastolic.text.toString().trim()
        val oxygenSaturationString = etOxygenSaturation.text.toString().trim()
        val notes = etNotes.text.toString().trim()


        if(isFormValid(heartRateString, pressureSystolicString, pressureDiastolicString, oxygenSaturationString)) {
            submitToDatabase(pressureSystolicString.toInt(), pressureDiastolicString.toInt(), heartRateString.toInt(), oxygenSaturationString.toInt(), notes)
        }

    }

    private fun submitToDatabase(systolicPressure: Int, diastolicPressure: Int, heartRate: Int, oxygenSaturation: Int, notes: String) {
        val uId = authProvider.getUid()
        val register = uId?.let { Register(it, systolicPressure, diastolicPressure, heartRate, oxygenSaturation, notes, Timestamp(Date())) }

        if (register != null) {
            rlLoadingRegister.visibility = View.VISIBLE
            registersProvider.insert(register).addOnCompleteListener {documentReference: Task<DocumentReference> ->

                rlLoadingRegister.visibility = View.INVISIBLE

                if(documentReference.isSuccessful) {

                    MaterialAlertDialogBuilder(this.requireContext())
                        .setTitle("Guardado")
                        .setMessage("Informacion guardada con exito")
                        .setNegativeButton("Okey") { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        }
                        .setBackground(
                            ResourcesCompat.getDrawable(resources,
                            R.drawable.alert_dialog_bg, null))
                        .setCancelable(false)
                        .show()

                    resetFragment()

                } else {

                    MaterialAlertDialogBuilder(this.requireContext())
                        .setTitle("Error")
                        .setMessage("Hubo un error en la operación")
                        .setNegativeButton("Okey") { dialogInterface, _ ->
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
    }

    private fun isFormValid(heartRateString: String, pressureSystolicString: String, pressureDiastolicString: String, oxygenSaturationString: String): Boolean {
        var isValid = true

        when {
            pressureSystolicString.isEmpty() -> {
                tilPressSystolic.error = getString(R.string.requiered_field)
                tilPressSystolic.isErrorEnabled = true
                isValid = false
            }
            !isSystolicPressureValid(pressureSystolicString) -> {
                tilPressSystolic.error = "Entrada no valida"
                tilPressSystolic.isErrorEnabled = true
                isValid = false
            }
            else -> {
                tilPressSystolic.isErrorEnabled = false
            }
        }

        when {
            pressureDiastolicString.isEmpty() -> {
                tilPressDiastolic.error = getString(R.string.requiered_field)
                tilPressDiastolic.isErrorEnabled = true
                isValid = false
            }
            !isDiastolicPressureValid(pressureDiastolicString) -> {
                tilPressDiastolic.error = getString(R.string.invalid_value)
                tilPressDiastolic.isErrorEnabled = true
                isValid = false
            }
            pressureDiastolicString.toInt() >= pressureSystolicString.toInt() -> {
                tilPressDiastolic.error = getString(R.string.invalid_pressure)
                tilPressDiastolic.isErrorEnabled = true
                isValid = false
            }
            else -> {
                tilPressDiastolic.isErrorEnabled = false
            }
        }

        when {
            heartRateString.isEmpty() -> {
                tilHeartRate.error = getString(R.string.requiered_field)
                tilHeartRate.isErrorEnabled = true
                isValid = false
            }
            !isHeartRateValid(heartRateString) -> {
                tilHeartRate.error = "Entrada no valida"
                tilHeartRate.isErrorEnabled = true
                isValid = false
            }
            else -> {
                tilHeartRate.isErrorEnabled = false
            }
        }

        when {
            oxygenSaturationString.isEmpty() -> {
                tilOxygenSaturation.error = getString(R.string.requiered_field)
                tilOxygenSaturation.isErrorEnabled = true
                isValid = false
            }
            !isOxygenSaturationValid(oxygenSaturationString) -> {
                tilOxygenSaturation.error = getString(R.string.invalid_oxygen_saturation)
                tilOxygenSaturation.isErrorEnabled = true
                isValid = false
            }
            else -> {
                tilOxygenSaturation.isErrorEnabled = false
            }
        }

        return isValid
    }

    private fun isHeartRateValid(heartRate: String): Boolean {
        val expression = "^500|[1-4]\\d{0,2}|[1-9]\\d|\\d$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(heartRate)

        return matcher.matches()
    }

    private fun isSystolicPressureValid(systolicPressure: String): Boolean {
        val expression = "^300|[1-2]\\d{0,2}|[1-9]\\d|\\d\$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(systolicPressure)

        return matcher.matches()
    }

    private fun isDiastolicPressureValid(diastolicPressure: String): Boolean {
        val expression = "^200|1\\d{0,2}|[1-9]\\d|\\d\$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(diastolicPressure)

        return matcher.matches()
    }

    private fun isOxygenSaturationValid(oxygenSaturation: String): Boolean {
        val expression = "^100|[1-9]\\d|\\d\$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(oxygenSaturation)

        return matcher.matches()
    }

    private fun resetFragment() {
        val fragment: Fragment = RegisterFragment()
        try {
            val transaction: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragmentContent, fragment)
            transaction.commit()
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        }
    }
}