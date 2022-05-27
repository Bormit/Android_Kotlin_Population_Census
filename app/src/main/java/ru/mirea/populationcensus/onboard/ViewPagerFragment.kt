package ru.mirea.populationcensus.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import ru.mirea.populationcensus.R
import ru.mirea.populationcensus.onboard.fragment.FirstOnboard
import ru.mirea.populationcensus.onboard.fragment.SecOnboard
import ru.mirea.populationcensus.onboard.fragment.ThirdOnboard


class ViewPagerFragment : Fragment() {
    var count: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstOnboard(),
            SecOnboard(),
            ThirdOnboard()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.findViewById<ViewPager2>(R.id.viewPager).adapter = adapter

        return view
    }
}