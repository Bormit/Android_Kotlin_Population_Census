package ru.mirea.populationcensus

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat.requestPermissions

import android.content.pm.PackageManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*

import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.mindrot.jbcrypt.BCrypt


class CreateForm : Fragment() {
    private val dataModel : DataModel by activityViewModels()
    private val locationPermissionCode = 2

    private var city: String = ""
    private var sex: String = ""
    private var age: String = ""
    private var family: String = ""

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
        val editAge = view.findViewById<EditText>(R.id.editAge)
        val createForm = view.findViewById<Button>(R.id.createForm)

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
            city = createClc.text.toString()
        })

        editAge.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                try {
                    val check: Int = editAge.text.toString().toInt()
                    if (check in 19..99)
                        age = check.toString()

                } catch (e: Exception){
                    Toast.makeText(context, "Неправильный ввод!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
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
                    sex = spinnerArraySex[p2]
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
                    family = spinnerArrayFamily[p2]
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        createForm.setOnClickListener {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val login = sharedPref?.getString("login", "Пользователь")
            val database = Firebase.database.reference

            val form = Person(
                city, sex, age, family
            )
            database.child("person").child(login.toString()).get().addOnSuccessListener {
                if (it.value == null)
                {
                    database.child("person").child(login.toString())
                    if (city != "")
                    {
                        if (sex != "")
                        {
                            if (age != "")
                            {
                                if (family != "")
                                {
                                    database.child("person").child(login.toString())
                                        .child("age")
                                        .setValue(form.age)

                                    database.child("person").child(login.toString())
                                        .child("city")
                                        .setValue(form.city)

                                    database.child("person").child(login.toString())
                                        .child("sex")
                                        .setValue(form.sex)

                                    database.child("person").child(login.toString())
                                        .child("family")
                                        .setValue(form.family)
                                    
                                    Toast.makeText(context, "Успешно!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else
                                {
                                    Toast.makeText(context, "Выберите семейное положение!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            else
                            {
                                Toast.makeText(context, "Выберите возраст!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        else
                        {
                            Toast.makeText(context, "Выберите пол!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Выберите город!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                else
                {
                    Toast.makeText(context, "Вы уже заполнили анкету!", Toast.LENGTH_SHORT)
                        .show()
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Ошибка подключения к базе данных!", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        return view
    }
}