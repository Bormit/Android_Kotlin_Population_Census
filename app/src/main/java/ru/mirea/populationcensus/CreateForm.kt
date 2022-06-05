package ru.mirea.populationcensus

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat.requestPermissions

import android.content.pm.PackageManager
import android.util.Log
import android.widget.*

import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner


class CreateForm : Fragment() {
    private val dataModel : DataModel by activityViewModels()
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_form, container, false)

        val spinnerSex = view.findViewById<Spinner>(R.id.spinnerSex)
        val spinnerArraySex = resources.getStringArray(R.array.spinnerSex)
        val spinnerFamily = view.findViewById<Spinner>(R.id.spinnerFamily)
        val spinnerArrayFamily = resources.getStringArray(R.array.spinnerFamily)
        val buttonLocation = view.findViewById<ConstraintLayout>(R.id.buttonLocation)
        val createClc = view.findViewById<TextView>(R.id.createClc)

        val permissions = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        buttonLocation.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    locationPermissionCode
                )
            } else {
                (activity as MainActivity?)?.getLocation()

            }

        }
        dataModel.messageForCreateFragment.observe(activity as LifecycleOwner,{
            createClc.text = it
        })

        val arrayAdapterSex =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spinnerArraySex) }
        spinnerSex.adapter = arrayAdapterSex
        spinnerSex.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spinnerArraySex[p2] != "None") {
                    Toast.makeText(context, spinnerArraySex[p2], Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        val arrayAdapterFamily =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spinnerArrayFamily) }
        spinnerFamily.adapter = arrayAdapterFamily
        spinnerFamily.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spinnerArrayFamily[p2] != "None") {
                    Toast.makeText(context, spinnerArrayFamily[p2], Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        return view
    }
}