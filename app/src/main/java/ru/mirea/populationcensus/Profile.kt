package ru.mirea.populationcensus

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController

class Profile : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val buttLogin = view.findViewById<ConstraintLayout>(R.id.loginProfile)
        buttLogin.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavigation_to_loginFragment)
        }
        val buttReg = view.findViewById<ConstraintLayout>(R.id.regProfile)
        buttReg.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavigation_to_regFragment)
        }

        return view
    }

}